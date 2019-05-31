/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchatdetails.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户与坐席会话消息明细Entity
 * @author cz
 * @version 2019-05-20
 */
public class HqrtAgentChatdetails extends DataEntity<HqrtAgentChatdetails> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String sessionid;		// 会话ID
	private String customerid;		// 客户标识
	private String customername;		// 客户姓名
	private String customermobile;		// 客户手机号码
	private String customerprovince;		// 客户所在省
	private String agentid;		// 坐席ID编号
	private String agentname;		// 坐席姓名
	private String agentmobile;		// 坐席手机号码
	private String agentprovince;		// 坐席所在省
	private String queueid;		// 队列（技能组）编号
	private String queuecode;		// 队列编码
	private String queuename;		// 队列（技能组）名称
	private String messagetype;		// 消息类型：1文本，3图片，4文件
	private String messagecontext;		// 消息内容，非文本消息时存的是url地址
	private String messagesender;		// 发送消息方：1客户，2坐席，3系统
	private String messagedatetime;		// 消息时间
	private String originalsessionid;		// 原始SessionID
	private String talkindex;		// 原始SessionID
	
	public HqrtAgentChatdetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentChatdetails(String id){
		super(id);
	}

	
	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}
	
	
	public String getRowdatetime() {
		return rowdatetime;
	}

	public void setRowdatetime(String rowdatetime) {
		this.rowdatetime = rowdatetime;
	}
	
	
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	
	public String getCustomermobile() {
		return customermobile;
	}

	public void setCustomermobile(String customermobile) {
		this.customermobile = customermobile;
	}
	
	
	public String getCustomerprovince() {
		return customerprovince;
	}

	public void setCustomerprovince(String customerprovince) {
		this.customerprovince = customerprovince;
	}
	
	
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	
	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	
	
	public String getAgentmobile() {
		return agentmobile;
	}

	public void setAgentmobile(String agentmobile) {
		this.agentmobile = agentmobile;
	}
	
	
	public String getAgentprovince() {
		return agentprovince;
	}

	public void setAgentprovince(String agentprovince) {
		this.agentprovince = agentprovince;
	}
	
	
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	
	
	public String getQueuecode() {
		return queuecode;
	}

	public void setQueuecode(String queuecode) {
		this.queuecode = queuecode;
	}
	
	
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	
	
	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
	
	@ExcelField(title="聊天内容", align=2, sort=20)
	public String getMessagecontext() {
		return messagecontext;
	}

	public void setMessagecontext(String messagecontext) {
		this.messagecontext = messagecontext;
	}
	
	
	public String getMessagesender() {
		return messagesender;
	}

	public void setMessagesender(String messagesender) {
		this.messagesender = messagesender;
	}
	

	public String getMessagedatetime() {
		return messagedatetime;
	}

	public void setMessagedatetime(String messagedatetime) {
		this.messagedatetime = messagedatetime;
	}
	
	
	public String getOriginalsessionid() {
		return originalsessionid;
	}

	public void setOriginalsessionid(String originalsessionid) {
		this.originalsessionid = originalsessionid;
	}

	public String getTalkindex() {
		return talkindex;
	}

	public void setTalkindex(String talkindex) {
		this.talkindex = talkindex;
	}
	
}