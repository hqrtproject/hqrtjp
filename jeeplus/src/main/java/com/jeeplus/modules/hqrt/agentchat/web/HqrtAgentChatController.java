/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.agentchat.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.agentchat.entity.HqrtAgentChat;
import com.jeeplus.modules.hqrt.agentchat.service.HqrtAgentChatService;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetails;
import com.jeeplus.modules.hqrt.agentchatdetails.entity.HqrtAgentChatdetailsForExport;
import com.jeeplus.modules.hqrt.agentchatdetails.service.HqrtAgentChatdetailsService;

/**
 * 客户与坐席会话Controller
 * @author cz
 * @version 2019-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/agentchat/hqrtAgentChat")
public class HqrtAgentChatController extends BaseController {

	@Autowired
	private HqrtAgentChatService hqrtAgentChatService;
	
	@Autowired
	private HqrtAgentChatdetailsService hqrtAgentChatdetailsService;
	
	@ModelAttribute
	public HqrtAgentChat get(@RequestParam(required=false) String id) {
		HqrtAgentChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtAgentChatService.get(id);
		}
		if (entity == null){
			entity = new HqrtAgentChat();
		}
		return entity;
	}
	
	/**
	 * 客户与坐席会话列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtAgentChat hqrtAgentChat, Model model) {
		model.addAttribute("hqrtAgentChat", hqrtAgentChat);
		return "modules/hqrt/agentchat/hqrtAgentChatList";
	}
	
	/**
	 * 客户与坐席会话列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtAgentChat hqrtAgentChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(hqrtAgentChat.getCustomerprovince())) {
			hqrtAgentChat.setCustomerprovinceList(Arrays.asList(hqrtAgentChat.getCustomerprovince().split(",")));
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getQueuename())) {
        	hqrtAgentChat.setQueuenameList(Arrays.asList(hqrtAgentChat.getQueuename().split(",")));
        }
        if (StringUtils.isNotBlank(hqrtAgentChat.getAgentname())) {
        	hqrtAgentChat.setAgentnameList(Arrays.asList(hqrtAgentChat.getAgentname().split(",")));
        }
		Page<HqrtAgentChat> page = hqrtAgentChatService.findPage(new Page<HqrtAgentChat>(request, response), hqrtAgentChat); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户与坐席会话表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtAgentChat hqrtAgentChat, Model model) {
		model.addAttribute("hqrtAgentChat", hqrtAgentChat);
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
            String fileName = "客户与坐席会话"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		if (StringUtils.isNotBlank(hqrtAgentChat.getCustomerprovince())) {
    			hqrtAgentChat.setCustomerprovinceList(Arrays.asList(hqrtAgentChat.getCustomerprovince().split(",")));
            }
            if (StringUtils.isNotBlank(hqrtAgentChat.getQueuename())) {
            	hqrtAgentChat.setQueuenameList(Arrays.asList(hqrtAgentChat.getQueuename().split(",")));
            }
            Page<HqrtAgentChat> page = hqrtAgentChatService.findPage(new Page<HqrtAgentChat>(request, response, -1), hqrtAgentChat);
    		new ExportExcel("客户与坐席会话", HqrtAgentChat.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户与坐席会话记录失败！失败信息："+e.getMessage());
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
			List<HqrtAgentChatdetailsForExport> hqrtAgentChatdetailsForExportList = hqrtAgentChatdetailsService.findListForExport(hqrtAgentChatdetails);
			for (HqrtAgentChatdetailsForExport hqrtAgentChatdetailsForExport : hqrtAgentChatdetailsForExportList) {
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
			new ExportExcel("客户与坐席会话明细", HqrtAgentChatdetailsForExport.class).setDataList(hqrtAgentChatdetailsForExportList).write(response, fileName).dispose();
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