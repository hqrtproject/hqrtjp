/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.agentconfig.entity.HqrtAgentConfig;
import com.jeeplus.modules.hqrt.agentconfig.mapper.HqrtAgentConfigMapper;

/**
 * 坐席配置Service
 * @author cz
 * @version 2019-05-21
 */
@Service
@Transactional(readOnly = true)
public class HqrtAgentConfigService extends CrudService<HqrtAgentConfigMapper, HqrtAgentConfig> {

	public HqrtAgentConfig get(String id) {
		return super.get(id);
	}
	
	public List<HqrtAgentConfig> findList(HqrtAgentConfig hqrtAgentConfig) {
		return super.findList(hqrtAgentConfig);
	}
	
	public Page<HqrtAgentConfig> findPage(Page<HqrtAgentConfig> page, HqrtAgentConfig hqrtAgentConfig) {
		return super.findPage(page, hqrtAgentConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtAgentConfig hqrtAgentConfig) {
		super.save(hqrtAgentConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtAgentConfig hqrtAgentConfig) {
		super.delete(hqrtAgentConfig);
	}
	
}