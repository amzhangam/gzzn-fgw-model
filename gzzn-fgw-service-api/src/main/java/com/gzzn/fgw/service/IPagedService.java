package com.gzzn.fgw.service;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: IPagedService</p>
 * <p>Description: 业务层数据分页接口</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author yjf
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-11-28 下午2:47:33  yjf    new
 */
public interface IPagedService<DTO> {
	/**
	 * 条件、排序、分页查询
	 * 
	 * @param entityClass
	 * @param page
	 * @param condition
	 * @param sort
	 */
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page, Condition condition,
			Sort sort);

	/**
	 * 条件、分页查询
	 * 
	 * @param entityClass
	 * @param page
	 * @param condition
	 */
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page, Condition condition);

	/**
	 * 排序、分页查询
	 * 
	 * @param entityClass
	 * @param page
	 * @param sort
	 */
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page, Sort sort);

	/**
	 * 分页查询
	 * 
	 * @param entityClass
	 * @param page
	 */
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page);

	/**
	 * medol对象转化为DTO对象
	 * @param o
	 * @return
	 */
	public DTO convertToDTO(Object o);
}
