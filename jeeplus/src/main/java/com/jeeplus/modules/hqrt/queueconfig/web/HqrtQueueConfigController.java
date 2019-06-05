/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queueconfig.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.agentconfig.entity.HqrtAgentConfig;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.hqrt.queueconfig.service.HqrtQueueConfigService;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 配置文件Controller
 * @author cz
 * @version 2019-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/queueconfig/hqrtQueueConfig")
public class HqrtQueueConfigController extends BaseController {

	@Autowired
	private HqrtQueueConfigService hqrtQueueConfigService;
	
	@ModelAttribute
	public HqrtQueueConfig get(@RequestParam(required=false) String id) {
		HqrtQueueConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtQueueConfigService.get(id);
		}
		if (entity == null){
			entity = new HqrtQueueConfig();
		}
		return entity;
	}
	
	/**
	 * 配置文件列表页面
	 */
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtQueueConfig hqrtQueueConfig, Model model) {
		model.addAttribute("hqrtQueueConfig", hqrtQueueConfig);
		return "modules/hqrt/queueconfig/hqrtQueueConfigList";
	}
	
		/**
	 * 配置文件列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtQueueConfig hqrtQueueConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtQueueConfig> page = hqrtQueueConfigService.findPage(new Page<HqrtQueueConfig>(request, response), hqrtQueueConfig); 
		return getBootstrapData(page);
	}
	
	/**
	 * 坐席配置列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "combotreedata")
	public List<Map<String, Object>> combotreedata(HqrtQueueConfig hqrtQueueConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename' FROM hqrt_queue_config a";
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
		List<HqrtQueueConfig> queryList = md.queryList(sql, HqrtQueueConfig.class);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		int id = 1;
		Iterator<HqrtQueueConfig> it = queryList.iterator();
		while(it.hasNext()){
			Map<String, Object> map = new HashMap<String, Object>();
			HqrtQueueConfig config = it.next();
			map.put("id", id);
			map.put("text", config.getQueuename());
			it.remove();
			id++;
			mapList.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("text", "其他");
		mapList.add(map);
		return mapList;
	}
	
	/**
	 * 坐席配置列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "cascadeAgent")
	public List<Map<String, Object>> cascadeAgent(HqrtQueueConfig hqrtQueueConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename' FROM hqrt_agent_config a";
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
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
				if (!hqrtQueueConfig.getQueuename().contains(config.getQueuename())) {
					it.remove();
					continue;
				}
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
	 * 查看，增加，编辑配置文件表单页面
	 */
	@RequiresPermissions(value={"hqrt:queueconfig:hqrtQueueConfig:view","hqrt:queueconfig:hqrtQueueConfig:add","hqrt:queueconfig:hqrtQueueConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtQueueConfig hqrtQueueConfig, Model model) {
		model.addAttribute("hqrtQueueConfig", hqrtQueueConfig);
		model.addAttribute("mode", mode);
		return "modules/hqrt/queueconfig/hqrtQueueConfigForm";
	}

	/**
	 * 保存配置文件
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:queueconfig:hqrtQueueConfig:add","hqrt:queueconfig:hqrtQueueConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtQueueConfig hqrtQueueConfig, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtQueueConfig);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtQueueConfigService.save(hqrtQueueConfig);//保存
		j.setSuccess(true);
		j.setMsg("保存配置文件成功");
		return j;
	}
	
	/**
	 * 删除配置文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtQueueConfig hqrtQueueConfig) {
		AjaxJson j = new AjaxJson();
		hqrtQueueConfigService.delete(hqrtQueueConfig);
		j.setMsg("删除配置文件成功");
		return j;
	}
	
	/**
	 * 批量删除配置文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtQueueConfigService.delete(hqrtQueueConfigService.get(id));
		}
		j.setMsg("删除配置文件成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtQueueConfig hqrtQueueConfig, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "配置文件"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtQueueConfig> page = hqrtQueueConfigService.findPage(new Page<HqrtQueueConfig>(request, response, -1), hqrtQueueConfig);
    		new ExportExcel("配置文件", HqrtQueueConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出配置文件记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtQueueConfig> list = ei.getDataList(HqrtQueueConfig.class);
			for (HqrtQueueConfig hqrtQueueConfig : list){
				try{
					hqrtQueueConfigService.save(hqrtQueueConfig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条配置文件记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条配置文件记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入配置文件失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入配置文件数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queueconfig:hqrtQueueConfig:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "配置文件数据导入模板.xlsx";
    		List<HqrtQueueConfig> list = Lists.newArrayList(); 
    		new ExportExcel("配置文件数据", HqrtQueueConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}