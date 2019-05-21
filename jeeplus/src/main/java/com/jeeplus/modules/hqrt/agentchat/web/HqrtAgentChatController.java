/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.web;

import java.util.Arrays;
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
import com.jeeplus.modules.hqrt.agentchat.entity.HqrtAgentChat;
import com.jeeplus.modules.hqrt.agentchat.service.HqrtAgentChatService;

/**
 * 客户与坐席会话Controller
 * @author cz
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentchat/hqrtAgentChat")
public class HqrtAgentChatController extends BaseController {

	@Autowired
	private HqrtAgentChatService hqrtAgentChatService;
	
	@ModelAttribute
	public HqrtAgentChat get(@RequestParam(required=false) String id) {
		HqrtAgentChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentChatService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentChat();
		}
		return entity;
	}
	
	/**
	 * 客户与坐席会话列表页面
	 */
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentChat hqrtAgentChat, Model model) {
		model.addAttribute("hqrtAgentChat", hqrtAgentChat);
		return "modules/hqrt/agentchat/hqrtAgentChatList";
	}
	
		/**
	 * 客户与坐席会话列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentChat hqrtAgentChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(hqrtAgentChat.getCustomerprovince())) {
			hqrtAgentChat.setCustomerprovinceList(Arrays.asList(hqrtAgentChat.getCustomerprovince().split(",")));
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getQueuename())) {
        	hqrtAgentChat.setQueuenameList(Arrays.asList(hqrtAgentChat.getQueuename().split(",")));
        }
		Page<HqrtAgentChat> page = hqrtAgentChatService.findPage(new Page<HqrtAgentChat>(request, response), hqrtAgentChat); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户与坐席会话表单页面
	 */
	@RequiresPermissions(value={"hqrt:agentchat:hqrtAgentChat:view","hqrt:agentchat:hqrtAgentChat:add","hqrt:agentchat:hqrtAgentChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentChat hqrtAgentChat, Model model) {
		model.addAttribute("hqrtAgentChat", hqrtAgentChat);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentchat/hqrtAgentChatForm";
	}

	/**
	 * 保存客户与坐席会话
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:agentchat:hqrtAgentChat:add","hqrt:agentchat:hqrtAgentChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtAgentChat hqrtAgentChat, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtAgentChat);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtAgentChatService.save(hqrtAgentChat);//保存
		j.setSuccess(true);
		j.setMsg("保存客户与坐席会话成功");
		return j;
	}
	
	/**
	 * 删除客户与坐席会话
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtAgentChat hqrtAgentChat) {
		AjaxJson j = new AjaxJson();
		hqrtAgentChatService.delete(hqrtAgentChat);
		j.setMsg("删除客户与坐席会话成功");
		return j;
	}
	
	/**
	 * 批量删除客户与坐席会话
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtAgentChatService.delete(hqrtAgentChatService.get(id));
		}
		j.setMsg("删除客户与坐席会话成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentChat hqrtAgentChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户与坐席会话"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtAgentChat> page = hqrtAgentChatService.findPage(new Page<HqrtAgentChat>(request, response, -1), hqrtAgentChat);
    		new ExportExcel("客户与坐席会话", HqrtAgentChat.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户与坐席会话记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtAgentChat> list = ei.getDataList(HqrtAgentChat.class);
			for (HqrtAgentChat hqrtAgentChat : list){
				try{
					hqrtAgentChatService.save(hqrtAgentChat);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户与坐席会话记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条客户与坐席会话记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入客户与坐席会话失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入客户与坐席会话数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentchat:hqrtAgentChat:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户与坐席会话数据导入模板.xlsx";
    		List<HqrtAgentChat> list = Lists.newArrayList(); 
    		new ExportExcel("客户与坐席会话数据", HqrtAgentChat.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}