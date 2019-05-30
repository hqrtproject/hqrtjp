/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqview.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.faqview.entity.HqrtFaqView;
import com.jeeplus.modules.hqrt.faqview.mapper.HqrtFaqViewMapper;

/**
 * 知识点击量统计Service
 * @author duan
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class HqrtFaqViewService extends CrudService<HqrtFaqViewMapper, HqrtFaqView> {

	public HqrtFaqView get(String id) {
		return super.get(id);
	}
	
	public List<HqrtFaqView> findList(HqrtFaqView hqrtFaqView) {
		return super.findList(hqrtFaqView);
	}
	
	public Page<HqrtFaqView> findPage(Page<HqrtFaqView> page, HqrtFaqView hqrtFaqView) {
		return super.findPage(page, hqrtFaqView);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtFaqView hqrtFaqView) {
		super.save(hqrtFaqView);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtFaqView hqrtFaqView) {
		super.delete(hqrtFaqView);
	}
	
}