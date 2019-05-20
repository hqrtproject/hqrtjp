/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.agentchat.entity.HqrtAgentChat;
import com.jeeplus.modules.hqrt.agentchat.mapper.HqrtAgentChatMapper;

/**
 * 客户与坐席会话Service
 * @author cz
 * @version 2019-05-20
 */
@Service
@Transactional(readOnly = true)
public class HqrtAgentChatService extends CrudService<HqrtAgentChatMapper, HqrtAgentChat> {

	public HqrtAgentChat get(String id) {
		return super.get(id);
	}
	
	public List<HqrtAgentChat> findList(HqrtAgentChat hqrtAgentChat) {
		return super.findList(hqrtAgentChat);
	}
	
	public Page<HqrtAgentChat> findPage(Page<HqrtAgentChat> page, HqrtAgentChat hqrtAgentChat) {
		return super.findPage(page, hqrtAgentChat);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtAgentChat hqrtAgentChat) {
		super.save(hqrtAgentChat);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtAgentChat hqrtAgentChat) {
		super.delete(hqrtAgentChat);
	}
	
}