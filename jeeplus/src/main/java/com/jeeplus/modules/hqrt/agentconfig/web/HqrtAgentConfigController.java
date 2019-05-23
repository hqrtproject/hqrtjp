/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentconfig.web;

import java.util.ArrayList;
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

import com.jeeplus.common.utils.StringUtils;
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
		Page<HqrtAgentConfig> page = hqrtAgentConfigService.findPage(new Page<HqrtAgentConfig>(request, response), hqrtAgentConfig); 
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
					mapChild.put("text", config.getAgentname());
					mapListChild.add(mapChild);
					it.remove();
				} else if (map.get("text") == null) {
					map.put("id", id);
					map.put("text", config.getQueuename());
					mapChild.put("id", id*10+idChildBase);
					mapChild.put("text", config.getAgentname());
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
	/*@ResponseBody
	@RequiresPermissions("hqrt:agentconfig:hqrtAgentConfig:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentConfig hqrtAgentConfig, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "坐席配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtAgentConfig> page = hqrtAgentConfigService.findPage(new Page<HqrtAgentConfig>(request, response, -1), hqrtAgentConfig);
    		new ExportExcel("坐席配置", HqrtAgentConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出坐席配置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }*/

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