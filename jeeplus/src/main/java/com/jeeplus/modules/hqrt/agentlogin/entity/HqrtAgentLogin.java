/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentlogin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 坐席登录日志Entity
 * @author cz
 * @version 2019-05-20
 */
public class HqrtAgentLogin extends DataEntity<HqrtAgentLogin> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String agentid;		// 坐席ID编号
	private String agentname;		// 坐席姓名
	private String agentmobile;		// 坐席手机号码
	private String agentprovince;		// 坐席所在省
	private String startdatetime;		// 登录时间
	private String enddatetime;		// 退出时间
	private String timelen;		// 登录时长(单位：秒)
	
	public HqrtAgentLogin() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentLogin(String id){
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
	
	@ExcelField(title="坐席ID编号", align=2, sort=3)
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	@ExcelField(title="坐席姓名", align=2, sort=4)
	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	
	@ExcelField(title="坐席手机号码", align=2, sort=5)
	public String getAgentmobile() {
		return agentmobile;
	}

	public void setAgentmobile(String agentmobile) {
		this.agentmobile = agentmobile;
	}
	
	@ExcelField(title="坐席所在省", align=2, sort=6)
	public String getAgentprovince() {
		return agentprovince;
	}

	public void setAgentprovince(String agentprovince) {
		this.agentprovince = agentprovince;
	}
	
	@ExcelField(title="登录时间", align=2, sort=7)
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@ExcelField(title="退出时间", align=2, sort=8)
	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	
	@ExcelField(title="登录时长(单位：秒)", align=2, sort=9)
	public String getTimelen() {
		return timelen;
	}

	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	
}