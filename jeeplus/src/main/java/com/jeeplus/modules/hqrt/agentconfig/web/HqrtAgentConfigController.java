/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentconfig.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.jeeplus.modules.hqrt.agentconfig.entity.HqrtAgentConfig;
import com.jeeplus.modules.hqrt.agentconfig.service.HqrtAgentConfigService;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 坐席配置Controller
 * @author cz
 * @version 2019-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentconfig/hqrtAgentConfig")
public class HqrtAgentConfigController extends BaseController {

	@Autowired
	private HqrtAgentConfigService hqrtAgentConfigService;
	
	@ModelAttribute
	public HqrtAgentConfig get(@RequestParam(required=false) String id) {
		HqrtAgentConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentConfigService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentConfig();
		}
		return entity;
	}
	
	/**
	 * 坐席配置列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentConfig hqrtAgentConfig, Model model) {
		model.addAttribute("hqrtAgentConfig", hqrtAgentConfig);
		return "modules/hqrt/agentconfig/hqrtAgentConfigList";
	}
	
	/**
	 * 坐席配置列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentConfig hqrtAgentConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		String sql = "SELECT a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename',IFNULL(b.TimeLen,0) AS timelenlogin,IFNULL((b.TimeLen-c.TimeLen),0) AS timelenonline,IFNULL(c.TimeLen,0) AS timelendnd,IFNULL(d.TimeLen,0) AS timelenwork,IFNULL((b.TimeLen-c.TimeLen-d.TimeLen),0) AS timelenfree,IFNULL(e.advicecount,0) AS advicecount,IFNULL(e.receivecount,0) AS receivecount,IFNULL(e.invalidcount,0) AS invalidcount,IFNULL(e.chattotaltime,0) AS chattotaltime,IFNULL(e.chatavgtime,0) AS chatavgtime,IFNULL(e.firstresponsetimelenavg,0) AS firstresponsetimelenavg,IFNULL(e.avgresponsetimelen,0) AS avgresponsetimelen,IFNULL(e.qaratio,0) AS qaratio,IFNULL(e.participationcount,0) AS participationcount,FORMAT(IFNULL(e.participationcount,0)/IFNULL(e.advicecount,0),2) AS participationrate,IFNULL(e.avgstarcount,0) AS avgstarcount,IFNULL(e.onestarcount,0) AS onestarcount,IFNULL(e.twostarcount,0) AS twostarcount,IFNULL(e.threestarcount,0) AS threestarcount,IFNULL(e.fourstarcount,0) AS fourstarcount,IFNULL(e.fivestarcount,0) AS fivestarcount FROM hqrt_agent_config a LEFT JOIN (select AgentID,SUM(IFNULL(TimeLen,0)) AS TimeLen from  hqrt_agent_login WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS b ON a.AgentID = b.AgentID LEFT JOIN (select AgentID,SUM(IFNULL(TimeLen,0)) AS TimeLen from  hqrt_agent_dnd WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS c ON a.AgentID = c.AgentID LEFT JOIN (select AgentID,SUM(IFNULL(TimeLen,0)) AS TimeLen from  hqrt_agent_work WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS d ON a.AgentID = d.AgentID LEFT JOIN (select AgentID,COUNT(1) AS advicecount,SUM(IFNULL(TimeLen,0)) AS chattotaltime,FORMAT(AVG(IFNULL(TimeLen,0)),2) AS chatavgtime,FORMAT(AVG(IFNULL(FirstResponseTimeLen,0)),2) AS firstresponsetimelenavg,FORMAT(AVG(IFNULL(AvgResponseTimeLen,0)),2) AS avgresponsetimelen,FORMAT(SUM(IFNULL(CustomerMessageCount,0))/SUM(IFNULL(AgentMessageCount,0)),2) AS qaratio,FORMAT(AVG(IFNULL(EvaluateStar,0)),2) AS avgstarcount,SUM(case when IsValid = '1' then 1 else 0 end) AS receivecount,SUM(case when IsValid = '0' then 1 else 0 end) AS invalidcount,SUM(case when EvaluateStar != '0' then 1 else 0 end) AS participationcount,SUM(case when EvaluateStar = '1' then 1 else 0 end) AS onestarcount,SUM(case when EvaluateStar = '2' then 1 else 0 end) AS twostarcount,SUM(case when EvaluateStar = '3' then 1 else 0 end) AS threestarcount,SUM(case when EvaluateStar = '4' then 1 else 0 end) AS fourstarcount,SUM(case when EvaluateStar = '5' then 1 else 0 end) AS fivestarcount from  hqrt_agent_chat WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS e ON a.AgentID = e.AgentID ";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
        if (StringUtils.isNotBlank(hqrtAgentConfig.getQueuename())) {
        	sqlcondition += " AND a.queuename in ('" + hqrtAgentConfig.getQueuename().replace(",", "','") + "')";
        }
        if (StringUtils.isNotBlank(hqrtAgentConfig.getAgentid())) {
        	sqlcondition += " AND a.agentid in ('" + hqrtAgentConfig.getAgentid().replace(",", "','") + "')";
        }
        if (hqrtAgentConfig.getStarttime() != null && hqrtAgentConfig.getEndtime() != null) {
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
        	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
        	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
        	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
        	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
        	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
        	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
        	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
        } else {
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            Date beginOfDate = cal.getTime();
        	Calendar calendar2 = Calendar.getInstance();
        	calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
        	        23, 59, 59);
        	Date endOfDate = calendar2.getTime();
        	paramList.add(ft.format(beginOfDate));
        	paramList.add(ft.format(endOfDate));
        	paramList.add(ft.format(beginOfDate));
        	paramList.add(ft.format(endOfDate));
        	paramList.add(ft.format(beginOfDate));
        	paramList.add(ft.format(endOfDate));
        	paramList.add(ft.format(beginOfDate));
        	paramList.add(ft.format(endOfDate));
        }
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }
        Page<HqrtAgentConfig> page = new Page<>(request, response);
        String selectcountsql = sql + sqlcondition;
        sql += sqlcondition + " limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get("company");
        List<HqrtAgentConfig> detailsList = md.queryList(sql, HqrtAgentConfig.class, paramList.toArray());
        List<HqrtAgentConfig> allDetailslList = md.queryList(selectcountsql, HqrtAgentConfig.class, paramList.toArray());
        page.setList(detailsList);
		page.setCount(allDetailslList.size());
		return getBootstrapData(page);
	}
	
	/**
	 * 坐席配置列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "combotreedata")
	public List<Map<String, Object>> combotreedata(HqrtAgentConfig hqrtAgentConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename' FROM hqrt_agent_config a";
		MultiDBUtils md = MultiDBUtils.get("company");
		List<HqrtAgentConfig> queryList = md.queryList(sql, HqrtAgentConfig.class);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		int id = 1;
		while(queryList.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> mapListChild = new ArrayList<Map<String, Object>>();
			int idChildBase = 1;
			Iterator<HqrtAgentConfig> it = queryList.iterator();
			while(it.hasNext()){
				HqrtAgentConfig config = it.next();
				Map<String, Object> mapChild = new HashMap<String, Object>();
				if (map.get("text") != null && map.get("text").equals(config.getQueuename())) {
					mapChild.put("id", id*10+idChildBase);
					mapChild.put("text", config.getAgentname() + "(" + config.getAgentid() + ")");
					mapListChild.add(mapChild);
					it.remove();
				} else if (map.get("text") == null) {
					map.put("id", id);
					map.put("text", config.getQueuename());
					mapChild.put("id", id*10+idChildBase);
					mapChild.put("text", config.getAgentname() + "(" + config.getAgentid() + ")");
					mapListChild.add(mapChild);
					it.remove();
				}
				idChildBase++;
			}
			map.put("children", mapListChild);
			id++;
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 查看，增加，编辑坐席配置表单页面
	 */
	/*@RequiresPermissions(value={"hqrt:agentconfig:hqrtAgentConfig:view","hqrt:agentconfig:hqrtAgentConfig:add","hqrt:agentconfig:hqrtAgentConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentConfig hqrtAgentConfig, Model model) {
		model.addAttribute("hqrtAgentConfig", hqrtAgentConfig);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentconfig/hqrtAgentConfigForm";
	}*/

	/**
	 * 保存坐席配置
	 */
	/*@ResponseBody
	@RequiresPermissions(value={"hqrt:agentconfig:hqrtAgentConfig:add","hqrt:agentconfig:hqrtAgentConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtAgentConfig hqrtAgentConfig, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		*//**
		 * 后台hibernate-validation插件校验
		 *//*
		String errMsg = beanValidator(hqrtAgentConfig);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtAgentConfigService.save(hqrtAgentConfig);//保存
		j.setSuccess(true);
		j.setMsg("保存坐席配置成功");
		return j;
	}*/
	
	/**
	 * 删除坐席配置
	 */
	/*@ResponseBody
	@RequiresPermissions("hqrt:agentconfig:hqrtAgentConfig:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtAgentConfig hqrtAgentConfig) {
		AjaxJson j = new AjaxJson();
		hqrtAgentConfigService.delete(hqrtAgentConfig);
		j.setMsg("删除坐席配置成功");
		return j;
	}*/
	
	/**
	 * 批量删除坐席配置
	 */
	/*@ResponseBody
	@RequiresPermissions("hqrt:agentconfig:hqrtAgentConfig:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtAgentConfigService.delete(hqrtAgentConfigService.get(id));
		}
		j.setMsg("删除坐席配置成功");
		return j;
	}*/
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentConfig hqrtAgentConfig, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "在线客服坐席统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            String sql = "SELECT a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename',IFNULL(b.TimeLen,0) AS timelenlogin,IFNULL((b.TimeLen-c.TimeLen),0) AS timelenonline,IFNULL(c.TimeLen,0) AS timelendnd,IFNULL(d.TimeLen,0) AS timelenwork,IFNULL((b.TimeLen-c.TimeLen-d.TimeLen),0) AS timelenfree,IFNULL(e.advicecount,0) AS advicecount,IFNULL(e.receivecount,0) AS receivecount,IFNULL(e.invalidcount,0) AS invalidcount,IFNULL(e.chattotaltime,0) AS chattotaltime,IFNULL(e.chatavgtime,0) AS chatavgtime,IFNULL(e.firstresponsetimelenavg,0) AS firstresponsetimelenavg,IFNULL(e.avgresponsetimelen,0) AS avgresponsetimelen,IFNULL(e.qaratio,0) AS qaratio,IFNULL(e.participationcount,0) AS participationcount,FORMAT(IFNULL(e.participationcount,0)/IFNULL(e.advicecount,0),2) AS participationrate,IFNULL(e.avgstarcount,0) AS avgstarcount,IFNULL(e.onestarcount,0) AS onestarcount,IFNULL(e.twostarcount,0) AS twostarcount,IFNULL(e.threestarcount,0) AS threestarcount,IFNULL(e.fourstarcount,0) AS fourstarcount,IFNULL(e.fivestarcount,0) AS fivestarcount FROM hqrt_agent_config a LEFT JOIN (select AgentID,SUM(IFNULL(TimeLen,0)) AS TimeLen from  hqrt_agent_login WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS b ON a.AgentID = b.AgentID LEFT JOIN (select AgentID,SUM(IFNULL(TimeLen,0)) AS TimeLen from  hqrt_agent_dnd WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS c ON a.AgentID = c.AgentID LEFT JOIN (select AgentID,SUM(IFNULL(TimeLen,0)) AS TimeLen from  hqrt_agent_work WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS d ON a.AgentID = d.AgentID LEFT JOIN (select AgentID,COUNT(1) AS advicecount,SUM(IFNULL(TimeLen,0)) AS chattotaltime,FORMAT(AVG(IFNULL(TimeLen,0)),2) AS chatavgtime,FORMAT(AVG(IFNULL(FirstResponseTimeLen,0)),2) AS firstresponsetimelenavg,FORMAT(AVG(IFNULL(AvgResponseTimeLen,0)),2) AS avgresponsetimelen,FORMAT(SUM(IFNULL(CustomerMessageCount,0))/SUM(IFNULL(AgentMessageCount,0)),2) AS qaratio,FORMAT(AVG(IFNULL(EvaluateStar,0)),2) AS avgstarcount,SUM(case when IsValid = '1' then 1 else 0 end) AS receivecount,SUM(case when IsValid = '0' then 1 else 0 end) AS invalidcount,SUM(case when EvaluateStar != '0' then 1 else 0 end) AS participationcount,SUM(case when EvaluateStar = '1' then 1 else 0 end) AS onestarcount,SUM(case when EvaluateStar = '2' then 1 else 0 end) AS twostarcount,SUM(case when EvaluateStar = '3' then 1 else 0 end) AS threestarcount,SUM(case when EvaluateStar = '4' then 1 else 0 end) AS fourstarcount,SUM(case when EvaluateStar = '5' then 1 else 0 end) AS fivestarcount from  hqrt_agent_chat WHERE StartDateTime BETWEEN ? AND ? GROUP BY AgentID) AS e ON a.AgentID = e.AgentID ";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
            if (StringUtils.isNotBlank(hqrtAgentConfig.getQueuename())) {
            	sqlcondition += " AND a.queuename in ('" + hqrtAgentConfig.getQueuename().replace(",", "','") + "')";
            }
            if (StringUtils.isNotBlank(hqrtAgentConfig.getAgentid())) {
            	sqlcondition += " AND a.agentid in ('" + hqrtAgentConfig.getAgentid().replace(",", "','") + "')";
            }
            if (hqrtAgentConfig.getStarttime() != null && hqrtAgentConfig.getEndtime() != null) {
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
            	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
            	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
            	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
            	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
            	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
            	paramList.add(ft.format(hqrtAgentConfig.getStarttime()));
            	paramList.add(ft.format(hqrtAgentConfig.getEndtime()));
            } else {
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	Calendar cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                Date beginOfDate = cal.getTime();
            	Calendar calendar2 = Calendar.getInstance();
            	calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
            	        23, 59, 59);
            	Date endOfDate = calendar2.getTime();
            	paramList.add(ft.format(beginOfDate));
            	paramList.add(ft.format(endOfDate));
            	paramList.add(ft.format(beginOfDate));
            	paramList.add(ft.format(endOfDate));
            	paramList.add(ft.format(beginOfDate));
            	paramList.add(ft.format(endOfDate));
            	paramList.add(ft.format(beginOfDate));
            	paramList.add(ft.format(endOfDate));
            }
            if (StringUtils.isNotBlank(sqlcondition)) {
            	sqlcondition = sqlcondition.replaceFirst(" AND", "");
            	sqlcondition  = " where" + sqlcondition;
            }
            sql += sqlcondition;
            MultiDBUtils md = MultiDBUtils.get("company");
            List<HqrtAgentConfig> detailsList = md.queryList(sql, HqrtAgentConfig.class, paramList.toArray());
    		new ExportExcel("在线客服坐席统计", HqrtAgentConfig.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出在线客服坐席统计记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	/*@ResponseBody
	@RequiresPermissions("hqrt:agentconfig:hqrtAgentConfig:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtAgentConfig> list = ei.getDataList(HqrtAgentConfig.class);
			for (HqrtAgentConfig hqrtAgentConfig : list){
				try{
					hqrtAgentConfigService.save(hqrtAgentConfig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条坐席配置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条坐席配置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入坐席配置失败！失败信息："+e.getMessage());
		}
		return j;
    }*/
	
	/**
	 * 下载导入坐席配置数据模板
	 */
	/*@ResponseBody
	@RequiresPermissions("hqrt:agentconfig:hqrtAgentConfig:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "坐席配置数据导入模板.xlsx";
    		List<HqrtAgentConfig> list = Lists.newArrayList(); 
    		new ExportExcel("坐席配置数据", HqrtAgentConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }*/

}