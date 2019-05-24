/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentwork.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.agentwork.entity.HqrtAgentWork;
import com.jeeplus.modules.hqrt.agentwork.mapper.HqrtAgentWorkMapper;

/**
 * 坐席接待日志Service
 * @author duan
 * @version 2019-05-24
 */
@Service
@Transactional(readOnly = true)
public class HqrtAgentWorkService extends CrudService<HqrtAgentWorkMapper, HqrtAgentWork> {

	public HqrtAgentWork get(String id) {
		return super.get(id);
	}
	
	public List<HqrtAgentWork> findList(HqrtAgentWork hqrtAgentWork) {
		return super.findList(hqrtAgentWork);
	}
	
	public Page<HqrtAgentWork> findPage(Page<HqrtAgentWork> page, HqrtAgentWork hqrtAgentWork) {
		return super.findPage(page, hqrtAgentWork);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtAgentWork hqrtAgentWork) {
		super.save(hqrtAgentWork);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtAgentWork hqrtAgentWork) {
		super.delete(hqrtAgentWork);
	}
	
}