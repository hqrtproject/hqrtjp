/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hqrt.queueconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.hqrt.queueconfig.entity.HqrtQueueConfig;
import com.jeeplus.modules.hqrt.queueconfig.mapper.HqrtQueueConfigMapper;

/**
 * 配置文件Service
 * @author cz
 * @version 2019-05-22
 */
@Service
@Transactional(readOnly = true)
public class HqrtQueueConfigService extends CrudService<HqrtQueueConfigMapper, HqrtQueueConfig> {

	public HqrtQueueConfig get(String id) {
		return super.get(id);
	}
	
	public List<HqrtQueueConfig> findList(HqrtQueueConfig hqrtQueueConfig) {
		return super.findList(hqrtQueueConfig);
	}
	
	public Page<HqrtQueueConfig> findPage(Page<HqrtQueueConfig> page, HqrtQueueConfig hqrtQueueConfig) {
		return super.findPage(page, hqrtQueueConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(HqrtQueueConfig hqrtQueueConfig) {
		super.save(hqrtQueueConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(HqrtQueueConfig hqrtQueueConfig) {
		super.delete(hqrtQueueConfig);
	}

	public List<String> findAllQueuenameList() {
		return mapper.findAllQueuenameList();
	}
	
}