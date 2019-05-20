/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.hqrt.agentchat.entity.HqrtAgentChat;

/**
 * 客户与坐席会话MAPPER接口
 * @author cz
 * @version 2019-05-20
 */
@MyBatisMapper
public interface HqrtAgentChatMapper extends BaseMapper<HqrtAgentChat> {
	
}