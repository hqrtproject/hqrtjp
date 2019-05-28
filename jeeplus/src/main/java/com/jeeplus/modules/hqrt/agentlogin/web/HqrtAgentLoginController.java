/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentlogin.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
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
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentLogin hqrtAgentLogin, Model model) {
		model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
		return "modules/hqrt/agentlogin/hqrtAgentLoginList";
	}
	
		/**
	 * 坐席登录日志列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentLogin hqrtAgentLogin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtAgentLogin> page = hqrtAgentLoginService.findPage(new Page<HqrtAgentLogin>(request, response), hqrtAgentLogin); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑坐席登录日志表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentLogin hqrtAgentLogin, Model model) {
		model.addAttribute("hqrtAgentLogin", hqrtAgentLogin);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentlogin/hqrtAgentLoginForm";
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
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

}