package com.gzzn.fgw.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.util.IConstants;

@Service
@Transactional
public class SysDictionaryitemsServiceImpl extends
		AbstractService<SysDictionaryitems> implements
		ISysDictionaryitemsService {
	
	@Resource
	private IEntityDao entityDao;


	@Override
	public List<SysDictionaryitems> findDictionaryitems(String name) {
		Condition condition = new Condition();
		condition.add("name", Operator.EQ,name);
		Order order = new Order(Direction.ASC,"id");
		Sort sort = new Sort(order);
		List<SysDictionaryitems> list = entityDao.find(SysDictionaryitems.class,condition,sort);
		return list;
	}
	
	@Override
	public List<SysDictionaryitems> findDictionaryitems() {
		Condition condition = new Condition();
		Order order = new Order(Direction.DESC,"id");
		Sort sort = new Sort(order);
		List<SysDictionaryitems> list = entityDao.find(SysDictionaryitems.class,condition,sort);
		return list;
	}
	
	/**
	 * 获取项目类型信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findProjectTypeTreeJson(Boolean nocheck){
		Condition con = new Condition();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.DWXZ_CODE_1));
		objTree.setName(IConstants.DWXZ_NAME_1);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.DWXZ_CODE_2));
		objTree.setName(IConstants.DWXZ_NAME_2);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.DWXZ_CODE_3));
		objTree.setName(IConstants.DWXZ_NAME_3);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.DWXZ_CODE_4));
		objTree.setName(IConstants.DWXZ_NAME_4);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.DWXZ_CODE_5));
		objTree.setName(IConstants.DWXZ_NAME_5);
		listTree.add(objTree);
		
		return new Gson().toJson(listTree);
	}

}
