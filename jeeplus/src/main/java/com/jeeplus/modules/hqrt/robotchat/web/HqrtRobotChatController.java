/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchat.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.cmccarea.service.HqrtCmccAreaService;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChatForDetails;
import com.jeeplus.modules.hqrt.robotchat.service.HqrtRobotChatService;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetails;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetailsForView;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 机器人对话Controller
 * @author cz
 * @version 2019-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/robotchat/hqrtRobotChat")
public class HqrtRobotChatController extends BaseController {

	@Autowired
	private HqrtRobotChatService hqrtRobotChatService;
	
	@Autowired
	private HqrtCmccAreaService hqrtCmccAreaService;
	
	@ModelAttribute
	public HqrtRobotChat get(@RequestParam(required=false) String id) {
		HqrtRobotChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtRobotChatService.get(id);
		}
		if (entity == null){
			entity = new HqrtRobotChat();
		}
		return entity;
	}
	
	/**
	 * 机器人对话列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(
			/*@RequestParam(name="timestamp", required=true) Long timestamp,
			@RequestParam(name="sign", required=true) String sign,*/
			HqrtRobotChat hqrtRobotChat, Model model) {
		/*long nowtimestamp = System.currentTimeMillis();
		Long ratio = Math.round(nowtimestamp*0.1/600000*10);
		if (ratio.equals(timestamp)) {
			String salt = ratio + Global.getConfig("secretkey");
			String md5 = DigestUtils.md5Hex(salt);
			if (md5.equals(sign)) {
				model.addAttribute("hqrtRobotChat", hqrtRobotChat);
				return "modules/hqrt/robotchat/hqrtRobotChatList";
			} else {
				return "modules/hqrt/404";
			}
		} else {
			return "modules/hqrt/404";
		}*/
		model.addAttribute("hqrtRobotChat", hqrtRobotChat);
		return "modules/hqrt/robotchat/hqrtRobotChatList";
	}
	
	/**
	 * 机器人对话列表页面
	 */
	@RequestMapping(value = "listdetails")
	public String listdetails(
			/*@RequestParam(name="timestamp", required=true) Long timestamp,
			@RequestParam(name="sign", required=true) String sign,*/
			HqrtRobotChat hqrtRobotChat, Model model) {
		/*long nowtimestamp = System.currentTimeMillis();
		Long ratio = Math.round(nowtimestamp*0.1/600000*10);
		if (ratio.equals(timestamp)) {
			String salt = ratio + Global.getConfig("secretkey");
			String md5 = DigestUtils.md5Hex(salt);
			if (md5.equals(sign)) {
				model.addAttribute("hqrtRobotChat", hqrtRobotChat);
				return "modules/hqrt/robotchat/hqrtRobotChatList";
			} else {
				return "modules/hqrt/404";
			}
		} else {
			return "modules/hqrt/404";
		}*/
		model.addAttribute("hqrtRobotChat", hqrtRobotChat);
		return "modules/hqrt/robotchat/hqrtRobotChatdetailsList";
	}
	
	/**
	 * 机器人对话列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtRobotChat hqrtRobotChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
		List<String> queueNameList = new ArrayList<String>();
		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
			queueNameList.add(hqrtQueueConfig.getQueuename());
		}
		// 首先根据业务和省份分组查询
        String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chat a ";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
        if (StringUtils.isNotBlank(hqrtRobotChat.getQueuename())) {
        	if (!hqrtRobotChat.getQueuename().contains("其他")) {
        		sqlcondition += " AND a.queuename in ('" + hqrtRobotChat.getQueuename().replace(",", "','") + "')";
			} else {
				String[] queueselect = hqrtRobotChat.getQueuename().split(",");
				for (String queuename : queueselect) {
					if (queueNameList.contains(queuename)) {
						queueNameList.remove(queuename);
					}
				}
				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
        if (hqrtRobotChat.getStarttime() != null && hqrtRobotChat.getEndttime() != null) {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtRobotChat.getStarttime()));
        	paramList.add(ft.format(hqrtRobotChat.getEndttime()));
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
		if (StringUtils.isNotBlank(hqrtRobotChat.getCustomerprovince())) {
			if (!hqrtRobotChat.getCustomerprovince().contains("其他")) {
				String[] provinceselect = hqrtRobotChat.getCustomerprovince().split(",");
				sqlcondition += " AND (";
				for (String province : provinceselect) {
					sqlcondition += "a.customerprovince like ? OR ";
					paramList.add("%" + province + "%");
				}
				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
				sqlcondition += ")";
			} else {
				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
				String[] provinceselect = hqrtRobotChat.getCustomerprovince().split(",");
				for (String province : provinceselect) {
					if (hqrtCmccAreaList.contains(province)) {
						hqrtCmccAreaList.remove(province);
					}
				}
				sqlcondition += " AND (";
				for (String province : hqrtCmccAreaList) {
					sqlcondition += "a.customerprovince not like ? AND ";
					paramList.add("%" + province + "%");
				}
				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("AND"));
				sqlcondition += ")";
			}
        }
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }
        sql += sqlcondition + " GROUP BY a.QueueName,a.CustomerProvince";
        List<HqrtRobotChat> hqrtRobotChatlist = md.queryList(sql, HqrtRobotChat.class, paramList.toArray());
		for (HqrtRobotChat robotChat : hqrtRobotChatlist) {
			String _sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chat a";
			String _sqlcondition = "";
			paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(robotChat.getQueuename())) {
				_sqlcondition += " AND a.queuename = ?";
	        	paramList.add(robotChat.getQueuename());
	        } else {
				_sqlcondition += " AND (a.queuename = '' OR isnull(a.queuename))";
	        }
			if (StringUtils.isNotBlank(robotChat.getCustomerprovince())) {
				_sqlcondition += " AND a.customerprovince like ? ";
				paramList.add("%" + robotChat.getCustomerprovince() + "%");
	        } else {
				_sqlcondition += " AND (a.customerprovince = '' OR isnull(a.customerprovince))";
	        }
			if (hqrtRobotChat.getStarttime() != null && hqrtRobotChat.getEndttime() != null) {
				_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
	        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	paramList.add(ft.format(hqrtRobotChat.getStarttime()));
	        	paramList.add(ft.format(hqrtRobotChat.getEndttime()));
	        } else {
	        	_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
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
	        if (StringUtils.isNotBlank(_sqlcondition)) {
	        	_sqlcondition = _sqlcondition.replaceFirst(" AND", "");
	        	_sqlcondition  = " where" + _sqlcondition;
	        }
			_sql += _sqlcondition;
			List<HqrtRobotChat> queueNameAndCustomerProvinceList = md.queryList(_sql, HqrtRobotChat.class, paramList.toArray());
			robotChat.setTotalincount(queueNameAndCustomerProvinceList.size());
			// 转人工量
			int conversionvolume = 0;
			// 用户提问总量
			int totaluserquestions = 0;
			// 机器人已解决量
			int resolved = 0;
			// 机器人未解决量
			int unresolved = 0;
			// 未评价
			int notevaluated = 0;
			// 未找到知识
			int failurefindknowledge = 0;
			HqrtRobotChatdetails hqrtRobotChatdetails = new HqrtRobotChatdetails();
			for (HqrtRobotChat hrc : queueNameAndCustomerProvinceList) {
				hqrtRobotChatdetails.setSessionid(hrc.getSessionid());
				String __sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.queueid AS 'queueid',a.queuename AS 'queuename', a.requestcontext AS 'requestcontext',a.responsecontext AS 'responsecontext', a.responseno AS 'responseno', a.responsenodesc AS 'responsenodesc',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel', a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.satisfyno AS 'satisfyno',a.satisfydesc AS 'satisfydesc',a.MessageDateTime  AS 'messagedatetime',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chatdetails a ";
				String __sqlcondition = "";
				paramList = new ArrayList<Object>();
				if (StringUtils.isNotBlank(hrc.getSessionid())) {
					__sqlcondition += " AND a.sessionid = ?";
		        	paramList.add(hrc.getSessionid());
		        }
		        if (StringUtils.isNotBlank(__sqlcondition)) {
		        	__sqlcondition = __sqlcondition.replaceFirst(" AND", "");
		        	__sqlcondition  = " where" + __sqlcondition;
		        }
				__sql += __sqlcondition;
				List<HqrtRobotChatdetails> hqrtRobotChatdetailsList = md.queryList(__sql, HqrtRobotChatdetails.class, paramList.toArray());
				if (hrc.getEndreasonno() == 1) {
					conversionvolume++;
				}
				totaluserquestions += hqrtRobotChatdetailsList.size();
				for (HqrtRobotChatdetails hrcdetails : hqrtRobotChatdetailsList) {
					if (hrcdetails.getSatisfyno() == 2) {
						resolved++;
					}
					if (hrcdetails.getSatisfyno() == 1) {
						unresolved++;
					}
					if (hrcdetails.getSatisfyno() == 0) {
						notevaluated++;
					}
					if (hrcdetails.getResponseno() == 100) {
						failurefindknowledge++;
					}
				}
			}
			robotChat.setConversionvolume(conversionvolume);
			robotChat.setTotaluserquestions(totaluserquestions);
			robotChat.setResolved(resolved);
			robotChat.setUnresolved(unresolved);
			robotChat.setNotevaluated(notevaluated);
			robotChat.setFailurefindknowledge(failurefindknowledge);
			DecimalFormat df = new DecimalFormat("#0.00");
			robotChat.setConversionrate(df.format(robotChat.getConversionvolume()*0.1/robotChat.getTotalincount()*1000) + "%");
		}
		Iterator<HqrtRobotChat> it = hqrtRobotChatlist.iterator();
		List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
		// map的key是queuename和province联合组成，用来唯一确定一条记录
		Map<String, HqrtRobotChat> mapnew = new HashMap<String, HqrtRobotChat>();
		while (it.hasNext()) {
			HqrtRobotChat hrc = it.next();
			Boolean isOtherProvince = true;
			Boolean isOtherQueue = true;
			for (String area : hqrtCmccAreaList) {
				if (hrc.getCustomerprovince().contains(area)) {
					isOtherProvince = false;
					break;
				}
			}
			if (queueNameList.contains(hrc.getQueuename()) || StringUtils.isNotBlank(hrc.getQueuename()) && hqrtRobotChat.getQueuename().contains(hrc.getQueuename())) {
				isOtherQueue = false;
			}
			if (isOtherProvince || isOtherQueue) {
				// 业务系统和省份有一个属于“其他”的，则需要重新统计
				if (isOtherProvince) {
					hrc.setCustomerprovince("其他");
				}
				if (isOtherQueue) {
					hrc.setQueuename("其他");
				}
				String key = hrc.getQueuename() + hrc.getCustomerprovince();
				HqrtRobotChat robotChat = mapnew.get(key);
				if (robotChat == null) {
					// 直接存储到map中
					mapnew.put(key, hrc);
				} else {
					robotChat.setTotalincount(robotChat.getTotalincount() + hrc.getTotalincount());
					robotChat.setConversionvolume(robotChat.getConversionvolume() + hrc.getConversionvolume());
					robotChat.setTotaluserquestions(robotChat.getTotaluserquestions() + hrc.getTotaluserquestions());
					robotChat.setResolved(robotChat.getResolved() + hrc.getResolved());
					robotChat.setUnresolved(robotChat.getUnresolved() + hrc.getUnresolved());
					robotChat.setNotevaluated(robotChat.getNotevaluated() + hrc.getNotevaluated());
					robotChat.setFailurefindknowledge(robotChat.getFailurefindknowledge() + hrc.getFailurefindknowledge());
					mapnew.put(key, robotChat);
				}
				// 删除掉存在其他的数据，统计后重新加入
				it.remove();
			}
		}
		for (String key : mapnew.keySet()) {
			HqrtRobotChat chat = mapnew.get(key);
			DecimalFormat df = new DecimalFormat("#0.00");
			chat.setConversionrate(df.format(chat.getConversionvolume()*0.1/chat.getTotalincount()*1000) + "%");
			hqrtRobotChatlist.add(chat);
		}
		for(int i = 0 ; i < hqrtRobotChatlist.size(); i++){
			hqrtRobotChatlist.get(i).setOrdernumber(i+1);
    	}
		map.put("rows", hqrtRobotChatlist);
		// map.put("total", page.getCount());
		return map;
	}
	
	/**
	 * 机器人对话列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "datadetails")
	public Map<String, Object> datadetails(HqrtRobotChatForDetails hqrtRobotChatDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
		List<String> queueNameList = new ArrayList<String>();
		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
			queueNameList.add(hqrtQueueConfig.getQueuename());
		}
		// 首先根据业务和省份分组查询
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chat a ";
		String sqlcondition = "";
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(hqrtRobotChatDetails.getQueuename())) {
			if (!hqrtRobotChatDetails.getQueuename().contains("其他")) {
				sqlcondition += " AND a.queuename in ('" + hqrtRobotChatDetails.getQueuename().replace(",", "','") + "')";
			} else {
				String[] queueselect = hqrtRobotChatDetails.getQueuename().split(",");
				for (String queuename : queueselect) {
					if (queueNameList.contains(queuename)) {
						queueNameList.remove(queuename);
					}
				}
				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
		}
		if (hqrtRobotChatDetails.getStarttime() != null && hqrtRobotChatDetails.getEndttime() != null) {
			sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paramList.add(ft.format(hqrtRobotChatDetails.getStarttime()));
			paramList.add(ft.format(hqrtRobotChatDetails.getEndttime()));
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
		if (StringUtils.isNotBlank(hqrtRobotChatDetails.getCustomerprovince())) {
			if (!hqrtRobotChatDetails.getCustomerprovince().contains("其他")) {
				String[] provinceselect = hqrtRobotChatDetails.getCustomerprovince().split(",");
				sqlcondition += " AND (";
				for (String province : provinceselect) {
					sqlcondition += "a.customerprovince like ? OR ";
					paramList.add("%" + province + "%");
				}
				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
				sqlcondition += ")";
			} else {
				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
				String[] provinceselect = hqrtRobotChatDetails.getCustomerprovince().split(",");
				for (String province : provinceselect) {
					if (hqrtCmccAreaList.contains(province)) {
						hqrtCmccAreaList.remove(province);
					}
				}
				sqlcondition += " AND (";
				for (String province : hqrtCmccAreaList) {
					sqlcondition += "a.customerprovince not like ? AND ";
					paramList.add("%" + province + "%");
				}
				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("AND"));
				sqlcondition += ")";
			}
		}
		if (StringUtils.isNotBlank(hqrtRobotChatDetails.getCustomername())) {
        	sqlcondition += " AND a.customername = ?";
        	paramList.add(hqrtRobotChatDetails.getCustomername());
        }
        if (StringUtils.isNotBlank(hqrtRobotChatDetails.getSessionid())) {
        	sqlcondition += " AND a.sessionid = ?";
        	paramList.add(hqrtRobotChatDetails.getSessionid());
        }
        if (StringUtils.isNotBlank(hqrtRobotChatDetails.getEndreasonno())) {
        	sqlcondition += " AND a.endreasonno = ?";
        	paramList.add(hqrtRobotChatDetails.getEndreasonno());
        }
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition  = " where" + sqlcondition;
		}
		Page<HqrtRobotChatForDetails> page = new Page<HqrtRobotChatForDetails>(request, response);
		hqrtRobotChatDetails.setPage(page);
        String selectcountsql = sql + sqlcondition;
		sql += sqlcondition + " limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
		List<HqrtRobotChatForDetails> hqrtRobotChatDetailslist = md.queryList(sql, HqrtRobotChatForDetails.class, paramList.toArray());
		List<HqrtRobotChatForDetails> allHqrtRobotChatDetailslist = md.queryList(selectcountsql, HqrtRobotChatForDetails.class, paramList.toArray());
		page.setCount(allHqrtRobotChatDetailslist.size());
		for (int i = 0; i < hqrtRobotChatDetailslist.size(); i++) {
			hqrtRobotChatDetailslist.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
		}
		page.setList(hqrtRobotChatDetailslist);
		return getBootstrapData(page);
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtRobotChat hqrtRobotChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "机器人拦截统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
         // 首先根据业务和省份分组查询
    		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
    		// 首先根据业务和省份分组查询
            String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chat a ";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
            if (StringUtils.isNotBlank(hqrtRobotChat.getQueuename())) {
            	if (!hqrtRobotChat.getQueuename().contains("其他")) {
            		sqlcondition += " AND a.queuename in ('" + hqrtRobotChat.getQueuename().replace(",", "','") + "')";
    			} else {
    				String[] queueselect = hqrtRobotChat.getQueuename().split(",");
    				for (String queuename : queueselect) {
    					if (queueNameList.contains(queuename)) {
    						queueNameList.remove(queuename);
    					}
    				}
    				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
    			}
            }
            if (hqrtRobotChat.getStarttime() != null && hqrtRobotChat.getEndttime() != null) {
            	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtRobotChat.getStarttime()));
            	paramList.add(ft.format(hqrtRobotChat.getEndttime()));
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
    		if (StringUtils.isNotBlank(hqrtRobotChat.getCustomerprovince())) {
    			if (!hqrtRobotChat.getCustomerprovince().contains("其他")) {
    				String[] provinceselect = hqrtRobotChat.getCustomerprovince().split(",");
    				sqlcondition += " AND (";
    				for (String province : provinceselect) {
    					sqlcondition += "a.customerprovince like ? OR ";
    					paramList.add("%" + province + "%");
    				}
    				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
    				sqlcondition += ")";
    			} else {
    				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
    				String[] provinceselect = hqrtRobotChat.getCustomerprovince().split(",");
    				for (String province : provinceselect) {
    					if (hqrtCmccAreaList.contains(province)) {
    						hqrtCmccAreaList.remove(province);
    					}
    				}
    				sqlcondition += " AND (";
    				for (String province : hqrtCmccAreaList) {
    					sqlcondition += "a.customerprovince not like ? AND ";
    					paramList.add("%" + province + "%");
    				}
    				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("AND"));
    				sqlcondition += ")";
    			}
            }
            if (StringUtils.isNotBlank(sqlcondition)) {
            	sqlcondition = sqlcondition.replaceFirst(" AND", "");
            	sqlcondition  = " where" + sqlcondition;
            }
            sql += sqlcondition + " GROUP BY a.QueueName,a.CustomerProvince";
            List<HqrtRobotChat> hqrtRobotChatlist = md.queryList(sql, HqrtRobotChat.class, paramList.toArray());
    		for (HqrtRobotChat robotChat : hqrtRobotChatlist) {
    			String _sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chat a";
    			String _sqlcondition = "";
    			paramList = new ArrayList<Object>();
    			if (StringUtils.isNotBlank(robotChat.getQueuename())) {
    				_sqlcondition += " AND a.queuename = ?";
    	        	paramList.add(robotChat.getQueuename());
    	        } else {
    				_sqlcondition += " AND (a.queuename = '' OR isnull(a.queuename))";
    	        }
    			if (StringUtils.isNotBlank(robotChat.getCustomerprovince())) {
    				_sqlcondition += " AND a.customerprovince like ? ";
    				paramList.add("%" + robotChat.getCustomerprovince() + "%");
    	        } else {
    				_sqlcondition += " AND (a.customerprovince = '' OR isnull(a.customerprovince))";
    	        }
    			if (hqrtRobotChat.getStarttime() != null && hqrtRobotChat.getEndttime() != null) {
    				_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
    	        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	        	paramList.add(ft.format(hqrtRobotChat.getStarttime()));
    	        	paramList.add(ft.format(hqrtRobotChat.getEndttime()));
    	        } else {
    	        	_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
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
    	        if (StringUtils.isNotBlank(_sqlcondition)) {
    	        	_sqlcondition = _sqlcondition.replaceFirst(" AND", "");
    	        	_sqlcondition  = " where" + _sqlcondition;
    	        }
    			_sql += _sqlcondition;
    			List<HqrtRobotChat> queueNameAndCustomerProvinceList = md.queryList(_sql, HqrtRobotChat.class, paramList.toArray());
    			robotChat.setTotalincount(queueNameAndCustomerProvinceList.size());
    			// 转人工量
    			int conversionvolume = 0;
    			// 用户提问总量
    			int totaluserquestions = 0;
    			// 机器人已解决量
    			int resolved = 0;
    			// 机器人未解决量
    			int unresolved = 0;
    			// 未评价
    			int notevaluated = 0;
    			// 未找到知识
    			int failurefindknowledge = 0;
    			HqrtRobotChatdetails hqrtRobotChatdetails = new HqrtRobotChatdetails();
    			for (HqrtRobotChat hrc : queueNameAndCustomerProvinceList) {
    				hqrtRobotChatdetails.setSessionid(hrc.getSessionid());
    				String __sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.queueid AS 'queueid',a.queuename AS 'queuename', a.requestcontext AS 'requestcontext',a.responsecontext AS 'responsecontext', a.responseno AS 'responseno', a.responsenodesc AS 'responsenodesc',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel', a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.satisfyno AS 'satisfyno',a.satisfydesc AS 'satisfydesc',a.MessageDateTime  AS 'messagedatetime',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chatdetails a ";
    				String __sqlcondition = "";
    				paramList = new ArrayList<Object>();
    				if (StringUtils.isNotBlank(hrc.getSessionid())) {
    					__sqlcondition += " AND a.sessionid = ?";
    		        	paramList.add(hrc.getSessionid());
    		        }
    		        if (StringUtils.isNotBlank(__sqlcondition)) {
    		        	__sqlcondition = __sqlcondition.replaceFirst(" AND", "");
    		        	__sqlcondition  = " where" + __sqlcondition;
    		        }
    				__sql += __sqlcondition;
    				List<HqrtRobotChatdetails> hqrtRobotChatdetailsList = md.queryList(__sql, HqrtRobotChatdetails.class, paramList.toArray());
    				if (hrc.getEndreasonno() == 1) {
    					conversionvolume++;
    				}
    				totaluserquestions += hqrtRobotChatdetailsList.size();
    				for (HqrtRobotChatdetails hrcdetails : hqrtRobotChatdetailsList) {
    					if (hrcdetails.getSatisfyno() == 2) {
    						resolved++;
    					}
    					if (hrcdetails.getSatisfyno() == 1) {
    						unresolved++;
    					}
    					if (hrcdetails.getSatisfyno() == 0) {
    						notevaluated++;
    					}
    					if (hrcdetails.getResponseno() == 100) {
    						failurefindknowledge++;
    					}
    				}
    			}
    			robotChat.setConversionvolume(conversionvolume);
    			robotChat.setTotaluserquestions(totaluserquestions);
    			robotChat.setResolved(resolved);
    			robotChat.setUnresolved(unresolved);
    			robotChat.setNotevaluated(notevaluated);
    			robotChat.setFailurefindknowledge(failurefindknowledge);
    			DecimalFormat df = new DecimalFormat("#0.00");
    			robotChat.setConversionrate(df.format(robotChat.getConversionvolume()*0.1/robotChat.getTotalincount()*1000) + "%");
    		}
    		Iterator<HqrtRobotChat> it = hqrtRobotChatlist.iterator();
    		List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
    		// map的key是queuename和province联合组成，用来唯一确定一条记录
    		Map<String, HqrtRobotChat> mapnew = new HashMap<String, HqrtRobotChat>();
    		while (it.hasNext()) {
    			HqrtRobotChat hrc = it.next();
    			Boolean isOtherProvince = true;
    			Boolean isOtherQueue = true;
    			for (String area : hqrtCmccAreaList) {
    				if (hrc.getCustomerprovince().contains(area)) {
    					isOtherProvince = false;
    					break;
    				}
    			}
    			if (queueNameList.contains(hrc.getQueuename()) || StringUtils.isNotBlank(hrc.getQueuename()) && hqrtRobotChat.getQueuename().contains(hrc.getQueuename())) {
    				isOtherQueue = false;
    			}
    			if (isOtherProvince || isOtherQueue) {
    				// 业务系统和省份有一个属于“其他”的，则需要重新统计
    				if (isOtherProvince) {
    					hrc.setCustomerprovince("其他");
    				}
    				if (isOtherQueue) {
    					hrc.setQueuename("其他");
    				}
    				String key = hrc.getQueuename() + hrc.getCustomerprovince();
    				HqrtRobotChat robotChat = mapnew.get(key);
    				if (robotChat == null) {
    					// 直接存储到map中
    					mapnew.put(key, hrc);
    				} else {
    					robotChat.setTotalincount(robotChat.getTotalincount() + hrc.getTotalincount());
    					robotChat.setConversionvolume(robotChat.getConversionvolume() + hrc.getConversionvolume());
    					robotChat.setTotaluserquestions(robotChat.getTotaluserquestions() + hrc.getTotaluserquestions());
    					robotChat.setResolved(robotChat.getResolved() + hrc.getResolved());
    					robotChat.setUnresolved(robotChat.getUnresolved() + hrc.getUnresolved());
    					robotChat.setNotevaluated(robotChat.getNotevaluated() + hrc.getNotevaluated());
    					robotChat.setFailurefindknowledge(robotChat.getFailurefindknowledge() + hrc.getFailurefindknowledge());
    					mapnew.put(key, robotChat);
    				}
    				// 删除掉存在其他的数据，统计后重新加入
    				it.remove();
    			}
    		}
    		for (String key : mapnew.keySet()) {
    			HqrtRobotChat chat = mapnew.get(key);
    			DecimalFormat df = new DecimalFormat("#0.00");
    			chat.setConversionrate(df.format(chat.getConversionvolume()*0.1/chat.getTotalincount()*1000) + "%");
    			hqrtRobotChatlist.add(chat);
    		}
    		for(int i = 0 ; i < hqrtRobotChatlist.size(); i++){
    			hqrtRobotChatlist.get(i).setOrdernumber(i+1);
        	}
    		new ExportExcel("机器人拦截统计", HqrtRobotChat.class).setDataList(hqrtRobotChatlist).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出机器人拦截统计记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequestMapping(value = "exportdetails")
	public AjaxJson exportdetails(HqrtRobotChatForDetails hqrtRobotChatDetails, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "机器人会话明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
			List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
			List<String> queueNameList = new ArrayList<String>();
			for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
				queueNameList.add(hqrtQueueConfig.getQueuename());
			}
			// 首先根据业务和省份分组查询
			String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chat a ";
			String sqlcondition = "";
			List<Object> paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(hqrtRobotChatDetails.getQueuename())) {
				if (!hqrtRobotChatDetails.getQueuename().contains("其他")) {
					sqlcondition += " AND a.queuename in ('" + hqrtRobotChatDetails.getQueuename().replace(",", "','") + "')";
				} else {
					String[] queueselect = hqrtRobotChatDetails.getQueuename().split(",");
					for (String queuename : queueselect) {
						if (queueNameList.contains(queuename)) {
							queueNameList.remove(queuename);
						}
					}
					sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
				}
			}
			if (hqrtRobotChatDetails.getStarttime() != null && hqrtRobotChatDetails.getEndttime() != null) {
				sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				paramList.add(ft.format(hqrtRobotChatDetails.getStarttime()));
				paramList.add(ft.format(hqrtRobotChatDetails.getEndttime()));
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
			if (StringUtils.isNotBlank(hqrtRobotChatDetails.getCustomerprovince())) {
				if (!hqrtRobotChatDetails.getCustomerprovince().contains("其他")) {
					String[] provinceselect = hqrtRobotChatDetails.getCustomerprovince().split(",");
					sqlcondition += " AND (";
					for (String province : provinceselect) {
						sqlcondition += "a.customerprovince like ? OR ";
						paramList.add("%" + province + "%");
					}
					sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
					sqlcondition += ")";
				} else {
					List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
					String[] provinceselect = hqrtRobotChatDetails.getCustomerprovince().split(",");
					for (String province : provinceselect) {
						if (hqrtCmccAreaList.contains(province)) {
							hqrtCmccAreaList.remove(province);
						}
					}
					sqlcondition += " AND (";
					for (String province : hqrtCmccAreaList) {
						sqlcondition += "a.customerprovince not like ? AND ";
						paramList.add("%" + province + "%");
					}
					sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("AND"));
					sqlcondition += ")";
				}
			}
			if (StringUtils.isNotBlank(hqrtRobotChatDetails.getCustomername())) {
	        	sqlcondition += " AND a.customername = ?";
	        	paramList.add(hqrtRobotChatDetails.getCustomername());
	        }
	        if (StringUtils.isNotBlank(hqrtRobotChatDetails.getSessionid())) {
	        	sqlcondition += " AND a.sessionid = ?";
	        	paramList.add(hqrtRobotChatDetails.getSessionid());
	        }
	        if (StringUtils.isNotBlank(hqrtRobotChatDetails.getEndreasonno())) {
	        	sqlcondition += " AND a.endreasonno = ?";
	        	paramList.add(hqrtRobotChatDetails.getEndreasonno());
	        }
			if (StringUtils.isNotBlank(sqlcondition)) {
				sqlcondition = sqlcondition.replaceFirst(" AND", "");
				sqlcondition  = " where" + sqlcondition;
			}
			sql += sqlcondition;
			List<HqrtRobotChatForDetails> hqrtRobotChatDetailslist = md.queryList(sql, HqrtRobotChatForDetails.class, paramList.toArray());
			for (int i = 0; i < hqrtRobotChatDetailslist.size(); i++) {
				hqrtRobotChatDetailslist.get(i).setOrdernumber(i+1);
			}
			new ExportExcel("机器人会话明细", HqrtRobotChatForDetails.class).setDataList(hqrtRobotChatDetailslist).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出机器人会话明细记录失败！失败信息："+e.getMessage());
		}
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequestMapping(value = "exportdetailsforview")
	public AjaxJson exportdetailsforview(HqrtRobotChat hqrtRobotChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "机器人会话明细内容"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
			// 首先根据业务和省份分组查询
			String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.queueid AS 'queueid',a.queuename AS 'queuename', a.requestcontext AS 'requestcontext',a.responsecontext AS 'responsecontext', a.responseno AS 'responseno', a.responsenodesc AS 'responsenodesc',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel', a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.satisfyno AS 'satisfyno',a.satisfydesc AS 'satisfydesc',a.MessageDateTime  AS 'messagedatetime',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chatdetails a where a.sessionid=?";
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(hqrtRobotChat.getSessionid());
			List<HqrtRobotChatdetailsForView> hqrtRobotChatDetailslist = md.queryList(sql, HqrtRobotChatdetailsForView.class, paramList.toArray());
			for (int i = 0; i < hqrtRobotChatDetailslist.size(); i++) {
				hqrtRobotChatDetailslist.get(i).setOrdernumber(i+1);
			}
			new ExportExcel("机器人会话明细内容", HqrtRobotChatdetailsForView.class).setDataList(hqrtRobotChatDetailslist).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出机器人会话明细内容记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 查看，增加，编辑客户与坐席会话表单页面
	 */
	@RequestMapping(value = "listforview")
	public String listforview(HqrtRobotChat hqrtRobotChat, Model model) {
		model.addAttribute("sessionid", hqrtRobotChat.getSessionid());
		return "modules/hqrt/robotchatdetails/hqrtRobotChatdetailsListForView";
	}
	
	/**
	 * 查看，增加，编辑客户与坐席会话表单页面
	 */
	@ResponseBody
	@RequestMapping(value = "dataforview")
	public Map<String, Object> dataforview(HqrtRobotChat hqrtRobotChat, Model model) {
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
		// 首先根据业务和省份分组查询
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.queueid AS 'queueid',a.queuename AS 'queuename', a.requestcontext AS 'requestcontext',a.responsecontext AS 'responsecontext', a.responseno AS 'responseno', a.responsenodesc AS 'responsenodesc',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel', a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.satisfyno AS 'satisfyno',a.satisfydesc AS 'satisfydesc',a.MessageDateTime  AS 'messagedatetime',a.originalsessionid AS 'originalsessionid' FROM hqrt_robot_chatdetails a where a.sessionid=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(hqrtRobotChat.getSessionid());
		List<HqrtRobotChatdetailsForView> hqrtRobotChatDetailslist = md.queryList(sql, HqrtRobotChatdetailsForView.class, paramList.toArray());
		for (int i = 0; i < hqrtRobotChatDetailslist.size(); i++) {
			hqrtRobotChatDetailslist.get(i).setOrdernumber(i+1);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", hqrtRobotChatDetailslist);
		return map;
	}

}