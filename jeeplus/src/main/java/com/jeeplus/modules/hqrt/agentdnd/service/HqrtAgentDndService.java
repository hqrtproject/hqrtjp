/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentdnd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.agentdnd.entity.HqrtAgentDnd;
import com.jeeplus.modules.hqrt.agentdnd.mapper.HqrtAgentDndMapper;

/**
 * 坐席置忙日志Service
 * @author cz
 * @version 2019-05-23
 */
@Service
@Transactional(readOnly = true)
public class HqrtAgentDndService extends CrudService<HqrtAgentDndMapper, HqrtAgentDnd> {

	public HqrtAgentDnd get(String id) {
		return super.get(id);
	}
	
	public List<HqrtAgentDnd> findList(HqrtAgentDnd hqrtAgentDnd) {
		return super.findList(hqrtAgentDnd);
	}
	
	public Page<HqrtAgentDnd> findPage(Page<HqrtAgentDnd> page, HqrtAgentDnd hqrtAgentDnd) {
		return super.findPage(page, hqrtAgentDnd);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtAgentDnd hqrtAgentDnd) {
		super.save(hqrtAgentDnd);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtAgentDnd hqrtAgentDnd) {
		super.delete(hqrtAgentDnd);
	}
	
}