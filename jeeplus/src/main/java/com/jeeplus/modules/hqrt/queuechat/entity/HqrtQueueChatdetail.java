/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.entity;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户排队日志Entity
 * @author duan
 * @version 2019-05-20
 */
public class HqrtQueueChatdetail extends DataEntity<HqrtQueueChatdetail> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String sessionid;		// 会话ID
	private String customerid;		// 客户标识
	private String customername;		// 客户姓名
	private String customermobile;		// 客户手机号码
	private String customerprovince;		// 客户所在省
	private String startdatetime;		// 进入队列时间
	private String enddatetime;		// 退出队列时间
	private String timelen;		// 会话持续时长(单位：秒)
	private Integer endreasonno;		// 会话结束原因：1转人工成功、2客户取消、3超时结束
	private String endreason;		// 会话结束原因：1转人工成功、2客户取消、3超时结束
	private String queueid;		// 队列（技能组）编号
	private String queuename;		// 队列（技能组）名称
	private List<String> queuenameList; //  选中业务系统（可能是多个）
	private String originalsessionid;		// 原始SessionID

	// 过滤的开始结束时间
	private Date starttime;
	private Date endttime;

	public HqrtQueueChatdetail() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtQueueChatdetail(String id){
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
	
	@ExcelField(title="用户ID", align=2, sort=4)
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	@ExcelField(title="用户名", align=2, sort=3)
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
	
	@ExcelField(title="进线开始时间", align=2, sort=5)
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@ExcelField(title="进线结束时间", align=2, sort=6)
	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	
	
	public String getTimelen() {
		return timelen;
	}

	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	
	public Integer getEndreasonno() {
		return endreasonno;
	}

	public void setEndreasonno(Integer endreasonno) {
		this.endreasonno = endreasonno;
	}
	
	@ExcelField(title="结束原因", align=2, sort=7)
	public String getEndreason() {
		return endreason;
	}

	public void setEndreason(String endreason) {
		this.endreason = endreason;
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
	
	public List<String> getQueuenameList() {
		return queuenameList;
	}

	public void setQueuenameList(List<String> queuenameList) {
		this.queuenameList = queuenameList;
	}
	
	@ExcelField(title="会话ID", align=2, sort=2)
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
}