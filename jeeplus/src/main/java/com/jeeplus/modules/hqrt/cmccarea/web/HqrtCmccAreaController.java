/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.cmccarea.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.cmccarea.entity.HqrtCmccArea;
import com.jeeplus.modules.hqrt.cmccarea.service.HqrtCmccAreaService;

/**
 * 区域管理Controller
 * @author duan
 * @version 2019-05-27
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/cmccarea/hqrtCmccArea")
public class HqrtCmccAreaController extends BaseController {

	@Autowired
	private HqrtCmccAreaService hqrtCmccAreaService;
	
	@ModelAttribute
	public HqrtCmccArea get(@RequestParam(required=false) String id) {
		HqrtCmccArea entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtCmccAreaService.get(id);
		}
		if (entity == null){
			entity = new HqrtCmccArea();
		}
		return entity;
	}
	
	/**
	 * 区域管理列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtCmccArea hqrtCmccArea, @ModelAttribute("parentIds") String parentIds, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(StringUtils.isNotBlank(parentIds)){
			model.addAttribute("parentIds", parentIds);
		}
		model.addAttribute("hqrtCmccArea", hqrtCmccArea);
		return "modules/hqrt/cmccarea/hqrtCmccAreaList";
	}

	/**
	 * 查看，增加，编辑区域管理表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtCmccArea hqrtCmccArea, Model model) {
		if (hqrtCmccArea.getParent()!=null && StringUtils.isNotBlank(hqrtCmccArea.getParent().getId())){
			hqrtCmccArea.setParent(hqrtCmccAreaService.get(hqrtCmccArea.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(hqrtCmccArea.getId())){
				HqrtCmccArea hqrtCmccAreaChild = new HqrtCmccArea();
				hqrtCmccAreaChild.setParent(new HqrtCmccArea(hqrtCmccArea.getParent().getId()));
				List<HqrtCmccArea> list = hqrtCmccAreaService.findList(hqrtCmccArea); 
				if (list.size() > 0){
					hqrtCmccArea.setSort(list.get(list.size()-1).getSort());
					if (hqrtCmccArea.getSort() != null){
						hqrtCmccArea.setSort(hqrtCmccArea.getSort() + 30);
					}
				}
			}
		}
		if (hqrtCmccArea.getSort() == null){
			hqrtCmccArea.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("hqrtCmccArea", hqrtCmccArea);
		return "modules/hqrt/cmccarea/hqrtCmccAreaForm";
	}
	
	/*
	 * 获取区域数据
	 */
	@ResponseBody
	@RequestMapping(value = "combotreedata")
	public List<Map<String, Object>> combotreedata(HqrtCmccArea hqrtCmccArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<HqrtCmccArea> queryList = hqrtCmccAreaService.findListForCombotree(hqrtCmccArea);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		while(queryList.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> mapListChild = new ArrayList<Map<String, Object>>();
			Iterator<HqrtCmccArea> it = queryList.iterator();
			while(it.hasNext()){
				HqrtCmccArea area = it.next();
				Map<String, Object> mapChild = new HashMap<String, Object>();
				if (map.get("text") != null && map.get("id").equals(area.getParentId())) {
					mapChild.put("id", area.getId());
					mapChild.put("text", area.getName());
					mapListChild.add(mapChild);
					it.remove();
				} else if (map.get("text") == null && "0".equals(area.getParentId())) {
					map.put("id", area.getId());
					map.put("text", area.getName());
					it.remove();
				}
			}
			map.put("children", mapListChild);
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 保存区域管理
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtCmccArea hqrtCmccArea, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtCmccArea);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		hqrtCmccAreaService.save(hqrtCmccArea);//保存
		j.setSuccess(true);
		j.put("parentIds", hqrtCmccArea.getParentIds());
		j.setMsg("保存区域管理成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<HqrtCmccArea> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return hqrtCmccAreaService.getChildren(parentId);
	}
	
	/**
	 * 删除区域管理
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtCmccArea hqrtCmccArea) {
		AjaxJson j = new AjaxJson();
		hqrtCmccAreaService.delete(hqrtCmccArea);
		j.setSuccess(true);
		j.setMsg("删除区域管理成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<HqrtCmccArea> list = hqrtCmccAreaService.findList(new HqrtCmccArea());
		for (int i=0; i<list.size(); i++){
			HqrtCmccArea e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}