/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqquestion.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 知识提问与解答明细Entity
 * @author duan
 * @version 2019-05-29
 */
public class HqrtFaqQuestion extends DataEntity<HqrtFaqQuestion> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String customerid;		// 客户标识
	private String customername;		// 客户姓名
	private String customermobile;		// 客户手机号码
	private String customerprovince;		// 客户所在省
	private String faqid;		// 对应的是知识库中t_faq表主键tuid
	private String faqroot;		// 知识根目录（所处业务系统）
	private String faqmodel;		// 知识子模块
	private String faqserialno;		// 知识序列号
	private String faqtitle;		// 知识标题
	private String faqcreaterid;		// 知识创建人标识
	private String faqcreatername;		// 知识创建人姓名
	private String faqcreatedatetime;		// 知识创建时间
	private String questiontitle;		// 提问标题
	private String questioncontext;		// 提问内容
	private String questiondatetime;		// 提问时间
	private String answeragentid;		// 回答问题坐席标识
	private String answeragentname;		// 回答问题坐席姓名
	private String answercontext;		// 回答内容
	private String answerdatetime;		// 回答时间
	private String isanswered;		// 是否回答(0未回答，1已回答)
	private String answertimelen;		// 回答时长(单位：秒)
	private String sessionid;		// 关联会话ID
	private String originalsessionid;		// 原始SessionID
	private Integer ordernumber; // 序号
	
	private HqrtRobotChat parent ;

	public HqrtFaqQuestion() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtFaqQuestion(String id){
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
	
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	@ExcelField(title="提问人用户名", align=2, sort=9)
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
	
	@ExcelField(title="提问人省份", align=2, sort=10)
	public String getCustomerprovince() {
		return customerprovince;
	}

	public void setCustomerprovince(String customerprovince) {
		this.customerprovince = customerprovince;
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
	
	@ExcelField(title="知识模块", align=2, sort=3)
	public String getFaqmodel() {
		return faqmodel;
	}

	public void setFaqmodel(String faqmodel) {
		this.faqmodel = faqmodel;
	}
	
	@ExcelField(title="知识编号", align=2, sort=4)
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
	
	@ExcelField(title="知识创建人", align=2, sort=6)
	public String getFaqcreatername() {
		return faqcreatername;
	}

	public void setFaqcreatername(String faqcreatername) {
		this.faqcreatername = faqcreatername;
	}
	
	@ExcelField(title="创建时间", align=2, sort=7)
	public String getFaqcreatedatetime() {
		return faqcreatedatetime;
	}

	public void setFaqcreatedatetime(String faqcreatedatetime) {
		this.faqcreatedatetime = faqcreatedatetime;
	}
	
	public String getQuestiontitle() {
		return questiontitle;
	}

	public void setQuestiontitle(String questiontitle) {
		this.questiontitle = questiontitle;
	}
	
	@ExcelField(title="提问内容", align=2, sort=12)
	public String getQuestioncontext() {
		return questioncontext;
	}

	public void setQuestioncontext(String questioncontext) {
		this.questioncontext = questioncontext;
	}
	
	@ExcelField(title="提问时间", align=2, sort=11)
	public String getQuestiondatetime() {
		return questiondatetime;
	}

	public void setQuestiondatetime(String questiondatetime) {
		this.questiondatetime = questiondatetime;
	}
	
	public String getAnsweragentid() {
		return answeragentid;
	}

	public void setAnsweragentid(String answeragentid) {
		this.answeragentid = answeragentid;
	}
	
	public String getAnsweragentname() {
		return answeragentname;
	}

	public void setAnsweragentname(String answeragentname) {
		this.answeragentname = answeragentname;
	}
	
	public String getAnswercontext() {
		return answercontext;
	}

	public void setAnswercontext(String answercontext) {
		this.answercontext = answercontext;
	}
	
	public String getAnswerdatetime() {
		return answerdatetime;
	}

	public void setAnswerdatetime(String answerdatetime) {
		this.answerdatetime = answerdatetime;
	}
	
	@ExcelField(title="是否回答", align=2, sort=8)
	public String getIsanswered() {
		return isanswered;
	}

	public void setIsanswered(String isanswered) {
		this.isanswered = isanswered;
	}
	
	public String getAnswertimelen() {
		return answertimelen;
	}

	public void setAnswertimelen(String answertimelen) {
		this.answertimelen = answertimelen;
	}
	
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	public String getOriginalsessionid() {
		return originalsessionid;
	}

	public void setOriginalsessionid(String originalsessionid) {
		this.originalsessionid = originalsessionid;
	}
	
	@ExcelField(title="序号", align=2, sort=1)
	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}
	public HqrtRobotChat getParent() {
		return parent;
	}

	public void setParent(HqrtRobotChat parent) {
		this.parent = parent;
	}
}