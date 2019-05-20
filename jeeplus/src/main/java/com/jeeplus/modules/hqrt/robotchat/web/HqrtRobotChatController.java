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
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
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
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtRobotChat hqrtRobotChat, Model model) {
		model.addAttribute("hqrtRobotChat", hqrtRobotChat);
		return "modules/hqrt/robotchat/hqrtRobotChatList";
	}
	
	/**
	 * 机器人对话列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:list")
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
	 * 查看，增加，编辑机器人对话表单页面
	 */
	@RequiresPermissions(value={"hqrt:robotchat:hqrtRobotChat:view","hqrt:robotchat:hqrtRobotChat:add","hqrt:robotchat:hqrtRobotChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtRobotChat hqrtRobotChat, Model model) {
		model.addAttribute("hqrtRobotChat", hqrtRobotChat);
		model.addAttribute("mode", mode);
		return "modules/hqrt/robotchat/hqrtRobotChatForm";
	}

	/**
	 * 保存机器人对话
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:robotchat:hqrtRobotChat:add","hqrt:robotchat:hqrtRobotChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtRobotChat hqrtRobotChat, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtRobotChat);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtRobotChatService.save(hqrtRobotChat);//保存
		j.setSuccess(true);
		j.setMsg("保存机器人对话成功");
		return j;
	}
	
	/**
	 * 删除机器人对话
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtRobotChat hqrtRobotChat) {
		AjaxJson j = new AjaxJson();
		hqrtRobotChatService.delete(hqrtRobotChat);
		j.setMsg("删除机器人对话成功");
		return j;
	}
	
	/**
	 * 批量删除机器人对话
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtRobotChatService.delete(hqrtRobotChatService.get(id));
		}
		j.setMsg("删除机器人对话成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:export")
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

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtRobotChat> list = ei.getDataList(HqrtRobotChat.class);
			for (HqrtRobotChat hqrtRobotChat : list){
				try{
					hqrtRobotChatService.save(hqrtRobotChat);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条机器人对话记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条机器人对话记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入机器人对话失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入机器人对话数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:robotchat:hqrtRobotChat:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "机器人对话数据导入模板.xlsx";
    		List<HqrtRobotChat> list = Lists.newArrayList(); 
    		new ExportExcel("机器人对话数据", HqrtRobotChat.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}