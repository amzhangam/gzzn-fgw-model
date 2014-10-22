package com.gzzn.fgw.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.sys.ISysRolePermObjService;
import com.gzzn.fgw.model.SysModule;
import com.gzzn.fgw.model.SysPermissionobject;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysRolePermissionobject;
import com.gzzn.fgw.model.SysSystem;
/**
 * <p>Title: SysRolePermObjServiceImpl</p>
 * <p>Description: 角色权限分配实现类  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-11 下午4:27:41 amzhang  new
 */
@Service
@Transactional
public class SysRolePermObjServiceImpl extends AbstractService<SysRolePermissionobject> implements
		ISysRolePermObjService {
	
	@Resource
	private IEntityDao entityDao;

	@Override
	public List<TreeNodesPojo> findAllPermission() {
		List<TreeNodesPojo> listTNP = new ArrayList<TreeNodesPojo>();
		Condition conS = new Condition();
		conS.add("useStatus", Operator.EQ, 0);//使态   0-表示正常使用；1-表示暂停使用；
		List<SysSystem> listSS = entityDao.find(SysSystem.class, conS, new Sort(Direction.ASC, "systemId"));
		TreeNodesPojo objTNP = null;
		for(SysSystem objSS : listSS){
			objTNP = new TreeNodesPojo();
			objTNP.setId("S"+objSS.getSystemId().toString());
			objTNP.setpId("0");
			objTNP.setName(objSS.getSystemName());
			listTNP.add(objTNP);
			//获取该系统下的模块信息
			this.getSysModuleTNP(objSS.getSystemId(), listTNP);
		}
		return listTNP;
	}
	
	/**
	 * 获取指定系统下的全部模块信息节点
	 * S - 系统表、M - 系统模块表、  P - 权限对象
	 * @param systemId
	 * @param listTNP
	 */
	private void getSysModuleTNP(Integer systemId,List<TreeNodesPojo> listTNP){
		Condition conM = new Condition();
		conM.add("sysSystem.systemId", Operator.EQ, systemId);
		List<SysModule> listSM = entityDao.find(SysModule.class, conM, new Sort(Direction.ASC, "moduleId"));
		TreeNodesPojo objTNP = null;
		for(SysModule objSM : listSM){
			objTNP = new TreeNodesPojo();
			objTNP.setId("M"+objSM.getModuleId().toString());
			if(objSM.getParentModule()!=null && objSM.getParentModule().getModuleId()>0L){
				objTNP.setpId("M"+objSM.getParentModule().getModuleId());
			}else{
				objTNP.setpId("S"+systemId);
			}
			objTNP.setName(objSM.getModuleName());
			listTNP.add(objTNP);
			//获取模块下边的权限对象：【2013-12-31权限只控制到页面，不控制到按钮】
			this.getSysPermissionObjectTNP(objSM.getModuleId(), listTNP);
		}
	}
	
	/**
	 * 获取指定模块下的全部权限对象节点
	 * S - 系统表、M - 系统模块表、  P - 权限对象
	 * @param moduleId
	 * @param listTNP
	 */
	private void getSysPermissionObjectTNP(Integer moduleId, List<TreeNodesPojo> listTNP){
		Condition conP = new Condition();
		conP.add("sysModule.moduleId", Operator.EQ, moduleId);
		List<SysPermissionobject> listSP = entityDao.find(SysPermissionobject.class, conP, new Sort(Direction.ASC, "permissionobjectId"));
		TreeNodesPojo objTNP = null;
		for(SysPermissionobject objSP : listSP){
			objTNP = new TreeNodesPojo();
			objTNP.setId("P"+objSP.getPermissionobjectId());
			objTNP.setpId("M"+moduleId);
			objTNP.setName(objSP.getPermissionobjectName());
			listTNP.add(objTNP);
		}
	}
	

	@Override
	public List<String> findActorPermissionNodes(String actorId, String sType) {
		List<String> listretn = new ArrayList<String>();
		if(StringUtils.isNotEmpty(actorId)){
			Condition con = new Condition();
			con.add("sysRole.roleId", Operator.EQ, Integer.parseInt(actorId));
			con.add("sysSystem.useStatus", Operator.EQ, 0);//系统使用使态   0-表示正常使用；1-表示暂停使用；
			List<SysRolePermissionobject> list = entityDao.find(SysRolePermissionobject.class, con, new Sort(Direction.ASC, "rolePermissionId"));
			if(list.size()>0){
				if(sType.equalsIgnoreCase("leafNodes")){
					getLeafNodes(list,listretn);
				}else{
					getAllNodes(list,listretn);
				}
			}
		}
				
		return listretn;
	}
	
	
	/**
	 * sType=leafNodes：只选出具有权限的叶子节点
	 * @param list
	 * @param listretn
	 * @return
	 */
	private void getLeafNodes(List<SysRolePermissionobject> list,List<String> listretn){
		for(SysRolePermissionobject obj : list){
			//【2013-12-31权限只控制到页面，不控制到按钮】
			if(obj.getSysPermissionobject()!=null){
				addNodesToList("P"+obj.getSysPermissionobject().getPermissionobjectId().toString(),listretn);
			}else 
			if(obj.getSysModule()!=null){
				addNodesToList("M"+obj.getSysModule().getModuleId().toString(),listretn);
			}else if(obj.getSysSystem()!=null){
				addNodesToList("S"+obj.getSysSystem().getSystemId().toString(),listretn);
			}
		}
	}
	
	/**
	 * sType=nodes：选出具有权限的全部节点
	 * @param list
	 * @param listretn
	 * @return
	 */
	private void getAllNodes(List<SysRolePermissionobject> list,List<String> listretn){
		for(SysRolePermissionobject obj : list){
			//对象信息 【2013-12-31权限只控制到页面，不控制到按钮】
			if(obj.getSysPermissionobject()!=null){
				addNodesToList("P"+obj.getSysPermissionobject().getPermissionobjectId().toString(),listretn);
			}
			//模块信息进行递归调用获取
			recSysModel(obj.getSysModule(),listretn);
			//系统信息
			if(obj.getSysSystem()!=null){
				addNodesToList("S"+obj.getSysSystem().getSystemId().toString(),listretn);
			}
		}
	}
	
	/**
	 * 递归调用：如果当前模块被选中的话，那它的上级（上上级。。。）模块也将被选中 
	 * @param sysModule
	 * @param listretn
	 * @return
	 */
	private void recSysModel(SysModule sysModule,List<String> listretn){
		if(sysModule!=null && sysModule.getModuleId()>0){
			addNodesToList("M"+sysModule.getModuleId().toString(),listretn);
			recSysModel(sysModule.getParentModule(),listretn);
		}
	}
	
	/**
	 * 将节点nodes的值加入到List中
	 * @param nodes
	 * @param listretn
	 * @return
	 */
	private void addNodesToList(String nodes,List<String> listretn){
		if(StringUtils.isNotEmpty(nodes) && !listretn.contains(nodes)){
			listretn.add(nodes); 
		}
	}
	
	

	@Override
	public void saveChecked(String actorId, String ids) {
		//第一步：查找该角色信息
		if(StringUtils.isNotEmpty(actorId)){
			SysRole sysRole = entityDao.findOne(SysRole.class, Integer.parseInt(actorId)); 
			if(sysRole!=null){
				//第二步：获取该角色库中具有的全部叶子节点权限，如果库中用户不具有该权限的话就加入该权限
				List<String> listLeafNodes = this.findActorPermissionNodes(actorId, "leafNodes");
				if(StringUtils.isNotEmpty(ids)){
					for(String id :ids.split("@")){
						if(listLeafNodes.size()>0 && listLeafNodes.contains(id)){//用户已经具有该叶子节点的权限
							listLeafNodes.remove(id);
						}else{
							SysRolePermissionobject obj = this.findSavePerObj(id.substring(0,1),id.substring(1),sysRole);
							entityDao.save(obj);
						}
					}
				}
				//第三步：删除掉余下的库表叶子节点权限，用户不具有该权限啦
				if(listLeafNodes.size()>0){
					for(String delId : listLeafNodes){
						SysRolePermissionobject obj = this.findDeletePerObj(delId.substring(0,1),delId.substring(1),sysRole);
						if(obj != null){
							entityDao.delete(obj);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 获取即将要保存的角色权限
	 * @param nowTab  表名
	 * @param nowId	  当前表的主键Id
	 * @param sysRole 角色对象
	 * @return
	 */
	private SysRolePermissionobject findSavePerObj(String nowTab,String nowId,SysRole sysRole){
		SysRolePermissionobject obj = new SysRolePermissionobject();
		obj.setSysRole(sysRole);
		//对象信息 【2013-12-31权限只控制到页面，不控制到按钮】
		if(nowTab.equalsIgnoreCase("P")){//权限对象表
			SysPermissionobject objSP = entityDao.findOne(SysPermissionobject.class, Integer.parseInt(nowId));
			obj.setSysPermissionobject(objSP);
			obj.setSysModule(objSP.getSysModule());
			obj.setSysSystem(objSP.getSysModule()==null?null:objSP.getSysModule().getSysSystem());
		}else
		if(nowTab.equalsIgnoreCase("M")){//模块表
			SysModule objSM = entityDao.findOne(SysModule.class, Integer.parseInt(nowId));
			obj.setSysModule(objSM);
			obj.setSysSystem(objSM.getSysSystem());
		}else if(nowTab.equalsIgnoreCase("S")){//系统表
			SysSystem objSS = entityDao.findOne(SysSystem.class, Integer.parseInt(nowId));
			obj.setSysSystem(objSS);
		}
		return obj;
	}
	
	/**
	 * 获取即将要被删除的角色权限
	 * @param nowTab  表名
	 * @param nowId	  当前表的主键Id
	 * @param sysRole 角色对象
	 * @return
	 */
	private SysRolePermissionobject findDeletePerObj(String nowTab,String nowId,SysRole sysRole){
		Condition con = new Condition();
		con.add("sysRole", Operator.EQ, sysRole);
		//对象信息 【2013-12-31权限只控制到页面，不控制到按钮】
		if(nowTab.equalsIgnoreCase("P")){//权限对象表
			con.add("sysPermissionobject.permissionobjectId", Operator.EQ, Integer.parseInt(nowId));
		}else
		if(nowTab.equalsIgnoreCase("M")){//模块表
			con.add("sysModule.moduleId", Operator.EQ, Integer.parseInt(nowId));
		}else if(nowTab.equalsIgnoreCase("S")){//系统表
			con.add("sysSystem.systemId", Operator.EQ, Integer.parseInt(nowId));
		}
		return  entityDao.findOne(SysRolePermissionobject.class, con);
	}

}
