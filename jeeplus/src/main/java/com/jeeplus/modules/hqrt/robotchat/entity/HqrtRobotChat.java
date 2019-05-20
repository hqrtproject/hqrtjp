/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchat.entity;


import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 机器人对话Entity
 * @author cz
 * @version 2019-05-16
 */
public class HqrtRobotChat extends DataEntity<HqrtRobotChat> {
	
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
	private Integer endreasonno;		// 会话结束原因：1转人工、2客户关闭、3超时结束
	private String endreason;		// 会话结束原因：1转人工、2客户关闭、3超时结束
	private String queueid;		// 队列（技能组）编号
	private String queuename;		// 队列（技能组）名称
	private List<String> queuenameList;	// 选中业务系统（可能是多个）
	private String originalsessionid;		// 原始SessionID
	// 过滤的开始结束时间
	private Date starttime;
	private Date endttime;
	// 统计字段
	private Integer totalincount;		// 总进线量
	private Integer conversionvolume;		// 转人工量
	private String conversionrate;		// 转人工率
	private Integer totaluserquestions;		// 用户提问总量
	private Integer resolved;		// 机器人已解决量
	private Integer unresolved;		// 机器人未解决量
	private Integer notevaluated;		// 未评价
	private Integer failurefindknowledge;		// 未找到知识
	
	public HqrtRobotChat() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtRobotChat(String id){
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
	
	@ExcelField(title="省份", align=2, sort=3)
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
	
	public Integer getTimelen() {
		return timelen;
	}

	public void setTimelen(Integer timelen) {
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

	@ExcelField(title="总进线量", align=2, sort=2)
	public Integer getTotalincount() {
		return totalincount;
	}

	public void setTotalincount(Integer totalincount) {
		this.totalincount = totalincount;
	}

	@ExcelField(title="转人工量", align=2, sort=4)
	public Integer getConversionvolume() {
		return conversionvolume;
	}

	public void setConversionvolume(Integer conversionvolume) {
		this.conversionvolume = conversionvolume;
	}

	@ExcelField(title="转人工率", align=2, sort=5)
	public String getConversionrate() {
		return conversionrate;
	}

	public void setConversionrate(String conversionrate) {
		this.conversionrate = conversionrate;
	}

	@ExcelField(title="用户提问总量", align=2, sort=6)
	public Integer getTotaluserquestions() {
		return totaluserquestions;
	}

	public void setTotaluserquestions(Integer totaluserquestions) {
		this.totaluserquestions = totaluserquestions;
	}
	
	@ExcelField(title="机器人已解决量", align=2, sort=7)
	public Integer getResolved() {
		return resolved;
	}

	public void setResolved(Integer resolved) {
		this.resolved = resolved;
	}

	@ExcelField(title="机器人未解决量", align=2, sort=8)
	public Integer getUnresolved() {
		return unresolved;
	}

	public void setUnresolved(Integer unresolved) {
		this.unresolved = unresolved;
	}
	
	@ExcelField(title="未评价", align=2, sort=9)
	public Integer getNotevaluated() {
		return notevaluated;
	}

	public void setNotevaluated(Integer notevaluated) {
		this.notevaluated = notevaluated;
	}

	@ExcelField(title="未找到知识", align=2, sort=10)
	public Integer getFailurefindknowledge() {
		return failurefindknowledge;
	}

	public void setFailurefindknowledge(Integer failurefindknowledge) {
		this.failurefindknowledge = failurefindknowledge;
	}
	
}