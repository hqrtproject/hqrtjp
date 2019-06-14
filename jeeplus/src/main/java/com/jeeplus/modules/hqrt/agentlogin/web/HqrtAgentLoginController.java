/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentlogin.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.agentlogin.entity.HqrtAgentLogin;
import com.jeeplus.modules.hqrt.agentlogin.service.HqrtAgentLoginService;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 坐席登录日志Controller
 * @author cz
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentlogin/hqrtAgentLogin")
public class HqrtAgentLoginController extends BaseController {

	@Autowired
	private HqrtAgentLoginService hqrtAgentLoginService;
	
	@ModelAttribute
	public HqrtAgentLogin get(@RequestParam(required=false) String id) {
		HqrtAgentLogin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentLoginService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentLogin();
		}
		return entity;
	}
	
	/**
	 * 坐席登录日志列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(
			/*@RequestParam(name="timestamp", required=true) Long timestamp,
			@RequestParam(name="sign", required=true) String sign,*/
			HqrtAgentLogin hqrtAgentLogin, Model model) {
		/*long nowtimestamp = System.currentTimeMillis();
		Long ratio = Math.round(nowtimestamp*0.1/600000*10);
		if (ratio.equals(timestamp)) {
			String salt = ratio + Global.getConfig("secretkey");
			String md5 = DigestUtils.md5Hex(salt);
			if (md5.equals(sign)) {
				model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
				return "modules/hqrt/agentlogin/hqrtAgentLoginList";
			} else {
				return "modules/hqrt/404";
			}
		} else {
			return "modules/hqrt/404";
		}*/
		model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
		return "modules/hqrt/agentlogin/hqrtAgentLoginList";
	}
	
		/**
	 * 坐席登录日志列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentLogin hqrtAgentLogin, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<HqrtAgentLogin> page = new Page<>(request, response);
		hqrtAgentLogin.setPage(page);
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen' FROM hqrt_agent_login a LEFT JOIN hqrt_agent_config b ON a.agentid = b.agentid";
		String sqlcondition = "";
		List<Object> paramList = new ArrayList<Object>();
	     if (StringUtils.isNotBlank(hqrtAgentLogin.getExqueuename())) {
	        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
	    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
	    		List<String> queueNameList = new ArrayList<String>();
	    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
	    			queueNameList.add(hqrtQueueConfig.getQueuename());
	    		}
	        	if (!hqrtAgentLogin.getExqueuename().contains("其他")) {
	        		sqlcondition += " AND b.queuename in ('" + hqrtAgentLogin.getExqueuename().replace(",", "','") + "')";
				} else {
					String[] queueselect = hqrtAgentLogin.getExqueuename().split(",");
					for (String queuename : queueselect) {
						if (queueNameList.contains(queuename)) {
							queueNameList.remove(queuename);
						}
					}
					sqlcondition += " AND b.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
				}
	        }
        if (StringUtils.isNotBlank(hqrtAgentLogin.getAgentid())) {
        	sqlcondition += " AND a.agentid in ('" + hqrtAgentLogin.getAgentid().replace(",", "','") + "')";
        }
        if (hqrtAgentLogin.getStarttime() != null && hqrtAgentLogin.getEndtime() != null) {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtAgentLogin.getStarttime()));
        	paramList.add(ft.format(hqrtAgentLogin.getEndtime()));
        } else {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            Date beginOfDate = cal.getTime();
        	paramList.add(ft.format(beginOfDate));
        	Calendar calendar2 = Calendar.getInstance();
        	calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
        	        23, 59, 59);
        	Date endOfDate = calendar2.getTime();
        	paramList.add(ft.format(endOfDate));
        }
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }
        sql += sqlcondition + " limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        List<HqrtAgentLogin> detailsList = md.queryList(sql, HqrtAgentLogin.class, paramList.toArray());
    	List<HqrtAgentLogin> allDetailslList = md.queryList("select count(1) AS ordernumber from hqrt_agent_login a LEFT JOIN hqrt_agent_config b ON a.agentid = b.agentid " + sqlcondition, HqrtAgentLogin.class, paramList.toArray());
		page.setCount(allDetailslList.get(0).getOrdernumber());
        for(int i = 0 ; i < detailsList.size(); i++){
        	detailsList.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
    	}
		page.setList(detailsList);
		return getBootstrapData(page);
		/*Page<HqrtAgentLogin> page = hqrtAgentLoginService.findPage(new Page<HqrtAgentLogin>(request, response), hqrtAgentLogin); 
		return getBootstrapData(page);*/
	}

	/**
	 * 查看，增加，编辑坐席登录日志表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentLogin hqrtAgentLogin, Model model) {
		model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentlogin/hqrtAgentLoginForm";
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentLogin hqrtAgentLogin, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "坐席登录日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
        	String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen' FROM hqrt_agent_login a LEFT JOIN hqrt_agent_config b ON a.agentid = b.agentid";
    		String sqlcondition = "";
    		List<Object> paramList = new ArrayList<Object>();
            if (StringUtils.isNotBlank(hqrtAgentLogin.getExqueuename())) {
            	// List<String> queuenameList = Arrays.asList(hqrtAgentChat.getQueuename().split(","));
            	sqlcondition += " AND b.queuename in ('" + hqrtAgentLogin.getExqueuename().replace(",", "','") + "')";
            	// paramList.add(queuenameList);
            }
            if (hqrtAgentLogin.getStarttime() != null && hqrtAgentLogin.getEndtime() != null) {
            	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtAgentLogin.getStarttime()));
            	paramList.add(ft.format(hqrtAgentLogin.getEndtime()));
            } else {
            	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	Calendar cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                Date beginOfDate = cal.getTime();
            	paramList.add(ft.format(beginOfDate));
            	Calendar calendar2 = Calendar.getInstance();
            	calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
            	        23, 59, 59);
            	Date endOfDate = calendar2.getTime();
            	paramList.add(ft.format(endOfDate));
            }
            if (StringUtils.isNotBlank(sqlcondition)) {
            	sqlcondition = sqlcondition.replaceFirst(" AND", "");
            	sqlcondition  = " where" + sqlcondition;
            }
            sql += sqlcondition ;
            MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
            List<HqrtAgentLogin> detailsList = md.queryList(sql, HqrtAgentLogin.class, paramList.toArray());
            for(int i = 0 ; i < detailsList.size(); i++){
            	detailsList.get(i).setOrdernumber(i+1);
        	}
    		new ExportExcel("坐席登录日志", HqrtAgentLogin.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出坐席登录日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

}