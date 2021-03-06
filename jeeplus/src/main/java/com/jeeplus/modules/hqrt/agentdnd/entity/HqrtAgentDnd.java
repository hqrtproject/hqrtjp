/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentdnd.entity;


import java.util.Date;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 坐席置忙日志Entity
 * @author cz
 * @version 2019-05-23
 */
public class HqrtAgentDnd extends DataEntity<HqrtAgentDnd> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String agentid;		// 坐席ID编号
	private String agentname;		// 坐席姓名
	private String agentmobile;		// 坐席手机号码
	private String agentprovince;		// 坐席所在省
	private String startdatetime;		// 置忙开始时间
	private String enddatetime;		// 置忙结束时间
	private String timelen;		// 置忙时长(单位：秒)
	private String dndvalue;		// 置忙值
	private String dndvaluedesc;		// 置忙值描述
	private String exqueuename;    // 冗余配置文件内容，查找坐席所在技能组
	private Date starttime;
	private Date endtime;
	private Integer ordernumber; // 序号
	
	public HqrtAgentDnd() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentDnd(String id){
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
	
	@ExcelField(title="坐席ID", align=2, sort=3)
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
	
	public String getAgentmobile() {
		return agentmobile;
	}

	public void setAgentmobile(String agentmobile) {
		this.agentmobile = agentmobile;
	}
	
	public String getAgentprovince() {
		return agentprovince;
	}

	public void setAgentprovince(String agentprovince) {
		this.agentprovince = agentprovince;
	}
	
	@ExcelField(title="开始时间", align=2, sort=5)
	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@ExcelField(title="结束时间", align=2, sort=6)
	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	
	@ExcelField(title="持续时长", align=2, sort=7)
	public String getTimelen() {
		return timelen;
	}

	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	
	public String getDndvalue() {
		return dndvalue;
	}

	public void setDndvalue(String dndvalue) {
		this.dndvalue = dndvalue;
	}
	
	public String getDndvaluedesc() {
		return dndvaluedesc;
	}

	public void setDndvaluedesc(String dndvaluedesc) {
		this.dndvaluedesc = dndvaluedesc;
	}

	@ExcelField(title="业务系统", align=2, sort=2)
	public String getExqueuename() {
		return exqueuename;
	}

	public void setExqueuename(String exqueuename) {
		this.exqueuename = exqueuename;
	}
	
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@ExcelField(title="序号", align=2, sort=1)
	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}

}