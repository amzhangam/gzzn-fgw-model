package com.gzzn.fgw.service.sys;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.sys.ISysOrganizationService;
import com.gzzn.fgw.service.sys.pojo.SysOrgPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.util.common.Reflections;
import com.gzzn.util.web.PageUtil;


/**
 * 
 * <p>Title: SysOrganizationServiceImpl</p>
 * <p>Description: 机构信息维护实现类 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-4 下午7:51:28 amzhang  new
 */

@Service
@Transactional
public class SysOrganizationServiceImpl extends AbstractService<SysOrgPojo> implements ISysOrganizationService {

	@Resource
	private IEntityDao entityDao;

	@Override
	public PageUtil<SysOrgPojo> findList(PageUtil<SysOrgPojo> page, Condition condition, Sort sort) {
		//返回数据集
		List<SysOrgPojo> list = new ArrayList<SysOrgPojo>();
		//查询机构信息
		List<SysOrganization> listData = entityDao.find(SysOrganization.class, condition, sort);
		Map<Object,String> dWLXMap = this.getDWLXMap();
		Map<Object,String> dWXZMap = this.getDWXZMap();
		
//		//获取全部的上级机构信息
//		Map<String,SysOrganization> organUpMap = getOrganUpMap();
		
		page.setCount(listData.size());
		listData = listData.subList(page.getOffset(), page.getEndIndex());
		
		for(SysOrganization objData : listData){
			SysOrgPojo obj = new SysOrgPojo();
			PojoCopyUtil.copySameTypeField(objData, obj);
			if(objData.getWorkunitstype()!=null){
				obj.setWorkunitstypeName(dWLXMap.get(objData.getWorkunitstype())); 
			}
			if(objData.getWorkunitsquality()!=null){
				obj.setWorkunitsqualityName(dWXZMap.get(objData.getWorkunitsquality())); 
			}
//			if(objData.getOrganUpId()!=null && organUpMap.containsKey(String.valueOf(objData.getOrganUpId()))){
//				obj.setSysOrganization(organUpMap.get(String.valueOf(objData.getOrganUpId())));
//			}
			list.add(obj);
		}
		page.setList(list);
		
		return page;
	}
	
	@Override
	public Map<Object,String> getDWLXMap() {
		Map<Object,String> dWLXMap = new HashMap<Object, String>();
		dWLXMap.put(IConstants.ORGAN_TYPE_1, IConstants.ORGAN_TYPE_NAME_1);
		dWLXMap.put(IConstants.ORGAN_TYPE_2, IConstants.ORGAN_TYPE_NAME_2);
		dWLXMap.put(IConstants.ORGAN_TYPE_3, IConstants.ORGAN_TYPE_NAME_3);
		dWLXMap.put(IConstants.ORGAN_TYPE_4, IConstants.ORGAN_TYPE_NAME_4);
		return dWLXMap;
	}

	@Override
	public Map<Object, String> getDWXZMap() {
		Map<Object,String> dWXZMap = new HashMap<Object, String>();
		dWXZMap.put(IConstants.DWXZ_CODE_1.intValue(), IConstants.DWXZ_NAME_1);
		dWXZMap.put(IConstants.DWXZ_CODE_2.intValue(), IConstants.DWXZ_NAME_2);
		dWXZMap.put(IConstants.DWXZ_CODE_3.intValue(), IConstants.DWXZ_NAME_3);
		dWXZMap.put(IConstants.DWXZ_CODE_4.intValue(), IConstants.DWXZ_NAME_4);
		dWXZMap.put(IConstants.DWXZ_CODE_5.intValue(), IConstants.DWXZ_NAME_5);
		return dWXZMap;
	}

	
	
	@Override
	public String findSysOrgTreeJson(Boolean nocheck,String workunitstype){
		Condition con = new Condition();
		Order order2 = new Order(Direction.ASC, "organizationId");
		Sort sort = new Sort(order2);
		if(StringUtils.isNotEmpty(workunitstype)){
			con.add("workunitstype", Operator.ISNOTNULL,null);
			con.add("workunitstype", Operator.EQ,workunitstype);
		}
		List<SysOrganization> list = entityDao.find(SysOrganization.class, con, sort);
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysOrganization obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getOrganizationId()));
			objTree.setName(obj.getOrganizationName());
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 根据上级机构ID返回上级机构对象
	 * @param list
	 * @param organUpId
	 * @return
	 */
	public SysOrganization getOrganUpObj(List<SysOrganization> list,String organUpId){
		for(SysOrganization organUpObj : list){
			if(organUpId.equals(String.valueOf(organUpObj.getOrganizationId()))){
				return organUpObj;
			}
		}
		return null;
	}

	@Override
	public void saveOrUpdate(SysOrganization obj) {
		SysOrganization objSys = null;
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			if (m.getAnnotation(Id.class) != null) {
				Serializable id = (Serializable) Reflections.invokeMethod(obj, m.getName(),
						null, null);
				if (id != null) {
					objSys = entityDao.findOne(SysOrganization.class, id);
				}
				if(objSys == null){
					entityDao.save(obj);
				}
				entityDao.update(obj);
				break;
			}
		}
	}

	/**
	 * 获取所属地区信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	@Override
	public String findAreaTreeJson(Boolean nocheck){
		Condition con = new Condition();
		Order order1 = new Order(Direction.ASC, "xqId");
		Sort sort = new Sort(order1);
		List<SysXq> list = entityDao.find(SysXq.class, con, sort);
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysXq obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getXqId()));
			objTree.setName(obj.getXqmc());
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 获取单位性质信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	@Override
	public String findUnitNatureTreeJson(Boolean nocheck){
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
	
	/**
	 * 获取单位类型
	 * @param nocheck
	 * @return
	 */
	public String findUnitTypeTreeJson(Boolean nocheck){
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.ORGAN_TYPE_1));
		objTree.setName(IConstants.ORGAN_TYPE_NAME_1);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.ORGAN_TYPE_2));
		objTree.setName(IConstants.ORGAN_TYPE_NAME_2);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.ORGAN_TYPE_3));
		objTree.setName(IConstants.ORGAN_TYPE_NAME_3);
		listTree.add(objTree);
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 获取单位状态
	 * @param nocheck
	 * @return
	 */
	public String findUnitStatusTreeJson(Boolean nocheck){
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_1));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_1);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_2));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_2);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_3));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_3);
		listTree.add(objTree);
		
		return new Gson().toJson(listTree);
	}


}
