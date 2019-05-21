/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchatdetails.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetails;
import com.jeeplus.modules.hqrt.agentchatdetails.service.HqrtAgentChatdetailsService;

/**
 * 客户与坐席会话消息明细Controller
 * @author cz
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentchatdetails/hqrtAgentChatdetails")
public class HqrtAgentChatdetailsController extends BaseController {

	@Autowired
	private HqrtAgentChatdetailsService hqrtAgentChatdetailsService;
	
	@ModelAttribute
	public HqrtAgentChatdetails get(@RequestParam(required=false) String id) {
		HqrtAgentChatdetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentChatdetailsService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentChatdetails();
		}
		return entity;
	}
	
	/**
	 * 客户与坐席会话消息明细列表页面
	 */
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentChatdetails hqrtAgentChatdetails, Model model) {
		model.addAttribute("hqrtAgentChatdetails", hqrtAgentChatdetails);
		return "modules/hqrt/agentchatdetails/hqrtAgentChatdetailsList";
	}
	
		/**
	 * 客户与坐席会话消息明细列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentChatdetails hqrtAgentChatdetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtAgentChatdetails> page = hqrtAgentChatdetailsService.findPage(new Page<HqrtAgentChatdetails>(request, response), hqrtAgentChatdetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户与坐席会话消息明细表单页面
	 */
	@RequiresPermissions(value={"hqrt:agentchatdetails:hqrtAgentChatdetails:view","hqrt:agentchatdetails:hqrtAgentChatdetails:add","hqrt:agentchatdetails:hqrtAgentChatdetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentChatdetails hqrtAgentChatdetails, Model model) {
		model.addAttribute("hqrtAgentChatdetails", hqrtAgentChatdetails);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentchatdetails/hqrtAgentChatdetailsForm";
	}

	/**
	 * 保存客户与坐席会话消息明细
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:agentchatdetails:hqrtAgentChatdetails:add","hqrt:agentchatdetails:hqrtAgentChatdetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtAgentChatdetails hqrtAgentChatdetails, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtAgentChatdetails);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtAgentChatdetailsService.save(hqrtAgentChatdetails);//保存
		j.setSuccess(true);
		j.setMsg("保存客户与坐席会话消息明细成功");
		return j;
	}
	
	/**
	 * 删除客户与坐席会话消息明细
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtAgentChatdetails hqrtAgentChatdetails) {
		AjaxJson j = new AjaxJson();
		hqrtAgentChatdetailsService.delete(hqrtAgentChatdetails);
		j.setMsg("删除客户与坐席会话消息明细成功");
		return j;
	}
	
	/**
	 * 批量删除客户与坐席会话消息明细
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtAgentChatdetailsService.delete(hqrtAgentChatdetailsService.get(id));
		}
		j.setMsg("删除客户与坐席会话消息明细成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentChatdetails hqrtAgentChatdetails, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户与坐席会话消息明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtAgentChatdetails> page = hqrtAgentChatdetailsService.findPage(new Page<HqrtAgentChatdetails>(request, response, -1), hqrtAgentChatdetails);
    		new ExportExcel("客户与坐席会话消息明细", HqrtAgentChatdetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户与坐席会话消息明细记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtAgentChatdetails> list = ei.getDataList(HqrtAgentChatdetails.class);
			for (HqrtAgentChatdetails hqrtAgentChatdetails : list){
				try{
					hqrtAgentChatdetailsService.save(hqrtAgentChatdetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户与坐席会话消息明细记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条客户与坐席会话消息明细记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入客户与坐席会话消息明细失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入客户与坐席会话消息明细数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchatdetails:hqrtAgentChatdetails:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户与坐席会话消息明细数据导入模板.xlsx";
    		List<HqrtAgentChatdetails> list = Lists.newArrayList(); 
    		new ExportExcel("客户与坐席会话消息明细数据", HqrtAgentChatdetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}