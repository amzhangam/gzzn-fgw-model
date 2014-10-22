package com.gzzn.fgw.service.sys;

import java.util.List;

import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.service.IPagedService;

public interface ISysDictionaryitemsService extends IPagedService<SysDictionaryitems> {
	
	/**
	 * 根据数据字典项目名称查找数据字典项对应的内容
	 * @param name
	 * @return
	 */
	List<SysDictionaryitems> findDictionaryitems(String name);
	/**
	 * 查找全部数据字典项对应的内容
	 * @param name
	 * @return
	 */
	List<SysDictionaryitems> findDictionaryitems();
}
