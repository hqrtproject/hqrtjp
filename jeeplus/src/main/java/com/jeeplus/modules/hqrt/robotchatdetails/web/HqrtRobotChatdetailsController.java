/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchatdetails.web;

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
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetails;
import com.jeeplus.modules.hqrt.robotchatdetails.service.HqrtRobotChatdetailsService;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 机器人对话内容Controller
 * @author cz
 * @version 2019-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/robotchatdetails/hqrtRobotChatdetails")
public class HqrtRobotChatdetailsController extends BaseController {

	@Autowired
	private HqrtRobotChatdetailsService hqrtRobotChatdetailsService;
	
	@ModelAttribute
	public HqrtRobotChatdetails get(@RequestParam(required=false) String id) {
		HqrtRobotChatdetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtRobotChatdetailsService.get(id);
		}
		if (entity == null){
			entity = new HqrtRobotChatdetails();
		}
		return entity;
	}
	
	/**
	 * 机器人对话内容列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(
			/*@RequestParam(name="timestamp", required=true) Long timestamp,
			@RequestParam(name="sign", required=true) String sign,*/
			HqrtRobotChatdetails hqrtRobotChatdetails, Model model) {
		/*long nowtimestamp = System.currentTimeMillis();
		Long ratio = Math.round(nowtimestamp*0.1/600000*10);
		if (ratio.equals(timestamp)) {
			String salt = ratio + Global.getConfig("secretkey");
			String md5 = DigestUtils.md5Hex(salt);
			if (md5.equals(sign)) {
				model.addAttribute("hqrtRobotChatdetails", hqrtRobotChatdetails);
				return "modules/hqrt/robotchatdetails/hqrtRobotChatdetailsList";
			} else {
				return "modules/hqrt/404";
			}
		} else {
			return "modules/hqrt/404";
		}*/
		model.addAttribute("hqrtRobotChatdetails", hqrtRobotChatdetails);
		return "modules/hqrt/robotchatdetails/hqrtRobotChatdetailsList";
	}
	
		/**
	 * 机器人对话内容列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtRobotChatdetails hqrtRobotChatdetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.queueid AS 'queueid',a.queuename AS 'queuename', a.requestcontext AS 'requestcontext',a.responsecontext AS 'responsecontext', a.responseno AS 'responseno', a.responsenodesc AS 'responsenodesc',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel', a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.satisfyno AS 'satisfyno',a.satisfydesc AS 'satisfydesc',a.MessageDateTime  AS 'messagedatetime',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chatdetails a ";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
        if (StringUtils.isNotBlank(hqrtRobotChatdetails.getQueuename())) {
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtRobotChatdetails.getQueuename().contains("其他")) {
        		sqlcondition += " AND a.queuename in ('" + hqrtRobotChatdetails.getQueuename().replace(",", "','") + "')";
			} else {
				String[] queueselect = hqrtRobotChatdetails.getQueuename().split(",");
				for (String queuename : queueselect) {
					if (queueNameList.contains(queuename)) {
						queueNameList.remove(queuename);
					}
				}
				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
        if (StringUtils.isNotBlank(hqrtRobotChatdetails.getSatisfydesc())) {
        	sqlcondition += " AND a.satisfydesc = ?";
        	paramList.add(hqrtRobotChatdetails.getSatisfydesc());
        }
        if (StringUtils.isNotBlank(hqrtRobotChatdetails.getSessionid())) {
        	sqlcondition += " AND a.sessionid = ?";
        	paramList.add(hqrtRobotChatdetails.getSessionid());
        }
		if (StringUtils.isNotBlank(hqrtRobotChatdetails.getCustomername())) {
        	sqlcondition += " AND (a.customername like ? OR a.customerid like ?)";
        	paramList.add("%" + hqrtRobotChatdetails.getCustomername() + "%");
        	paramList.add("%" + hqrtRobotChatdetails.getCustomername() + "%");
        }
        if (hqrtRobotChatdetails.getParent() != null && hqrtRobotChatdetails.getParent().getStarttime() != null && hqrtRobotChatdetails.getParent().getEndttime() != null) {
        	sqlcondition += " AND a.messagedatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtRobotChatdetails.getParent().getStarttime()));
        	paramList.add(ft.format(hqrtRobotChatdetails.getParent().getEndttime()));
        } else {
        	sqlcondition += " AND a.messagedatetime BETWEEN ? AND ?";
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
        Page<HqrtRobotChatdetails> page = new Page<HqrtRobotChatdetails>(request, response);
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }
        // 该语句仅仅为了查询当前条件下有多少符合条件的数据
        sql += sqlcondition + "ORDER BY a.queuename limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        List<HqrtRobotChatdetails> detailsList = md.queryList(sql, HqrtRobotChatdetails.class, paramList.toArray());
        List<HqrtRobotChatdetails> allDetailslList = md.queryList("select count(1) AS ordernumber from hqrt_robot_chatdetails a" + sqlcondition, HqrtRobotChatdetails.class, paramList.toArray());
		page.setCount(allDetailslList.get(0).getOrdernumber());
		hqrtRobotChatdetails.setPage(page);
		for (int i = 0; i < detailsList.size(); i++) {
			detailsList.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
		}
		page.setList(detailsList);
		return getBootstrapData(page);
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtRobotChatdetails hqrtRobotChatdetails, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "机器人拦截明细日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.queueid AS 'queueid',a.queuename AS 'queuename', a.requestcontext AS 'requestcontext',a.responsecontext AS 'responsecontext', a.responseno AS 'responseno', a.responsenodesc AS 'responsenodesc',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel', a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.satisfyno AS 'satisfyno',a.satisfydesc AS 'satisfydesc',a.MessageDateTime  AS 'messagedatetime',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chatdetails a ";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
            if (StringUtils.isNotBlank(hqrtRobotChatdetails.getQueuename())) {
            	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
        		List<String> queueNameList = new ArrayList<String>();
        		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
        			queueNameList.add(hqrtQueueConfig.getQueuename());
        		}
            	if (!hqrtRobotChatdetails.getQueuename().contains("其他")) {
            		sqlcondition += " AND a.queuename in ('" + hqrtRobotChatdetails.getQueuename().replace(",", "','") + "')";
    			} else {
    				String[] queueselect = hqrtRobotChatdetails.getQueuename().split(",");
    				for (String queuename : queueselect) {
    					if (queueNameList.contains(queuename)) {
    						queueNameList.remove(queuename);
    					}
    				}
    				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
    			}
            }
            if (StringUtils.isNotBlank(hqrtRobotChatdetails.getSatisfydesc())) {
            	sqlcondition += " AND a.satisfydesc = ?";
            	paramList.add(hqrtRobotChatdetails.getSatisfydesc());
            }
            if (StringUtils.isNotBlank(hqrtRobotChatdetails.getSessionid())) {
            	sqlcondition += " AND a.sessionid = ?";
            	paramList.add(hqrtRobotChatdetails.getSessionid());
            }
    		if (StringUtils.isNotBlank(hqrtRobotChatdetails.getCustomername())) {
            	sqlcondition += " AND (a.customername like ? OR a.customerid like ?)";
            	paramList.add("%" + hqrtRobotChatdetails.getCustomername() + "%");
            	paramList.add("%" + hqrtRobotChatdetails.getCustomername() + "%");
            }
            if (hqrtRobotChatdetails.getParent() != null && hqrtRobotChatdetails.getParent().getStarttime() != null && hqrtRobotChatdetails.getParent().getEndttime() != null) {
            	sqlcondition += " AND a.messagedatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtRobotChatdetails.getParent().getStarttime()));
            	paramList.add(ft.format(hqrtRobotChatdetails.getParent().getEndttime()));
            } else {
            	sqlcondition += " AND a.messagedatetime BETWEEN ? AND ?";
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
            // 该语句仅仅为了查询当前条件下有多少符合条件的数据
            sql += sqlcondition + "ORDER BY a.queuename";
            MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
            List<HqrtRobotChatdetails> detailsList = md.queryList(sql, HqrtRobotChatdetails.class, paramList.toArray());
    		for (int i = 0; i < detailsList.size(); i++) {
    			detailsList.get(i).setOrdernumber(i+1);
    		}
            new ExportExcel("机器人拦截明细日志", HqrtRobotChatdetails.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出机器人拦截明细日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

}