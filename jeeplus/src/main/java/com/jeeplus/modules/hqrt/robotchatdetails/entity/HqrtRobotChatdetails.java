/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchatdetails.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;

/**
 * 机器人对话内容Entity
 * @author cz
 * @version 2019-05-16
 */
public class HqrtRobotChatdetails extends DataEntity<HqrtRobotChatdetails> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String sessionid;		// 会话ID
	private String customerid;		// 客户标识
	private String customername;		// 客户姓名
	private String customermobile;		// 客户手机号码
	private String customerprovince;		// 客户所在省
	private String queueid;		// 队列（技能组）编号
	private String queuename;		// 队列（技能组）名称
	private String requestcontext;		// 客户发起咨询的内容
	private String responsecontext;		// 机器人回复的内容
	private Integer responseno;		// 机器人回复编码:100无回复；200回复知识;300回复推荐
	private String responsenodesc;		// 机器人回复编码描述:100无回复；200回复知识;300回复推荐
	private String faqid;		// 对应的是知识库中t_faq表主键tuid
	private String faqroot;		// 知识根目录（所处业务系统）
	private String faqmodel;		// 知识子模块
	private String faqserialno;		// 知识序列号
	private String faqtitle;		// 知识标题
	private String faqcreaterid;		// 知识创建人标识
	private String faqcreatername;		// 知识创建人姓名
	private String faqcreatedatetime;		// 知识创建时间
	private Integer satisfyno;		// 是否解决了客户的问题(0未评价；1未解决；2已解决)
	private String satisfydesc;		// 是否解决了客户的问题描述
	private String messagedatetime;		// 消息时间
	private String originalsessionid;  // 原始SessionID
	private HqrtRobotChat parent ;
	
	public HqrtRobotChatdetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtRobotChatdetails(String id){
		super(id);
	}

	
	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}
	
	@ExcelField(title="用户提问时间", align=2, sort=9)
	public String getRowdatetime() {
		return rowdatetime;
	}

	public void setRowdatetime(String rowdatetime) {
		this.rowdatetime = rowdatetime;
	}
	
	@ExcelField(title="会话ID", align=2, sort=12)
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	@ExcelField(title="用户ID", align=2, sort=7)
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	@ExcelField(title="用户名", align=2, sort=6)
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
	
	@ExcelField(title="用户所属省份", align=2, sort=8)
	public String getCustomerprovince() {
		return customerprovince;
	}

	public void setCustomerprovince(String customerprovince) {
		this.customerprovince = customerprovince;
	}
	
	
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	
	@ExcelField(title="业务系统", align=2, sort=1)
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	
	@ExcelField(title="用户提问内容", align=2, sort=10)
	public String getRequestcontext() {
		return requestcontext;
	}

	public void setRequestcontext(String requestcontext) {
		this.requestcontext = requestcontext;
	}
	
	
	public String getResponsecontext() {
		return responsecontext;
	}

	public void setResponsecontext(String responsecontext) {
		this.responsecontext = responsecontext;
	}
	
	
	public Integer getResponseno() {
		return responseno;
	}

	public void setResponseno(Integer responseno) {
		this.responseno = responseno;
	}
	
	
	public String getResponsenodesc() {
		return responsenodesc;
	}

	public void setResponsenodesc(String responsenodesc) {
		this.responsenodesc = responsenodesc;
	}
	
	
	public String getFaqid() {
		return faqid;
	}

	public void setFaqid(String faqid) {
		this.faqid = faqid;
	}
	
	@ExcelField(title="知识分类", align=2, sort=2)
	public String getFaqroot() {
		return faqroot;
	}

	public void setFaqroot(String faqroot) {
		this.faqroot = faqroot;
	}
	
	@ExcelField(title="子模块", align=2, sort=3)
	public String getFaqmodel() {
		return faqmodel;
	}

	public void setFaqmodel(String faqmodel) {
		this.faqmodel = faqmodel;
	}
	
	@ExcelField(title="知识编码", align=2, sort=4)
	public String getFaqserialno() {
		return faqserialno;
	}

	public void setFaqserialno(String faqserialno) {
		this.faqserialno = faqserialno;
	}
	
	@ExcelField(title="知识标题", align=2, sort=5)
	public String getFaqtitle() {
		return faqtitle;
	}

	public void setFaqtitle(String faqtitle) {
		this.faqtitle = faqtitle;
	}
	
	
	public String getFaqcreaterid() {
		return faqcreaterid;
	}

	public void setFaqcreaterid(String faqcreaterid) {
		this.faqcreaterid = faqcreaterid;
	}
	
	@ExcelField(title="知识创建人", align=2, sort=13)
	public String getFaqcreatername() {
		return faqcreatername;
	}

	public void setFaqcreatername(String faqcreatername) {
		this.faqcreatername = faqcreatername;
	}
	
	@ExcelField(title="知识创建时间", align=2, sort=14)
	public String getFaqcreatedatetime() {
		return faqcreatedatetime;
	}

	public void setFaqcreatedatetime(String faqcreatedatetime) {
		this.faqcreatedatetime = faqcreatedatetime;
	}
	
	
	public Integer getSatisfyno() {
		return satisfyno;
	}

	public void setSatisfyno(Integer satisfyno) {
		this.satisfyno = satisfyno;
	}
	
	@ExcelField(title="是否解决（解决状态）", align=2, sort=11)
	public String getSatisfydesc() {
		return satisfydesc;
	}
	public void setSatisfydesc(String satisfydesc) {
		this.satisfydesc = satisfydesc;
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

	public HqrtRobotChat getParent() {
		return parent;
	}

	public void setParent(HqrtRobotChat parent) {
		this.parent = parent;
	}
	
}