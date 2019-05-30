/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.faqquestion.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.jeeplus.core.service.BaseService;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.hqrt.cmccarea.service.HqrtCmccAreaService;
import com.jeeplus.modules.hqrt.faqquestion.entity.HqrtFaqQuestion;
import com.jeeplus.modules.hqrt.faqquestion.service.HqrtFaqQuestionService;
import com.jeeplus.modules.tools.utils.MultiDBUtils;

/**
 * 知识提问与解答明细Controller
 * @author duan
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hqrt/faqquestion/hqrtFaqQuestion")
public class HqrtFaqQuestionController extends BaseController {

	@Autowired
	private HqrtFaqQuestionService hqrtFaqQuestionService;
	@Autowired
	private HqrtCmccAreaService hqrtCmccAreaService;
	
	@ModelAttribute
	public HqrtFaqQuestion get(@RequestParam(required=false) String id) {
		HqrtFaqQuestion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hqrtFaqQuestionService.get(id);
		}
		if (entity == null){
			entity = new HqrtFaqQuestion();
		}
		return entity;
	}
	
	/**
	 * 知识提问与解答明细列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HqrtFaqQuestion hqrtFaqQuestion, Model model) {
		model.addAttribute("hqrtFaqQuestion", hqrtFaqQuestion);
		return "modules/hqrt/faqquestion/hqrtFaqQuestionList";
	}
	
		/**
	 * 知识提问与解答明细列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(HqrtFaqQuestion hqrtFaqQuestion, HttpServletRequest request, HttpServletResponse response, Model model) {
		String sql = "select a.id AS 'id',a.rowguid AS 'rowguid', a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.questiontitle AS 'questiontitle',a.questioncontext AS 'questioncontext',a.questiondatetime AS 'questiondatetime',a.answeragentid AS 'answeragentid',a.answeragentname AS 'answeragentname',a.answercontext AS 'answercontext',a.answerdatetime AS 'answerdatetime',a.isanswered AS 'isanswered',a.answertimelen AS 'answertimelen',a.sessionid AS 'sessionid',a.originalsessionid AS 'originalsessionid' FROM hqrt_faq_question a";
        String sqlcondition = "";
        List<Object> paramList = new ArrayList<Object>();
    	if (StringUtils.isNotBlank(hqrtFaqQuestion.getCustomerprovince())) {
			if (!hqrtFaqQuestion.getCustomerprovince().contains("其他")) {
				String[] provincesplit = hqrtFaqQuestion.getCustomerprovince().split(",");
				sqlcondition += " AND (";
				for (String province : provincesplit) {
					sqlcondition += "a.customerprovince like ? OR ";
					paramList.add("%" + province + "%");
				}
				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
				sqlcondition += ")";
			} else {
				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
				String[] provinceselect = hqrtFaqQuestion.getCustomerprovince().split(",");
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
        if (StringUtils.isNotBlank(hqrtFaqQuestion.getFaqserialno())) {
        	sqlcondition += " AND a.faqserialno = ?";
        	paramList.add(hqrtFaqQuestion.getFaqserialno());
        }
        if (StringUtils.isNotBlank(hqrtFaqQuestion.getIsanswered())) {
        	sqlcondition += " AND a.isanswered = ?";
        	paramList.add(hqrtFaqQuestion.getIsanswered());
        }
        if (StringUtils.isNotBlank(hqrtFaqQuestion.getFaqcreatername())) {
        	sqlcondition += " AND a.faqcreatername = ?";
        	paramList.add(hqrtFaqQuestion.getFaqcreatername());
        }
        if (hqrtFaqQuestion.getParent() != null && hqrtFaqQuestion.getParent().getStarttime() != null && hqrtFaqQuestion.getParent().getEndttime() != null) {
        	sqlcondition += " AND a.questiondatetime BETWEEN ? AND ?";
        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	paramList.add(ft.format(hqrtFaqQuestion.getParent().getStarttime()));
        	paramList.add(ft.format(hqrtFaqQuestion.getParent().getEndttime()));
        } else {
        	sqlcondition += " AND a.questiondatetime BETWEEN ? AND ?";
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
        Page<HqrtFaqQuestion> page = new Page<HqrtFaqQuestion>(request, response);
        if (StringUtils.isNotBlank(sqlcondition)) {
        	sqlcondition = sqlcondition.replaceFirst(" AND", "");
        	sqlcondition  = " where" + sqlcondition;
        }
        // 该语句仅仅为了查询当前条件下有多少符合条件的数据
        String selectcountsql = sql + sqlcondition;
        sql += sqlcondition + "limit " + (page.getPageNo()-1)*page.getPageSize() + "," + page.getPageSize();
        MultiDBUtils md = MultiDBUtils.get("company");
        List<HqrtFaqQuestion> detailsList = md.queryList(sql, HqrtFaqQuestion.class, paramList.toArray());
        List<HqrtFaqQuestion> allDetailslList = md.queryList(selectcountsql, HqrtFaqQuestion.class, paramList.toArray());
        page.setCount(allDetailslList.size());
		BaseService.dataRuleFilter(hqrtFaqQuestion);
		hqrtFaqQuestion.setPage(page);
		for (int i = 0; i < detailsList.size(); i++) {
			detailsList.get(i).setOrdernumber(i+1+((page.getPageNo()-1)*page.getPageSize()));
		}
		page.setList(detailsList);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑知识提问与解答明细表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, HqrtFaqQuestion hqrtFaqQuestion, Model model) {
		model.addAttribute("hqrtFaqQuestion", hqrtFaqQuestion);
		model.addAttribute("mode", mode);
		return "modules/hqrt/faqquestion/hqrtFaqQuestionForm";
	}
	
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(HqrtFaqQuestion hqrtFaqQuestion, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "知识提问与解答明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            String sql = "select a.id AS 'id',a.rowguid AS 'rowguid', a.rowdatetime AS 'rowdatetime',a.customerid AS 'customerid',a.customername AS 'customername',a.customermobile AS 'customermobile',a.customerprovince AS 'customerprovince',a.faqid AS 'faqid',a.faqroot AS 'faqroot',a.faqmodel AS 'faqmodel',a.faqserialno AS 'faqserialno',a.faqtitle AS 'faqtitle',a.faqcreaterid AS 'faqcreaterid',a.faqcreatername AS 'faqcreatername',a.faqcreatedatetime AS 'faqcreatedatetime',a.questiontitle AS 'questiontitle',a.questioncontext AS 'questioncontext',a.questiondatetime AS 'questiondatetime',a.answeragentid AS 'answeragentid',a.answeragentname AS 'answeragentname',a.answercontext AS 'answercontext',a.answerdatetime AS 'answerdatetime',a.isanswered AS 'isanswered',a.answertimelen AS 'answertimelen',a.sessionid AS 'sessionid',a.originalsessionid AS 'originalsessionid' FROM hqrt_faq_question a";
            String sqlcondition = "";
            List<Object> paramList = new ArrayList<Object>();
        	if (StringUtils.isNotBlank(hqrtFaqQuestion.getCustomerprovince())) {
    			if (!hqrtFaqQuestion.getCustomerprovince().contains("其他")) {
    				String[] provincesplit = hqrtFaqQuestion.getCustomerprovince().split(",");
    				sqlcondition += " AND (";
    				for (String province : provincesplit) {
    					sqlcondition += "a.customerprovince like ? OR ";
    					paramList.add("%" + province + "%");
    				}
    				sqlcondition = sqlcondition.substring(0, sqlcondition.lastIndexOf("OR"));
    				sqlcondition += ")";
    			} else {
    				List<String> hqrtCmccAreaList = hqrtCmccAreaService.findAllProvineList();
    				String[] provinceselect = hqrtFaqQuestion.getCustomerprovince().split(",");
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
            if (StringUtils.isNotBlank(hqrtFaqQuestion.getFaqserialno())) {
            	sqlcondition += " AND a.faqserialno = ?";
            	paramList.add(hqrtFaqQuestion.getFaqserialno());
            }
            if (StringUtils.isNotBlank(hqrtFaqQuestion.getIsanswered())) {
            	sqlcondition += " AND a.isanswered = ?";
            	paramList.add(hqrtFaqQuestion.getIsanswered());
            }
            if (StringUtils.isNotBlank(hqrtFaqQuestion.getFaqcreatername())) {
            	sqlcondition += " AND a.faqcreatername = ?";
            	paramList.add(hqrtFaqQuestion.getFaqcreatername());
            }
            if (hqrtFaqQuestion.getParent() != null && hqrtFaqQuestion.getParent().getStarttime() != null && hqrtFaqQuestion.getParent().getEndttime() != null) {
            	sqlcondition += " AND a.questiondatetime BETWEEN ? AND ?";
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	paramList.add(ft.format(hqrtFaqQuestion.getParent().getStarttime()));
            	paramList.add(ft.format(hqrtFaqQuestion.getParent().getEndttime()));
            } else {
            	sqlcondition += " AND a.questiondatetime BETWEEN ? AND ?";
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
            	sqlcondition  = " where" + sqlcondition;
            }
            // 该语句仅仅为了查询当前条件下有多少符合条件的数据
            sql += sqlcondition;
            MultiDBUtils md = MultiDBUtils.get("company");
            List<HqrtFaqQuestion> detailsList = md.queryList(sql, HqrtFaqQuestion.class, paramList.toArray());
            sql += sqlcondition;
    		for (int i = 0; i < detailsList.size(); i++) {
    			detailsList.get(i).setOrdernumber(i+1);
    		}
    		for (HqrtFaqQuestion faqQuestion : detailsList){
    			if ("0".equals(faqQuestion.getIsanswered())) {
    				faqQuestion.setIsanswered("未回答");
				} else {
					faqQuestion.setIsanswered("已回答");
				}
    		}
    		
    		new ExportExcel("知识提问与解答明细", HqrtFaqQuestion.class).setDataList(detailsList).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出知识提问与解答明细记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

}