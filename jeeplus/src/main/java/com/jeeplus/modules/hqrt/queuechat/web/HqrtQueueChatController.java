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
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChat;
import com.jeeplus.modules.hqrt.queuechat.entity.HqrtQueueChatdetail;
import com.jeeplus.modules.hqrt.queuechat.service.HqrtQueueChatService;
import com.jeeplus.modules.hqrt.robotchat.entity.HqrtRobotChat;
import com.jeeplus.modules.hqrt.robotchatdetails.entity.HqrtRobotChatdetails;

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
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list")
	@RequestMapping(value = {"list", ""})
	public String list(HqrtQueueChat hqrtQueueChat, Model model) {
		model.addAttribute("hqrtQueueChat", hqrtQueueChat);
		return "modules/hqrt/queuechat/hqrtQueueChatList";
	}
	
		/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
			hqrtQueueChat.setQueuenameList(Arrays.asList(hqrtQueueChat.getQueuename().split(",")));
        }
		// 首先根据业务分组查询
		List<HqrtQueueChat> hqrtQueueChatList = hqrtQueueChatService.findListGroupBy(hqrtQueueChat);
		for (HqrtQueueChat queueChat : hqrtQueueChatList) {
			List<HqrtQueueChat> queueNameChatList = hqrtQueueChatService.findListByQueueName(hqrtQueueChat);
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
	/*	Page<HqrtQueueChat> page = hqrtQueueChatService.findPage(new Page<HqrtQueueChat>(request, response), hqrtQueueChat); */
		map.put("rows", hqrtQueueChatList);
		return map;
	}

	/**
	 * 查看，增加，编辑客户排队日志表单页面
	 */
	@RequiresPermissions(value={"hqrt:queuechat:hqrtQueueChat:view","hqrt:queuechat:hqrtQueueChat:add","hqrt:queuechat:hqrtQueueChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtQueueChat hqrtQueueChat, Model model) {
		model.addAttribute("hqrtQueueChat", hqrtQueueChat);
		model.addAttribute("mode", mode);
		return "modules/hqrt/queuechat/hqrtQueueChatForm";
	}

	/**
	 * 保存客户排队日志
	 */
	@ResponseBody
	@RequiresPermissions(value={"hqrt:queuechat:hqrtQueueChat:add","hqrt:queuechat:hqrtQueueChat:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtQueueChat hqrtQueueChat, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtQueueChat);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtQueueChatService.save(hqrtQueueChat);//保存
		j.setSuccess(true);
		j.setMsg("保存客户排队日志成功");
		return j;
	}
	
	/**
	 * 删除客户排队日志
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtQueueChat hqrtQueueChat) {
		AjaxJson j = new AjaxJson();
		hqrtQueueChatService.delete(hqrtQueueChat);
		j.setMsg("删除客户排队日志成功");
		return j;
	}
	
	/**
	 * 批量删除客户排队日志
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtQueueChatService.delete(hqrtQueueChatService.get(id));
		}
		j.setMsg("删除客户排队日志成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtQueueChat hqrtQueueChat, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户排队日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Map<String,Object> map = new HashMap<String,Object>();
    		if (StringUtils.isNotBlank(hqrtQueueChat.getQueuename())) {
    			hqrtQueueChat.setQueuenameList(Arrays.asList(hqrtQueueChat.getQueuename().split(",")));
            }
    		// 首先根据业务分组查询
    		List<HqrtQueueChat> hqrtQueueChatList = hqrtQueueChatService.findListGroupBy(hqrtQueueChat);
    		for (HqrtQueueChat queueChat : hqrtQueueChatList) {
    			List<HqrtQueueChat> queueNameChatList = hqrtQueueChatService.findListByQueueName(hqrtQueueChat);
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
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtQueueChat> list = ei.getDataList(HqrtQueueChat.class);
			for (HqrtQueueChat hqrtQueueChat : list){
				try{
					hqrtQueueChatService.save(hqrtQueueChat);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户排队日志记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条客户排队日志记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入客户排队日志失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入客户排队日志数据模板
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechat:hqrtQueueChat:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户排队日志数据导入模板.xlsx";
    		List<HqrtQueueChat> list = Lists.newArrayList(); 
    		new ExportExcel("客户排队日志数据", HqrtQueueChat.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	//排队明细页面查询
	@ModelAttribute
	public HqrtQueueChatdetail getDetail(@RequestParam(required=false) String id) {
		HqrtQueueChatdetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtQueueChatService.getDetail(id);
		}
		if (entity == null){
			entity = new HqrtQueueChatdetail();
		}
		return entity;
	}
	/**
	 * 客户排队日志列表页面
	 */
	@RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list")
	@RequestMapping(value = "listdetail")
	public String list(HqrtQueueChatdetail hqrtQueueChatdetail, Model model) {
		model.addAttribute("HqrtQueueChatdetail", hqrtQueueChatdetail);
		return "modules/hqrt/queuechat/hqrtQueueChatdetail";
	}
	/**
	 * 客户排队日志列表数据
	 */
	@ResponseBody
	@RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:list")
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
	@RequiresPermissions("hqrt:queuechatdetail:hqrtqueuechatdetail:export")
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