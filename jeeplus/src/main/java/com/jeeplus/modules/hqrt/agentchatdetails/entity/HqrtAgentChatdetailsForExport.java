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
public class HqrtAgentChatdetailsForExport extends DataEntity<HqrtAgentChatdetailsForExport> {
	
	private static final long serialVersionUID = 1L;
	private String customername;		// 发送消息方：1客户，2坐席，3系统
	private String agentname;		// 发送消息方：1客户，2坐席，3系统
	private String messagecontext;		// 发送消息方：1客户，2坐席，3系统
	private Integer messagesender;		// 发送消息方：1客户，2坐席，3系统
	private String messagedatetime;		// 发送消息方：1客户，2坐席，3系统
	private String namecustomer;		// 发送消息方：1客户，2坐席，3系统
	private String messagedatetimecustomer;		// 消息时间
	private String messagecontextcustomer;		// 消息内容，非文本消息时存的是url地址
	private String nameagent;		// 发送消息方：1客户，2坐席，3系统
	private String messagedatetimeagent;		// 消息时间
	private String messagecontextagent;		// 消息内容，非文本消息时存的是url地址
	
	@ExcelField(title="客户姓名", align=2, sort=0)
	public String getNamecustomer() {
		return namecustomer;
	}

	public void setNamecustomer(String namecustomer) {
		this.namecustomer = namecustomer;
	}

	@ExcelField(title="消息时间", align=2, sort=1)
	public String getMessagedatetimecustomer() {
		return messagedatetimecustomer;
	}

	public void setMessagedatetimecustomer(String messagedatetimecustomer) {
		this.messagedatetimecustomer = messagedatetimecustomer;
	}

	@ExcelField(title="消息内容", align=2, sort=2)
	public String getMessagecontextcustomer() {
		return messagecontextcustomer;
	}

	public void setMessagecontextcustomer(String messagecontextcustomer) {
		this.messagecontextcustomer = messagecontextcustomer;
	}

	@ExcelField(title="坐席名", align=2, sort=3)
	public String getNameagent() {
		return nameagent;
	}

	public void setNameagent(String nameagent) {
		this.nameagent = nameagent;
	}

	@ExcelField(title="消息时间", align=2, sort=4)
	public String getMessagedatetimeagent() {
		return messagedatetimeagent;
	}

	public void setMessagedatetimeagent(String messagedatetimeagent) {
		this.messagedatetimeagent = messagedatetimeagent;
	}

	@ExcelField(title="消息内容", align=2, sort=5)
	public String getMessagecontextagent() {
		return messagecontextagent;
	}

	public void setMessagecontextagent(String messagecontextagent) {
		this.messagecontextagent = messagecontextagent;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getMessagecontext() {
		return messagecontext;
	}

	public void setMessagecontext(String messagecontext) {
		this.messagecontext = messagecontext;
	}

	public Integer getMessagesender() {
		return messagesender;
	}

	public void setMessagesender(Integer messagesender) {
		this.messagesender = messagesender;
	}

	public String getMessagedatetime() {
		return messagedatetime;
	}

	public void setMessagedatetime(String messagedatetime) {
		this.messagedatetime = messagedatetime;
	}
}