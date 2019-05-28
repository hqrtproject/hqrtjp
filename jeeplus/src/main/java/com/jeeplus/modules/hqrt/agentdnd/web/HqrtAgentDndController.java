/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentdnd.web;

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

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.agentdnd.entity.HqrtAgentDnd;
import com.jeeplus.modules.hqrt.agentdnd.service.HqrtAgentDndService;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 坐席置忙日志Controller
 * @author cz
 * @version 2019-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentdnd/hqrtAgentDnd")
public class HqrtAgentDndController extends BaseController {

	@Autowired
	private HqrtAgentDndService hqrtAgentDndService;
	
	@ModelAttribute
	public HqrtAgentDnd get(@RequestParam(required=false) String id) {
		HqrtAgentDnd entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentDndService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentDnd();
		}
		return entity;
	}
	
	/**
	 * 坐席置忙日志列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentDnd hqrtAgentDnd, Model model) {
		model.addAttribute("hqrtAgentDnd", hqrtAgentDnd);
		return "modules/hqrt/agentdnd/hqrtAgentDndList";
	}
	
		/**
	 * 坐席置忙日志列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentDnd hqrtAgentDnd, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtAgentDnd> page = new Page<>(request, response);
		hqrtAgentDnd.setPage(page);
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.dndvalue AS 'dndvalue',a.dndvaluedesc AS 'dndvaluedesc',b.queuename AS 'exqueuename' FROM hqrt_agent_dnd a LEFT JOIN hqrt_agent_config b ON a.agentid = b.agentid";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
        if (StringUtils.isNotBlank(hqrtAgentDnd.getExqueuename())) {
        	// List<String> queuenameList = Arrays.asList(hqrtAgentChat.getQueuename().split(","));
        	sqlcondition += " AND b.queuename in ('" + hqrtAgentDnd.getExqueuename().replace(",", "','") + "')";
        	// paramList.add(queuenameList);
        }
        if (hqrtAgentDnd.getStarttime() != null && hqrtAgentDnd.getEndtime() != null) {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtAgentDnd.getStarttime()));
        	paramList.add(ft.format(hqrtAgentDnd.getEndtime()));
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
        String selectcountsql = sql + sqlcondition;
        sql += sqlcondition + " limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get("company");
        List<HqrtAgentDnd> detailsList = md.queryList(sql, HqrtAgentDnd.class, paramList.toArray());
        List<HqrtAgentDnd> allDetailslList = md.queryList(selectcountsql, HqrtAgentDnd.class, paramList.toArray());
        for(int i = 0 ; i < detailsList.size(); i++){
        	detailsList.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
    	}
		page.setList(detailsList);
		page.setCount(allDetailslList.size());
		return getBootstrapData(page);
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentDnd hqrtAgentDnd, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "坐席置忙日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.dndvalue AS 'dndvalue',a.dndvaluedesc AS 'dndvaluedesc',b.queuename AS 'exqueuename' FROM hqrt_agent_dnd a LEFT JOIN hqrt_agent_config b ON a.agentid = b.agentid";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
            if (StringUtils.isNotBlank(hqrtAgentDnd.getExqueuename())) {
            	sqlcondition += " AND b.queuename in ('" + hqrtAgentDnd.getExqueuename().replace(",", "','") + "')";
            }
            if (hqrtAgentDnd.getStarttime() != null && hqrtAgentDnd.getEndtime() != null) {
            	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtAgentDnd.getStarttime()));
            	paramList.add(ft.format(hqrtAgentDnd.getEndtime()));
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
            sql += sqlcondition;
            MultiDBUtils md = MultiDBUtils.get("company");
            List<HqrtAgentDnd> detailsList = md.queryList(sql, HqrtAgentDnd.class, paramList.toArray());
            for(int i = 0 ; i < detailsList.size(); i++){
            	detailsList.get(i).setOrdernumber(i+1);
        	}
            
    		new ExportExcel("坐席置忙日志", HqrtAgentDnd.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出坐席置忙日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

}