/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetails;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户与坐席会话Entity
 * @author cz
 * @version 2019-05-20
 */
public class HqrtAgentChat extends DataEntity<HqrtAgentChat> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String sessionid;		// 会话ID
	private String talkindex;		// 人工会话交谈索引
	private String customerid;		// 客户标识
	private String customername;		// 客户姓名
	private String customermobile;		// 客户手机号码
	private String customerprovince;		// 客户所在省
	private List<String> customerprovinceList;		// 选中客户所在省（可能是多个）
	private String agentid;		// 坐席ID编号
	private String agentname;		// 坐席姓名
	private String agentmobile;		// 坐席手机号码
	private String agentprovince;		// 坐席所在省
	private String startdatetime;		// 会话开始时间
	private String enddatetime;		// 会话结束时间
	private String timelen;		// 会话持续时长(单位：秒)
	private String endreasonno;		// 会话结束原因：1客户关闭、2坐席关闭、3系统关闭、4超时结束、5转接其他坐席
	private String endreason;		// 会话结束原因：1客户关闭、2坐席关闭、3系统关闭、4超时结束、5转接其他坐席
	private String queueid;		// 队列（技能组）编号
	private String queuecode;		// 队列编码
	private String queuename;		// 队列（技能组）名称
	private List<String> queuenameList;	// 选中业务系统（可能是多个）
	private String isvalid;		// 是否有效会话（0无效、1有效）
	private String firstresponsetimelen;		// 首次响应时长
	private String avgresponsetimelen;		// 平均响应时长
	private String customermessagecount;		// 客户发送消息数量
	private String agentmessagecount;		// 坐席发送消息数量
	private String evaluatestar;		// 评价星级(0未评价，1-5星)
	private String evaluatetext;		// 评价内容
	private String originalsessionid;		// 原始SessionID
	// 过滤的开始结束时间
	private Date starttime;
	private Date endttime;
	private HqrtAgentChatdetails hqrtAgentChatdetails;
	
	public HqrtAgentChat() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentChat(String id){
		super(id);
	}

	@ExcelField(title="唯一记录标识（主键）", align=2, sort=1)
	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}
	
	@ExcelField(title="记录创建时间", align=2, sort=2)
	public String getRowdatetime() {
		return rowdatetime;
	}

	public void setRowdatetime(String rowdatetime) {
		this.rowdatetime = rowdatetime;
	}
	
	@ExcelField(title="会话ID", align=2, sort=3)
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	@ExcelField(title="人工会话交谈索引", align=2, sort=4)
	public String getTalkindex() {
		return talkindex;
	}

	public void setTalkindex(String talkindex) {
		this.talkindex = talkindex;
	}
	
	@ExcelField(title="客户标识", align=2, sort=5)
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	@ExcelField(title="客户姓名", align=2, sort=6)
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	@ExcelField(title="客户手机号码", align=2, sort=7)
	public String getCustomermobile() {
		return customermobile;
	}

	public void setCustomermobile(String customermobile) {
		this.customermobile = customermobile;
	}
	
	@ExcelField(title="客户所在省", align=2, sort=8)
	public String getCustomerprovince() {
		return customerprovince;
	}

	public void setCustomerprovince(String customerprovince) {
		this.customerprovince = customerprovince;
	}

	public List<String> getCustomerprovinceList() {
		return customerprovinceList;
	}

	public void setCustomerprovinceList(List<String> customerprovinceList) {
		this.customerprovinceList = customerprovinceList;
	}
	
	@ExcelField(title="坐席ID编号", align=2, sort=9)
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	@ExcelField(title="坐席姓名", align=2, sort=10)
	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	
	@ExcelField(title="坐席手机号码", align=2, sort=11)
	public String getAgentmobile() {
		return agentmobile;
	}

	public void setAgentmobile(String agentmobile) {
		this.agentmobile = agentmobile;
	}
	
	@ExcelField(title="坐席所在省", align=2, sort=12)
	public String getAgentprovince() {
		return agentprovince;
	}

	public void setAgentprovince(String agentprovince) {
		this.agentprovince = agentprovince;
	}
	
	@ExcelField(title="会话开始时间", align=2, sort=13)
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@ExcelField(title="会话结束时间", align=2, sort=14)
	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	
	@ExcelField(title="会话持续时长(单位：秒)", align=2, sort=15)
	public String getTimelen() {
		return timelen;
	}

	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	
	@ExcelField(title="会话结束原因：1客户关闭、2坐席关闭、3系统关闭、4超时结束、5转接其他坐席", align=2, sort=16)
	public String getEndreasonno() {
		return endreasonno;
	}

	public void setEndreasonno(String endreasonno) {
		this.endreasonno = endreasonno;
	}
	
	@ExcelField(title="会话结束原因：1客户关闭、2坐席关闭、3系统关闭、4超时结束、5转接其他坐席", align=2, sort=17)
	public String getEndreason() {
		return endreason;
	}

	public void setEndreason(String endreason) {
		this.endreason = endreason;
	}
	
	@ExcelField(title="队列（技能组）编号", align=2, sort=18)
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	
	@ExcelField(title="队列编码", align=2, sort=19)
	public String getQueuecode() {
		return queuecode;
	}

	public void setQueuecode(String queuecode) {
		this.queuecode = queuecode;
	}
	
	@ExcelField(title="队列（技能组）名称", align=2, sort=20)
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}

	public List<String> getQueuenameList() {
		return queuenameList;
	}

	public void setQueuenameList(List<String> queuenameList) {
		this.queuenameList = queuenameList;
	}
	
	@ExcelField(title="是否有效会话（0无效、1有效）", align=2, sort=21)
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@ExcelField(title="首次响应时长", align=2, sort=22)
	public String getFirstresponsetimelen() {
		return firstresponsetimelen;
	}

	public void setFirstresponsetimelen(String firstresponsetimelen) {
		this.firstresponsetimelen = firstresponsetimelen;
	}
	
	@ExcelField(title="平均响应时长", align=2, sort=23)
	public String getAvgresponsetimelen() {
		return avgresponsetimelen;
	}

	public void setAvgresponsetimelen(String avgresponsetimelen) {
		this.avgresponsetimelen = avgresponsetimelen;
	}
	
	@ExcelField(title="客户发送消息数量", align=2, sort=24)
	public String getCustomermessagecount() {
		return customermessagecount;
	}

	public void setCustomermessagecount(String customermessagecount) {
		this.customermessagecount = customermessagecount;
	}
	
	@ExcelField(title="坐席发送消息数量", align=2, sort=25)
	public String getAgentmessagecount() {
		return agentmessagecount;
	}

	public void setAgentmessagecount(String agentmessagecount) {
		this.agentmessagecount = agentmessagecount;
	}
	
	@ExcelField(title="评价星级(0未评价，1-5星)", align=2, sort=26)
	public String getEvaluatestar() {
		return evaluatestar;
	}

	public void setEvaluatestar(String evaluatestar) {
		this.evaluatestar = evaluatestar;
	}
	
	@ExcelField(title="评价内容", align=2, sort=27)
	public String getEvaluatetext() {
		return evaluatetext;
	}

	public void setEvaluatetext(String evaluatetext) {
		this.evaluatetext = evaluatetext;
	}
	
	@ExcelField(title="原始SessionID", align=2, sort=28)
	public String getOriginalsessionid() {
		return originalsessionid;
	}

	public void setOriginalsessionid(String originalsessionid) {
		this.originalsessionid = originalsessionid;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndttime() {
		return endttime;
	}

	public void setEndttime(Date endttime) {
		this.endttime = endttime;
	}

	public HqrtAgentChatdetails getHqrtAgentChatdetails() {
		return hqrtAgentChatdetails;
	}

	public void setHqrtAgentChatdetails(HqrtAgentChatdetails hqrtAgentChatdetails) {
		this.hqrtAgentChatdetails = hqrtAgentChatdetails;
	}
	
}