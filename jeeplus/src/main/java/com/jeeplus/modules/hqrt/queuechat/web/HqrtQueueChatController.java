/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChatdetail;
import com.jeeplus.modules.hqrt.queuechat.service.HqrtQueueChatService;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;
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
	public String list(
			/*@RequestParam(name="timestamp", required=true) Long timestamp,
			@RequestParam(name="sign", required=true) String sign,*/
			HqrtQueueChat hqrtQueueChat, Model model) {
		/*long nowtimestamp = System.currentTimeMillis();
		Long ratio = Math.round(nowtimestamp*0.1/600000*10);
		if (ratio.equals(timestamp)) {
			String salt = ratio + Global.getConfig("secretkey");
			String md5 = DigestUtils.md5Hex(salt);
			if (md5.equals(sign)) {
				model.addAttribute("hqrtQueueChat", hqrtQueueChat);
				return "modules/hqrt/queuechat/hqrtQueueChatList";
			} else {
				return "modules/hqrt/404";
			}
		} else {
			return "modules/hqrt/404";
		}*/
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
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtQueueChat.getQueuename().contains("其他")) {
        		sqlcondition += " AND a.queuename in ('" + hqrtQueueChat.getQueuename().replace(",", "','") + "')";
			} else {
				String[] queueselect = hqrtQueueChat.getQueuename().split(",");
				for (String queuename : queueselect) {
					if (queueNameList.contains(queuename)) {
						queueNameList.remove(queuename);
					}
				}
				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
		if (hqrtQueueChat.getStarttime() != null && hqrtQueueChat.getEndttime() != null) {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtQueueChat.getStarttime()));
        	paramList.add(ft.format(hqrtQueueChat.getEndttime()));
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
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition = " WHERE" + sqlcondition;
		}
		sql += sqlcondition + " GROUP BY a.QueueName";
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
		List<HqrtQueueChat> queryListGroupby = md.queryList(sql, HqrtQueueChat.class, paramList.toArray());
		for (HqrtQueueChat queueChat : queryListGroupby) {
			String _sql= "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
			String _sqlcondition = "";
			paramList = new ArrayList<Object>();
			
			if (StringUtils.isNotBlank(queueChat.getQueuename())) {
				_sqlcondition += " AND a.queuename in ('" + queueChat.getQueuename().replace(",", "','") + "')";
			} else {
				_sqlcondition += " AND (a.queuename = '' || isnull(a.queuename))";
			}
			if (hqrtQueueChat.getStarttime() != null && hqrtQueueChat.getEndttime() != null) {
				_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
	        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	paramList.add(ft.format(hqrtQueueChat.getStarttime()));
	        	paramList.add(ft.format(hqrtQueueChat.getEndttime()));
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
				_sqlcondition = " WHERE" + _sqlcondition;
			}
			_sql += _sqlcondition;
			MultiDBUtils mu = MultiDBUtils.get(Global.getConfig("datasourcename"));
			List<HqrtQueueChat> queryList = mu.queryList(_sql, HqrtQueueChat.class, paramList.toArray());
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
				queueChat.setConnectrate(df.format(queueChat.getConversionvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
				queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
				queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
			}
		}
		Iterator<HqrtQueueChat> it = queryListGroupby.iterator();
		// map的key是queuename和province联合组成，用来唯一确定一条记录
		Map<String, HqrtQueueChat> mapnew = new HashMap<String, HqrtQueueChat>();
		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
		List<String> queueNameList = new ArrayList<String>();
		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
			queueNameList.add(hqrtQueueConfig.getQueuename());
		}
		while (it.hasNext()) {
			HqrtQueueChat hqc = it.next();
			if (StringUtils.isBlank(hqc.getQueuename()) || !queueNameList.contains(hqc.getQueuename())) {
				// 业务系统有属于“其他”的，则需要重新统计
				hqc.setQueuename("其他");
				String key = hqc.getQueuename();
				HqrtQueueChat queueChat = mapnew.get(key);
				if (queueChat == null) {
					// 直接存储到map中
					mapnew.put(key, hqc);
				} else {
					queueChat.setTotalincount(queueChat.getTotalincount() + hqc.getTotalincount());
					queueChat.setConversionvolume(queueChat.getConversionvolume() + hqc.getConversionvolume());
					queueChat.setLinupcancelvolume(queueChat.getLinupcancelvolume() + hqc.getLinupcancelvolume());
					queueChat.setLinuptimeoutvolume(queueChat.getLinuptimeoutvolume() + hqc.getLinuptimeoutvolume());
					DecimalFormat df = new DecimalFormat("#0.00"); 
					queueChat.setConnectrate(df.format(queueChat.getConversionvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					mapnew.put(key, queueChat);
				}
				// 删除掉存在其他的数据，统计后重新加入
				it.remove();
			}
		}
		for (String key : mapnew.keySet()) {
			HqrtQueueChat chat = mapnew.get(key);
			queryListGroupby.add(chat);
		}
		for (int i = 0; i < queryListGroupby.size(); i++) {
			queryListGroupby.get(i).setOrdernumber(i+1);
		}
		Collections.sort(queryListGroupby, new Comparator<HqrtQueueChat>() {
			public int compare(HqrtQueueChat p1, HqrtQueueChat p2) {
			return p1.getQueuename().compareTo(p2.getQueuename());
			   }
			});
		map.put("rows", queryListGroupby);
		return map;
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
			String fileName = "排队统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
			String sqlcondition = "";
			List<Object> paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
	        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
	    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
	    		List<String> queueNameList = new ArrayList<String>();
	    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
	    			queueNameList.add(hqrtQueueConfig.getQueuename());
	    		}
	        	if (!hqrtQueueChat.getQueuename().contains("其他")) {
	        		sqlcondition += " AND a.queuename in ('" + hqrtQueueChat.getQueuename().replace(",", "','") + "')";
				} else {
					String[] queueselect = hqrtQueueChat.getQueuename().split(",");
					for (String queuename : queueselect) {
						if (queueNameList.contains(queuename)) {
							queueNameList.remove(queuename);
						}
					}
					sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
				}
	        }
			if (hqrtQueueChat.getStarttime() != null && hqrtQueueChat.getEndttime() != null) {
	        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
	        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	paramList.add(ft.format(hqrtQueueChat.getStarttime()));
	        	paramList.add(ft.format(hqrtQueueChat.getEndttime()));
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
			if (StringUtils.isNotBlank(sqlcondition)) {
				sqlcondition = sqlcondition.replaceFirst(" AND", "");
				sqlcondition = " WHERE" + sqlcondition;
			}
			sql += sqlcondition + " GROUP BY a.QueueName";
			MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
			List<HqrtQueueChat> queryListGroupby = md.queryList(sql, HqrtQueueChat.class, paramList.toArray());
			for (HqrtQueueChat queueChat : queryListGroupby) {
				String _sql= "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
				String _sqlcondition = "";
				paramList = new ArrayList<Object>();
				
				if (StringUtils.isNotBlank(queueChat.getQueuename())) {
					_sqlcondition += " AND a.queuename in ('" + queueChat.getQueuename().replace(",", "','") + "')";
				} else {
					_sqlcondition += " AND (a.queuename = '' || isnull(a.queuename))";
				}
				if (hqrtQueueChat.getStarttime() != null && hqrtQueueChat.getEndttime() != null) {
					_sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
		        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        	paramList.add(ft.format(hqrtQueueChat.getStarttime()));
		        	paramList.add(ft.format(hqrtQueueChat.getEndttime()));
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
					_sqlcondition = " WHERE" + _sqlcondition;
				}
				_sql += _sqlcondition;
				MultiDBUtils mu = MultiDBUtils.get(Global.getConfig("datasourcename"));
				List<HqrtQueueChat> queryList = mu.queryList(_sql, HqrtQueueChat.class, paramList.toArray());
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
					queueChat.setConnectrate(df.format(queueChat.getConversionvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
					queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
				}
			}
			Iterator<HqrtQueueChat> it = queryListGroupby.iterator();
			// map的key是queuename和province联合组成，用来唯一确定一条记录
			Map<String, HqrtQueueChat> mapnew = new HashMap<String, HqrtQueueChat>();
			List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
			List<String> queueNameList = new ArrayList<String>();
			for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
				queueNameList.add(hqrtQueueConfig.getQueuename());
			}
			while (it.hasNext()) {
				HqrtQueueChat hqc = it.next();
				if (StringUtils.isBlank(hqc.getQueuename()) || !queueNameList.contains(hqc.getQueuename())) {
					// 业务系统有属于“其他”的，则需要重新统计
					hqc.setQueuename("其他");
					String key = hqc.getQueuename();
					HqrtQueueChat queueChat = mapnew.get(key);
					if (queueChat == null) {
						// 直接存储到map中
						mapnew.put(key, hqc);
					} else {
						queueChat.setTotalincount(queueChat.getTotalincount() + hqc.getTotalincount());
						queueChat.setConversionvolume(queueChat.getConversionvolume() + hqc.getConversionvolume());
						queueChat.setLinupcancelvolume(queueChat.getLinupcancelvolume() + hqc.getLinupcancelvolume());
						queueChat.setLinuptimeoutvolume(queueChat.getLinuptimeoutvolume() + hqc.getLinuptimeoutvolume());
						DecimalFormat df = new DecimalFormat("#0.00"); 
						queueChat.setConnectrate(df.format(queueChat.getConversionvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
						queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
						queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume() * 0.1 / queueChat.getTotalincount() * 1000) + "%");
						mapnew.put(key, queueChat);
					}
					// 删除掉存在其他的数据，统计后重新加入
					it.remove();
				}
			}
			for (String key : mapnew.keySet()) {
				HqrtQueueChat chat = mapnew.get(key);
				queryListGroupby.add(chat);
			}
			for (int i = 0; i < queryListGroupby.size(); i++) {
				queryListGroupby.get(i).setOrdernumber(i+1);
			}
			Collections.sort(queryListGroupby, new Comparator<HqrtQueueChat>() {
				public int compare(HqrtQueueChat p1, HqrtQueueChat p2) {
				return p1.getQueuename().compareTo(p2.getQueuename());
				   }
				});
			new ExportExcel("排队统计", HqrtQueueChat.class).setDataList(queryListGroupby).write(response, fileName)
					.dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出排队统计记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 客户排队日志列表页面
	 */
	/* @RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list") */
	@RequestMapping(value = "listdetail")
	public String list(
			/*@RequestParam(name="timestamp", required=true) Long timestamp,
			@RequestParam(name="sign", required=true) String sign,*/
			HqrtQueueChatdetail hqrtQueueChatdetail, Model model) {
		/*long nowtimestamp = System.currentTimeMillis();
		Long ratio = Math.round(nowtimestamp*0.1/600000*10);
		if (ratio.equals(timestamp)) {
			String salt = ratio + Global.getConfig("secretkey");
			String md5 = DigestUtils.md5Hex(salt);
			if (md5.equals(sign)) {
				model.addAttribute("HqrtQueueChatdetail", hqrtQueueChatdetail);
				return "modules/hqrt/queuechat/hqrtQueueChatdetailList";
			} else {
				return "modules/hqrt/404";
			}
		} else {
			return "modules/hqrt/404";
		}*/
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
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
		String sqlcondition = "";
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtQueueChatdetail.getQueuename().contains("其他")) {
        		sqlcondition += " AND a.queuename in ('" + hqrtQueueChatdetail.getQueuename().replace(",", "','") + "')";
			} else {
				String[] queueselect = hqrtQueueChatdetail.getQueuename().split(",");
				for (String queuename : queueselect) {
					if (queueNameList.contains(queuename)) {
						queueNameList.remove(queuename);
					}
				}
				sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
		if (hqrtQueueChatdetail.getStarttime() != null && hqrtQueueChatdetail.getEndttime() != null) {
        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtQueueChatdetail.getStarttime()));
        	paramList.add(ft.format(hqrtQueueChatdetail.getEndttime()));
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
		if (StringUtils.isNotBlank(hqrtQueueChatdetail.getEndreasonno())) {
			sqlcondition += " AND a.endreasonno = ?";
			paramList.add(hqrtQueueChatdetail.getEndreasonno());
		}
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition = " WHERE" + sqlcondition;
		}
		Page<HqrtQueueChatdetail> page = new Page<HqrtQueueChatdetail>(request, response);
		String selectcountsql = sql + sqlcondition;
        sql += sqlcondition + "ORDER BY a.queuename limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        List<HqrtQueueChatdetail> detailsList = md.queryList(sql, HqrtQueueChatdetail.class, paramList.toArray());
        List<HqrtQueueChatdetail> allDetailslList = md.queryList(selectcountsql, HqrtQueueChatdetail.class, paramList.toArray());
    	for(int i = 0 ; i < detailsList.size(); i++){
    		detailsList.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
    	}
        page.setCount(allDetailslList.size());
		hqrtQueueChatdetail.setPage(page);
		page.setList(detailsList);
		return getBootstrapData(page);
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
			String fileName = "排队明细日志" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.sessionid AS 'sessionid',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.startdatetime AS 'startdatetime',a.enddatetime AS 'enddatetime',a.timelen AS 'timelen',a.endreasonno AS 'endreasonno',a.endreason AS 'endreason',a.queueid AS 'queueid',a.queuename AS 'queuename',a.originalsessionid AS 'originalsessionid' FROM hqrt_queue_chat a";
			String sqlcondition = "";
			List<Object> paramList = new ArrayList<Object>();
			if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
	        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
	    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
	    		List<String> queueNameList = new ArrayList<String>();
	    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
	    			queueNameList.add(hqrtQueueConfig.getQueuename());
	    		}
	        	if (!hqrtQueueChatdetail.getQueuename().contains("其他")) {
	        		sqlcondition += " AND a.queuename in ('" + hqrtQueueChatdetail.getQueuename().replace(",", "','") + "')";
				} else {
					String[] queueselect = hqrtQueueChatdetail.getQueuename().split(",");
					for (String queuename : queueselect) {
						if (queueNameList.contains(queuename)) {
							queueNameList.remove(queuename);
						}
					}
					sqlcondition += " AND a.queuename not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
				}
	        }
			if (hqrtQueueChatdetail.getStarttime() != null && hqrtQueueChatdetail.getEndttime() != null) {
	        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
	        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	paramList.add(ft.format(hqrtQueueChatdetail.getStarttime()));
	        	paramList.add(ft.format(hqrtQueueChatdetail.getEndttime()));
	        } else {
	        	sqlcondition += " AND a.startdatetime BETWEEN ? AND ?";
	        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	Calendar cal = Calendar.getInstance();
	            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	            Date beginOfDate = cal.getTime();
	        	paramList.add(ft.format(beginOfDate));
	        	Calendar calendar2 = Calendar.getInstance();
	        	calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
	        	Date endOfDate = calendar2.getTime();
	        	paramList.add(ft.format(endOfDate));
	        }
			if (StringUtils.isNotBlank(hqrtQueueChatdetail.getEndreasonno())) {
				sqlcondition += " AND a.endreasonno = ?";
				paramList.add(hqrtQueueChatdetail.getEndreasonno());
			}
			if (StringUtils.isNotBlank(sqlcondition)) {
				sqlcondition = sqlcondition.replaceFirst(" AND", "");
				sqlcondition = " WHERE" + sqlcondition;
			}
			sql += sqlcondition + "ORDER BY a.queuename";
			MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
			List<HqrtQueueChatdetail> queryList = md.queryList(sql, HqrtQueueChatdetail.class, paramList.toArray());
			for(int i = 0 ; i < queryList.size(); i++){
				queryList.get(i).setOrdernumber(i+1);
        	}
			new ExportExcel("排队明细日志", HqrtQueueChatdetail.class).setDataList(queryList).write(response, fileName)
					.dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出排队明细日志记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

}