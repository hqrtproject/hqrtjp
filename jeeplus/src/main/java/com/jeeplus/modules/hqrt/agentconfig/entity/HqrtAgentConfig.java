/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentconfig.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 坐席配置Entity
 * @author cz
 * @version 2019-05-21
 */
public class HqrtAgentConfig extends DataEntity<HqrtAgentConfig> {
	
	private static final long serialVersionUID = 1L;
	private String rowguid;		// 唯一记录标识（主键）
	private String rowdatetime;		// 记录创建时间
	private String agentid;		// 坐席ID编号
	private String agentname;		// 坐席姓名
	private String agentmobile;		// 坐席手机号码
	private String agentprovince;		// 坐席所在省
	private String queueid;		// 所在技能组编号
	private String queuecode;		// 技能组代码
	private String queuename;		// 技能组名称
	
	public HqrtAgentConfig() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentConfig(String id){
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
	
	@ExcelField(title="所在技能组编号", align=2, sort=7)
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	
	@ExcelField(title="技能组代码", align=2, sort=8)
	public String getQueuecode() {
		return queuecode;
	}

	public void setQueuecode(String queuecode) {
		this.queuecode = queuecode;
	}
	
	@ExcelField(title="技能组名称", align=2, sort=9)
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	
}