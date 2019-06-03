/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqmirror.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.hqrt.faqmirror.entity.HqrtFaqMirror;
import com.jeeplus.modules.hqrt.faqmirror.service.HqrtFaqMirrorService;

/**
 * 知识状态明细报表Controller
 * @author duan
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/faqmirror/hqrtFaqMirror")
public class HqrtFaqMirrorController extends BaseController {

	@Autowired
	private HqrtFaqMirrorService hqrtFaqMirrorService;
	
	@ModelAttribute
	public HqrtFaqMirror get(@RequestParam(required=false) String id) {
		HqrtFaqMirror entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtFaqMirrorService.get(id);
		}
		if (entity == null){
			entity = new HqrtFaqMirror();
		}
		return entity;
	}
	
	/**
	 * 知识状态明细报表列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtFaqMirror hqrtFaqMirror, Model model) {
		model.addAttribute("hqrtFaqMirror", hqrtFaqMirror);
		return "modules/hqrt/faqmirror/hqrtFaqMirrorList";
	}
	
		/**
	 * 知识状态明细报表列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtFaqMirror hqrtFaqMirror, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtFaqMirror> page = hqrtFaqMirrorService.findPage(new Page<HqrtFaqMirror>(request, response), hqrtFaqMirror);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑知识状态明细报表表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtFaqMirror hqrtFaqMirror, Model model) {
		model.addAttribute("hqrtFaqMirror", hqrtFaqMirror);
		model.addAttribute("mode", mode);
		return "modules/hqrt/faqmirror/hqrtFaqMirrorForm";
	}

	/**
	 * 保存知识状态明细报表
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtFaqMirror hqrtFaqMirror, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtFaqMirror);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtFaqMirrorService.save(hqrtFaqMirror);//保存
		j.setSuccess(true);
		j.setMsg("保存知识状态明细报表成功");
		return j;
	}
	
	/**
	 * 删除知识状态明细报表
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtFaqMirror hqrtFaqMirror) {
		AjaxJson j = new AjaxJson();
		hqrtFaqMirrorService.delete(hqrtFaqMirror);
		j.setMsg("删除知识状态明细报表成功");
		return j;
	}
	
	/**
	 * 批量删除知识状态明细报表
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtFaqMirrorService.delete(hqrtFaqMirrorService.get(id));
		}
		j.setMsg("删除知识状态明细报表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtFaqMirror hqrtFaqMirror, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识状态明细报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtFaqMirror> page = hqrtFaqMirrorService.findPage(new Page<HqrtFaqMirror>(request, response, -1), hqrtFaqMirror);
    		new ExportExcel("知识状态明细报表", HqrtFaqMirror.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识状态明细报表记录失败！失败信息："+e.getMessage());
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
			List<HqrtFaqMirror> list = ei.getDataList(HqrtFaqMirror.class);
			for (HqrtFaqMirror hqrtFaqMirror : list){
				try{
					hqrtFaqMirrorService.save(hqrtFaqMirror);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条知识状态明细报表记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条知识状态明细报表记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入知识状态明细报表失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入知识状态明细报表数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识状态明细报表数据导入模板.xlsx";
    		List<HqrtFaqMirror> list = Lists.newArrayList(); 
    		new ExportExcel("知识状态明细报表数据", HqrtFaqMirror.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}