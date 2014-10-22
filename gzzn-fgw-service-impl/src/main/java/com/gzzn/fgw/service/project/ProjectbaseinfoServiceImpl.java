package com.gzzn.fgw.service.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.util.ReadFileByteUtil;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.ProjectinvestSh;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.XmpsPjadjunct;
import com.gzzn.fgw.model.XmpsPjbaseinfo;
import com.gzzn.fgw.model.XmpsProjectinvest;
import com.gzzn.fgw.model.XmpsPsrw;
import com.gzzn.fgw.model.XmpsPsrwlx;
import com.gzzn.fgw.model.XmpsXmfjnr;
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
 * 2.2014-03-08 PM 14:20:58 lzq update
 */
@Service
@Transactional
public class ProjectbaseinfoServiceImpl extends AbstractService<PjbaseinfoPojo> implements
		IProjectbaseinfoService {
	@Resource
	private IEntityDao entityDao;
	
//	@Override
//	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page,
//			Condition condition, Sort sort,SysUser user) {
//		List<Projectinvest> listData = entityDao.find(Projectinvest.class, condition, sort);
//		page = getPageUtil(listData,page,user,false);
//		
//		return page;
//	}
	
//	@Override
//	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page,
//			Condition condition, Sort sort,SysUser user) {
//		List<Pjbaseinfo> listData = entityDao.find(Pjbaseinfo.class, condition, sort);
//		page = getPageUtil(listData,page,user,false);
//		
//		return page;
//	}
	
	@Override
	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page,String countSql, String objSql,Map<Integer,String> organMap) {
		long count = entityDao.findCountBySql(countSql);
		page.setCount(Integer.valueOf(count+""));
		int limit = page.getLimitNum();
//		int pageSize = 20;
		int offSet = page.getOffset();
		List<Pjbaseinfo> listData = entityDao.findPageBySql(objSql,limit,offSet);
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		for(Pjbaseinfo objData : listData){
			PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
			list.add(obj);
		}
		page.setList(list);
		return page;
	}
	
	@Override
	public List<PjbaseinfoPojo> findList(String countSql, String objSql,Map<Integer,String> organMap) {
		List<Pjbaseinfo> listData = entityDao.findBySql(objSql);
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		for(Pjbaseinfo objData : listData){
			PjbaseinfoPojo obj = this.copyPjbaseinfoAndProjectinvest(objData,organMap);
			list.add(obj);
		}
		return list;
	}
	
	@Override
	public PageUtil<PjbaseinfoPojo> findListSh(PageUtil<PjbaseinfoPojo> page,String countSql, String objSql,Map<Integer,String> organMap) {
		long count = entityDao.findCountBySql(countSql);
		page.setCount(Integer.valueOf(count+""));
		int limit = page.getLimitNum();
//		int pageSize = 20;
		int offSet = page.getOffset();
		List<Pjbaseinfo> listData = entityDao.findPageBySql(objSql,limit,offSet);
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		for(Pjbaseinfo objData : listData){
			PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
			list.add(obj);
		}
		page.setList(list);
		return page;
	}
	
	@Override
	public List<PjbaseinfoPojo> findListSh(String countSql, String objSql,Map<Integer,String> organMap) {
		List<Pjbaseinfo> listData = entityDao.findBySql(objSql);
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		for(Pjbaseinfo objData : listData){
			PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
			list.add(obj);
		}
		return list;
	}
	
	@Override
	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page,List<Pjbaseinfo> listData,SysUser user,Map<Integer,String> organMap) {
		//返回数据集
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		List<PjbaseinfoPojo> listAfter = new ArrayList<PjbaseinfoPojo>();
		
		for(Pjbaseinfo objData : listData){
			if(objData.getPjstatus()>0){
				PjbaseinfoPojo obj = new PjbaseinfoPojo();
				PojoCopyUtil.copySameTypeField(objData, obj);
				
				obj.setXmsbHylb(objData.getXmsbHylb());
				obj.setXmsbXmlx(objData.getXmsbXmlx());
				obj.setXmsbZjxz(objData.getXmsbZjxz());
				obj.setSysOrganizationByDeclareunitsid(objData.getSysOrganizationByDeclareunitsid());
				obj.setSysOrganizationByDirectorunitsId(objData.getSysOrganizationByDirectorunitsid());
				obj.setSysOrganizationByRecordOrgan(objData.getSysOrganizationByRecordOrgan());
				obj.setSysUserByNexttacheer(objData.getSysUserByNexttacheer());
				obj.setSysUserByRecorderid(objData.getSysUserByRecorderid());
				obj.setSysXq(objData.getSysXq());
				list.add(obj);
			}
			else{
				if(user.getSysOrganization()!=null&&objData.getSysOrganizationByDeclareunitsid()!=null
						&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByDeclareunitsid().getOrganizationId())){
					PjbaseinfoPojo obj = new PjbaseinfoPojo();
					PojoCopyUtil.copySameTypeField(objData, obj);
					
					obj.setXmsbHylb(objData.getXmsbHylb());
					obj.setXmsbXmlx(objData.getXmsbXmlx());
					obj.setXmsbZjxz(objData.getXmsbZjxz());
					obj.setSysOrganizationByDeclareunitsid(objData.getSysOrganizationByDeclareunitsid());
					obj.setSysOrganizationByDirectorunitsId(objData.getSysOrganizationByDirectorunitsid());
					obj.setSysOrganizationByRecordOrgan(objData.getSysOrganizationByRecordOrgan());
					obj.setSysUserByNexttacheer(objData.getSysUserByNexttacheer());
					obj.setSysUserByRecorderid(objData.getSysUserByRecorderid());
					obj.setSysXq(objData.getSysXq());
					list.add(obj);
				}
			}
			
		}
		page.setSize(20);
		page.setCount(list.size());
		System.out.println("page.getOffset()="+page.getOffset());
		listAfter = list.subList(page.getOffset(), page.getSize()+page.getOffset()>page.getEndIndex()?page.getEndIndex():page.getSize()+page.getOffset());
		page.setList(listAfter);
		
		
		return page;
	}

	@Override
	public String findSysOrginationJson(Integer unitType) {
		Condition con = new Condition();
		if(unitType!=null&&unitType>0){
			con.add("workunitstype", Operator.ISNOTNULL,null);
			con.add("workunitstype", Operator.EQ,unitType);
		}
		Order order1 = new Order(Direction.ASC, "organizationId");
		Sort sort = new Sort(order1);
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

	@Override
	public String findSysDictionaryitemsJson(String name) {
		Condition con = new Condition();
		con.add("name", Operator.ISNOTNULL,null);
		con.add("name", Operator.EQ,name);
		Order order1 = new Order(Direction.ASC, "id");
		Sort sort = new Sort(order1);
		List<SysDictionaryitems> list = entityDao.find(SysDictionaryitems.class, con, sort);
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysDictionaryitems obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getId()));
			objTree.setName(obj.getItemtext());
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	@Override
	public List<Pjbaseinfo> findPjbaseinfos() {
		Condition con = new Condition();
		con.add("pjstatus", Operator.ISNOTNULL,null);
		con.add("pjstatus", Operator.NE,IConstants.XMZT_0);
		con.add("pjstatus", Operator.NE,IConstants.XMZT_12);
		List<Pjbaseinfo> list = entityDao.find(Pjbaseinfo.class, con);
		return list;
	}
	
	@Override
	public String findPjbaseinfoTreeJson() {
		Condition con = new Condition();
		con.add("pjstatus", Operator.ISNOTNULL,null);
		con.add("pjstatus", Operator.NE,IConstants.XMZT_0);
		con.add("pjstatus", Operator.NE,IConstants.XMZT_12);
		List<Pjbaseinfo> list = entityDao.find(Pjbaseinfo.class, con);
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(Pjbaseinfo obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getProjectid()));
			objTree.setName(obj.getProjectname());
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}

	/**
	 * 将项目转到项目评审管理系统(研评中心)
	 * @param pjbaseinfo
	 * @param fpath
	 * @throws Exception
	 */
	public void toxmps(Pjbaseinfo pjbaseinfo,String fpath,String organizationName)throws Exception{
		XmpsPjbaseinfo xmpsPjbaseinfo = new XmpsPjbaseinfo();
		PojoCopyUtil.copySameTypeField(pjbaseinfo, xmpsPjbaseinfo);
		
		xmpsPjbaseinfo.setXmsbHylb(pjbaseinfo.getXmsbHylb());
		xmpsPjbaseinfo.setXmsbXmlx(pjbaseinfo.getXmsbXmlx());
		xmpsPjbaseinfo.setXmsbZjxz(pjbaseinfo.getXmsbZjxz());
		xmpsPjbaseinfo.setSysOrganizationByDeclareunitsid(pjbaseinfo.getSysOrganizationByDeclareunitsid());
		xmpsPjbaseinfo.setSysOrganizationByDirectorunitsid(pjbaseinfo.getSysOrganizationByDirectorunitsid());
		xmpsPjbaseinfo.setSysUserByNexttacheer(pjbaseinfo.getSysUserByNexttacheer());
		xmpsPjbaseinfo.setSysUserByRecorderid(pjbaseinfo.getSysUserByRecorderid());
		xmpsPjbaseinfo.setSysXq(pjbaseinfo.getSysXq());
		xmpsPjbaseinfo.setPjstatus(IConstants.XMZT_13);
		xmpsPjbaseinfo.setXmly(organizationName);
		entityDao.save(xmpsPjbaseinfo);
		pjbaseinfo.setPjstatus(IConstants.XMZT_13);
//		entityDao.save(pjbaseinfo);
		
		if(pjbaseinfo.getProjectinvests()!=null&&!pjbaseinfo.getProjectinvests().isEmpty()){
			Projectinvest projectinvest = pjbaseinfo.getProjectinvests().iterator().next();
			XmpsProjectinvest xmpsProjectinvest = new XmpsProjectinvest(); 
			PojoCopyUtil.copySameTypeField(projectinvest, xmpsProjectinvest);
			xmpsProjectinvest.setXmpsPjbaseinfo(xmpsPjbaseinfo);
			entityDao.save(xmpsProjectinvest);
		}
		
		if(pjbaseinfo.getPjadjuncts()!=null&&!pjbaseinfo.getPjadjuncts().isEmpty()){
			for(Pjadjunct pjadjunct:pjbaseinfo.getPjadjuncts()){
				XmpsPjadjunct xmpsPjadjunct = new XmpsPjadjunct();
				PojoCopyUtil.copySameTypeField(pjadjunct, xmpsPjadjunct);
				xmpsPjadjunct.setXmpsPjbaseinfo(xmpsPjbaseinfo);
				entityDao.save(xmpsPjadjunct);
				File file = new File(pjadjunct.getFileurl());
				boolean flag = file.exists();

				if(flag){
					
					byte[] bytes = ReadFileByteUtil.getBytes(file);
					
					XmpsXmfjnr xmpsXmfjnr = new XmpsXmfjnr();
					xmpsXmfjnr.setPjadjunctid(xmpsPjadjunct.getPjadjunctid());
					xmpsXmfjnr.setFjnr(bytes);
					entityDao.save(xmpsXmfjnr);
				}
			}
		}
		
		Condition con = new Condition();
		con.add("psrwlxid", Operator.ISNOTNULL,null);
		con.add("psrwlxid", Operator.EQ,1);
		List<XmpsPsrwlx> list = (List<XmpsPsrwlx>) entityDao.findOne(XmpsPsrwlx.class, con);
		XmpsPsrw xmpsPsrw = new XmpsPsrw();
		xmpsPsrw.setProjectid(xmpsPjbaseinfo.getProjectid());
		xmpsPsrw.setPsrwzt(1);
		if(list!=null&&!list.isEmpty()){
			xmpsPsrw.setXmpsPsrwlx(list.get(0));
		}
		xmpsPsrw.setXmbh(xmpsPjbaseinfo.getProjectcode());
		xmpsPsrw.setXmmc(xmpsPjbaseinfo.getProjectname());
		xmpsPsrw.setSbdw(xmpsPjbaseinfo.getSysOrganizationByDeclareunitsid()!=null?xmpsPjbaseinfo.getSysOrganizationByDeclareunitsid().getOrganizationName():null);
		xmpsPsrw.setSbrq(xmpsPjbaseinfo.getDeclartime());
		entityDao.save(xmpsPsrw);
		SysProjectlog projectlog = new SysProjectlog();
		String operationContent = "请补充"+pjbaseinfo.getProjectname()+"项目指标数据";
		byte[] data = operationContent.getBytes("UTF-8");
		projectlog.setOperationContent(data);
		projectlog.setOperationDatetime(new Date());
		projectlog.setPjbaseinfo(pjbaseinfo);
		projectlog.setRead(0);
		projectlog.setSysOrganization(pjbaseinfo.getSysOrganizationByDeclareunitsid());
		entityDao.save(projectlog);
	}

//	@Override
//	public PageUtil<PjbaseinfoPojo> findListbySql(String sqlString, PageUtil<PjbaseinfoPojo> page, SysUser user) {
//		List<Projectinvest> listData = entityDao.findBySql(sqlString);
//		
//		return getPageUtil(listData,page,user,false);
//	}
//	@Override
//	public PageUtil<PjbaseinfoPojo> findListbySql(String sqlString, PageUtil<PjbaseinfoPojo> page, SysUser user) {
//		List<Pjbaseinfo> listData = entityDao.findBySql(sqlString);
//		
//		return getPageUtil(listData,page,user,false);
//	}
	
//	/**
//	 * 
//	 * @param listData
//	 * @param page
//	 * @param user
//	 * @param onlyNew 是否只查新项目 true-只查新项目（非PJ开头的项目编码） false-新旧项目一起查
//	 * @return
//	 */
//	private PageUtil<PjbaseinfoPojo> getPageUtil(List<Projectinvest> listData, PageUtil<PjbaseinfoPojo> page, SysUser user,boolean onlyNew) {
//		//返回数据集
//				List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
//				List<PjbaseinfoPojo> listAfter = new ArrayList<PjbaseinfoPojo>();
//				//查询一页的机构信息
//				//System.out.println("当前页面="+page.getCurrent()+"===页面条数="+page.getSize()+"====总记录数="+page.getCount()+"====开始位置="+page.getOffset()+"===结束位置="+page.getEndIndex());
////				List<Pjbaseinfo> listData = entityDao.find(Pjbaseinfo.class, condition, sort,page.getOffset(),page.getSize());
////				page.setCount((int) entityDao.count(Pjbaseinfo.class, condition)); 
////				page.setCount((int) entityDao.count(Pjbaseinfo.class, condition)); 
//
//				list = this.getPjbaseinfoPojo(listData, user, onlyNew);
//				page.setSize(20);
//				page.setCount(list.size());
//				System.out.println("page.getOffset()="+page.getOffset());
//				listAfter = list.subList(page.getOffset(), page.getSize()+page.getOffset()>page.getEndIndex()?page.getEndIndex():page.getSize()+page.getOffset());
//				page.setList(listAfter);
//				
//				return page;
//	}
	
	/**
	 * 
	 * @param listData
	 * @param page
	 * @param user
	 * @param onlyNew 是否只查新项目 true-只查新项目（非PJ开头的项目编码） false-新旧项目一起查
	 * @return
	 */
	private PageUtil<PjbaseinfoPojo> getPageUtil(List<Pjbaseinfo> listData, PageUtil<PjbaseinfoPojo> page, SysUser user,boolean onlyNew,Map<Integer,String> organMap) {
		//返回数据集
				List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
				List<PjbaseinfoPojo> listAfter = new ArrayList<PjbaseinfoPojo>();
				//查询一页的机构信息
				//System.out.println("当前页面="+page.getCurrent()+"===页面条数="+page.getSize()+"====总记录数="+page.getCount()+"====开始位置="+page.getOffset()+"===结束位置="+page.getEndIndex());
//				List<Pjbaseinfo> listData = entityDao.find(Pjbaseinfo.class, condition, sort,page.getOffset(),page.getSize());
//				page.setCount((int) entityDao.count(Pjbaseinfo.class, condition)); 
//				page.setCount((int) entityDao.count(Pjbaseinfo.class, condition)); 

				list = this.getPjbaseinfoPojo(listData, user, onlyNew,organMap);
				page.setSize(20);
				page.setCount(list.size());
				System.out.println("page.getOffset()="+page.getOffset());
				listAfter = list.subList(page.getOffset(), page.getSize()+page.getOffset()>page.getEndIndex()?page.getEndIndex():page.getSize()+page.getOffset());
				page.setList(listAfter);
				
				return page;
	}
	
	
//	public List<PjbaseinfoPojo> getPjbaseinfoPojo(List<Projectinvest> listData, SysUser user,boolean onlyNew){
//		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
//		
//		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_1)){
//			for(Projectinvest objData : listData){
//				if(objData.getPjbaseinfo().getPjstatus()>0){
//					if(objData.getPjbaseinfo().getSysOrganizationByRecordOrgan()!=null&&user.getSysOrganization().getOrganizationId().equals(objData.getPjbaseinfo().getSysOrganizationByRecordOrgan().getOrganizationId())){
//						PjbaseinfoPojo obj = this.copyPjinvestInfo(objData);
//						if(onlyNew){
//							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
//								list.add(obj);
//							}
//						}else{
//							list.add(obj);
//						}
//					}
//				}else{
//					if(user.getSysOrganization()!=null&&objData.getPjbaseinfo().getSysOrganizationByDeclareunitsid()!=null
//							&&user.getSysOrganization().getOrganizationId().equals(objData.getPjbaseinfo().getSysOrganizationByDeclareunitsid().getOrganizationId())){
//						PjbaseinfoPojo obj = this.copyPjinvestInfo(objData);
//						if(onlyNew){
//							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
//								list.add(obj);
//							}
//						}else{
//							list.add(obj);
//						}
//					}
//				}
//				
//			}
//		}
//		else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
//			for(Projectinvest objData : listData){
//				if(objData.getPjbaseinfo().getPjstatus()>0){
//					if((objData.getPjbaseinfo().getSysOrganizationByRecordOrgan()!=null&&user.getSysOrganization().getOrganizationId().equals(objData.getPjbaseinfo().getSysOrganizationByRecordOrgan().getOrganizationId()))
//							||(objData.getPjbaseinfo().getSysOrganizationByDirectorunitsid()!=null&&user.getSysOrganization().getOrganizationId().equals(objData.getPjbaseinfo().getSysOrganizationByDirectorunitsid().getOrganizationId()))){
//						PjbaseinfoPojo obj = this.copyPjinvestInfo(objData);
//						if(onlyNew){
//							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
//								list.add(obj);
//							}
//						}else{
//							list.add(obj);
//						}
//					}
//				}
//				else{
//					if(user.getSysOrganization()!=null&&objData.getPjbaseinfo().getSysOrganizationByRecordOrgan()!=null
//							&&user.getSysOrganization().getOrganizationId().equals(objData.getPjbaseinfo().getSysOrganizationByRecordOrgan().getOrganizationId())){
//						PjbaseinfoPojo obj = this.copyPjinvestInfo(objData);
//						if(onlyNew){
//							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
//								list.add(obj);
//							}
//						}
//						else{
//							list.add(obj);
//						}
//					}
//				}
//				
//			}
//		}
//		else if(user!=null&&user.getUserType()!=null&&(user.getUserType().equals(IConstants.USER_TYPE_3)||user.getUserType().equals(IConstants.USER_TYPE_5))){//超级管理员admin
//			for(Projectinvest objData : listData){
//				if(objData.getPjbaseinfo().getPjstatus()>0){
//					PjbaseinfoPojo obj = this.copyPjinvestInfo(objData);
//					if(onlyNew){
//						if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
//							list.add(obj);
//						}
//					}
//					else{
//						list.add(obj);
//					}
//				}
//				else{
//					if(user.getSysOrganization()!=null&&objData.getPjbaseinfo().getSysOrganizationByRecordOrgan()!=null
//							&&user.getSysOrganization().getOrganizationId().equals(objData.getPjbaseinfo().getSysOrganizationByRecordOrgan().getOrganizationId())){
//						PjbaseinfoPojo obj = this.copyPjinvestInfo(objData);
//						if(onlyNew){
//							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
//								list.add(obj);
//							}
//						}
//						else{
//							list.add(obj);
//						}
//					}
//				}
//				
//			}
//		}
//		return list;
//	}
	
	public List<PjbaseinfoPojo> getPjbaseinfoPojo(List<Pjbaseinfo> listData, SysUser user,boolean onlyNew,Map<Integer,String> organMap){
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		
		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_1)){
			for(Pjbaseinfo objData : listData){
				if(objData.getPjstatus()>0){
					if(objData.getSysOrganizationByRecordOrgan()!=null&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByRecordOrgan().getOrganizationId())){
						PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
						if(onlyNew){
							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
								list.add(obj);
							}
						}else{
							list.add(obj);
						}
					}
				}else{
					if(user.getSysOrganization()!=null&&objData.getSysOrganizationByDeclareunitsid()!=null
							&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByDeclareunitsid().getOrganizationId())){
						PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
						if(onlyNew){
							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
								list.add(obj);
							}
						}else{
							list.add(obj);
						}
					}
				}
				
			}
		}
		else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
			for(Pjbaseinfo objData : listData){
				if(objData.getPjstatus()>0){
					if((objData.getSysOrganizationByRecordOrgan()!=null&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByRecordOrgan().getOrganizationId()))
							||(objData.getSysOrganizationByDirectorunitsid()!=null&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByDirectorunitsid().getOrganizationId()))){
						PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
						if(onlyNew){
							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
								list.add(obj);
							}
						}else{
							list.add(obj);
						}
					}
				}
				else{
					if(user.getSysOrganization()!=null&&objData.getSysOrganizationByRecordOrgan()!=null
							&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByRecordOrgan().getOrganizationId())){
						PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
						if(onlyNew){
							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
								list.add(obj);
							}
						}
						else{
							list.add(obj);
						}
					}
				}
				
			}
		}
		else if(user!=null&&user.getUserType()!=null&&(user.getUserType().equals(IConstants.USER_TYPE_3)||user.getUserType().equals(IConstants.USER_TYPE_5))){//超级管理员admin
			for(Pjbaseinfo objData : listData){
				if(objData.getPjstatus()>0){
					PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
					if(onlyNew){
						if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
							list.add(obj);
						}
					}
					else{
						list.add(obj);
					}
				}
				else{
					if(user.getSysOrganization()!=null&&objData.getSysOrganizationByRecordOrgan()!=null
							&&user.getSysOrganization().getOrganizationId().equals(objData.getSysOrganizationByRecordOrgan().getOrganizationId())){
						PjbaseinfoPojo obj = this.copyPjbaseinfo(objData,organMap);
						if(onlyNew){
							if(obj.getProjectcode()!=null&&obj.getProjectcode().length()>2&&!obj.getProjectcode().substring(0,2).equalsIgnoreCase("PJ")){
								list.add(obj);
							}
						}
						else{
							list.add(obj);
						}
					}
				}
				
			}
		}
		return list;
	}
	
//	/**
//	 * 将项目基本信息、项目投资信息赋值到PjbaseinfoPojo中
//	 * @param objData
//	 * @return
//	 */
//	private PjbaseinfoPojo copyPjinvestInfo(Projectinvest objData){
//		PjbaseinfoPojo obj = new PjbaseinfoPojo();
//		PojoCopyUtil.copySameTypeField(objData, obj);
//		PojoCopyUtil.copySameTypeField(objData.getPjbaseinfo(), obj);
//		
//		obj.setXmsbHylb(objData.getPjbaseinfo().getXmsbHylb());
//		obj.setXmsbXmlx(objData.getPjbaseinfo().getXmsbXmlx());
//		obj.setXmsbZjxz(objData.getPjbaseinfo().getXmsbZjxz());
//		obj.setSysOrganizationByDeclareunitsid(objData.getPjbaseinfo().getSysOrganizationByDeclareunitsid());
//		obj.setSysOrganizationByDirectorunitsId(objData.getPjbaseinfo().getSysOrganizationByDirectorunitsid());
//		obj.setSysOrganizationByRecordOrgan(objData.getPjbaseinfo().getSysOrganizationByRecordOrgan());
//		obj.setSysUserByNexttacheer(objData.getPjbaseinfo().getSysUserByNexttacheer());
//		obj.setSysUserByRecorderid(objData.getPjbaseinfo().getSysUserByRecorderid());
//		obj.setSysXq(objData.getPjbaseinfo().getSysXq());
//		
//		return obj;
//	}
	/**
	 * 将项目基本信息、项目投资信息赋值到PjbaseinfoPojo中
	 * @param objData
	 * @return
	 */
	private PjbaseinfoPojo copyPjbaseinfo(Pjbaseinfo objData,Map<Integer,String> organMap){
		PjbaseinfoPojo obj = new PjbaseinfoPojo();
		PojoCopyUtil.copySameTypeField(objData, obj);
//		PojoCopyUtil.copySameTypeField(objData.getProjectinvests().iterator().next(), obj);
//		if(objData.getProjectinvestShs()!=null&&!objData.getProjectinvestShs().isEmpty()){
//			PojoCopyUtil.copySameTypeField(objData.getProjectinvestShs().iterator().next(), obj);
//		}
		
		obj.setXmsbHylb(objData.getXmsbHylb());
		obj.setXmsbXmlx(objData.getXmsbXmlx());
		obj.setXmsbZjxz(objData.getXmsbZjxz());
		obj.setSysOrganizationByDeclareunitsid(objData.getSysOrganizationByDeclareunitsid());
		obj.setSysOrganizationByDirectorunitsId(objData.getSysOrganizationByDirectorunitsid());
		obj.setBudgetDeclareOrgan(objData.getSysOrganizationByDeclareunitsid()!=null?organMap.get(objData.getSysOrganizationByDeclareunitsid().getOrganizationId()):null);
		obj.setBudgetDirectorOrgan(objData.getSysOrganizationByDirectorunitsid()!=null?organMap.get(objData.getSysOrganizationByDirectorunitsid().getOrganizationId()):null);
		obj.setSysOrganizationByRecordOrgan(objData.getSysOrganizationByRecordOrgan());
		obj.setSysUserByNexttacheer(objData.getSysUserByNexttacheer());
		obj.setSysUserByRecorderid(objData.getSysUserByRecorderid());
		obj.setSysXq(objData.getSysXq());
		
		return obj;
	}
	
	/**
	 * 将项目基本信息、项目投资信息赋值到PjbaseinfoPojo中
	 * @param objData
	 * @return
	 */
	private PjbaseinfoPojo copyPjbaseinfoAndProjectinvest(Pjbaseinfo objData,Map<Integer,String> organMap){
		PjbaseinfoPojo obj = new PjbaseinfoPojo();
		PojoCopyUtil.copySameTypeField(objData, obj);
		PojoCopyUtil.copySameTypeField(objData.getProjectinvests().iterator().next(), obj);
		
		obj.setXmsbHylb(objData.getXmsbHylb());
		obj.setXmsbXmlx(objData.getXmsbXmlx());
		obj.setXmsbZjxz(objData.getXmsbZjxz());
		obj.setSysOrganizationByDeclareunitsid(objData.getSysOrganizationByDeclareunitsid());
		obj.setSysOrganizationByDirectorunitsId(objData.getSysOrganizationByDirectorunitsid());
		obj.setBudgetDeclareOrgan(objData.getSysOrganizationByDeclareunitsid()!=null?organMap.get(objData.getSysOrganizationByDeclareunitsid().getOrganizationId()):null);
		obj.setBudgetDirectorOrgan(objData.getSysOrganizationByDirectorunitsid()!=null?organMap.get(objData.getSysOrganizationByDirectorunitsid().getOrganizationId()):null);
		obj.setSysOrganizationByRecordOrgan(objData.getSysOrganizationByRecordOrgan());
		obj.setSysUserByNexttacheer(objData.getSysUserByNexttacheer());
		obj.setSysUserByRecorderid(objData.getSysUserByRecorderid());
		obj.setSysXq(objData.getSysXq());
		
		return obj;
	}

//	@Override
//	public PageUtil<PjbaseinfoPojo> findNewProjectList(
//			PageUtil<PjbaseinfoPojo> page, Condition condition, Sort sort,
//			SysUser user) {
//		List<Projectinvest> listData = entityDao.find(Projectinvest.class, condition, sort);
//		page = getPageUtil(listData,page,user,true);
//		
//		return page;
//	}
	
//	@Override
//	public PageUtil<PjbaseinfoPojo> findNewProjectList(
//			PageUtil<PjbaseinfoPojo> page, Condition condition, Sort sort,
//			SysUser user) {
//		List<Pjbaseinfo> listData = entityDao.find(Pjbaseinfo.class, condition, sort);
//		page = getPageUtil(listData,page,user,true);
//		
//		return page;
//	}
}
