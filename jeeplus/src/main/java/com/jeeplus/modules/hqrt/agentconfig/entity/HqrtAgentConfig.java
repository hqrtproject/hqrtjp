/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentconfig.entity;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

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
	
	private Integer timelenlogin;		// 登陆时长
	private Integer timelenonline;		// 在线时长
	private Integer timelendnd;		// 置忙时长
	private Integer timelenwork;		// 接待时长
	private Integer timelenfree;		// 空闲时长
	private Integer advicecount;		// 咨询量
	private Integer receivecount;		// 接待量
	private Integer invalidcount;		// 无效量
	private Integer chattotaltime;		// 会话总时长
	private Integer chatavgtime;		//会话平均时长
	private Integer firstresponsetimelenavg;	// 首次响应平均时长
	private Integer avgresponsetimelen;		// 响应平均时长
	private String qaratio;		// 问答比
	private Integer participationcount;		// 参评量 
	private String participationrate;		// 参评率
	private Integer onestarcount;		// 一星评价量
	private Integer twostarcount;		// 二星评价量
	private Integer threestarcount;		// 三星评价量
	private Integer fourstarcount;		// 四星评价量
	private Integer fivestarcount;		// 五星评价量
	private Integer avgstarcount;		// 平均评价量
	
	private Date starttime;
	private Date endtime;
	
	public HqrtAgentConfig() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HqrtAgentConfig(String id){
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
	
	@ExcelField(title="坐席工号", align=2, sort=2)
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	@ExcelField(title="坐席姓名", align=2, sort=3)
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
	
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	
	public String getQueuecode() {
		return queuecode;
	}

	public void setQueuecode(String queuecode) {
		this.queuecode = queuecode;
	}
	
	@ExcelField(title="业务系统", align=2, sort=1)
	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}

	@ExcelField(title="登录时长", align=2, sort=4)
	public Integer getTimelenlogin() {
		return timelenlogin;
	}

	public void setTimelenlogin(Integer timelenlogin) {
		this.timelenlogin = timelenlogin;
	}

	@ExcelField(title="在线时长", align=2, sort=5)
	public Integer getTimelenonline() {
		return timelenonline;
	}

	public void setTimelenonline(Integer timelenonline) {
		this.timelenonline = timelenonline;
	}

	@ExcelField(title="置忙时长", align=2, sort=6)
	public Integer getTimelendnd() {
		return timelendnd;
	}

	public void setTimelendnd(Integer timelendnd) {
		this.timelendnd = timelendnd;
	}
	
	@ExcelField(title="接待时长", align=2, sort=7)
	public Integer getTimelenwork() {
		return timelenwork;
	}

	public void setTimelenwork(Integer timelenwork) {
		this.timelenwork = timelenwork;
	}

	@ExcelField(title="空闲时长", align=2, sort=8)
	public Integer getTimelenfree() {
		return timelenfree;
	}

	public void setTimelenfree(Integer timelenfree) {
		this.timelenfree = timelenfree;
	}

	@ExcelField(title="咨询量", align=2, sort=9)
	public Integer getAdvicecount() {
		return advicecount;
	}

	public void setAdvicecount(Integer advicecount) {
		this.advicecount = advicecount;
	}

	@ExcelField(title="接待量", align=2, sort=10)
	public Integer getReceivecount() {
		return receivecount;
	}

	public void setReceivecount(Integer receivecount) {
		this.receivecount = receivecount;
	}

	@ExcelField(title="无效量", align=2, sort=11)
	public Integer getInvalidcount() {
		return invalidcount;
	}

	public void setInvalidcount(Integer invalidcount) {
		this.invalidcount = invalidcount;
	}

	@ExcelField(title="会话总时长", align=2, sort=12)
	public Integer getChattotaltime() {
		return chattotaltime;
	}

	public void setChattotaltime(Integer chattotaltime) {
		this.chattotaltime = chattotaltime;
	}

	@ExcelField(title="会话平均时长", align=2, sort=13)
	public Integer getChatavgtime() {
		return chatavgtime;
	}

	public void setChatavgtime(Integer chatavgtime) {
		this.chatavgtime = chatavgtime;
	}

	@ExcelField(title="首次响应平均时长", align=2, sort=14)
	public Integer getFirstresponsetimelenavg() {
		return firstresponsetimelenavg;
	}

	public void setFirstresponsetimelenavg(Integer firstresponsetimelenavg) {
		this.firstresponsetimelenavg = firstresponsetimelenavg;
	}

	@ExcelField(title="响应平均时长", align=2, sort=15)
	public Integer getAvgresponsetimelen() {
		return avgresponsetimelen;
	}

	public void setAvgresponsetimelen(Integer avgresponsetimelen) {
		this.avgresponsetimelen = avgresponsetimelen;
	}

	@ExcelField(title="问答比", align=2, sort=16)
	public String getQaratio() {
		return qaratio;
	}

	public void setQaratio(String qaratio) {
		this.qaratio = qaratio;
	}

	@ExcelField(title="参评量", align=2, sort=17)
	public Integer getParticipationcount() {
		return participationcount;
	}

	public void setParticipationcount(Integer participationcount) {
		this.participationcount = participationcount;
	}

	@ExcelField(title="参评率", align=2, sort=18)
	public String getParticipationrate() {
		return participationrate;
	}

	public void setParticipationrate(String participationrate) {
		this.participationrate = participationrate;
	}

	@ExcelField(title="一星评价量", align=2, sort=19)
	public Integer getOnestarcount() {
		return onestarcount;
	}

	public void setOnestarcount(Integer onestarcount) {
		this.onestarcount = onestarcount;
	}

	@ExcelField(title="二星评价量", align=2, sort=20)
	public Integer getTwostarcount() {
		return twostarcount;
	}

	public void setTwostarcount(Integer twostarcount) {
		this.twostarcount = twostarcount;
	}

	@ExcelField(title="三星评价量", align=2, sort=21)
	public Integer getThreestarcount() {
		return threestarcount;
	}

	public void setThreestarcount(Integer threestarcount) {
		this.threestarcount = threestarcount;
	}

	@ExcelField(title="四星评价量", align=2, sort=22)
	public Integer getFourstarcount() {
		return fourstarcount;
	}

	public void setFourstarcount(Integer fourstarcount) {
		this.fourstarcount = fourstarcount;
	}

	@ExcelField(title="五星评价量", align=2, sort=23)
	public Integer getFivestarcount() {
		return fivestarcount;
	}

	public void setFivestarcount(Integer fivestarcount) {
		this.fivestarcount = fivestarcount;
	}

	@ExcelField(title="平均评价", align=2, sort=24)
	public Integer getAvgstarcount() {
		return avgstarcount;
	}

	public void setAvgstarcount(Integer avgstarcount) {
		this.avgstarcount = avgstarcount;
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
	
}