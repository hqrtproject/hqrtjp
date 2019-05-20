/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchatdetails.web;

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
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetails;
import com.jeeplus.modules.hqrt.robotchatdetails.service.HqrtRobotChatdetailsService;
import com.jeeplus.modules.test.one.dialog.entity.Leave1;

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
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtRobotChatdetails hqrtRobotChatdetails, Model model) {
		model.addAttribute("hqrtRobotChatdetails", hqrtRobotChatdetails);
		return "modules/hqrt/robotchatdetails/hqrtRobotChatdetailsList";
	}
	
		/**
	 * 机器人对话内容列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtRobotChatdetails hqrtRobotChatdetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtRobotChatdetails> page = hqrtRobotChatdetailsService.findPage(new Page<HqrtRobotChatdetails>(request, response), hqrtRobotChatdetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑机器人对话内容表单页面
	 */
	@RequiresPermissions(value={"hqrt:robotchatdetails:hqrtRobotChatdetails:view","hqrt:robotchatdetails:hqrtRobotChatdetails:add","hqrt:robotchatdetails:hqrtRobotChatdetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtRobotChatdetails hqrtRobotChatdetails, Model model) {
		model.addAttribute("hqrtRobotChatdetails", hqrtRobotChatdetails);
		model.addAttribute("mode", mode);
		return "modules/hqrt/robotchatdetails/hqrtRobotChatdetailsForm";
	}

	/**
	 * 保存机器人对话内容
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:robotchatdetails:hqrtRobotChatdetails:add","hqrt:robotchatdetails:hqrtRobotChatdetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtRobotChatdetails hqrtRobotChatdetails, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtRobotChatdetails);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtRobotChatdetailsService.save(hqrtRobotChatdetails);//保存
		j.setSuccess(true);
		j.setMsg("保存机器人对话内容成功");
		return j;
	}
	
	/**
	 * 删除机器人对话内容
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtRobotChatdetails hqrtRobotChatdetails) {
		AjaxJson j = new AjaxJson();
		hqrtRobotChatdetailsService.delete(hqrtRobotChatdetails);
		j.setMsg("删除机器人对话内容成功");
		return j;
	}
	
	/**
	 * 批量删除机器人对话内容
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtRobotChatdetailsService.delete(hqrtRobotChatdetailsService.get(id));
		}
		j.setMsg("删除机器人对话内容成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtRobotChatdetails hqrtRobotChatdetails, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "机器人对话内容"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtRobotChatdetails> page = hqrtRobotChatdetailsService.findPage(new Page<HqrtRobotChatdetails>(request, response, -1), hqrtRobotChatdetails);
            new ExportExcel("机器人对话内容", HqrtRobotChatdetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出机器人对话内容记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtRobotChatdetails> list = ei.getDataList(HqrtRobotChatdetails.class);
			for (HqrtRobotChatdetails hqrtRobotChatdetails : list){
				try{
					hqrtRobotChatdetailsService.save(hqrtRobotChatdetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条机器人对话内容记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条机器人对话内容记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入机器人对话内容失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入机器人对话内容数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchatdetails:hqrtRobotChatdetails:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "机器人对话内容数据导入模板.xlsx";
    		List<HqrtRobotChatdetails> list = Lists.newArrayList(); 
    		new ExportExcel("机器人对话内容数据", HqrtRobotChatdetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}