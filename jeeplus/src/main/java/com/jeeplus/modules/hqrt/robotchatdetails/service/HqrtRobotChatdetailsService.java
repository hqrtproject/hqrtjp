/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchatdetails.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetails;
import com.jeeplus.modules.hqrt.robotchatdetails.mapper.HqrtRobotChatdetailsMapper;

/**
 * 机器人对话内容Service
 * @author cz
 * @version 2019-05-16
 */
@Service
@Transactional(readOnly = true)
public class HqrtRobotChatdetailsService extends CrudService<HqrtRobotChatdetailsMapper, HqrtRobotChatdetails> {

	public HqrtRobotChatdetails get(String id) {
		return super.get(id);
	}
	
	public List<HqrtRobotChatdetails> findList(HqrtRobotChatdetails hqrtRobotChatdetails) {
		return super.findList(hqrtRobotChatdetails);
	}
	
	public Page<HqrtRobotChatdetails> findPage(Page<HqrtRobotChatdetails> page, HqrtRobotChatdetails hqrtRobotChatdetails) {
		return super.findPage(page, hqrtRobotChatdetails);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtRobotChatdetails hqrtRobotChatdetails) {
		super.save(hqrtRobotChatdetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtRobotChatdetails hqrtRobotChatdetails) {
		super.delete(hqrtRobotChatdetails);
	}
	
}