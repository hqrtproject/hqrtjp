/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqevaluate.web;

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
import com.jeeplus.modules.hqrt.faqevaluate.entity.HqrtFaqEvaluate;
import com.jeeplus.modules.hqrt.faqevaluate.service.HqrtFaqEvaluateService;

/**
 * 知识评价信息报表Controller
 * @author duan
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/faqevaluate/hqrtFaqEvaluate")
public class HqrtFaqEvaluateController extends BaseController {

	@Autowired
	private HqrtFaqEvaluateService hqrtFaqEvaluateService;
	
	@ModelAttribute
	public HqrtFaqEvaluate get(@RequestParam(required=false) String id) {
		HqrtFaqEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtFaqEvaluateService.get(id);
		}
		if (entity == null){
			entity = new HqrtFaqEvaluate();
		}
		return entity;
	}
	
	/**
	 * 知识评价信息报表列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtFaqEvaluate hqrtFaqEvaluate, Model model) {
		model.addAttribute("hqrtFaqEvaluate", hqrtFaqEvaluate);
		return "modules/hqrt/faqevaluate/hqrtFaqEvaluateList";
	}
	
		/**
	 * 知识评价信息报表列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtFaqEvaluate hqrtFaqEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtFaqEvaluate> page = hqrtFaqEvaluateService.findPage(new Page<HqrtFaqEvaluate>(request, response), hqrtFaqEvaluate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑知识评价信息报表表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtFaqEvaluate hqrtFaqEvaluate, Model model) {
		model.addAttribute("hqrtFaqEvaluate", hqrtFaqEvaluate);
		model.addAttribute("mode", mode);
		return "modules/hqrt/faqevaluate/hqrtFaqEvaluateForm";
	}

	/**
	 * 保存知识评价信息报表
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtFaqEvaluate hqrtFaqEvaluate, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtFaqEvaluate);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtFaqEvaluateService.save(hqrtFaqEvaluate);//保存
		j.setSuccess(true);
		j.setMsg("保存知识评价信息报表成功");
		return j;
	}
	
	/**
	 * 删除知识评价信息报表
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtFaqEvaluate hqrtFaqEvaluate) {
		AjaxJson j = new AjaxJson();
		hqrtFaqEvaluateService.delete(hqrtFaqEvaluate);
		j.setMsg("删除知识评价信息报表成功");
		return j;
	}
	
	/**
	 * 批量删除知识评价信息报表
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtFaqEvaluateService.delete(hqrtFaqEvaluateService.get(id));
		}
		j.setMsg("删除知识评价信息报表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtFaqEvaluate hqrtFaqEvaluate, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识评价信息报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtFaqEvaluate> page = hqrtFaqEvaluateService.findPage(new Page<HqrtFaqEvaluate>(request, response, -1), hqrtFaqEvaluate);
    		new ExportExcel("知识评价信息报表", HqrtFaqEvaluate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识评价信息报表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtFaqEvaluate> list = ei.getDataList(HqrtFaqEvaluate.class);
			for (HqrtFaqEvaluate hqrtFaqEvaluate : list){
				try{
					hqrtFaqEvaluateService.save(hqrtFaqEvaluate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条知识评价信息报表记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条知识评价信息报表记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入知识评价信息报表失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入知识评价信息报表数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识评价信息报表数据导入模板.xlsx";
    		List<HqrtFaqEvaluate> list = Lists.newArrayList(); 
    		new ExportExcel("知识评价信息报表数据", HqrtFaqEvaluate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}