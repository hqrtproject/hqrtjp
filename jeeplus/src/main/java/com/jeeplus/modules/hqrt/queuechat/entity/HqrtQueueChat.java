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
	private Integer endreasonno;		// 会话结束原因：1转人工成功、2客户取消、3超时结束
	private String endreason;		// 会话结束原因：1转人工成功、2客户取消、3超时结束
	private String queueid;		// 队列（技能组）编号
	private String queuename;		// 队列（技能组）名称
	private List<String> queuenameList; //  选中业务系统（可能是多个）
	private String originalsessionid;		// 原始SessionID

	// 过滤的开始结束时间
	private Date starttime;
	private Date endttime;
	
	// 统计字段
	private Integer totalincount;    // 人工进线量
	private Integer conversionvolume;  // 转通坐席量
	private String connectrate;   // 接通率
	private Integer linupcancelvolume;  // 排队取消量
	private String cancelrate; //  取消率
	private Integer linuptimeoutvolume; // 排队超时量
	private String timeoutrate; // 超时率
	private Integer ordernumber; // 序号

	public HqrtQueueChat() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtQueueChat(String id){
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
	
	
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	
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
	@ExcelField(title="人工进线量", align=2, sort=3)
	public Integer getTotalincount() {
		return totalincount;
	}

	public void setTotalincount(Integer totalincount) {
		this.totalincount = totalincount;
	}
	@ExcelField(title="转通坐席量", align=2, sort=4)
	public Integer getConversionvolume() {
		return conversionvolume;
	}

	public void setConversionvolume(Integer conversionvolume) {
		this.conversionvolume = conversionvolume;
	}
	@ExcelField(title="接通率", align=2, sort=5)
	public String getConnectrate() {
		return connectrate;
	}

	public void setConnectrate(String connectrate) {
		this.connectrate = connectrate;
	}
	@ExcelField(title="排队取消量", align=2, sort=6)
	public Integer getLinupcancelvolume() {
		return linupcancelvolume;
	}
	
	public void setLinupcancelvolume(Integer linupcancelvolume) {
		this.linupcancelvolume = linupcancelvolume;
	}
	@ExcelField(title="取消率", align=2, sort=7)
	public String getCancelrate() {
		return cancelrate;
	}

	public void setCancelrate(String cancelrate) {
		this.cancelrate = cancelrate;
	}
	@ExcelField(title="排队超时量", align=2, sort=8)
	public Integer getLinuptimeoutvolume() {
		return linuptimeoutvolume;
	}

	public void setLinuptimeoutvolume(Integer linuptimeoutvolume) {
		this.linuptimeoutvolume = linuptimeoutvolume;
	}
	@ExcelField(title="超时率", align=2, sort=9)
	public String getTimeoutrate() {
		return timeoutrate;
	}

	public void setTimeoutrate(String timeoutrate) {
		this.timeoutrate = timeoutrate;
	}
	
	@ExcelField(title="序号", align=2, sort=1)
	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}
}