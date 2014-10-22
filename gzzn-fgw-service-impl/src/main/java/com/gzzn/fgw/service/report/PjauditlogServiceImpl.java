package com.gzzn.fgw.service.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: PjauditlogServiceImpl </p>
 * <p>Description: 设计日志维护实现类 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lzq  
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-25 下午3:51:28 lzq  new
 * @param 
 */
@Service
@Transactional
public class PjauditlogServiceImpl implements IPjauditlogService  {
	@Resource
	IEntityDao entityDao;

	@Override
	public Set<Integer> findProjectIdSet(Condition condition) {
		Set<Integer> projectIdSet=new HashSet<Integer>();
		List<Pjauditlog> pjlog=entityDao.find(Pjauditlog.class, condition);
		for(Pjauditlog obj:pjlog){
			projectIdSet.add(obj.getPjbaseinfo().getProjectid());			
		}
		pjlog.clear();
		return projectIdSet;
	}
	
//	@Override
//	public PageUtil<Pjauditlog> findList(PageUtil<Pjauditlog> page,
//			Condition condition, Sort sort) {
//		//查询一页的机构信息
//		List<Pjauditlog> listAfter = new ArrayList<Pjauditlog>();
//		List<Pjauditlog> listData = entityDao.find(Pjauditlog.class, condition, sort);
//		
//		page.setCount(listData.size());
//		listAfter = listData.subList(page.getOffset(), page.getSize()+page.getOffset()>page.getEndIndex()?page.getEndIndex():page.getSize()+page.getOffset());
//		page.setList(listAfter);
//		
//		return page;
//	}
	
	@Override
	public PageUtil<Pjauditlog> findList(PageUtil<Pjauditlog> page,String countSql, String objSql) {
		long count = entityDao.findCountBySql(countSql);
		page.setCount(Integer.valueOf(count+""));
		//查询一页的机构信息
//		int pageSize = page.getSize();
//		int pageSize = 15;
		int limit = page.getLimitNum();
		int offSet = page.getOffset();
		List<Pjauditlog> listData = entityDao.findPageBySql(objSql,limit,offSet);
		page.setList(listData);
		
		return page;
	}
	
	@Override
	public List<PjbaseinfoPojo> findList(String countSql, String objSql) {
		List<Pjauditlog> listData = entityDao.findBySql(objSql);
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		for(Pjauditlog objData : listData){
			PjbaseinfoPojo obj = this.copyPjbaseinfo(objData.getPjbaseinfo());
			list.add(obj);
		}
		return list;
	}

	/**
	 * 将项目基本信息、项目投资信息赋值到PjbaseinfoPojo中
	 * @param objData
	 * @return
	 */
	private PjbaseinfoPojo copyPjbaseinfo(Pjbaseinfo objData){
		PjbaseinfoPojo obj = new PjbaseinfoPojo();
		PojoCopyUtil.copySameTypeField(objData, obj);
		PojoCopyUtil.copySameTypeField(objData.getProjectinvests().iterator().next(), obj);
		
		obj.setXmsbHylb(objData.getXmsbHylb());
		obj.setXmsbXmlx(objData.getXmsbXmlx());
		obj.setXmsbZjxz(objData.getXmsbZjxz());
		obj.setSysOrganizationByDeclareunitsid(objData.getSysOrganizationByDeclareunitsid());
		obj.setSysOrganizationByDirectorunitsId(objData.getSysOrganizationByDirectorunitsid());
		obj.setSysOrganizationByRecordOrgan(objData.getSysOrganizationByRecordOrgan());
		obj.setSysUserByNexttacheer(objData.getSysUserByNexttacheer());
		obj.setSysUserByRecorderid(objData.getSysUserByRecorderid());
		obj.setSysXq(objData.getSysXq());
		
		return obj;
	}
	
	@Override
	public List<PjbaseinfoPojo> findAllExpList(String objSql,String directSelfSqlString,String from,Integer organizationId) {
		List<Pjbaseinfo> newListData = new ArrayList<Pjbaseinfo>();
		List<Integer> newIdListData = new ArrayList<Integer>();
		List<Pjauditlog> listData = entityDao.findBySql(objSql);
		Map<Integer,Pjauditlog> pjauditlogMap = new HashMap<Integer, Pjauditlog>();//项目Id和项目审核日志Map
		if(listData!=null&&!listData.isEmpty()){
			for(int i=0;i<listData.size();i++){
				Pjbaseinfo pjbaseinfo = listData.get(i).getPjbaseinfo();
				if(!pjauditlogMap.containsKey(pjbaseinfo.getProjectid())){
					pjauditlogMap.put(pjbaseinfo.getProjectid(),listData.get(i));
				}
				else{
					Pjauditlog oldPjauditlog = pjauditlogMap.get(pjbaseinfo.getProjectid());
					if(listData.get(i).getRecordertime()==null||(oldPjauditlog.getRecordertime()!=null&&listData.get(i).getPjauditlogid()>oldPjauditlog.getPjauditlogid())){
						pjauditlogMap.put(pjbaseinfo.getProjectid(),listData.get(i));
					}
				}
			}
		}
		Iterator<Pjauditlog> iterator = pjauditlogMap.values().iterator();
		while (iterator.hasNext()) {
			Pjauditlog pjauditlog = (Pjauditlog) iterator.next();
			if(pjauditlog.getRecordertime()!=null&&pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){
				if(pjauditlog.getPjaudittype()!=null&&(pjauditlog.getPjaudittype()==IConstants.SHLX_1||pjauditlog.getPjaudittype()==IConstants.SHLX_2||pjauditlog.getPjaudittype()==IConstants.SHLX_5)){//兼容主管单位审核和各处室处长审核,各处室经办审核通过不算
					newListData.add(pjauditlog.getPjbaseinfo());
					newIdListData.add(pjauditlog.getPjbaseinfo().getProjectid());
				}	
			}
		}
		
		if(from!=null&&from.equals("1")){//主管单位自己申报给自己的项目也算审核通过，要排出与上面重复的项目
			
			 List<Pjbaseinfo> directPjbaseinfos = entityDao.findBySql(directSelfSqlString);
			 
			 if(directPjbaseinfos!=null&&!directPjbaseinfos.isEmpty()){
				 for(Pjbaseinfo temPjbaseinfo:directPjbaseinfos){
					 if(!newIdListData.contains(temPjbaseinfo.getProjectid())){
						 newListData.add(temPjbaseinfo);
					 }
				 }
			 }
			
		}
		
		List<PjbaseinfoPojo> list = new ArrayList<PjbaseinfoPojo>();
		for(Pjbaseinfo objData : newListData){
			PjbaseinfoPojo obj = this.copyPjbaseinfo(objData);
			list.add(obj);
		}
		return list;
	}

	/**
	 * 获取主管审核通过项目的分页数据,不包括就项目的数据
	 * @param page
	 * @param objSql
	 * @return
	 */
	public PageUtil<Pjbaseinfo> findPagePassList(PageUtil<Pjbaseinfo> page, String objSql,String directSelfSqlString,String from,Integer organizationId){
		int pageSize = 15;
		int offSet = page.getOffset();
		long count = 0;
		List<Integer> newIdListData = new ArrayList<Integer>();
		List<Pjbaseinfo> newListData = new ArrayList<Pjbaseinfo>();
		List<Pjbaseinfo> pageListData = new ArrayList<Pjbaseinfo>();
		List<Pjauditlog> listData = entityDao.findBySql(objSql);
		Map<Integer,Pjauditlog> pjauditlogMap = new HashMap<Integer, Pjauditlog>();//项目Id和项目审核日志Map
		if(listData!=null&&!listData.isEmpty()){
			for(int i=0;i<listData.size();i++){
				Pjbaseinfo pjbaseinfo = listData.get(i).getPjbaseinfo();
				if(!pjauditlogMap.containsKey(pjbaseinfo.getProjectid())){
					pjauditlogMap.put(pjbaseinfo.getProjectid(),listData.get(i));
				}
				else{
					Pjauditlog oldPjauditlog = pjauditlogMap.get(pjbaseinfo.getProjectid());
					if(listData.get(i).getRecordertime()==null||(oldPjauditlog.getRecordertime()!=null&&listData.get(i).getPjauditlogid()>oldPjauditlog.getPjauditlogid())){
						pjauditlogMap.put(pjbaseinfo.getProjectid(),listData.get(i));
					}
				}
			}
		}
		Iterator<Pjauditlog> iterator = pjauditlogMap.values().iterator();
		while (iterator.hasNext()) {
			Pjauditlog pjauditlog = (Pjauditlog) iterator.next();
			if(pjauditlog.getRecordertime()!=null&&pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){
				newListData.add(pjauditlog.getPjbaseinfo());
				newIdListData.add(pjauditlog.getPjbaseinfo().getProjectid());
				count++;
			}
		}
		
		if(from!=null&&from.equals("1")){//主管单位自己申报给自己的项目也算审核通过，要排出与上面重复的项目
			
			 List<Pjbaseinfo> directPjbaseinfos = entityDao.findBySql(directSelfSqlString);
			 
			 if(directPjbaseinfos!=null&&!directPjbaseinfos.isEmpty()){
				 for(Pjbaseinfo temPjbaseinfo:directPjbaseinfos){
					 if(!newIdListData.contains(temPjbaseinfo.getProjectid())){
						 newListData.add(temPjbaseinfo);
						 count++;
					 }
				 }
			 }
			
		}
		
		 Collections.sort(newListData, new PjbaseinfoComparator());
		 
		page.setCount(Integer.valueOf(count+""));
		
		if(newListData.size()>=(offSet+page.getSize())){
			pageListData = newListData.subList(offSet,offSet+page.getSize());
		}
		else{
			pageListData = newListData.subList(offSet,newListData.size());
		}
		
		page.setList(pageListData);
		
		return page;
	}
	
	public static class PjbaseinfoComparator implements Comparator<Pjbaseinfo>{
		
		public int compare(Pjbaseinfo o1, Pjbaseinfo o2) {
			
			boolean result =o1.getDeclartime().after(o2.getDeclartime());
			if(result){
				return 1;
			}else{
				return 0;
			}
		}
	}
}
