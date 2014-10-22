package com.gzzn.fgw.service.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.common.persist.jdbc.IJdbcDao;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.XmsbZjxz;
import com.gzzn.fgw.model.pojo.CommonJsonDataPojo;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.com.ICommonJsonDataService;
import com.gzzn.fgw.util.IConstants;

/**
 * 
 * <p>Title: CommonJsonDataServiceImpl</p>
 * <p>Description:  获取通用JSON实现类 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-28 lhq  new
 */
@Service
@Transactional
public class CommonJsonDataServiceImpl implements ICommonJsonDataService {
	@Resource
	private IEntityDao entityDao;
	@Resource
	private IJdbcDao jdbcDao;
	
	private List<TreeNodesPojo> listTree = null;
	private TreeNodesPojo objTree = null;
	

	@Override
	public String findProjectInfoJson(Condition con, Sort sort) {
		List<Pjbaseinfo> list = entityDao.find(Pjbaseinfo.class, con, sort);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(Pjbaseinfo obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(obj.getProjectid().toString());
			objTree.setName(obj.getProjectname()!=null?obj.getProjectname().trim():null);
			listTree.add(objTree);
		}
		return new Gson().toJson(listTree);
	}
	
	@Override
	public String findXmsbHylbJson(Boolean nocheck) {
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
		Order order1 = new Order(Direction.ASC, "hylbId");
		Order order2 = new Order(Direction.ASC, "xmsbHylb.hylbId");
		Sort sort = new Sort(order1, order2);
		List<XmsbHylb> list = entityDao.find(XmsbHylb.class, con, sort);
		
		//获取全部的上级行业类别信息
		Map<Integer,XmsbHylb> hylbUpMap = getXmsbHylbUpMap(list);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(XmsbHylb obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getHylbId()));
			objTree.setpId(obj.getXmsbHylb()==null?null:String.valueOf(obj.getXmsbHylb().getHylbId()));
			objTree.setName(obj.getHylbmc());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getHylbId()!=null && hylbUpMap.containsKey(obj.getHylbId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找上级行业类别信息Map
	 * @return
	 */
	private Map<Integer,XmsbHylb> getXmsbHylbUpMap(List<XmsbHylb> list){
		Map<Integer,XmsbHylb> map = new HashMap<Integer, XmsbHylb>();
		for(XmsbHylb obj : list){
			if(obj.getXmsbHylb()!=null && !map.containsKey(obj.getXmsbHylb().getHylbId())){
				map.put(obj.getXmsbHylb().getHylbId(), obj.getXmsbHylb());
			}
		}
		return map;
	}


	@Override
	public String findXmsbXmlxJson(Boolean nocheck) {
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
		Order order1 = new Order(Direction.ASC, "xmlxId");
		Order order2 = new Order(Direction.ASC, "xmsbXmlx.xmlxId");
		Sort sort = new Sort(order1, order2);
		List<XmsbXmlx> list = entityDao.find(XmsbXmlx.class, con, sort);
		
		//获取全部的上级项目类型信息
		Map<Integer,XmsbXmlx> xmlxUpMap = getXmsbXmlxUpMap(list);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(XmsbXmlx obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getXmlxId()));
			objTree.setpId(obj.getXmsbXmlx()==null?null:String.valueOf(obj.getXmsbXmlx().getXmlxId()));
			objTree.setName(obj.getXmlxmc());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getXmlxId()!=null && xmlxUpMap.containsKey(obj.getXmlxId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找上级项目类型信息Map
	 * @return
	 */
	private Map<Integer,XmsbXmlx> getXmsbXmlxUpMap(List<XmsbXmlx> list){
		Map<Integer,XmsbXmlx> map = new HashMap<Integer, XmsbXmlx>();
		for(XmsbXmlx obj : list){
			if(obj.getXmsbXmlx()!=null && !map.containsKey(obj.getXmsbXmlx().getXmlxId())){
				map.put(obj.getXmsbXmlx().getXmlxId(), obj.getXmsbXmlx());
			}
		}
		return map;
	}
	

	@Override
	public String findXmsbZjxzJson(Boolean nocheck) {
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
		Order order1 = new Order(Direction.ASC, "zjxzId");
		Order order2 = new Order(Direction.ASC, "xmsbZjxz.zjxzId");
		Sort sort = new Sort(order1, order2);
		List<XmsbZjxz> list = entityDao.find(XmsbZjxz.class, con, sort);
		
		//获取全部的上级资金性质信息
		Map<Integer,XmsbZjxz> zjxzUpMap = getXmsbZjxzUpMap(list);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(XmsbZjxz obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getZjxzId()));
			objTree.setpId(obj.getXmsbZjxz()==null?null:String.valueOf(obj.getXmsbZjxz().getZjxzId()));
			objTree.setName(obj.getZjxzmc());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getZjxzId()!=null && zjxzUpMap.containsKey(obj.getZjxzId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找上级资金性质信息Map
	 * @return
	 */
	private Map<Integer,XmsbZjxz> getXmsbZjxzUpMap(List<XmsbZjxz> list){
		Map<Integer,XmsbZjxz> map = new HashMap<Integer, XmsbZjxz>();
		for(XmsbZjxz obj : list){
			if(obj.getXmsbZjxz()!=null && !map.containsKey(obj.getXmsbZjxz().getZjxzId())){
				map.put(obj.getXmsbZjxz().getZjxzId(), obj.getXmsbZjxz());
			}
		}
		return map;
	}

	@Override
	public String findSysXqJson(Condition con, Sort sort, Boolean nocheck) {
		List<SysXq> list = entityDao.find(SysXq.class, con, sort);
		
		//获取全部的上级辖区信息
		Map<Integer,SysXq> xmlbUpMap = getSysXqUpMap(list);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(SysXq obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getXqId()));
			objTree.setpId(obj.getSjxq()==null?null:String.valueOf(obj.getSjxq().getXqId()));
			objTree.setName(obj.getXqmc());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getXqId()!=null && xmlbUpMap.containsKey(obj.getXqId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找上级辖区信息Map
	 * @return
	 */
	private Map<Integer,SysXq> getSysXqUpMap(List<SysXq> list){
		Map<Integer,SysXq> map = new HashMap<Integer, SysXq>();
		for(SysXq obj : list){
			if(obj.getSjxq()!=null && !map.containsKey(obj.getSjxq().getXqId())){
				map.put(obj.getSjxq().getXqId(), obj.getSjxq());
			}
		}
		return map;
	}



	@Override
	public String findSysOrgJson(Condition con, Sort sort, Boolean nocheck) {
		List<SysOrganization> list = entityDao.find(SysOrganization.class, con, sort);
		
		//获取全部的上级单位信息
		Map<Integer,SysOrganization> orgUpMap = getSysOrgUpMap(list);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(SysOrganization obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getOrganizationId()));
			objTree.setName(obj.getOrganizationName());
			objTree.setpId(String.valueOf(obj.getParentOrganizationId()));
			
			//nocheck 父节点的单选或复选按钮是否显示: true不显示, false显示
			if(nocheck && obj.getOrganizationId()!=null && orgUpMap.containsKey(obj.getOrganizationId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找上级单位信息Map
	 * @return
	 */
	private Map<Integer,SysOrganization> getSysOrgUpMap(List<SysOrganization> list){
		Map<Integer,SysOrganization> map = new HashMap<Integer, SysOrganization>();
		Boolean flag = false;//当前单位是否为上级单位
		
		List<SysOrganization> listUp = new ArrayList<SysOrganization>();
		listUp.addAll(list);
		SysOrganization objUp = null;
		for(SysOrganization obj : list){
			flag = false;
			Iterator<SysOrganization> iteUp = listUp.iterator();
			while (iteUp.hasNext()) {
				objUp = iteUp.next();
				 if(objUp.getParentOrganizationId() != null
							&& objUp.getParentOrganizationId().equals(obj.getOrganizationId())){
					 flag = true;
					 iteUp.remove();
				 }
			 }
			if(flag){
				map.put(obj.getOrganizationId(), obj);
			}
		}
		return map;
	}


	@Override
	public String findSysDeptJson(Condition con, Sort sort, Boolean nocheck) {
		listTree = this.findSysDeptList(con, sort, false, nocheck);
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找部门信息List
	 * @param con
	 * @param sort
	 * @param casDeptInfo 是否级联部门信息
	 * @param nocheck
	 * @return
	 */
	private List<TreeNodesPojo> findSysDeptList(Condition con, Sort sort,Boolean casDeptInfo, Boolean nocheck){
		List<SysDept> list = entityDao.find(SysDept.class, con, sort);
		
		//获取全部的上级单位信息
		Map<Integer,SysDept> deptUpMap = getSysDeptUpMap(list);
		
		List<TreeNodesPojo> listDept = new ArrayList<TreeNodesPojo>();
		for(SysDept obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(obj.getDeptId().toString());
			objTree.setName(obj.getDeptname());
			objTree.setpId(casDeptInfo && obj.getParentdeptid() == null
					&& obj.getSysOrganization() != null ? String.valueOf("g"+obj.getSysOrganization().getOrganizationId())
					: String.valueOf(obj.getParentdeptid()));

			//nocheck 父节点的单选或复选按钮是否显示: true不显示, false显示
			if(nocheck && obj.getDeptId()!=null && deptUpMap.containsKey(obj.getDeptId())){
				objTree.setNocheck(true);
			}
			listDept.add(objTree);
		}
		return listDept;
	}
	
	/**
	 * 查找上级部门信息Map
	 * @return
	 */
	private Map<Integer,SysDept> getSysDeptUpMap(List<SysDept> list){
		Map<Integer,SysDept> map = new HashMap<Integer, SysDept>();
		Boolean flag = false;//当前部门是否为上级部门
		
		List<SysDept> listDept = new ArrayList<SysDept>();
		listDept.addAll(list);
		SysDept objUp = null;
		for(SysDept obj : list){
			flag = false;
			Iterator<SysDept> iteUp = listDept.iterator();
			while (iteUp.hasNext()) {
				objUp = iteUp.next();
				 if(objUp.getParentdeptid() != null
							&& objUp.getParentdeptid().equals(obj.getDeptId())){
					 flag = true;
					 iteUp.remove();
				 }
			 }
			if(flag){
				map.put(obj.getDeptId(), obj);
			}
		}
		return map;
	}


	@Override
	public String findOrgDeptTreeJson(Condition conOrg, Sort sortOrg,
			Boolean nocheckOrg, Condition conDept, Sort sortDept,
			Boolean nocheckDept) {
		List<SysOrganization> list = entityDao.find(SysOrganization.class, conOrg, sortOrg);
		List<TreeNodesPojo> listDept = null;
		Condition conDeptNew = null;
		
		//获取全部的上级单位信息
		Map<Integer,SysOrganization> orgUpMap = getSysOrgUpMap(list);
		listTree = new ArrayList<TreeNodesPojo>();
		for(SysOrganization obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf("g"+obj.getOrganizationId()));
			objTree.setName(obj.getOrganizationName());
			objTree.setpId(String.valueOf("g"+obj.getParentOrganizationId()));
			objTree.setNocheck(nocheckOrg);
			
			//nocheck 父节点的单选或复选按钮是否显示: true不显示, false显示
			/**if(nocheckOrg && obj.getOrganizationId()!=null && orgUpMap.containsKey(obj.getOrganizationId())){
				objTree.setNocheck(true);
			}*/
			listTree.add(objTree);
			
			//查找该节点下对应的部门信息
			conDeptNew = new Condition();
			conDeptNew.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
			if (obj.getOrganizationId()!=null && obj.getOrganizationId()>0) {
				//conDept.add("sysOrganization.organizationId", Operator.ISNOTNULL, null);
				//conDept.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
				conDeptNew.add("sysOrganization.organizationId", Operator.ISNOTNULL, null);
				conDeptNew.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
			}
			conDeptNew.add("sfxs", Operator.ISNOTNULL,null);
			conDeptNew.add("sfxs", Operator.EQ,1);
			listDept = this.findSysDeptList(conDeptNew, sortDept, true, nocheckDept);
			if (listDept != null) {
				listTree.addAll(listDept);
			}
		}
		
		return new Gson().toJson(listTree);
	}

	@Override
	public List<SysDictionaryitems> findDictItemsList(Condition con, Sort sort) {
		return entityDao.find(SysDictionaryitems.class, con, sort);
	}

	@Override
	public String findDictItemsJson(Condition con, Sort sort, Boolean nocheck) {
		List<SysDictionaryitems> list = this.findDictItemsList(con, sort);
		
		listTree = new ArrayList<TreeNodesPojo>();
		for(SysDictionaryitems obj : list){
			objTree = new TreeNodesPojo();
			objTree.setId(obj.getItemvalue());
			objTree.setName(obj.getItemtext());
			objTree.setNocheck(nocheck);
			listTree.add(objTree);
		}
		return new Gson().toJson(listTree);
	}
	
	@Override
	public String findReportUserJson(CommonJsonDataPojo params, SysUser sysUser) {
		List<Map<String,Object>> list = this.findReportUserList(params);
		listTree = new ArrayList<TreeNodesPojo>();

		if(sysUser==null){
			for(Map<String,Object> map : list){
				objTree = new TreeNodesPojo();
				objTree.setId(String.valueOf(map.get("user_id")));
				objTree.setName(String.valueOf(map.get("user_name")));
				listTree.add(objTree);
			}
		}else{
			for(Map<String,Object> map : list){
				if(map.get("user_id")!=null && !sysUser.getUserId().equals(Integer.valueOf(map.get("user_id").toString()))){	
					objTree = new TreeNodesPojo();
					objTree.setId(String.valueOf(map.get("user_id")));
					objTree.setName(String.valueOf(map.get("user_name")));
					listTree.add(objTree);
				}
			}
			//加入当前用户的信息
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(sysUser.getUserId()));
			objTree.setName(String.valueOf(sysUser.getUserName()));
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}

	@Override
	public List<Map<String, Object>> findReportUserList(CommonJsonDataPojo params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct t.user_id,t.user_name,t.login_name ");
		sql.append(" from pj_bulid_info p,sys_user t  ");
		sql.append(" where p.user_id=t.user_id ");
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getUseStatus())){
				sql.append(" and t.use_status in(").append(params.getUseStatus()).append(") ");
			}
			if(StringUtils.isNotEmpty(params.getUserType())){
				sql.append(" and t.user_type in(").append(params.getUserType()).append(") ");
			}
		}
		sql.append(" order by t.user_id ");
		return jdbcDao.queryForList(sql.toString());
	}

	@Override
	public String findReportOrgJson(CommonJsonDataPojo params, SysOrganization sysOrganization) {
		List<Map<String,Object>> list = this.findReportOrgList(params);
		listTree = new ArrayList<TreeNodesPojo>();

		if(sysOrganization==null){
			for(Map<String,Object> map : list){
				objTree = new TreeNodesPojo();
				objTree.setId(String.valueOf(map.get("organization_id")));
				objTree.setName(String.valueOf(map.get("organization_name")));
				listTree.add(objTree);
			}
		}else{
			for(Map<String,Object> map : list){
				if(map.get("organization_id")!=null && !sysOrganization.getOrganizationId().equals(Integer.valueOf(map.get("organization_id").toString()))){	
					objTree = new TreeNodesPojo();
					objTree.setId(String.valueOf(map.get("organization_id")));
					objTree.setName(String.valueOf(map.get("organization_name")));
					listTree.add(objTree);
				}
			}
			//加入当前用户的信息
			objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(sysOrganization.getOrganizationId()));
			objTree.setName(String.valueOf(sysOrganization.getOrganizationName()));
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}

	@Override
	public List<Map<String, Object>> findReportOrgList(CommonJsonDataPojo params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct t.organization_id,t.organization_name ");
		sql.append(" from pj_bulid_info p,sys_organization t  ");
		sql.append(" where p.organization_id=t.organization_id ");
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getUseStatus())){
				sql.append(" and t.workunitsstatus in(").append(params.getWorkunitsstatus()).append(") ");
			}
			if(StringUtils.isNotEmpty(params.getUserType())){
				sql.append(" and t.workunitstype in(").append(params.getWorkunitstype()).append(") ");
			}
		}
		sql.append(" order by t.organization_id ");
		return jdbcDao.queryForList(sql.toString());
	}

	@Override
	public List<Map<String, Object>> findReportOrgUserList(CommonJsonDataPojo params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct p.organization_id,s.organization_name,p.user_id,t.user_name ");
		sql.append(" from pj_bulid_info p,sys_organization s,sys_user t  ");
		sql.append(" where p.organization_id=s.organization_id and p.user_id=t.user_id ");
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getOrganizationId())){
				sql.append(" and p.organization_id in(").append(params.getOrganizationId()).append(") ");
			}
			if(StringUtils.isNotEmpty(params.getUserId())){
				sql.append(" and p.user_id in(").append(params.getUserId()).append(") ");
			}
		}
		sql.append(" order by  p.organization_id,p.user_id ");
		return jdbcDao.queryForList(sql.toString());
	}
	
	



}
