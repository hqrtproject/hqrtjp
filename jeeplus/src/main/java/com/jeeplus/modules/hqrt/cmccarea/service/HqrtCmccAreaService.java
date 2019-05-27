/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.cmccarea.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.hqrt.cmccarea.entity.HqrtCmccArea;
import com.jeeplus.modules.hqrt.cmccarea.mapper.HqrtCmccAreaMapper;

/**
 * 区域管理Service
 * @author duan
 * @version 2019-05-27
 */
@Service
@Transactional(readOnly = true)
public class HqrtCmccAreaService extends TreeService<HqrtCmccAreaMapper, HqrtCmccArea> {

	public HqrtCmccArea get(String id) {
		return super.get(id);
	}
	
	public List<HqrtCmccArea> findList(HqrtCmccArea hqrtCmccArea) {
		if (StringUtils.isNotBlank(hqrtCmccArea.getParentIds())){
			hqrtCmccArea.setParentIds(","+hqrtCmccArea.getParentIds()+",");
		}
		return super.findList(hqrtCmccArea);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtCmccArea hqrtCmccArea) {
		super.save(hqrtCmccArea);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtCmccArea hqrtCmccArea) {
		super.delete(hqrtCmccArea);
	}

	public List<String> findAllProvineList() {
		return mapper.findAllProvineList();
	}

	public List<HqrtCmccArea> findListForCombotree(HqrtCmccArea hqrtCmccArea) {
		// TODO Auto-generated method stub
		return mapper.findListForCombotree(hqrtCmccArea);
	}
	
}