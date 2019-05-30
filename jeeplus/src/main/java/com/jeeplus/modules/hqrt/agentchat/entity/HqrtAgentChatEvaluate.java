/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 客户与坐席会话Entity
 * @author cz
 * @version 2019-05-20
 */
public class HqrtAgentChatEvaluate extends DataEntity<HqrtAgentChatEvaluate> {
	
	private static final long serialVersionUID = 1L;
	private Integer ordernumber; // 序号
	private String sessionid;		// 会话ID
	private String agentid;		// 坐席ID编号
	private String queuename;		// 队列（技能组）名称
	private String evaluatedatetime;		// 评价时间
	private String evaluatestar;		// 评价星级(0未评价，1-5星)
	private String evaluatetext;		// 评价内容
	
	public HqrtAgentChatEvaluate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentChatEvaluate(String id){
		super(id);
	}

	
	@ExcelField(title="会话ID", align=2, sort=4)
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	@ExcelField(title="坐席工号", align=2, sort=3)
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	@ExcelField(title="业务系统", align=2, sort=2)
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}

	@ExcelField(title="评价时间", align=2, sort=5)
	public String getEvaluatedatetime() {
		return evaluatedatetime;
	}

	public void setEvaluatedatetime(String evaluatedatetime) {
		this.evaluatedatetime = evaluatedatetime;
	}
	
	@ExcelField(title="评价星级", align=2, sort=6)
	public String getEvaluatestar() {
		return evaluatestar;
	}

	public void setEvaluatestar(String evaluatestar) {
		this.evaluatestar = evaluatestar;
	}
	
	@ExcelField(title="评价内容", align=2, sort=7)
	public String getEvaluatetext() {
		return evaluatetext;
	}

	public void setEvaluatetext(String evaluatetext) {
		this.evaluatetext = evaluatetext;
	}
	
	@ExcelField(title="序号", align=2, sort=1)
	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}

}