/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.agentchat.entity.HqrtAgentChat;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetails;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetailsForExport;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 客户与坐席会话Controller
 * @author cz
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentchat/hqrtAgentChat")
public class HqrtAgentChatController extends BaseController {
	
	/*@ModelAttribute
	public HqrtAgentChat get(@RequestParam(required=false) String id) {
		HqrtAgentChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentChatService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentChat();
		}
		return entity;
	}*/
	
	/**
	 * 客户与坐席会话列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentChat hqrtAgentChat, Model model) {
		model.addAttribute("hqrtAgentChat", hqrtAgentChat);
		return "modules/hqrt/agentchat/hqrtAgentChatList";
	}
	
	/**
	 * 客户与坐席会话列表页面
	 */
	@RequestMapping(value = "evaluatelist")
	public String evaluatelist(HqrtAgentChat hqrtAgentChat, Model model) {
		model.addAttribute("hqrtAgentChat", hqrtAgentChat);
		return "modules/hqrt/agentchat/hqrtAgentChatEvaluateList";
	}
	
	/**
	 * 客户与坐席会话列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentChat hqrtAgentChat, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HqrtAgentChat> page = new Page<>(request, response);
        hqrtAgentChat.setPage(page);
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.talkindex AS 'talkindex',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename',a.isvalid AS 'isvalid',a.firstresponsetimelen AS 'firstresponsetimelen',a.avgresponsetimelen AS 'avgresponsetimelen',a.customermessagecount AS 'customermessagecount',a.agentmessagecount AS 'agentmessagecount',a.evaluatestar AS 'evaluatestar',a.evaluatetext AS 'evaluatetext',a.originalsessionid AS 'originalsessionid' FROM hqrt_agent_chat a LEFT JOIN hqrt_agent_chatdetails b ON a.sessionid = b.sessionid";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(hqrtAgentChat.getCustomerprovince())) {
			String[] provincesplit = hqrtAgentChat.getCustomerprovince().split(",");
			sqlcondition += " AND (";
			for (String province : provincesplit) {
				sqlcondition += "a.customerprovince like ? OR ";
				paramList.add("%" + province + "%");
			}
			sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
			sqlcondition += ")";
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getQueuename())) {
        	sqlcondition += " AND a.queuename in ('" + hqrtAgentChat.getQueuename().replace(",", "','") + "')";
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getAgentid())) {
        	sqlcondition += " AND a.agentid in ('" + hqrtAgentChat.getAgentid().replace(",", "','") + "')";
        }
        if (hqrtAgentChat.getStarttime() != null && hqrtAgentChat.getEndttime() != null) {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtAgentChat.getStarttime()));
        	paramList.add(ft.format(hqrtAgentChat.getEndttime()));
        } else {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            Date beginOfDate = cal.getTime();
        	paramList.add(ft.format(beginOfDate));
        	Calendar calendar2 = Calendar.getInstance();
        	calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
        	        23, 59, 59);
        	Date endOfDate = calendar2.getTime();
        	paramList.add(ft.format(endOfDate));
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getCustomername())) {
        	sqlcondition += " AND a.customername = ?";
        	paramList.add(hqrtAgentChat.getCustomername());
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getSessionid())) {
        	sqlcondition += " AND a.sessionid = ?";
        	paramList.add(hqrtAgentChat.getSessionid());
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getEvaluatestar())) {
        	sqlcondition += " AND a.evaluatestar = ?";
        	paramList.add(hqrtAgentChat.getEvaluatestar());
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getEndreasonno())) {
        	sqlcondition += " AND a.endreasonno = ?";
        	paramList.add(hqrtAgentChat.getEndreasonno());
        }
        if (hqrtAgentChat.getHqrtAgentChatdetails() != null && StringUtils.isNotBlank(hqrtAgentChat.getHqrtAgentChatdetails().getMessagecontext())) {
        	sqlcondition += " AND b.messagecontext like ?";
        	paramList.add("%" + hqrtAgentChat.getHqrtAgentChatdetails().getMessagecontext() + "%");
        }
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }
        String selectcountsql = sql + sqlcondition + " GROUP BY a.sessionid";
        sql += sqlcondition + " GROUP BY a.sessionid" + " limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get("company");
        List<HqrtAgentChat> detailsList = md.queryList(sql, HqrtAgentChat.class, paramList.toArray());
        List<HqrtAgentChat> allDetailslList = md.queryList(selectcountsql, HqrtAgentChat.class, paramList.toArray());
		page.setList(detailsList);
		page.setCount(allDetailslList.size());
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户与坐席会话表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentChat hqrtAgentChat, Model model) {
		String sql = "select a.messagecontext AS 'messagecontext',a.messagesender AS 'messagesender',a.messagedateTime AS 'messagedateTime' FROM hqrt_agent_chatdetails a where a.sessionid = ?";
        List<Object> paramList = new ArrayList<Object>();
        if (StringUtils.isNotBlank(hqrtAgentChat.getSessionid())) {
        	paramList.add(hqrtAgentChat.getSessionid());
        }
        sql = sql + " ORDER BY a.messagedatetime";
        MultiDBUtils md = MultiDBUtils.get("company");
        List<HqrtAgentChatdetails> detailsList = md.queryList(sql, HqrtAgentChatdetails.class, paramList.toArray());
		model.addAttribute("detailsList", detailsList);
		model.addAttribute("mode", mode);
		return "modules/hqrt/agentchat/hqrtAgentChatForm";
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtAgentChat hqrtAgentChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "在线客服会话明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HqrtAgentChat> page = new Page<>(request, response);
            hqrtAgentChat.setPage(page);
    		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.talkindex AS 'talkindex',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.agentid AS 'agentid',a.agentname AS 'agentname',a.agentmobile AS 'agentmobile',a.agentprovince AS 'agentprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuecode AS 'queuecode',a.queuename AS 'queuename',a.isvalid AS 'isvalid',a.firstresponsetimelen AS 'firstresponsetimelen',a.avgresponsetimelen AS 'avgresponsetimelen',a.customermessagecount AS 'customermessagecount',a.agentmessagecount AS 'agentmessagecount',a.evaluatestar AS 'evaluatestar',a.evaluatetext AS 'evaluatetext',a.originalsessionid AS 'originalsessionid' FROM hqrt_agent_chat a LEFT JOIN hqrt_agent_chatdetails b ON a.sessionid = b.sessionid";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
    		if (StringUtils.isNotBlank(hqrtAgentChat.getCustomerprovince())) {
            	sqlcondition += " AND a.customerprovince in ('" + hqrtAgentChat.getCustomerprovince().replace(",", "','") + "')";
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getQueuename())) {
            	sqlcondition += " AND a.queuename in ('" + hqrtAgentChat.getQueuename().replace(",", "','") + "')";
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getAgentid())) {
            	sqlcondition += " AND a.agentid in ('" + hqrtAgentChat.getAgentid().replace(",", "','") + "')";
            }
            if (hqrtAgentChat.getStarttime() != null && hqrtAgentChat.getEndttime() != null) {
            	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtAgentChat.getStarttime()));
            	paramList.add(ft.format(hqrtAgentChat.getEndttime()));
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getCustomername())) {
            	sqlcondition += " AND a.customername = ?";
            	paramList.add(hqrtAgentChat.getCustomername());
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getSessionid())) {
            	sqlcondition += " AND a.sessionid = ?";
            	paramList.add(hqrtAgentChat.getSessionid());
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getEvaluatestar())) {
            	sqlcondition += " AND a.evaluatestar = ?";
            	paramList.add(hqrtAgentChat.getEvaluatestar());
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getEndreasonno())) {
            	sqlcondition += " AND a.endreasonno = ?";
            	paramList.add(hqrtAgentChat.getEndreasonno());
            }
            if (hqrtAgentChat.getHqrtAgentChatdetails() != null && StringUtils.isNotBlank(hqrtAgentChat.getHqrtAgentChatdetails().getMessagecontext())) {
            	sqlcondition += " AND b.messagecontext like ?";
            	paramList.add("%" + hqrtAgentChat.getHqrtAgentChatdetails().getMessagecontext() + "%");
            }
            if (StringUtils.isNotBlank(sqlcondition)) {
            	sqlcondition = sqlcondition.replaceFirst(" AND", "");
            	sqlcondition  = " where" + sqlcondition;
            }
            sql += sqlcondition + " GROUP BY a.sessionid";
            MultiDBUtils md = MultiDBUtils.get("company");
            List<HqrtAgentChat> detailsList = md.queryList(sql, HqrtAgentChat.class, paramList.toArray());
            for (HqrtAgentChat agentChat : detailsList) {
				if ("0".equals(agentChat.getIsvalid())) {
					agentChat.setIsvalid("无效");
				} else {
					agentChat.setIsvalid("有效");
				}
				if ("0".equals(agentChat.getEvaluatestar())) {
					agentChat.setEvaluatestar("未评价");
				} else if ("1".equals(agentChat.getEvaluatestar())) {
					agentChat.setEvaluatestar("一星");
				} else if ("2".equals(agentChat.getEvaluatestar())) {
					agentChat.setEvaluatestar("二星");
				} else if ("3".equals(agentChat.getEvaluatestar())) {
					agentChat.setEvaluatestar("三星");
				} else if ("4".equals(agentChat.getEvaluatestar())) {
					agentChat.setEvaluatestar("四星");
				} else if ("5".equals(agentChat.getEvaluatestar())) {
					agentChat.setEvaluatestar("五星");
				}
			}
    		new ExportExcel("在线客服会话明细", HqrtAgentChat.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出在线客服会话明细记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequestMapping(value = "exportdetails")
	public AjaxJson exportdetails(HqrtAgentChat hqrtAgentChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		HqrtAgentChatdetails hqrtAgentChatdetails = new HqrtAgentChatdetails();
		hqrtAgentChatdetails.setSessionid(hqrtAgentChat.getSessionid());
		try {
			String fileName = "客户与坐席会话明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			String sql = "select a.customername AS 'customername',a.agentname AS 'agentname',a.messagecontext AS 'messagecontext',a.messagesender AS 'messagesender',a.messagedatetime AS 'messagedatetime' FROM hqrt_agent_chatdetails a";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
            if (StringUtils.isNotBlank(hqrtAgentChatdetails.getSessionid())) {
            	sqlcondition += " AND a.sessionid = ?";
            	paramList.add(hqrtAgentChatdetails.getSessionid());
            }
            if (StringUtils.isNotBlank(sqlcondition)) {
            	sqlcondition = sqlcondition.replaceFirst(" AND", "");
            	sqlcondition  = " where" + sqlcondition;
            }
            sql += sqlcondition + " ORDER BY a.messagedatetime";
            MultiDBUtils md = MultiDBUtils.get("company");
            List<HqrtAgentChatdetailsForExport> detailsList = md.queryList(sql, HqrtAgentChatdetailsForExport.class, paramList.toArray());
			for (HqrtAgentChatdetailsForExport hqrtAgentChatdetailsForExport : detailsList) {
				if (hqrtAgentChatdetailsForExport.getMessagesender() == 1) {
					hqrtAgentChatdetailsForExport.setNamecustomer(hqrtAgentChatdetailsForExport.getCustomername());
					hqrtAgentChatdetailsForExport.setMessagedatetimecustomer(hqrtAgentChatdetailsForExport.getMessagedatetime());
					hqrtAgentChatdetailsForExport.setMessagecontextcustomer(hqrtAgentChatdetailsForExport.getMessagecontext());
				} else if (hqrtAgentChatdetailsForExport.getMessagesender() == 2) {
					hqrtAgentChatdetailsForExport.setNameagent(hqrtAgentChatdetailsForExport.getAgentname());
					hqrtAgentChatdetailsForExport.setMessagedatetimeagent(hqrtAgentChatdetailsForExport.getMessagedatetime());
					hqrtAgentChatdetailsForExport.setMessagecontextagent(hqrtAgentChatdetailsForExport.getMessagecontext());
				} else {
					hqrtAgentChatdetailsForExport.setNameagent("系统");
					hqrtAgentChatdetailsForExport.setMessagedatetimeagent(hqrtAgentChatdetailsForExport.getMessagedatetime());
					hqrtAgentChatdetailsForExport.setMessagecontextagent(hqrtAgentChatdetailsForExport.getMessagecontext());
				}
			}
			new ExportExcel("客户与坐席会话明细", HqrtAgentChatdetailsForExport.class).setDataList(detailsList).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户与坐席会话明细记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

}