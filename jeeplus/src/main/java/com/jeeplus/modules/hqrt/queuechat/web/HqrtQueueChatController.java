/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChatdetail;
import com.jeeplus.modules.hqrt.queuechat.service.HqrtQueueChatService;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 客户排队日志Controller
 * 
 * @author duan
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/queuechat/hqrtQueueChat")
public class HqrtQueueChatController extends BaseController {

	@Autowired
	private HqrtQueueChatService hqrtQueueChatService;

	@ModelAttribute
	public HqrtQueueChat get(@RequestParam(required = false) String id) {
		HqrtQueueChat entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = hqrtQueueChatService.get(id);
		}
		if (entity == null) {
			entity = new HqrtQueueChat();
		}
		return entity;
	}

	/**
	 * 客户排队日志列表页面
	 */
	/* @RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list") */
	@RequestMapping(value = { "list", "" })
	public String list(HqrtQueueChat hqrtQueueChat, Model model) {
		model.addAttribute("hqrtQueueChat", hqrtQueueChat);
		return "modules/hqrt/queuechat/hqrtQueueChatList";
	}

	/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	/* @RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list") */
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtQueueChat hqrtQueueChat, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		 Map<String, Object> map = new HashMap<String, Object>(); 
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
		String sqlcondition = "";
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
			// List<String> queuenameList =
			// Arrays.asList(hqrtAgentChat.getQueuename().split(","));
			sqlcondition += " AND a.queuename in ('" + hqrtQueueChat.getQueuename().replace(",", "','") + "')";
			// paramList.add(queuenameList);
		}
		if (hqrtQueueChat.getStarttime() != null && hqrtQueueChat.getEndttime() != null) {
			sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paramList.add(ft.format(hqrtQueueChat.getStarttime()));
			paramList.add(ft.format(hqrtQueueChat.getEndttime()));
		}
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition = " WHERE" + sqlcondition;
		}
		sql += sqlcondition + " GROUP BY a.QueueName";
		MultiDBUtils md = MultiDBUtils.get("company");
		List<HqrtQueueChat> queryListGroupby = md.queryList(sql, HqrtQueueChat.class, paramList.toArray());
		for (HqrtQueueChat queueChat : queryListGroupby) {
			String __sql= "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
			String _sqlcondition = "";
			paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(queueChat.getQueuename())) {
				// List<String> queuenameList =
				// Arrays.asList(hqrtAgentChat.getQueuename().split(","));
				_sqlcondition += " AND a.queuename in ('" + queueChat.getQueuename().replace(",", "','") + "')";
				// paramList.add(queuenameList);
			}
			if (queueChat.getStarttime() != null && queueChat.getEndttime() != null) {
				_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				paramList.add(ft.format(queueChat.getStarttime()));
				paramList.add(ft.format(queueChat.getEndttime()));
			}
			if (StringUtils.isNotBlank(_sqlcondition)) {
				_sqlcondition = _sqlcondition.replaceFirst(" AND", "");
				_sqlcondition = " WHERE" + _sqlcondition;
			}
			__sql += _sqlcondition;
			MultiDBUtils mu = MultiDBUtils.get("company");
			List<HqrtQueueChat> queryList = mu.queryList(__sql, HqrtQueueChat.class, paramList.toArray());
			queueChat.setTotalincount(queryList.size());
			// 转通坐席量
			int conversionvolume = 0;
			// 排队取消量
			int linupcancelvolume = 0;
			// 排队超时量
			int linuptimeoutvolume = 0;
			for (HqrtQueueChat hrc : queryList) {

				if (hrc.getEndreasonno() == 1) {
					conversionvolume++;
				}
				if (hrc.getEndreasonno() == 2) {
					linupcancelvolume++;
				}
				if (hrc.getEndreasonno() == 3) {
					linuptimeoutvolume++;
				}
				queueChat.setConversionvolume(conversionvolume);
				queueChat.setLinupcancelvolume(linupcancelvolume);
				queueChat.setLinuptimeoutvolume(linuptimeoutvolume);
				DecimalFormat df = new DecimalFormat("#0.00");
				queueChat.setConnectrate(
						df.format(queueChat.getConversionvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
				queueChat.setCancelrate(
						df.format(queueChat.getLinupcancelvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
				queueChat.setTimeoutrate(
						df.format(queueChat.getLinuptimeoutvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");

			}
		}
		map.put("rows", queryListGroupby);
		// map.put("total", page.getCount());
		return map;
		/*
		 * if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
		 * hqrtQueueChat.setQueuenameList(Arrays.asList(hqrtQueueChat.
		 * getQueuename().split(","))); } // 首先根据业务分组查询 List<HqrtQueueChat>
		 * hqrtQueueChatList =
		 * hqrtQueueChatService.findListGroupBy(hqrtQueueChat); for
		 * (HqrtQueueChat queueChat : hqrtQueueChatList) { List<HqrtQueueChat>
		 * queueNameChatList =
		 * hqrtQueueChatService.findListByQueueName(queueChat);
		 * queueChat.setTotalincount(queueNameChatList.size()); //转通坐席量 int
		 * conversionvolume = 0; //排队取消量 int linupcancelvolume = 0; //排队超时量 int
		 * linuptimeoutvolume = 0; for (HqrtQueueChat hrc : queueNameChatList) {
		 * if (hrc.getEndreasonno() == 1) { conversionvolume++; }
		 * if(hrc.getEndreasonno() == 2){ linupcancelvolume++; }
		 * if(hrc.getEndreasonno() == 3){ linuptimeoutvolume++; } }
		 * queueChat.setConversionvolume(conversionvolume);
		 * queueChat.setLinupcancelvolume(linupcancelvolume);
		 * queueChat.setLinuptimeoutvolume(linuptimeoutvolume); DecimalFormat df
		 * = new DecimalFormat("#0.00");
		 * queueChat.setConnectrate(df.format(queueChat.getConversionvolume()*0.
		 * 1/queueChat.getTotalincount()*1000) + "%");
		 * queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume()*0.
		 * 1/queueChat.getTotalincount()*1000) + "%");
		 * queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume()*
		 * 0.1/queueChat.getTotalincount()*1000) + "%"); } map.put("rows",
		 * hqrtQueueChatList);
		 */
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	/* @RequiresPermissions("hqrt:queuechat:hqrtQueueChat:export") */
	@RequestMapping(value = "export")
	public AjaxJson exportFile(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "客户排队日志" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
			String sqlcondition = "";
			List<Object> paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
				// List<String> queuenameList =
				// Arrays.asList(hqrtAgentChat.getQueuename().split(","));
				sqlcondition += " AND a.queuename in ('" + hqrtQueueChat.getQueuename().replace(",", "','") + "')";
				// paramList.add(queuenameList);
			}
			if (hqrtQueueChat.getStarttime() != null && hqrtQueueChat.getEndttime() != null) {
				sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				paramList.add(ft.format(hqrtQueueChat.getStarttime()));
				paramList.add(ft.format(hqrtQueueChat.getEndttime()));
			}
			if (StringUtils.isNotBlank(sqlcondition)) {
				sqlcondition = sqlcondition.replaceFirst(" AND", "");
				sqlcondition = " WHERE" + sqlcondition;
			}
			sql += sqlcondition + " GROUP BY a.QueueName";
			MultiDBUtils md = MultiDBUtils.get("company");
			List<HqrtQueueChat> queryListGroupby = md.queryList(sql, HqrtQueueChat.class, paramList.toArray());
			for (HqrtQueueChat queueChat : queryListGroupby) {
				String __sql= "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
				String _sqlcondition = "";
				paramList = new ArrayList<Object>();
				if (StringUtils.isNotBlank(queueChat.getQueuename())) {
					// List<String> queuenameList =
					// Arrays.asList(hqrtAgentChat.getQueuename().split(","));
					_sqlcondition += " AND a.queuename in ('" + queueChat.getQueuename().replace(",", "','") + "')";
					// paramList.add(queuenameList);
				}
				if (queueChat.getStarttime() != null && queueChat.getEndttime() != null) {
					_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					paramList.add(ft.format(queueChat.getStarttime()));
					paramList.add(ft.format(queueChat.getEndttime()));
				}
				if (StringUtils.isNotBlank(_sqlcondition)) {
					_sqlcondition = _sqlcondition.replaceFirst(" AND", "");
					_sqlcondition = " WHERE" + _sqlcondition;
				}
				__sql += _sqlcondition;
				MultiDBUtils mu = MultiDBUtils.get("company");
				List<HqrtQueueChat> queryList = mu.queryList(__sql, HqrtQueueChat.class, paramList.toArray());
				queueChat.setTotalincount(queryList.size());
				// 转通坐席量
				int conversionvolume = 0;
				// 排队取消量
				int linupcancelvolume = 0;
				// 排队超时量
				int linuptimeoutvolume = 0;
				for (HqrtQueueChat hrc : queryList) {

					if (hrc.getEndreasonno() == 1) {
						conversionvolume++;
					}
					if (hrc.getEndreasonno() == 2) {
						linupcancelvolume++;
					}
					if (hrc.getEndreasonno() == 3) {
						linuptimeoutvolume++;
					}
					queueChat.setConversionvolume(conversionvolume);
					queueChat.setLinupcancelvolume(linupcancelvolume);
					queueChat.setLinuptimeoutvolume(linuptimeoutvolume);
					DecimalFormat df = new DecimalFormat("#0.00");
					queueChat.setConnectrate(
							df.format(queueChat.getConversionvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					queueChat.setCancelrate(
							df.format(queueChat.getLinupcancelvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					queueChat.setTimeoutrate(
							df.format(queueChat.getLinuptimeoutvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");

				}
			}
			new ExportExcel("在线客服排队统计", HqrtQueueChat.class).setDataList(hqrtQueueChatList).write(response, fileName)
					.dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户排队日志记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 客户排队日志列表页面
	 */
	/* @RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list") */
	@RequestMapping(value = "listdetail")
	public String list(HqrtQueueChatdetail hqrtQueueChatdetail, Model model) {
		model.addAttribute("HqrtQueueChatdetail", hqrtQueueChatdetail);
		return "modules/hqrt/queuechat/hqrtQueueChatdetailList";
	}

	/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	/* @RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list") */
	@RequestMapping(value = "datadetail")
	public Map<String, Object> data(HqrtQueueChatdetail hqrtQueueChatdetail, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<HqrtQueueChatdetail> page = new Page<>(request, response);
		BaseService.dataRuleFilter(hqrtQueueChatdetail);
		hqrtQueueChatdetail.setPage(page);
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
		String sqlcondition = "";
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
			// List<String> queuenameList =
			// Arrays.asList(hqrtAgentChat.getQueuename().split(","));
			sqlcondition += " AND a.queuename in ('" + hqrtQueueChatdetail.getQueuename().replace(",", "','") + "')";
			// paramList.add(queuenameList);
		}
		if (hqrtQueueChatdetail.getStarttime() != null && hqrtQueueChatdetail.getEndttime() != null) {
			sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paramList.add(ft.format(hqrtQueueChatdetail.getStarttime()));
			paramList.add(ft.format(hqrtQueueChatdetail.getEndttime()));
		}
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition = " WHERE" + sqlcondition;
		}
		if (StringUtils.isNotBlank(hqrtQueueChatdetail.getEndreasonno())) {
			sqlcondition += " AND a.endreasonno = ?";
			paramList.add(hqrtQueueChatdetail.getEndreasonno());
		}
		sql += sqlcondition;
		MultiDBUtils md = MultiDBUtils.get("company");
		List<HqrtQueueChatdetail> queryList = md.queryList(sql, HqrtQueueChatdetail.class, paramList.toArray());
		page.setList(queryList);
		return getBootstrapData(page);
		/*
		 * if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
		 * hqrtQueueChatdetail.setQueuenameList(Arrays.asList(
		 * hqrtQueueChatdetail.getQueuename().split(","))); }
		 * Page<HqrtQueueChatdetail> page = hqrtQueueChatService
		 * .findDetailPage(new Page<HqrtQueueChatdetail>(request, response),
		 * hqrtQueueChatdetail); return getBootstrapData(page);
		 */
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	/*
	 * @RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:export")
	 */
	@RequestMapping(value = "exportdetail")
	public AjaxJson exportFile(HqrtQueueChatdetail hqrtQueueChatdetail, HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "客户排队日志" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			/*
			 * if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
			 * hqrtQueueChatdetail.setQueuenameList(Arrays.asList(
			 * hqrtQueueChatdetail.getQueuename().split(","))); }
			 * Page<HqrtQueueChatdetail> page = hqrtQueueChatService
			 * .findDetailPage(new Page<HqrtQueueChatdetail>(request, response,
			 * -1), hqrtQueueChatdetail);
			 */
			Page<HqrtQueueChatdetail> page = new Page<>(request, response);
			BaseService.dataRuleFilter(hqrtQueueChatdetail);
			hqrtQueueChatdetail.setPage(page);
			String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
			String sqlcondition = "";
			List<Object> paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
				// List<String> queuenameList =
				// Arrays.asList(hqrtAgentChat.getQueuename().split(","));
				sqlcondition += " AND a.queuename in ('" + hqrtQueueChatdetail.getQueuename().replace(",", "','")
						+ "')";
				// paramList.add(queuenameList);
			}
			if (hqrtQueueChatdetail.getStarttime() != null && hqrtQueueChatdetail.getEndttime() != null) {
				sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				paramList.add(ft.format(hqrtQueueChatdetail.getStarttime()));
				paramList.add(ft.format(hqrtQueueChatdetail.getEndttime()));
			}
			if (StringUtils.isNotBlank(sqlcondition)) {
				sqlcondition = sqlcondition.replaceFirst(" AND", "");
				sqlcondition = " WHERE" + sqlcondition;
			}
			if (StringUtils.isNotBlank(hqrtQueueChatdetail.getEndreasonno())) {
				sqlcondition += " AND a.endreasonno = ?";
				paramList.add(hqrtQueueChatdetail.getEndreasonno());
			}
			sql += sqlcondition;
			MultiDBUtils md = MultiDBUtils.get("company");
			List<HqrtQueueChatdetail> queryList = md.queryList(sql, HqrtQueueChatdetail.class, paramList.toArray());
			page.setList(queryList);
			new ExportExcel("在线客服排队统计", HqrtQueueChatdetail.class).setDataList(page.getList()).write(response, fileName)
					.dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户排队日志记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

}