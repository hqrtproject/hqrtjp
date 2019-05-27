/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.cmccarea.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 区域管理Entity
 * @author duan
 * @version 2019-05-27
 */
public class HqrtCmccArea extends TreeEntity<HqrtCmccArea> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 区域编码
	private String type;		// 区域类型
	
	
	public HqrtCmccArea() {
		super();
	}

	public HqrtCmccArea(String id){
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public  HqrtCmccArea getParent() {
			return parent;
	}
	
	@Override
	public void setParent(HqrtCmccArea parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}