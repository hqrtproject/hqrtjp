/**
hqrtQueueChatdetail * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChatdetail;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;

/**
 * 客户排队日志MAPPER接口
 * @author duan
 * @version 2019-05-20
 */
@MyBatisMapper
public interface HqrtQueueChatMapper extends BaseMapper<HqrtQueueChat> {
		
	List<HqrtQueueChat> findListGroupBy(HqrtQueueChat hqrtQueueChat);
	List<HqrtQueueChat> findListByQueueName(HqrtQueueChat hqrtQueueChat);
	HqrtQueueChatdetail getQueueChatdetail(String id);
	List<HqrtQueueChatdetail> findDetailList(HqrtQueueChatdetail hqrtQueueChatdetail);
	
}