/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchat.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;

/**
 * 机器人对话MAPPER接口
 * @author cz
 * @version 2019-05-16
 */
@MyBatisMapper
public interface HqrtRobotChatMapper extends BaseMapper<HqrtRobotChat> {

	List<HqrtRobotChat> findListGroupBy(HqrtRobotChat hqrtRobotChat);

	List<HqrtRobotChat> findListByQueueNameAndCustomerProvince(HqrtRobotChat hqrtRobotChat);
	
}