/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentlogin.web;

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
import com.jeeplus.modules.hqrt.agentlogin.entity.HqrtAgentLogin;
import com.jeeplus.modules.hqrt.agentlogin.service.HqrtAgentLoginService;

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
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentLogin hqrtAgentLogin, Model model) {
		model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
		return "modules/hqrt/agentlogin/hqrtAgentLoginList";
	}
	
		/**
	 * 坐席登录日志列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentLogin hqrtAgentLogin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtAgentLogin> page = hqrtAgentLoginService.findPage(new Page<HqrtAgentLogin>(request, response), hqrtAgentLogin); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑坐席登录日志表单页面
	 */
	@RequiresPermissions(value={"hqrt:agentlogin:hqrtAgentLogin:view","hqrt:agentlogin:hqrtAgentLogin:add","hqrt:agentlogin:hqrtAgentLogin:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentLogin hqrtAgentLogin, Model model) {
		model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentlogin/hqrtAgentLoginForm";
	}

	/**
	 * 保存坐席登录日志
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:agentlogin:hqrtAgentLogin:add","hqrt:agentlogin:hqrtAgentLogin:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtAgentLogin hqrtAgentLogin, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtAgentLogin);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtAgentLoginService.save(hqrtAgentLogin);//保存
		j.setSuccess(true);
		j.setMsg("保存坐席登录日志成功");
		return j;
	}
	
	/**
	 * 删除坐席登录日志
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtAgentLogin hqrtAgentLogin) {
		AjaxJson j = new AjaxJson();
		hqrtAgentLoginService.delete(hqrtAgentLogin);
		j.setMsg("删除坐席登录日志成功");
		return j;
	}
	
	/**
	 * 批量删除坐席登录日志
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtAgentLoginService.delete(hqrtAgentLoginService.get(id));
		}
		j.setMsg("删除坐席登录日志成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentLogin hqrtAgentLogin, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "坐席登录日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtAgentLogin> page = hqrtAgentLoginService.findPage(new Page<HqrtAgentLogin>(request, response, -1), hqrtAgentLogin);
    		new ExportExcel("坐席登录日志", HqrtAgentLogin.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出坐席登录日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtAgentLogin> list = ei.getDataList(HqrtAgentLogin.class);
			for (HqrtAgentLogin hqrtAgentLogin : list){
				try{
					hqrtAgentLoginService.save(hqrtAgentLogin);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条坐席登录日志记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条坐席登录日志记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入坐席登录日志失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入坐席登录日志数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:agentlogin:hqrtAgentLogin:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "坐席登录日志数据导入模板.xlsx";
    		List<HqrtAgentLogin> list = Lists.newArrayList(); 
    		new ExportExcel("坐席登录日志数据", HqrtAgentLogin.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}