/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqmirror.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.faqmirror.entity.HqrtFaqMirror;
import com.jeeplus.modules.hqrt.faqmirror.mapper.HqrtFaqMirrorMapper;

/**
 * 知识状态明细报表Service
 * @author duan
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class HqrtFaqMirrorService extends CrudService<HqrtFaqMirrorMapper, HqrtFaqMirror> {

	public HqrtFaqMirror get(String id) {
		return super.get(id);
	}
	
	public List<HqrtFaqMirror> findList(HqrtFaqMirror hqrtFaqMirror) {
		return super.findList(hqrtFaqMirror);
	}
	
	public Page<HqrtFaqMirror> findPage(Page<HqrtFaqMirror> page, HqrtFaqMirror hqrtFaqMirror) {
		return super.findPage(page, hqrtFaqMirror);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtFaqMirror hqrtFaqMirror) {
		super.save(hqrtFaqMirror);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtFaqMirror hqrtFaqMirror) {
		super.delete(hqrtFaqMirror);
	}
	
}