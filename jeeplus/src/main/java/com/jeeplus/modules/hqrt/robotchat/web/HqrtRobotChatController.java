/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.robotchat.web;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;
import com.jeeplus.modules.hqrt.robotchat.service.HqrtRobotChatService;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetails;
import com.jeeplus.modules.hqrt.robotchatdetails.service.HqrtRobotChatdetailsService;

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
	private HqrtRobotChatdetailsService hqrtRobotChatdetailsService;
	
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
	public String list(HqrtRobotChat hqrtRobotChat, Model model) {
		model.addAttribute("hqrtRobotChat", hqrtRobotChat);
		return "modules/hqrt/robotchat/hqrtRobotChatList";
	}
	
	/**
	 * 机器人对话列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtRobotChat hqrtRobotChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		// Page<HqrtRobotChat> page = hqrtRobotChatService.findPage(new Page<HqrtRobotChat>(request, response), hqrtRobotChat);
		Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(hqrtRobotChat.getCustomerprovince())) {
        	hqrtRobotChat.setCustomerprovinceList(Arrays.asList(hqrtRobotChat.getCustomerprovince().split(",")));
        }
        if (StringUtils.isNotBlank(hqrtRobotChat.getQueuename())) {
        	hqrtRobotChat.setQueuenameList(Arrays.asList(hqrtRobotChat.getQueuename().split(",")));
        }
		// 首先根据业务和省份分组查询
		List<HqrtRobotChat> hqrtRobotChatlist = hqrtRobotChatService.findListGroupBy(hqrtRobotChat);
		for (HqrtRobotChat robotChat : hqrtRobotChatlist) {
			List<HqrtRobotChat> queueNameAndCustomerProvinceList = hqrtRobotChatService.findListByQueueNameAndCustomerProvince(robotChat);
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
				List<HqrtRobotChatdetails> hqrtRobotChatdetailsList = hqrtRobotChatdetailsService.findList(hqrtRobotChatdetails);
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
		map.put("rows", hqrtRobotChatlist);
		// map.put("total", page.getCount());
		return map;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtRobotChat hqrtRobotChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "机器人对话"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            // 首先根据业务和省份分组查询
            if (StringUtils.isNotBlank(hqrtRobotChat.getCustomerprovince())) {
            	hqrtRobotChat.setCustomerprovinceList(Arrays.asList(hqrtRobotChat.getCustomerprovince().split(",")));
            }
            if (StringUtils.isNotBlank(hqrtRobotChat.getQueuename())) {
            	hqrtRobotChat.setQueuenameList(Arrays.asList(hqrtRobotChat.getQueuename().split(",")));
            }
    		List<HqrtRobotChat> hqrtRobotChatlist = hqrtRobotChatService.findListGroupBy(hqrtRobotChat);
    		// 所有业务省份的进线总量
    		for (HqrtRobotChat robotChat : hqrtRobotChatlist) {
    			List<HqrtRobotChat> queueNameAndCustomerProvinceList = hqrtRobotChatService.findListByQueueNameAndCustomerProvince(robotChat);
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
    				List<HqrtRobotChatdetails> hqrtRobotChatdetailsList = hqrtRobotChatdetailsService.findList(hqrtRobotChatdetails);
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
            
    		new ExportExcel("机器人对话", HqrtRobotChat.class).setDataList(hqrtRobotChatlist).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出机器人对话记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

}