/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queuechat.web;

import java.text.DecimalFormat;
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
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChatdetail;
import com.jeeplus.modules.hqrt.queuechat.service.HqrtQueueChatService;

/**
 * 客户排队日志Controller
 * @author duan
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/queuechat/hqrtQueueChat")
public class HqrtQueueChatController extends BaseController {

	@Autowired
	private HqrtQueueChatService hqrtQueueChatService;
	
	@ModelAttribute
	public HqrtQueueChat get(@RequestParam(required=false) String id) {
		HqrtQueueChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtQueueChatService.get(id);
		}
		if (entity == null){
			entity = new HqrtQueueChat();
		}
		return entity;
	}
	
	/**
	 * 客户排队日志列表页面
	 */
	/*@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list")*/
	@RequestMapping(value = {"list", ""})
	public String list(HqrtQueueChat hqrtQueueChat, Model model) {
		model.addAttribute("hqrtQueueChat", hqrtQueueChat);
		return "modules/hqrt/queuechat/hqrtQueueChatList";
	}
	
		/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	/*@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list")*/
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
			hqrtQueueChat.setQueuenameList(Arrays.asList(hqrtQueueChat.getQueuename().split(",")));
        }
		// 首先根据业务分组查询
		List<HqrtQueueChat> hqrtQueueChatList = hqrtQueueChatService.findListGroupBy(hqrtQueueChat);
		for (HqrtQueueChat queueChat : hqrtQueueChatList) {
			List<HqrtQueueChat> queueNameChatList = hqrtQueueChatService.findListByQueueName(queueChat);
			queueChat.setTotalincount(queueNameChatList.size());
			//转通坐席量
			int conversionvolume = 0;
			//排队取消量
			int linupcancelvolume = 0;
			//排队超时量
			int linuptimeoutvolume = 0;
			for (HqrtQueueChat hrc : queueNameChatList) {
				if (hrc.getEndreasonno() == 1) {
					conversionvolume++;
				}
				if(hrc.getEndreasonno() == 2){
					linupcancelvolume++;
				}
				if(hrc.getEndreasonno() == 3){
					linuptimeoutvolume++;
				}
			}
			queueChat.setConversionvolume(conversionvolume);
			queueChat.setLinupcancelvolume(linupcancelvolume);
			queueChat.setLinuptimeoutvolume(linuptimeoutvolume);
			DecimalFormat df = new DecimalFormat("#0.00");
			queueChat.setConnectrate(df.format(queueChat.getConversionvolume()*0.1/queueChat.getTotalincount()*1000) + "%");
			queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume()*0.1/queueChat.getTotalincount()*1000) + "%");
			queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume()*0.1/queueChat.getTotalincount()*1000) + "%");
		}
		map.put("rows", hqrtQueueChatList);
		return map;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	/*@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:export")*/
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户排队日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
    			hqrtQueueChat.setQueuenameList(Arrays.asList(hqrtQueueChat.getQueuename().split(",")));
            }
    		// 首先根据业务分组查询
    		List<HqrtQueueChat> hqrtQueueChatList = hqrtQueueChatService.findListGroupBy(hqrtQueueChat);
    		for (HqrtQueueChat queueChat : hqrtQueueChatList) {
    			List<HqrtQueueChat> queueNameChatList = hqrtQueueChatService.findListByQueueName(queueChat);
    			queueChat.setTotalincount(queueNameChatList.size());
    			//转通坐席量
    			int conversionvolume = 0;
    			//排队取消量
    			int linupcancelvolume = 0;
    			//排队超时量
    			int linuptimeoutvolume = 0;
	    		for (HqrtQueueChat hrc : queueNameChatList) {
	    			if(hrc.getEndreasonno()==1) {
	    				conversionvolume++;
	    			}
	    			if(hrc.getEndreasonno()==2){
	    				linupcancelvolume++;
	    			}
	    			if(hrc.getEndreasonno()==3){
	    				linuptimeoutvolume++;
	    			}
	    		}	
	    		queueChat.setConversionvolume(conversionvolume);
	    		queueChat.setLinupcancelvolume(linupcancelvolume);
	    		queueChat.setLinuptimeoutvolume(linuptimeoutvolume);
	    		DecimalFormat df = new DecimalFormat("#0.00");
	    		queueChat.setConnectrate(df.format(queueChat.getConversionvolume()*0.1/queueChat.getTotalincount()*1000) + "%");
	    		queueChat.setCancelrate(df.format(queueChat.getLinupcancelvolume()*0.1/queueChat.getTotalincount()*1000) + "%");
	    		queueChat.setTimeoutrate(df.format(queueChat.getLinuptimeoutvolume()*0.1/queueChat.getTotalincount()*1000) + "%");
    		}
    		new ExportExcel("在线客服排队统计", HqrtQueueChat.class).setDataList(hqrtQueueChatList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户排队日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	/**
	 * 客户排队日志列表页面
	 */
	/*@RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list")*/
	@RequestMapping(value = "listdetail")
	public String list(HqrtQueueChatdetail hqrtQueueChatdetail, Model model) {
		model.addAttribute("HqrtQueueChatdetail", hqrtQueueChatdetail);
		return "modules/hqrt/queuechat/hqrtQueueChatdetailList";
	}
	
	/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	/*@RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list")*/
	@RequestMapping(value = "datadetail")
	public Map<String, Object> data(HqrtQueueChatdetail hqrtQueueChatdetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
			hqrtQueueChatdetail.setQueuenameList(Arrays.asList(hqrtQueueChatdetail.getQueuename().split(",")));
        }
		Page<HqrtQueueChatdetail> page = hqrtQueueChatService.findDetailPage(new Page<HqrtQueueChatdetail>(request, response), hqrtQueueChatdetail); 
		return getBootstrapData(page);
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	/*@RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:export")*/
    @RequestMapping(value = "exportdetail")
    public AjaxJson exportFile(HqrtQueueChatdetail hqrtQueueChatdetail, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户排队日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		if (StringUtils.isNotBlank(hqrtQueueChatdetail.getQueuename())) {
    			hqrtQueueChatdetail.setQueuenameList(Arrays.asList(hqrtQueueChatdetail.getQueuename().split(",")));
            }
    		Page<HqrtQueueChatdetail> page = hqrtQueueChatService.findDetailPage(new Page<HqrtQueueChatdetail>(request, response,-1), hqrtQueueChatdetail); 
    		new ExportExcel("在线客服排队统计", HqrtQueueChatdetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户排队日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

}