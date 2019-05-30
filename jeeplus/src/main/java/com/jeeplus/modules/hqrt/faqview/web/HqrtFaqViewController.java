/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqview.web;

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
import com.jeeplus.modules.hqrt.faqview.entity.HqrtFaqView;
import com.jeeplus.modules.hqrt.faqview.service.HqrtFaqViewService;

/**
 * 知识点击量统计Controller
 * @author duan
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/faqview/hqrtFaqView")
public class HqrtFaqViewController extends BaseController {

	@Autowired
	private HqrtFaqViewService hqrtFaqViewService;
	
	@ModelAttribute
	public HqrtFaqView get(@RequestParam(required=false) String id) {
		HqrtFaqView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtFaqViewService.get(id);
		}
		if (entity == null){
			entity = new HqrtFaqView();
		}
		return entity;
	}
	
	/**
	 * 知识点击量统计列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtFaqView hqrtFaqView, Model model) {
		model.addAttribute("hqrtFaqView", hqrtFaqView);
		return "modules/hqrt/faqview/hqrtFaqViewList";
	}
	
		/**
	 * 知识点击量统计列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtFaqView hqrtFaqView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HqrtFaqView> page = hqrtFaqViewService.findPage(new Page<HqrtFaqView>(request, response), hqrtFaqView); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑知识点击量统计表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtFaqView hqrtFaqView, Model model) {
		model.addAttribute("hqrtFaqView", hqrtFaqView);
		model.addAttribute("mode", mode);
		return "modules/hqrt/faqview/hqrtFaqViewForm";
	}

	/**
	 * 保存知识点击量统计
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtFaqView hqrtFaqView, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtFaqView);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtFaqViewService.save(hqrtFaqView);//保存
		j.setSuccess(true);
		j.setMsg("保存知识点击量统计成功");
		return j;
	}
	
	/**
	 * 删除知识点击量统计
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtFaqView hqrtFaqView) {
		AjaxJson j = new AjaxJson();
		hqrtFaqViewService.delete(hqrtFaqView);
		j.setMsg("删除知识点击量统计成功");
		return j;
	}
	
	/**
	 * 批量删除知识点击量统计
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtFaqViewService.delete(hqrtFaqViewService.get(id));
		}
		j.setMsg("删除知识点击量统计成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtFaqView hqrtFaqView, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识点击量统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtFaqView> page = hqrtFaqViewService.findPage(new Page<HqrtFaqView>(request, response, -1), hqrtFaqView);
    		new ExportExcel("知识点击量统计", HqrtFaqView.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识点击量统计记录失败！失败信息："+e.getMessage());
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
			List<HqrtFaqView> list = ei.getDataList(HqrtFaqView.class);
			for (HqrtFaqView hqrtFaqView : list){
				try{
					hqrtFaqViewService.save(hqrtFaqView);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条知识点击量统计记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条知识点击量统计记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入知识点击量统计失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入知识点击量统计数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识点击量统计数据导入模板.xlsx";
    		List<HqrtFaqView> list = Lists.newArrayList(); 
    		new ExportExcel("知识点击量统计数据", HqrtFaqView.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}