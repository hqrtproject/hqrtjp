/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqquestion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.faqquestion.entity.HqrtFaqQuestion;
import com.jeeplus.modules.hqrt.faqquestion.mapper.HqrtFaqQuestionMapper;

/**
 * 知识提问与解答明细Service
 * @author duan
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class HqrtFaqQuestionService extends CrudService<HqrtFaqQuestionMapper, HqrtFaqQuestion> {

	public HqrtFaqQuestion get(String id) {
		return super.get(id);
	}
	
	public List<HqrtFaqQuestion> findList(HqrtFaqQuestion hqrtFaqQuestion) {
		return super.findList(hqrtFaqQuestion);
	}
	
	public Page<HqrtFaqQuestion> findPage(Page<HqrtFaqQuestion> page, HqrtFaqQuestion hqrtFaqQuestion) {
		return super.findPage(page, hqrtFaqQuestion);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtFaqQuestion hqrtFaqQuestion) {
		super.save(hqrtFaqQuestion);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtFaqQuestion hqrtFaqQuestion) {
		super.delete(hqrtFaqQuestion);
	}
	
}