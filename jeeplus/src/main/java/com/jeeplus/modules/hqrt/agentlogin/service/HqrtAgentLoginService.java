/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentlogin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.agentlogin.entity.HqrtAgentLogin;
import com.jeeplus.modules.hqrt.agentlogin.mapper.HqrtAgentLoginMapper;

/**
 * 坐席登录日志Service
 * @author cz
 * @version 2019-05-20
 */
@Service
@Transactional(readOnly = true)
public class HqrtAgentLoginService extends CrudService<HqrtAgentLoginMapper, HqrtAgentLogin> {

	public HqrtAgentLogin get(String id) {
		return super.get(id);
	}
	
	public List<HqrtAgentLogin> findList(HqrtAgentLogin hqrtAgentLogin) {
		return super.findList(hqrtAgentLogin);
	}
	
	public Page<HqrtAgentLogin> findPage(Page<HqrtAgentLogin> page, HqrtAgentLogin hqrtAgentLogin) {
		return super.findPage(page, hqrtAgentLogin);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtAgentLogin hqrtAgentLogin) {
		super.save(hqrtAgentLogin);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtAgentLogin hqrtAgentLogin) {
		super.delete(hqrtAgentLogin);
	}
	
}