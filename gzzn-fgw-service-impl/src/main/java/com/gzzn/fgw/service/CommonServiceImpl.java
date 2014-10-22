package com.gzzn.fgw.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.util.common.Reflections;

@Service
@Transactional(readOnly = true)
public class CommonServiceImpl implements ICommonService {

	@Autowired
	private IEntityDao dao;

	@Override
	@Transactional(readOnly = false)
	public <T> void save(T entity) {
		dao.save(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void save(Iterable<T> entities) {
		dao.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void update(T entity) {
		dao.update(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void saveOrUpdate(T entity) {
		Method[] methods = entity.getClass().getMethods();
		for (Method m : methods) {
			if (m.getAnnotation(Id.class) != null) {
				Serializable id = (Serializable) Reflections.invokeMethod(entity, m.getName(),
						null, null);
				if (id != null) {
					T obj = (T) dao.findOne(entity.getClass(), id);
					if (obj != null) {
						dao.update(entity);
					} else {
						dao.save(entity);
					}
				} else {
					dao.save(entity);
				}
				break;
			}
		}
	}

	@Override
	public <T> T findOne(Class<T> entityClass, Serializable id) {
		return dao.findOne(entityClass, id);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return dao.findAll(entityClass);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void delete(Class<T> entityClass, Serializable id) {
		dao.delete(entityClass, id);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void delete(T entity) {
		dao.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void delete(Iterable<? extends T> entities) {
		dao.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void deleteAll(Class<T> entityClass) {
		dao.deleteAll(entityClass);
	}

	@Override
	public <T> long count(Class<T> entityClass) {
		return dao.count(entityClass);
	}

	@Override
	public <T> long count(Class<T> entityClass, Condition condition) {
		return dao.count(entityClass, condition);
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, Condition condition) {
		return dao.find(entityClass, condition);
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, Condition condition, Sort sort) {
		return dao.find(entityClass, condition, sort);
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, Condition condition, int offset, int limit) {
		return dao.find(entityClass, condition, offset, limit);
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, Condition condition, Sort sort, int offset,
			int limit) {
		return dao.find(entityClass, condition, sort, offset, limit);
	}

	@Override
	public <T> T findOne(Class<T> entityClass, Condition condition) {
		return dao.findOne(entityClass, condition);
	}

	@Override
	public <T> T findOne(Class<T> entityClass, String property, Object value) {
		return this.findOne(entityClass, new Condition(property, Condition.Operator.EQ, value));
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void delete(Class<T> entityClass,Iterable<? extends Serializable> ids) {
		for (Serializable id : ids) {
			dao.delete(entityClass, id);
		}
	}

	@Override
	public <T> void delete(Class<T> entityClass, Integer... ids) {
		for (Serializable id : ids) {
			dao.delete(entityClass, id);
		}
	}

}
