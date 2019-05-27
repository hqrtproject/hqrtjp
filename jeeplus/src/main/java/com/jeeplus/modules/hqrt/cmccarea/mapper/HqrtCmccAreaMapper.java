/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.cmccarea.mapper;

import java.util.List;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.hqrt.cmccarea.entity.HqrtCmccArea;

/**
 * 区域管理MAPPER接口
 * @author duan
 * @version 2019-05-27
 */
@MyBatisMapper
public interface HqrtCmccAreaMapper extends TreeMapper<HqrtCmccArea> {

	List<String> findAllProvineList();
	
}