/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchatdetails.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetails;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetailsForExport;
import com.jeeplus.modules.hqrt.agentchatdetails.mapper.HqrtAgentChatdetailsMapper;

/**
 * 客户与坐席会话消息明细Service
 * @author cz
 * @version 2019-05-20
 */
@Service
@Transactional(readOnly = true)
public class HqrtAgentChatdetailsService extends CrudService<HqrtAgentChatdetailsMapper, HqrtAgentChatdetails> {

	public HqrtAgentChatdetails get(String id) {
		return super.get(id);
	}
	
	public List<HqrtAgentChatdetails> findList(HqrtAgentChatdetails hqrtAgentChatdetails) {
		return super.findList(hqrtAgentChatdetails);
	}
	
	public Page<HqrtAgentChatdetails> findPage(Page<HqrtAgentChatdetails> page, HqrtAgentChatdetails hqrtAgentChatdetails) {
		return super.findPage(page, hqrtAgentChatdetails);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtAgentChatdetails hqrtAgentChatdetails) {
		super.save(hqrtAgentChatdetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtAgentChatdetails hqrtAgentChatdetails) {
		super.delete(hqrtAgentChatdetails);
	}

	public List<HqrtAgentChatdetailsForExport> findListForExport(HqrtAgentChatdetails hqrtAgentChatdetails) {
		return mapper.findListForExport(hqrtAgentChatdetails);
	}
	
}