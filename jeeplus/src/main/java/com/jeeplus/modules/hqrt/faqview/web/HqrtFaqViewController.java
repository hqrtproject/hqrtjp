/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqview.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.faqview.entity.HqrtFaqView;
import com.jeeplus.modules.hqrt.faqview.service.HqrtFaqViewService;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 知识点击量统计Controller
 * @author duan
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/faqview/hqrtFaqView")
public class HqrtFaqViewController extends BaseController {

	@Autowired
	private HqrtFaqViewService hqrtFaqViewService;
	
	@ModelAttribute
	public HqrtFaqView get(@RequestParam(required=false) String id) {
		HqrtFaqView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtFaqViewService.get(id);
		}
		if (entity == null){
			entity = new HqrtFaqView();
		}
		return entity;
	}
	
	/**
	 * 知识点击量统计列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtFaqView hqrtFaqView, Model model) {
		model.addAttribute("hqrtFaqView", hqrtFaqView);
		return "modules/hqrt/faqview/hqrtFaqViewList";
	}
	
		/**
	 * 知识点击量统计列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtFaqView hqrtFaqView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));

		// 首先根据业务和省份分组查询
        String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.viewdatetime AS 'viewdatetime',COUNT(1) AS clickcount FROM hqrt_faq_view a ";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
    	if (StringUtils.isNotBlank(hqrtFaqView.getFaqroot())) {
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtFaqView.getFaqroot().contains("其他")) {
        		sqlcondition += " AND a.faqroot in ('" + hqrtFaqView.getFaqroot().replace(",", "','") + "')";
			} else {
				String[] faqroot = hqrtFaqView.getFaqroot().split(",");
				for (String faqrootname  : faqroot ) {
					if (queueNameList.contains(faqrootname)) {
						queueNameList.remove(faqrootname);
					}
				}
				sqlcondition += " AND a.faqroot not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
        if (hqrtFaqView.getStarttime() != null && hqrtFaqView.getEndtime() != null) {
        	sqlcondition += " AND a.viewdatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtFaqView.getStarttime()));
        	paramList.add(ft.format(hqrtFaqView.getEndtime()));
        } else {
        	sqlcondition += " AND a.viewdatetime BETWEEN ? AND ?";
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
        if (StringUtils.isNotBlank(hqrtFaqView.getFaqserialno())) {
        	sqlcondition += " AND a.faqserialno = ?";
        	paramList.add(hqrtFaqView.getFaqserialno());
        }
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }

        Page<HqrtFaqView> page = new Page<HqrtFaqView>(request, response);
        sql += sqlcondition + " GROUP BY a.FAQID ORDER BY clickcount DESC limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        List<HqrtFaqView> hqrtFaqViewlist = md.queryList(sql, HqrtFaqView.class, paramList.toArray());
        List<HqrtFaqView> allDetailslList = md.queryList("select count(1) AS ordernumber from hqrt_faq_view a" + sqlcondition, HqrtFaqView.class, paramList.toArray());
        page.setCount(allDetailslList.get(0).getOrdernumber());
        hqrtFaqView.setPage(page);
		for (int i = 0; i < hqrtFaqViewlist.size(); i++) {
			hqrtFaqViewlist.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
		}
		page.setList(hqrtFaqViewlist);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑知识点击量统计表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtFaqView hqrtFaqView, Model model) {
		model.addAttribute("hqrtFaqView", hqrtFaqView);
		model.addAttribute("mode", mode);
		return "modules/hqrt/faqview/hqrtFaqViewForm";
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtFaqView hqrtFaqView, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识点击量统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		MultiDBUtils mu = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		
            String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.viewdatetime AS 'viewdatetime',COUNT(1) AS clickcount FROM hqrt_faq_view a ";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
        	if (StringUtils.isNotBlank(hqrtFaqView.getFaqroot())) {
            	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
        		List<String> queueNameList = new ArrayList<String>();
        		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
        			queueNameList.add(hqrtQueueConfig.getQueuename());
        		}
            	if (!hqrtFaqView.getFaqroot().contains("其他")) {
            		sqlcondition += " AND a.faqroot in ('" + hqrtFaqView.getFaqroot().replace(",", "','") + "')";
    			} else {
    				String[] faqroot = hqrtFaqView.getFaqroot().split(",");
    				for (String faqrootname  : faqroot ) {
    					if (queueNameList.contains(faqrootname)) {
    						queueNameList.remove(faqrootname);
    					}
    				}
    				sqlcondition += " AND a.faqroot not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
    			}
            }
            if (hqrtFaqView.getStarttime() != null && hqrtFaqView.getEndtime() != null) {
            	sqlcondition += " AND a.viewdatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtFaqView.getStarttime()));
            	paramList.add(ft.format(hqrtFaqView.getEndtime()));
            } else {
            	sqlcondition += " AND a.viewdatetime BETWEEN ? AND ?";
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
            if (StringUtils.isNotBlank(hqrtFaqView.getFaqserialno())) {
            	sqlcondition += " AND a.faqserialno = ?";
            	paramList.add(hqrtFaqView.getFaqserialno());
            }
            if (StringUtils.isNotBlank(sqlcondition)) {
            	sqlcondition = sqlcondition.replaceFirst(" AND", "");
            	sqlcondition  = " where" + sqlcondition;
            }
            sql += sqlcondition + " GROUP BY a.FAQID ORDER BY clickcount DESC";
            List<HqrtFaqView> hqrtFaqViewlist = mu.queryList(sql, HqrtFaqView.class, paramList.toArray());
    		
    		for(int i = 0 ; i < hqrtFaqViewlist.size(); i++){
    			hqrtFaqViewlist.get(i).setOrdernumber(i+1);
        	}
    		new ExportExcel("知识点击量统计", HqrtFaqView.class).setDataList(hqrtFaqViewlist).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识点击量统计记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HqrtFaqView> list = ei.getDataList(HqrtFaqView.class);
			for (HqrtFaqView hqrtFaqView : list){
				try{
					hqrtFaqViewService.save(hqrtFaqView);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条知识点击量统计记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条知识点击量统计记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入知识点击量统计失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入知识点击量统计数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识点击量统计数据导入模板.xlsx";
    		List<HqrtFaqView> list = Lists.newArrayList(); 
    		new ExportExcel("知识点击量统计数据", HqrtFaqView.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}