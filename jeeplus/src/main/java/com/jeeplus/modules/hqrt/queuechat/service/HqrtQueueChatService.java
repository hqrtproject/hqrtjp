/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.mapper.HqrtQueueChatMapper;

/**
 * 客户排队日志Service
 * @author duan
 * @version 2019-05-20
 */
@Service
@Transactional(readOnly = true)
public class HqrtQueueChatService extends CrudService<HqrtQueueChatMapper, HqrtQueueChat> {

	public HqrtQueueChat get(String id) {
		return super.get(id);
	}
	
	public List<HqrtQueueChat> findList(HqrtQueueChat hqrtQueueChat) {
		return super.findList(hqrtQueueChat);
	}
	
	public Page<HqrtQueueChat> findPage(Page<HqrtQueueChat> page, HqrtQueueChat hqrtQueueChat) {
		return super.findPage(page, hqrtQueueChat);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtQueueChat hqrtQueueChat) {
		super.save(hqrtQueueChat);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtQueueChat hqrtQueueChat) {
		super.delete(hqrtQueueChat);
	}
	
}