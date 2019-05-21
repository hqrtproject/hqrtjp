/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchatdetails.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetails;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetailsForExport;

/**
 * 客户与坐席会话消息明细MAPPER接口
 * @author cz
 * @version 2019-05-20
 */
@MyBatisMapper
public interface HqrtAgentChatdetailsMapper extends BaseMapper<HqrtAgentChatdetails> {

	List<HqrtAgentChatdetailsForExport> findListForExport(HqrtAgentChatdetails hqrtAgentChatdetails);
	
}