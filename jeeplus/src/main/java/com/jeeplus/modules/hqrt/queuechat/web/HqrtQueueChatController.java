/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.web;

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
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.service.HqrtQueueChatService;

/**
 * 客户排队日志Controller
 * @author duan
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/queuechat/hqrtQueueChat")
public class HqrtQueueChatController extends BaseController {

	@Autowired
	private HqrtQueueChatService hqrtQueueChatService;
	
	@ModelAttribute
	public HqrtQueueChat get(@RequestParam(required=false) String id) {
		HqrtQueueChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtQueueChatService.get(id);
		}
		if (entity == null){
			entity = new HqrtQueueChat();
		}
		return entity;
	}
	
	/**
	 * 客户排队日志列表页面
	 */
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtQueueChat hqrtQueueChat, Model model) {
		model.addAttribute("hqrtQueueChat", hqrtQueueChat);
		return "modules/hqrt/queuechat/hqrtQueueChatList";
	}
	
		/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtQueueChat> page = hqrtQueueChatService.findPage(new Page<HqrtQueueChat>(request, response), hqrtQueueChat); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户排队日志表单页面
	 */
	@RequiresPermissions(value={"hqrt:queuechat:hqrtQueueChat:view","hqrt:queuechat:hqrtQueueChat:add","hqrt:queuechat:hqrtQueueChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtQueueChat hqrtQueueChat, Model model) {
		model.addAttribute("hqrtQueueChat", hqrtQueueChat);
		model.addAttribute("mode", mode);
		return "modules/hqrt/queuechat/hqrtQueueChatForm";
	}

	/**
	 * 保存客户排队日志
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:queuechat:hqrtQueueChat:add","hqrt:queuechat:hqrtQueueChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtQueueChat hqrtQueueChat, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtQueueChat);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtQueueChatService.save(hqrtQueueChat);//保存
		j.setSuccess(true);
		j.setMsg("保存客户排队日志成功");
		return j;
	}
	
	/**
	 * 删除客户排队日志
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtQueueChat hqrtQueueChat) {
		AjaxJson j = new AjaxJson();
		hqrtQueueChatService.delete(hqrtQueueChat);
		j.setMsg("删除客户排队日志成功");
		return j;
	}
	
	/**
	 * 批量删除客户排队日志
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtQueueChatService.delete(hqrtQueueChatService.get(id));
		}
		j.setMsg("删除客户排队日志成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户排队日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtQueueChat> page = hqrtQueueChatService.findPage(new Page<HqrtQueueChat>(request, response, -1), hqrtQueueChat);
    		new ExportExcel("客户排队日志", HqrtQueueChat.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户排队日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtQueueChat> list = ei.getDataList(HqrtQueueChat.class);
			for (HqrtQueueChat hqrtQueueChat : list){
				try{
					hqrtQueueChatService.save(hqrtQueueChat);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户排队日志记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条客户排队日志记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入客户排队日志失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入客户排队日志数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户排队日志数据导入模板.xlsx";
    		List<HqrtQueueChat> list = Lists.newArrayList(); 
    		new ExportExcel("客户排队日志数据", HqrtQueueChat.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}