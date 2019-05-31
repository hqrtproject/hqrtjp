/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqevaluate.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.jeeplus.modules.hqrt.cmccarea.service.HqrtCmccAreaService;
import com.jeeplus.modules.hqrt.faqevaluate.entity.HqrtFaqEvaluate;
import com.jeeplus.modules.hqrt.faqevaluate.entity.HqrtFaqEvaluateReport;
import com.jeeplus.modules.hqrt.faqevaluate.service.HqrtFaqEvaluateService;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 知识评价信息报表Controller
 * @author duan
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/faqevaluate/hqrtFaqEvaluate")
public class HqrtFaqEvaluateController extends BaseController {

	@Autowired
	private HqrtFaqEvaluateService hqrtFaqEvaluateService;
	@Autowired
	private HqrtCmccAreaService hqrtCmccAreaService;
	
	@ModelAttribute
	public HqrtFaqEvaluate get(@RequestParam(required=false) String id) {
		HqrtFaqEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtFaqEvaluateService.get(id);
		}
		if (entity == null){
			entity = new HqrtFaqEvaluate();
		}
		return entity;
	}
	
	/**
	 * 知识评价信息报表列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtFaqEvaluate hqrtFaqEvaluate, Model model) {
		model.addAttribute("hqrtFaqEvaluate", hqrtFaqEvaluate);
		return "modules/hqrt/faqevaluate/hqrtFaqEvaluateList";
	}
	
		/**
	 * 知识评价信息报表列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtFaqEvaluate hqrtFaqEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.evaluatestar AS 'evaluatestar',a.evaluatedatetime AS 'evaluatedatetime',a.sessionid AS 'sessionid',a.originalsessionid AS 'originalsessionid' FROM hqrt_faq_evaluate a";
		String sqlcondition = "";
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqroot())) {
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtFaqEvaluate.getFaqroot().contains("其他")) {
        		sqlcondition += " AND a.faqroot in ('" + hqrtFaqEvaluate.getFaqroot().replace(",", "','") + "')";
			} else {
				String[] faqroot = hqrtFaqEvaluate.getFaqroot().split(",");
				for (String faqrootname  : faqroot ) {
					if (queueNameList.contains(faqrootname)) {
						queueNameList.remove(faqrootname);
					}
				}
				sqlcondition += " AND a.faqroot not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
		/*if (StringUtils.isBlank(hqrtFaqEvaluate.getEvaluatestarmin())){
        	sqlcondition += " AND a.evaluatestar BETWEEN ? AND ?";
        	paramList.add(hqrtFaqEvaluate.getEvaluatestarmin());
        	paramList.add(hqrtFaqEvaluate.getEvaluatestarmax());
        }*/
		if (hqrtFaqEvaluate.getStarttime() != null && hqrtFaqEvaluate.getEndttime() != null) {
        	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtFaqEvaluate.getStarttime()));
        	paramList.add(ft.format(hqrtFaqEvaluate.getEndttime()));
        } else {
        	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
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
		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqmodel())) {
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtFaqEvaluate.getFaqmodel().contains("其他")) {
        		sqlcondition += " AND a.faqmodel in ('" + hqrtFaqEvaluate.getFaqmodel().replace(",", "','") + "')";
			} else {
				String[] faqmodel = hqrtFaqEvaluate.getFaqmodel().split(",");
				for (String faqmodelname  : faqmodel ) {
					if (queueNameList.contains(faqmodelname )) {
						queueNameList.remove(faqmodelname );
					}
				}
				sqlcondition += " AND a.faqmodel not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqserialno())) {
			sqlcondition += " AND a.faqserialno = ?";
			paramList.add(hqrtFaqEvaluate.getFaqserialno());
		}
		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getCustomername())) {
			sqlcondition += " AND a.customername = ?";
			paramList.add(hqrtFaqEvaluate.getCustomername());
		}
		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqserialno())) {
			sqlcondition += " AND a.faqserialno = ?";
			paramList.add(hqrtFaqEvaluate.getFaqserialno());
		}
		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getCustomerprovince())) {
			if (!hqrtFaqEvaluate.getCustomerprovince().contains("其他")) {
				String[] provinceselect = hqrtFaqEvaluate.getCustomerprovince().split(",");
				sqlcondition += " AND (";
				for (String province : provinceselect) {
					sqlcondition += "a.customerprovince like ? OR ";
					paramList.add("%" + province + "%");
				}
				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
				sqlcondition += ")";
			} else {
				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
				String[] provinceselect = hqrtFaqEvaluate.getCustomerprovince().split(",");
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
			sqlcondition = " WHERE" + sqlcondition;
		}
		Page<HqrtFaqEvaluate> page = new Page<HqrtFaqEvaluate>(request, response);
		String selectcountsql = sql + sqlcondition;
        sql += sqlcondition + " limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        List<HqrtFaqEvaluate> detailsList = md.queryList(sql, HqrtFaqEvaluate.class, paramList.toArray());
        List<HqrtFaqEvaluate> allDetailslList = md.queryList(selectcountsql, HqrtFaqEvaluate.class, paramList.toArray());
    	for(int i = 0 ; i < detailsList.size(); i++){
    		detailsList.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
    	}
    	for(HqrtFaqEvaluate faqEvaluate : detailsList){
    		if ("0".equals(faqEvaluate.getEvaluatestar())) {
    			faqEvaluate.setEvaluatestar("未评价");
			} else if ("1".equals(faqEvaluate.getEvaluatestar())) {
				faqEvaluate.setEvaluatestar("一星");
			} else if ("2".equals(faqEvaluate.getEvaluatestar())) {
				faqEvaluate.setEvaluatestar("二星");
			} else if ("3".equals(faqEvaluate.getEvaluatestar())) {
				faqEvaluate.setEvaluatestar("三星");
			} else if ("4".equals(faqEvaluate.getEvaluatestar())) {
				faqEvaluate.setEvaluatestar("四星");
			} else if ("5".equals(faqEvaluate.getEvaluatestar())) {
				faqEvaluate.setEvaluatestar("五星");
			}
    	}
        page.setCount(allDetailslList.size());
        hqrtFaqEvaluate.setPage(page);
		page.setList(detailsList);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑知识评价信息报表表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtFaqEvaluate hqrtFaqEvaluate, Model model) {
		model.addAttribute("hqrtFaqEvaluate", hqrtFaqEvaluate);
		model.addAttribute("mode", mode);
		return "modules/hqrt/faqevaluate/hqrtFaqEvaluateForm";
	}

	/**
	 * 保存知识评价信息报表
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(HqrtFaqEvaluate hqrtFaqEvaluate, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(hqrtFaqEvaluate);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		hqrtFaqEvaluateService.save(hqrtFaqEvaluate);//保存
		j.setSuccess(true);
		j.setMsg("保存知识评价信息报表成功");
		return j;
	}
	
	/**
	 * 删除知识评价信息报表
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(HqrtFaqEvaluate hqrtFaqEvaluate) {
		AjaxJson j = new AjaxJson();
		hqrtFaqEvaluateService.delete(hqrtFaqEvaluate);
		j.setMsg("删除知识评价信息报表成功");
		return j;
	}
	
	/**
	 * 批量删除知识评价信息报表
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hqrtFaqEvaluateService.delete(hqrtFaqEvaluateService.get(id));
		}
		j.setMsg("删除知识评价信息报表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtFaqEvaluate hqrtFaqEvaluate, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识评价信息报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.evaluatestar AS 'evaluatestar',a.evaluatedatetime AS 'evaluatedatetime',a.sessionid AS 'sessionid',a.originalsessionid AS 'originalsessionid' FROM hqrt_faq_evaluate a";
    		String sqlcondition = "";
    		List<Object> paramList = new ArrayList<Object>();
    		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqroot())) {
            	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
        		List<String> queueNameList = new ArrayList<String>();
        		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
        			queueNameList.add(hqrtQueueConfig.getQueuename());
        		}
            	if (!hqrtFaqEvaluate.getFaqroot().contains("其他")) {
            		sqlcondition += " AND a.faqroot in ('" + hqrtFaqEvaluate.getFaqroot().replace(",", "','") + "')";
    			} else {
    				String[] faqroot = hqrtFaqEvaluate.getFaqroot().split(",");
    				for (String faqrootname  : faqroot ) {
    					if (queueNameList.contains(faqrootname)) {
    						queueNameList.remove(faqrootname);
    					}
    				}
    				sqlcondition += " AND a.faqroot not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
    			}
            }
    		if (hqrtFaqEvaluate.getEvaluatestarmin() != null && hqrtFaqEvaluate.getEvaluatestarmax() != null) {
            	sqlcondition += " AND a.evaluatestar BETWEEN ? AND ?";
            	paramList.add(hqrtFaqEvaluate.getEvaluatestarmin());
            	paramList.add(hqrtFaqEvaluate.getEvaluatestarmax());
            }
    		if (hqrtFaqEvaluate.getStarttime() != null && hqrtFaqEvaluate.getEndttime() != null) {
            	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtFaqEvaluate.getStarttime()));
            	paramList.add(ft.format(hqrtFaqEvaluate.getEndttime()));
            } else {
            	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
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
    		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqmodel())) {
            	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
        		List<String> queueNameList = new ArrayList<String>();
        		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
        			queueNameList.add(hqrtQueueConfig.getQueuename());
        		}
            	if (!hqrtFaqEvaluate.getFaqmodel().contains("其他")) {
            		sqlcondition += " AND a.faqmodel in ('" + hqrtFaqEvaluate.getFaqmodel().replace(",", "','") + "')";
    			} else {
    				String[] faqmodel = hqrtFaqEvaluate.getFaqmodel().split(",");
    				for (String faqmodelname  : faqmodel ) {
    					if (queueNameList.contains(faqmodelname )) {
    						queueNameList.remove(faqmodelname );
    					}
    				}
    				sqlcondition += " AND a.faqmodel not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
    			}
            }
    		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqserialno())) {
    			sqlcondition += " AND a.faqserialno = ?";
    			paramList.add(hqrtFaqEvaluate.getFaqserialno());
    		}
    		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getCustomername())) {
    			sqlcondition += " AND a.customername = ?";
    			paramList.add(hqrtFaqEvaluate.getCustomername());
    		}
    		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getFaqserialno())) {
    			sqlcondition += " AND a.faqserialno = ?";
    			paramList.add(hqrtFaqEvaluate.getFaqserialno());
    		}
    		if (StringUtils.isNotBlank(hqrtFaqEvaluate.getCustomerprovince())) {
    			if (!hqrtFaqEvaluate.getCustomerprovince().contains("其他")) {
    				String[] provinceselect = hqrtFaqEvaluate.getCustomerprovince().split(",");
    				sqlcondition += " AND (";
    				for (String province : provinceselect) {
    					sqlcondition += "a.customerprovince like ? OR ";
    					paramList.add("%" + province + "%");
    				}
    				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
    				sqlcondition += ")";
    			} else {
    				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
    				String[] provinceselect = hqrtFaqEvaluate.getCustomerprovince().split(",");
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
    			sqlcondition = " WHERE" + sqlcondition;
    		}
    		sql += sqlcondition;
            MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
            List<HqrtFaqEvaluate> detailsList = md.queryList(sql, HqrtFaqEvaluate.class, paramList.toArray());
        	for(int i = 0 ; i < detailsList.size(); i++){
        		detailsList.get(i).setOrdernumber(i+1);
        	}
        	for(HqrtFaqEvaluate faqEvaluate : detailsList){
        		if ("0".equals(faqEvaluate.getEvaluatestar())) {
        			faqEvaluate.setEvaluatestar("未评价");
    			} else if ("1".equals(faqEvaluate.getEvaluatestar())) {
    				faqEvaluate.setEvaluatestar("一星");
    			} else if ("2".equals(faqEvaluate.getEvaluatestar())) {
    				faqEvaluate.setEvaluatestar("二星");
    			} else if ("3".equals(faqEvaluate.getEvaluatestar())) {
    				faqEvaluate.setEvaluatestar("三星");
    			} else if ("4".equals(faqEvaluate.getEvaluatestar())) {
    				faqEvaluate.setEvaluatestar("四星");
    			} else if ("5".equals(faqEvaluate.getEvaluatestar())) {
    				faqEvaluate.setEvaluatestar("五星");
    			}
        	}
         
    		new ExportExcel("知识评价信息报表", HqrtFaqEvaluate.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识评价信息报表记录失败！失败信息："+e.getMessage());
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
			List<HqrtFaqEvaluate> list = ei.getDataList(HqrtFaqEvaluate.class);
			for (HqrtFaqEvaluate hqrtFaqEvaluate : list){
				try{
					hqrtFaqEvaluateService.save(hqrtFaqEvaluate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条知识评价信息报表记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条知识评价信息报表记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入知识评价信息报表失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入知识评价信息报表数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识评价信息报表数据导入模板.xlsx";
    		List<HqrtFaqEvaluate> list = Lists.newArrayList(); 
    		new ExportExcel("知识评价信息报表数据", HqrtFaqEvaluate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	@RequestMapping(value = {"reportlist"})
	public String list(HqrtFaqEvaluateReport hqrtFaqEvaluatereport, Model model) {
		model.addAttribute("hqrtFaqEvaluatereport", hqrtFaqEvaluatereport);
		return "modules/hqrt/faqevaluate/hqrtFaqEvaluatereportList";
	}
	
	@ResponseBody
	@RequestMapping(value = "datareport")
	public Map<String, Object> dataReport(HqrtFaqEvaluateReport hqrtFaqEvaluatereport, HttpServletRequest request, HttpServletResponse response, Model model) {
		 Map<String, Object> map = new HashMap<String, Object>(); 
		 String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.evaluatestar AS 'evaluatestar',a.evaluatedatetime AS 'evaluatedatetime',a.sessionid AS 'sessionid',a.originalsessionid AS 'originalsessionid',FORMAT(AVG(a.evaluatestar),2) AS 'averagescore' FROM hqrt_faq_evaluate a";
		 String sqlcondition = "";
		 List<Object> paramList = new ArrayList<Object>();
		 if (StringUtils.isNotBlank(hqrtFaqEvaluatereport.getFaqroot())) {
         	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
     		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
     		List<String> queueNameList = new ArrayList<String>();
     		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
     			queueNameList.add(hqrtQueueConfig.getQueuename());
     		}
         	if (!hqrtFaqEvaluatereport.getFaqroot().contains("其他")) {
         		sqlcondition += " AND a.faqroot in ('" + hqrtFaqEvaluatereport.getFaqroot().replace(",", "','") + "')";
 			} else {
 				String[] faqroot = hqrtFaqEvaluatereport.getFaqroot().split(",");
 				for (String faqrootname  : faqroot ) {
 					if (queueNameList.contains(faqrootname)) {
 						queueNameList.remove(faqrootname);
 					}
 				}
 				sqlcondition += " AND a.faqroot not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
 			}
        }
		if (hqrtFaqEvaluatereport.getEvaluatestarmin() != null && hqrtFaqEvaluatereport.getEvaluatestarmax() != null) {
	        	sqlcondition += " AND a.averagescore BETWEEN ? AND ?";
	        	paramList.add(hqrtFaqEvaluatereport.getEvaluatestarmin());
	        	paramList.add(hqrtFaqEvaluatereport.getEvaluatestarmax());
	        }
		if (hqrtFaqEvaluatereport.getStarttime() != null && hqrtFaqEvaluatereport.getEndttime() != null) {
        	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtFaqEvaluatereport.getStarttime()));
        	paramList.add(ft.format(hqrtFaqEvaluatereport.getEndttime()));
        } else {
        	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
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
		if (StringUtils.isNotBlank(hqrtFaqEvaluatereport.getFaqmodel())) {
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtFaqEvaluatereport.getFaqmodel().contains("其他")) {
        		sqlcondition += " AND a.faqmodel in ('" + hqrtFaqEvaluatereport.getFaqmodel().replace(",", "','") + "')";
			} else {
				String[] faqmodel = hqrtFaqEvaluatereport.getFaqmodel().split(",");
				for (String faqmodelname  : faqmodel ) {
					if (queueNameList.contains(faqmodelname )) {
						queueNameList.remove(faqmodelname );
					}
				}
				sqlcondition += " AND a.faqmodel not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
		if (StringUtils.isNotBlank(hqrtFaqEvaluatereport.getFaqserialno())) {
			sqlcondition += " AND a.faqserialno = ?";
			paramList.add(hqrtFaqEvaluatereport.getFaqserialno());
		}
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition = " WHERE" + sqlcondition;
		}
		sql += sqlcondition+ " GROUP BY a.faqroot,a.faqserialno,a.faqserialno";
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        List<HqrtFaqEvaluateReport> detailsList = md.queryList(sql, HqrtFaqEvaluateReport.class, paramList.toArray());
      	for(int i = 0 ; i < detailsList.size(); i++){
      		detailsList.get(i).setOrdernumber(i+1);
      	}
      	map.put("rows", detailsList);
		return map;
	}
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "exportreport")
    public AjaxJson export(HqrtFaqEvaluateReport hqrtFaqEvaluatereport, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识评价信息报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
       	 String sql = "select a.id AS 'id',a.rowguid AS 'rowguid',a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.evaluatestar AS 'evaluatestar',a.evaluatedatetime AS 'evaluatedatetime',a.sessionid AS 'sessionid',a.originalsessionid AS 'originalsessionid',FORMAT(AVG(a.evaluatestar),2) AS 'averagescore' FROM hqrt_faq_evaluate a";
		 String sqlcondition = "";
		 List<Object> paramList = new ArrayList<Object>();
		 if (StringUtils.isNotBlank(hqrtFaqEvaluatereport.getFaqroot())) {
         	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
     		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
     		List<String> queueNameList = new ArrayList<String>();
     		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
     			queueNameList.add(hqrtQueueConfig.getQueuename());
     		}
         	if (!hqrtFaqEvaluatereport.getFaqroot().contains("其他")) {
         		sqlcondition += " AND a.faqroot in ('" + hqrtFaqEvaluatereport.getFaqroot().replace(",", "','") + "')";
 			} else {
 				String[] faqroot = hqrtFaqEvaluatereport.getFaqroot().split(",");
 				for (String faqrootname  : faqroot ) {
 					if (queueNameList.contains(faqrootname)) {
 						queueNameList.remove(faqrootname);
 					}
 				}
 				sqlcondition += " AND a.faqroot not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
 			}
        }
		if (hqrtFaqEvaluatereport.getEvaluatestarmin() != null && hqrtFaqEvaluatereport.getEvaluatestarmax() != null) {
	        	sqlcondition += " AND FORMAT(AVG(a.evaluatestar),2) BETWEEN ? AND ?";
	        	paramList.add(hqrtFaqEvaluatereport.getEvaluatestarmin());
	        	paramList.add(hqrtFaqEvaluatereport.getEvaluatestarmax());
	        }
		if (hqrtFaqEvaluatereport.getStarttime() != null && hqrtFaqEvaluatereport.getEndttime() != null) {
        	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtFaqEvaluatereport.getStarttime()));
        	paramList.add(ft.format(hqrtFaqEvaluatereport.getEndttime()));
        } else {
        	sqlcondition += " AND a.evaluatedatetime BETWEEN ? AND ?";
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
		if (StringUtils.isNotBlank(hqrtFaqEvaluatereport.getFaqmodel())) {
        	MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
    		List<HqrtQueueConfig> hqrtQueueConfigList = md.queryList("SELECT a.QueueName FROM hqrt_queue_config a", HqrtQueueConfig.class);
    		List<String> queueNameList = new ArrayList<String>();
    		for (HqrtQueueConfig hqrtQueueConfig : hqrtQueueConfigList) {
    			queueNameList.add(hqrtQueueConfig.getQueuename());
    		}
        	if (!hqrtFaqEvaluatereport.getFaqmodel().contains("其他")) {
        		sqlcondition += " AND a.faqmodel in ('" + hqrtFaqEvaluatereport.getFaqmodel().replace(",", "','") + "')";
			} else {
				String[] faqmodel = hqrtFaqEvaluatereport.getFaqmodel().split(",");
				for (String faqmodelname  : faqmodel ) {
					if (queueNameList.contains(faqmodelname )) {
						queueNameList.remove(faqmodelname );
					}
				}
				sqlcondition += " AND a.faqmodel not in ('" + StringUtils.join(queueNameList.toArray(), "','") + "')";
			}
        }
		if (StringUtils.isNotBlank(hqrtFaqEvaluatereport.getFaqserialno())) {
			sqlcondition += " AND a.faqserialno = ?";
			paramList.add(hqrtFaqEvaluatereport.getFaqserialno());
		}
		if (StringUtils.isNotBlank(sqlcondition)) {
			sqlcondition = sqlcondition.replaceFirst(" AND", "");
			sqlcondition = " WHERE" + sqlcondition;
		}
		sql += sqlcondition+ " GROUP BY a.faqroot,a.faqserialno,a.faqserialno";
		MultiDBUtils md = MultiDBUtils.get(Global.getConfig("datasourcename"));
        List<HqrtFaqEvaluateReport> detailsList = md.queryList(sql, HqrtFaqEvaluateReport.class, paramList.toArray());
      	for(int i = 0 ; i < detailsList.size(); i++){
      		detailsList.get(i).setOrdernumber(i+1);
      	}
    		new ExportExcel("知识评价信息报表", HqrtFaqEvaluateReport.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识评价信息报表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
}