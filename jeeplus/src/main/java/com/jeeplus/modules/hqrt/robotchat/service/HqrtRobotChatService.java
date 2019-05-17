/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;
import com.jeeplus.modules.hqrt.robotchat.mapper.HqrtRobotChatMapper;

/**
 * 机器人对话Service
 * @author cz
 * @version 2019-05-16
 */
@Service
@Transactional(readOnly = true)
public class HqrtRobotChatService extends CrudService<HqrtRobotChatMapper, HqrtRobotChat> {

	public HqrtRobotChat get(String id) {
		return super.get(id);
	}
	
	public List<HqrtRobotChat> findList(HqrtRobotChat hqrtRobotChat) {
		return super.findList(hqrtRobotChat);
	}
	
	public Page<HqrtRobotChat> findPage(Page<HqrtRobotChat> page, HqrtRobotChat hqrtRobotChat) {
		return super.findPage(page, hqrtRobotChat);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtRobotChat hqrtRobotChat) {
		super.save(hqrtRobotChat);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtRobotChat hqrtRobotChat) {
		super.delete(hqrtRobotChat);
	}
	
	@Transactional(readOnly = false)
	public List<HqrtRobotChat> findListGroupBy(HqrtRobotChat hqrtRobotChat) {
		return mapper.findListGroupBy(hqrtRobotChat);
	}
	
	@Transactional(readOnly = false)
	public List<HqrtRobotChat> findListByQueueNameAndCustomerProvince(HqrtRobotChat hqrtRobotChat) {
		return mapper.findListByQueueNameAndCustomerProvince(hqrtRobotChat);
	}
	
}