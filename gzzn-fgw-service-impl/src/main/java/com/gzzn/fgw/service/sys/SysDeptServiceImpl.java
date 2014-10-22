package com.gzzn.fgw.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.sys.pojo.SysDeptPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysDeptServiceImpl</p>
 * <p>Description: 部门信息维护实现类 </p>
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
public class SysDeptServiceImpl extends AbstractService<SysDeptPojo> implements
		ISysDeptService {


	@Resource
	private IEntityDao entityDao;
	
	@Override
	public PageUtil<SysDeptPojo> findList(PageUtil<SysDeptPojo> page,
			Condition condition, Sort sort) {
		//返回数据集
		List<SysDeptPojo> list = new ArrayList<SysDeptPojo>();
		//查询机构信息
		List<SysDept> listData = entityDao.find(SysDept.class, condition, sort);
		//获取全部的上级机构信息
		Map<Integer,SysDept> deptUpMap = getSysDeptUpMap();
		
		page.setCount(listData.size());
		//System.out.println("当前页面="+page.getCurrent()+"===页面条数="+page.getSize()+"====总记录数="+page.getCount()+"====开始位置="+page.getOffset()+"===结束位置="+page.getEndIndex());
		listData = listData.subList(page.getOffset(), page.getEndIndex());
		
		for(SysDept objData : listData){
			SysDeptPojo obj = new SysDeptPojo();
			PojoCopyUtil.copySameTypeField(objData, obj);
			
			if(objData.getParentdeptid()!=null && deptUpMap.containsKey(objData.getParentdeptid())){
				obj.setSysDept(deptUpMap.get(objData.getParentdeptid()));
			}
			list.add(obj);
		}
		page.setList(list);
		
		return page;
	}

	@Override
	public String findSysDeptTreeJson(Boolean nocheck) {
		Condition con = new Condition();
//		con.add("deleted", Operator.EQ, "1");//使用状态：1 正常 ，0 删除
//		if(StringUtils.isNotEmpty(organizationId)){
//			con.add("sysOrganization.organizationId", Operator.EQ, Integer.parseInt(organizationId));//使用状态：1 正常 ，0 删除
//			con.add("sysOrganization.organizationId",Operator.ISNOTNULL,null);
//		}
		con.add("sfxs", Operator.ISNOTNULL,null);
		con.add("sfxs", Operator.EQ,1);
		Order order1 = new Order(Direction.ASC, "levelnumber");
		Order order2 = new Order(Direction.ASC, "deptId");
		Sort sort = new Sort(order1, order2);
		List<SysDept> list = entityDao.find(SysDept.class, con, sort);
		
		//获取全部的上级机构信息
		Map<Integer,SysDept> deptUpMap = getSysDeptUpMap();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysDept obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getDeptId()));
			objTree.setpId(String.valueOf(obj.getParentdeptid()));
			objTree.setName(obj.getDeptname());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getDeptId()!=null && deptUpMap.containsKey(obj.getDeptId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}

	
	/**
	 * 查找上级部门信息Map
	 * @return
	 */
	private Map<Integer,SysDept> getSysDeptUpMap(){
		Map<Integer,SysDept> map = new HashMap<Integer, SysDept>();
		
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//删除标记：0-	非删除 1-	逻辑删除
		List<SysDept> list = entityDao.find(SysDept.class, con);
		for(SysDept obj : list){
			if(obj.getParentdeptid()!=null && obj.getParentdeptid()>0 && !map.containsKey(obj.getParentdeptid())){
				SysDept deptUpObj = getSysDeptUpObj(list, obj.getParentdeptid());
				if(deptUpObj != null){
					map.put(obj.getParentdeptid(), deptUpObj);
				}
			}
		}
		return map;
	}
	
	/**
	 * 根据上级部门ID返回上级部门对象
	 * @param list
	 * @param organUpId
	 * @return
	 */
	public SysDept getSysDeptUpObj(List<SysDept> list,Integer deptUpId){
		if(deptUpId>0){
			for(SysDept deptUpObj : list){
				if(deptUpId.equals(deptUpObj.getDeptId())){
					return deptUpObj;
				}
			}
		}
		
		return null;
	}
	
}
