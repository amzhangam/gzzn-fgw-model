package com.gzzn.fgw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;

@Service
@Transactional(readOnly = true)
public class TreeListOrJsonServiceImpl implements ITreeListOrJsonService{
	
	@Resource
	private ICommonService commonService;

	@Override
	public String getSysOrgTreeJson(Boolean nocheck) {
		List<TreeNodesPojo>  listTree = this.findSysOrgTree(nocheck, false, false);
		
		return new Gson().toJson(listTree);
	}

	@Override
	public String getSysDeptTreeJson(String organizationId, Boolean nocheck) {
		List<TreeNodesPojo>  listTree = this.findSysDeptTree(organizationId, false, nocheck);
		
		return new Gson().toJson(listTree);
	}

	@Override
	public String getOrgDeptTreeJson(Boolean nocheckOrg, Boolean nocheckDept) {
		List<TreeNodesPojo>  listTree = this.findSysOrgTree(nocheckOrg, true, nocheckDept);
		
		return new Gson().toJson(listTree);
	}
	
	
	public List<TreeNodesPojo> findSysOrgTree(Boolean nocheckOrg, Boolean casDeptInfo,Boolean nocheckDept){
		Condition con = new Condition();
		Order order2 = new Order(Direction.ASC, "organizationId");
		Sort sort = new Sort(order2);
		List<SysOrganization> list = commonService.find(SysOrganization.class, con, sort);
		
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysOrganization obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(casDeptInfo?String.valueOf("g"+obj.getOrganizationId()):String.valueOf(obj.getOrganizationId()));
			objTree.setName(obj.getOrganizationName());
			//是否隐藏父节点，当前节点是否为父节点
			if(casDeptInfo){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
			
			//是否级联部门信息
			if(casDeptInfo){
				List<TreeNodesPojo> listDept = findSysDeptTree(String.valueOf(obj.getOrganizationId()), casDeptInfo, nocheckDept);
				if(listDept!=null){
					listTree.addAll(listDept);
				}
			}
		}
		return listTree;
	}
	
	
	public List<TreeNodesPojo> findSysDeptTree(String organizationId, Boolean casDeptInfo, Boolean nocheckDept){
		List<SysDept> list = this.findSysDeptList(organizationId);
		//获取全部的上级部门信息
		Map<Integer,SysDept> deptUpMap = getSysDeptUpMap();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysDept obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getDeptId()));
			objTree.setpId(casDeptInfo&&obj.getParentdeptid()==null?"g"+organizationId:String.valueOf(obj.getParentdeptid()));
			objTree.setName(obj.getDeptname());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheckDept && obj.getDeptId()!=null && deptUpMap.containsKey(obj.getDeptId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		return listTree;
	}
	
	private List<SysDept> findSysDeptList(String organizationId){
		Condition con = new Condition();
//		con.add("deleted", Operator.EQ, "1");//使用状态：1 正常 ，0 删除
		if(StringUtils.isNotEmpty(organizationId)){
			con.add("sysOrganization.organizationId", Operator.EQ, Integer.parseInt(organizationId));//使用状态：1 正常 ，0 删除
			con.add("sysOrganization.organizationId",Operator.ISNOTNULL,null);
		}
		Order order1 = new Order(Direction.ASC, "levelnumber");
		Order order2 = new Order(Direction.ASC, "deptId");
		Sort sort = new Sort(order1, order2);
		con.add("sfxs", Operator.ISNOTNULL,null);
		con.add("sfxs", Operator.EQ,1);
		List<SysDept> list = commonService.find(SysDept.class, con, sort);
		return list;
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
	
	
	/**
	 * 查找上级部门信息Map
	 * @return
	 */
	private Map<Integer,SysDept> getSysDeptUpMap(){
		Map<Integer,SysDept> map = new HashMap<Integer, SysDept>();
		Condition con = new Condition();
//		con.add("deleted", Operator.EQ, "1");//使用状态：1 正常 ，0 删除
		List<SysDept> list = commonService.find(SysDept.class, con);
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
