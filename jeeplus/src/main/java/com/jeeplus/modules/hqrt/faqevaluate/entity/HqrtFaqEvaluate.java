/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqevaluate.entity;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 知识评价信息报表Entity
 * @author duan
 * @version 2019-05-29
 */
public class HqrtFaqEvaluate extends DataEntity<HqrtFaqEvaluate> {
	
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
	private String evaluatestar;		// 评价星级(0未评价，1-5星)
	private String evaluatedatetime;		// 评价时间
	private String sessionid;		// 关联会话ID
	private String originalsessionid;		// 原始SessionID
	private Integer ordernumber; // 序号
	
	private Date starttime; //开始时间
	private Date endttime; // 结束时间
	
	private String evaluatestarmin; //最小值
	private String evaluatestarmax; //最大值
	
	public HqrtFaqEvaluate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtFaqEvaluate(String id){
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
	
	@ExcelField(title="评价人用户名", align=2, sort=9)
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
	
	@ExcelField(title="评价人省份", align=2, sort=10)
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
	
	@ExcelField(title="知识分类（业务系统）", align=2, sort=2)
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
	
	@ExcelField(title="知识创建人", align=2, sort=7)
	public String getFaqcreatername() {
		return faqcreatername;
	}

	public void setFaqcreatername(String faqcreatername) {
		this.faqcreatername = faqcreatername;
	}
	
	@ExcelField(title="创建时间", align=2, sort=8)
	public String getFaqcreatedatetime() {
		return faqcreatedatetime;
	}

	public void setFaqcreatedatetime(String faqcreatedatetime) {
		this.faqcreatedatetime = faqcreatedatetime;
	}
	
	@ExcelField(title="评价分值", align=2, sort=6)
	public String getEvaluatestar() {
		return evaluatestar;
	}

	public void setEvaluatestar(String evaluatestar) {
		this.evaluatestar = evaluatestar;
	}
	
	@ExcelField(title="评价时间", align=2, sort=11)
	public String getEvaluatedatetime() {
		return evaluatedatetime;
	}

	public void setEvaluatedatetime(String evaluatedatetime) {
		this.evaluatedatetime = evaluatedatetime;
	}
	
	@ExcelField(title="关联会话ID", align=2, sort=12)
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
	
	public String getEvaluatestarmin() {
		return evaluatestarmin;
	}

	public void setEvaluatestarmin(String evaluatestarmin) {
		this.evaluatestarmin = evaluatestarmin;
	}

	public String getEvaluatestarmax() {
		return evaluatestarmax;
	}

	public void setEvaluatestarmax(String evaluatestarmax) {
		this.evaluatestarmax = evaluatestarmax;
	}

}