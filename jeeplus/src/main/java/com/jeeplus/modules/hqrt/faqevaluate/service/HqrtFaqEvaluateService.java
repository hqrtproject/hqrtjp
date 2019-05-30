/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqevaluate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.faqevaluate.entity.HqrtFaqEvaluate;
import com.jeeplus.modules.hqrt.faqevaluate.mapper.HqrtFaqEvaluateMapper;

/**
 * 知识评价信息报表Service
 * @author duan
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class HqrtFaqEvaluateService extends CrudService<HqrtFaqEvaluateMapper, HqrtFaqEvaluate> {

	public HqrtFaqEvaluate get(String id) {
		return super.get(id);
	}
	
	public List<HqrtFaqEvaluate> findList(HqrtFaqEvaluate hqrtFaqEvaluate) {
		return super.findList(hqrtFaqEvaluate);
	}
	
	public Page<HqrtFaqEvaluate> findPage(Page<HqrtFaqEvaluate> page, HqrtFaqEvaluate hqrtFaqEvaluate) {
		return super.findPage(page, hqrtFaqEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtFaqEvaluate hqrtFaqEvaluate) {
		super.save(hqrtFaqEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtFaqEvaluate hqrtFaqEvaluate) {
		super.delete(hqrtFaqEvaluate);
	}
	
}