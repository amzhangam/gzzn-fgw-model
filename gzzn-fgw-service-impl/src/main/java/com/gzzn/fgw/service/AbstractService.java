package com.gzzn.fgw.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: AbstractService</p>
 * <p>Description: 业务层抽象类，实现分页接口，可添加业务层公用的方法</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author yjf
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-11-19 下午6:27:04  yjf    new
 */
@Transactional
public abstract class AbstractService<DTO> implements IPagedService<DTO> {

	protected static Logger logger = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	private IEntityDao dao;

	@Override
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page, Condition condition,
			Sort sort) {
		page.setCount((int) dao.count(entityClass, condition));

		List<T> list = dao.find(entityClass, condition, sort, page.getOffset(), page.getLimitNum());
		page.setList(convert(list));
		logger.debug("count={},list size={}", page.getCount(), page.getList().size());
	}

	@Override
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page, Condition condition) {
		loadData(entityClass, page, condition, null);
	}

	@Override
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page, Sort sort) {
		loadData(entityClass, page, null, sort);
	}

	@Override
	public <T> void loadData(Class<T> entityClass, PageUtil<DTO> page) {
		loadData(entityClass, page, null, null);
	}

	protected List<DTO> convert(List<?> list) {
		List<DTO> result = new ArrayList<DTO>(list.size());
		for (Object o : list) {
			result.add(convertToDTO(o));
		}
		return result;
	}

	/**
	 * 将数据库映射的model对象类型转化为界面展示的DTO类型
	 * 勾子方法，供子类覆盖
	 * 提供默认实现，若DTO与model类型一致则无需在子类重写此方法
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DTO convertToDTO(Object o) {
		return (DTO) o;
	}

}
