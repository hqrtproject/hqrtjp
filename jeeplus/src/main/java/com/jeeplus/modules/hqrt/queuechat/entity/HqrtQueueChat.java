/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户排队日志Entity
 * @author duan
 * @version 2019-05-20
 */
public class HqrtQueueChat extends DataEntity<HqrtQueueChat> {
	
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
	private String endreasonno;		// 会话结束原因：1转人工成功、2客户取消、3超时结束
	private String endreason;		// 会话结束原因：1转人工成功、2客户取消、3超时结束
	private String queueid;		// 队列（技能组）编号
	private String queuename;		// 队列（技能组）名称
	private String originalsessionid;		// 原始SessionID
	
	public HqrtQueueChat() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtQueueChat(String id){
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
	
	@ExcelField(title="客户标识", align=2, sort=4)
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
	
	@ExcelField(title="客户手机号码", align=2, sort=6)
	public String getCustomermobile() {
		return customermobile;
	}

	public void setCustomermobile(String customermobile) {
		this.customermobile = customermobile;
	}
	
	@ExcelField(title="客户所在省", align=2, sort=7)
	public String getCustomerprovince() {
		return customerprovince;
	}

	public void setCustomerprovince(String customerprovince) {
		this.customerprovince = customerprovince;
	}
	
	@ExcelField(title="进入队列时间", align=2, sort=8)
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@ExcelField(title="退出队列时间", align=2, sort=9)
	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	
	@ExcelField(title="会话持续时长(单位：秒)", align=2, sort=10)
	public String getTimelen() {
		return timelen;
	}

	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	
	@ExcelField(title="会话结束原因：1转人工成功、2客户取消、3超时结束", align=2, sort=11)
	public String getEndreasonno() {
		return endreasonno;
	}

	public void setEndreasonno(String endreasonno) {
		this.endreasonno = endreasonno;
	}
	
	@ExcelField(title="会话结束原因：1转人工成功、2客户取消、3超时结束", align=2, sort=12)
	public String getEndreason() {
		return endreason;
	}

	public void setEndreason(String endreason) {
		this.endreason = endreason;
	}
	
	@ExcelField(title="队列（技能组）编号", align=2, sort=13)
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	
	@ExcelField(title="队列（技能组）名称", align=2, sort=14)
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	
	@ExcelField(title="原始SessionID", align=2, sort=15)
	public String getOriginalsessionid() {
		return originalsessionid;
	}

	public void setOriginalsessionid(String originalsessionid) {
		this.originalsessionid = originalsessionid;
	}
	
}