/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqmirror.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 知识状态明细报表Entity
 * @author duan
 * @version 2019-05-29
 */
public class HqrtFaqMirror extends DataEntity<HqrtFaqMirror> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String faqid;		// 对应的是知识库中t_faq表主键tuid
	private String faqroot;		// 知识根目录（所处业务系统）
	private String faqmodel;		// 知识子模块
	private String faqserialno;		// 知识序列号
	private String faqtitle;		// 知识标题
	private String faqcreaterid;		// 知识创建人标识
	private String faqcreatername;		// 知识创建人姓名
	private String faqcreatedatetime;		// 知识创建时间
	private String faqstatuno;		// 知识状态编号：2待审批，3审批中，4审批通过，5审批退回
	private String faqstatudesc;		// 知识状态编号：2待审批，3审批中，4审批通过，5审批退回
	private String faqapprovorid;		// 审批人标识
	private String faqapprovortime;		// faqapprovortime
	private String faqapprovortimelen;		// 审批时长(单位：秒)
	private Integer ordernumber; // 序号
	
	//统计
	private Integer faqcount; // 知识总数
	private Integer pendingcount; // 待审批数
	private Integer approvalcount; // 审批中数
	private Integer throughcount; // 审批通过数
	private Integer nothroughcount; // 审批未通过数
	
	public HqrtFaqMirror() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtFaqMirror(String id){
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
	
	@ExcelField(title="知识模块", align=2, sort=8)
	public String getFaqmodel() {
		return faqmodel;
	}

	public void setFaqmodel(String faqmodel) {
		this.faqmodel = faqmodel;
	}
	
	@ExcelField(title="知识编号", align=2, sort=9)
	public String getFaqserialno() {
		return faqserialno;
	}

	public void setFaqserialno(String faqserialno) {
		this.faqserialno = faqserialno;
	}
	
	@ExcelField(title="知识标题", align=2, sort=10)
	public String getFaqtitle() {
		return faqtitle;
	}

	public void setFaqtitle(String faqtitle) {
		this.faqtitle = faqtitle;
	}
	
	@ExcelField(title="知识创建人", align=2, sort=11)
	public String getFaqcreaterid() {
		return faqcreaterid;
	}

	public void setFaqcreaterid(String faqcreaterid) {
		this.faqcreaterid = faqcreaterid;
	}
	
	public String getFaqcreatername() {
		return faqcreatername;
	}

	public void setFaqcreatername(String faqcreatername) {
		this.faqcreatername = faqcreatername;
	}
	
	@ExcelField(title="创建时间", align=2, sort=12)
	public String getFaqcreatedatetime() {
		return faqcreatedatetime;
	}

	public void setFaqcreatedatetime(String faqcreatedatetime) {
		this.faqcreatedatetime = faqcreatedatetime;
	}
	
	public String getFaqstatuno() {
		return faqstatuno;
	}

	public void setFaqstatuno(String faqstatuno) {
		this.faqstatuno = faqstatuno;
	}
	
	public String getFaqstatudesc() {
		return faqstatudesc;
	}

	public void setFaqstatudesc(String faqstatudesc) {
		this.faqstatudesc = faqstatudesc;
	}
	
	@ExcelField(title="知识审批人", align=2, sort=13)
	public String getFaqapprovorid() {
		return faqapprovorid;
	}

	public void setFaqapprovorid(String faqapprovorid) {
		this.faqapprovorid = faqapprovorid;
	}
	
	@ExcelField(title="审核时间", align=2, sort=14)
	public String getFaqapprovortime() {
		return faqapprovortime;
	}

	public void setFaqapprovortime(String faqapprovortime) {
		this.faqapprovortime = faqapprovortime;
	}
	
	public String getFaqapprovortimelen() {
		return faqapprovortimelen;
	}

	public void setFaqapprovortimelen(String faqapprovortimelen) {
		this.faqapprovortimelen = faqapprovortimelen;
	}
	
	@ExcelField(title="序号", align=2, sort=1)
	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	@ExcelField(title="知识总数", align=2, sort=3)
	public Integer getFaqcount() {
		return faqcount;
	}

	public void setFaqcount(Integer faqcount) {
		this.faqcount = faqcount;
	}
	
	@ExcelField(title="待审批数", align=2, sort=4)
	public Integer getPendingcount() {
		return pendingcount;
	}

	public void setPendingcount(Integer pendingcount) {
		this.pendingcount = pendingcount;
	}

	@ExcelField(title="审批中数", align=2, sort=5)
	public Integer getApprovalcount() {
		return approvalcount;
	}

	public void setApprovalcount(Integer approvalcount) {
		this.approvalcount = approvalcount;
	}
	
	@ExcelField(title="审批通过数", align=2, sort=6)
	public Integer getThroughcount() {
		return throughcount;
	}

	public void setThroughcount(Integer throughcount) {
		this.throughcount = throughcount;
	}
	
	@ExcelField(title="审批未通过数", align=2, sort=7)
	public Integer getNothroughcount() {
		return nothroughcount;
	}

	public void setNothroughcount(Integer nothroughcount) {
		this.nothroughcount = nothroughcount;
	}

	
}