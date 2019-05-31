/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchat.entity;


import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 机器人对话内容Entity
 * @author cz
 * @version 2019-05-16
 */
public class HqrtRobotChatForDetails extends DataEntity<HqrtRobotChatForDetails> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String sessionid;		// 会话ID
	private String customerid;		// 客户标识
	private String customername;		// 客户姓名
	private String customermobile;		// 客户手机号码
	private String customerprovince;		// 客户所在省
	private List<String> customerprovinceList;		// 选中客户所在省（可能是多个）
	private String startdatetime;		// 会话开始时间
	private String enddatetime;		// 会话结束时间
	private Integer timelen;		// 会话持续时长(单位：秒)
	private String endreasonno;		// 会话结束原因：1转人工、2客户关闭、3超时结束
	private String endreason;		// 会话结束原因：1转人工、2客户关闭、3超时结束
	private String queueid;		// 队列（技能组）编号
	private String queuename;		// 队列（技能组）名称
	private List<String> queuenameList;	// 选中业务系统（可能是多个）
	private String originalsessionid;		// 原始SessionID
	private Integer ordernumber; // 序号
	private Date starttime;
	private Date endttime;
	
	public HqrtRobotChatForDetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtRobotChatForDetails(String id){
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

	@ExcelField(title="客户id", align=2, sort=4)
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@ExcelField(title="客户姓名", align=2, sort=5)
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

	@ExcelField(title="省份", align=2, sort=6)
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

	@ExcelField(title="开始时间", align=2, sort=7)
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}

	@ExcelField(title="结束时间", align=2, sort=8)
	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}

	@ExcelField(title="会话时长", align=2, sort=9)
	public Integer getTimelen() {
		return timelen;
	}

	public void setTimelen(Integer timelen) {
		this.timelen = timelen;
	}

	public String getEndreasonno() {
		return endreasonno;
	}

	public void setEndreasonno(String endreasonno) {
		this.endreasonno = endreasonno;
	}

	@ExcelField(title="结束原因", align=2, sort=10)
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

	@ExcelField(title="业务系统", align=2, sort=2)
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
	
	@ExcelField(title="会话id", align=2, sort=3)
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


}