package com.gzzn.fgw.service.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.pojo.ReportPojoOld;
import com.gzzn.fgw.service.sys.pojo.CsxmPojo;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: PjbaseinfoReportServiceImpl</p>
 * <p>Description: 项目报表维护实现类 </p>
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
public class ReportServiceImpl implements IReportService{  

	@Autowired
	private IEntityDao entityDao;	 
	
	@Override
	public PageUtil<ReportPojoOld> findList(PageUtil<ReportPojoOld> page,
			Condition condition, Sort sort) { 			
		//System.out.println("当前页面="+page.getCurrent()+"===页面条数="+page.getSize()
		//	+"====总记录数="+page.getCount()+"====开始位置="+page.getOffset()+"===结束位置="+page.getEndIndex());
		// 获取符合条件的一页的记录	
		List<Projectinvest> pjInvestList = entityDao.find(Projectinvest.class
				, condition, sort,page.getOffset(),page.getSize());
		
		List<ReportPojoOld> reportPojoList=new ArrayList<ReportPojoOld>();
		for(Projectinvest obj:pjInvestList){
			ReportPojoOld reportpojo=new ReportPojoOld();
			//投资属性
			PojoCopyUtil.copySameTypeField(obj, reportpojo);
			//项目属性
			PojoCopyUtil.copySameTypeField(obj.getPjbaseinfo(), reportpojo); 
			//主管单位可以为空
			SysOrganization sysorg=obj.getPjbaseinfo().getSysOrganizationByDirectorunitsid();
			reportpojo.setOrganizationName(sysorg==null?"":sysorg.getOrganizationName());
			reportPojoList.add(reportpojo);
		}		
		page.setCount((int) entityDao.count(Projectinvest.class, condition));
		page.setList(reportPojoList);
		pjInvestList.clear();	//清除内存
		return page;
	}

	@Override
	public List<ReportPojoOld> findList(Condition cond, Sort sort) {
		//System.out.println("当前页面="+page.getCurrent()+"===页面条数="+page.getSize()
		//	+"====总记录数="+page.getCount()+"====开始位置="+page.getOffset()+"===结束位置="+page.getEndIndex());
		// 获取符合条件的一页的记录	
		List<Projectinvest> pjInvestList = entityDao.find(Projectinvest.class, cond, sort);	
		
		//封装为自己的类，便于调用
		List<ReportPojoOld> reportPojoList=new ArrayList<ReportPojoOld>();
		for(Projectinvest obj:pjInvestList){
			ReportPojoOld reportpojo=new ReportPojoOld();
			//投资属性
			PojoCopyUtil.copySameTypeField(obj, reportpojo);
			//项目属性
			PojoCopyUtil.copySameTypeField(obj.getPjbaseinfo(), reportpojo); 
			//主管单位可以为空
			SysOrganization sysorg=obj.getPjbaseinfo().getSysOrganizationByDirectorunitsid();
			reportpojo.setOrganizationName(sysorg==null?"":sysorg.getOrganizationName());
			reportPojoList.add(reportpojo);
		}	 
		//清除内存
		pjInvestList.clear();
		return reportPojoList;
	}
	
	@Override
	public PageUtil<Projectinvest> findStatPageList(PageUtil<Projectinvest> page,Condition con, Sort sort) { 
		page.setCount((int) entityDao.count(Projectinvest.class, con));//记录总条数
		page.setList(entityDao.find(Projectinvest.class, con, sort,page.getOffset(),page.getSize()));//当前页显示记录
		return page;
	}
	
	@Override
	public List<Projectinvest> findStatList(Condition con, Sort sort) {
		return entityDao.find(Projectinvest.class, con, sort);
	}
	
//	public List<CsxmPojo> findList(String sqlString,Map<Integer,SysDept> deptMap,String fgwtzc){
//		List<CsxmPojo> csxmPojos = new ArrayList<CsxmPojo>();
////		Iterator<Integer> iterator = deptMap.keySet().iterator();
////		while (iterator.hasNext()) {
////			Integer deptId = (Integer) iterator.next();
////			if(deptMap.get(deptId).getFullcode()!=null&&!deptMap.get(deptId).getFullcode().equalsIgnoreCase(fgwtzc)){
////				CsxmPojo csxmPojo = new CsxmPojo();
////				csxmPojo.setDeptName(deptMap.get(deptId).getDeptname());
////				csxmPojos.add(csxmPojo);
////			}
////		}
//		List<Integer> deptIds = new ArrayList<Integer>();
//		List<Pjauditlog> pjauditlogs = entityDao.findBySql(sqlString);
//		Map<Integer,CsxmPojo> deptPojoMap = new HashMap<Integer, CsxmPojo>();
//		if(pjauditlogs!=null&&!pjauditlogs.isEmpty()){
//			for(Pjauditlog pjauditlog:pjauditlogs){
//				CsxmPojo csxmPojo = null;
//				if(!deptIds.contains(pjauditlog.getSysDeptBySenddeptid().getDeptId())){
//					deptIds.add(pjauditlog.getSysDeptBySenddeptid().getDeptId()); 
//					csxmPojo = new CsxmPojo();
//					csxmPojo.setDeptName(pjauditlog.getSysDeptBySenddeptid().getDeptname());
//					deptPojoMap.put(pjauditlog.getSysDeptBySenddeptid().getDeptId(),csxmPojo);
//					csxmPojos.add(csxmPojo);
//					csxmPojo.setUnPassCount(csxmPojo.getUnPassCount()+1);
//				}
//				else{
//					csxmPojo = deptPojoMap.get(pjauditlog.getSysDeptBySenddeptid().getDeptId());
//					csxmPojo.setUnPassCount(csxmPojo.getUnPassCount()+1);
//				}
//				if(pjauditlog.getPjbaseinfo().getXmztz()!=null&&pjauditlog.getPjbaseinfo().getXmztz()<1000d){//1000万以下 
//					if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==1){//基本建设类
//						csxmPojo.setUnPassUnderJbjsCount(csxmPojo.getUnPassUnderJbjsCount()+1);
//					}
//					else if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==2){//更新改造类
//						csxmPojo.setUnPassUnderGxgzCount(csxmPojo.getUnPassUnderGxgzCount()+1);
//					}
//				}
//				else if(pjauditlog.getPjbaseinfo().getXmztz()!=null&&pjauditlog.getPjbaseinfo().getXmztz()>=1000d){//1000万以上
//					if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==1){//基本建设类
//						csxmPojo.setUnPassUpJbjsCount(csxmPojo.getUnPassUpJbjsCount()+1);
//					}
//					else if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==2){//更新改造类
//						csxmPojo.setUnPassUpGxgzCount(csxmPojo.getUnPassUpGxgzCount()+1);
//					}
//				}
//			}
//		}
//		return csxmPojos;
//	}
	
	public List<CsxmPojo> findList(String unPassSql,String passSql,Map<Integer,SysDept> deptMap,String fgwtzc){
		List<CsxmPojo> csxmPojos = new ArrayList<CsxmPojo>();
		CsxmPojo sumCsxmPojo = new CsxmPojo();
		Map<Integer,CsxmPojo> deptPojoMap = new HashMap<Integer, CsxmPojo>();
		Iterator<Integer> iterator = deptMap.keySet().iterator();
		List<Integer> projectids = new ArrayList<Integer>();
		while (iterator.hasNext()) {
			Integer deptId = (Integer) iterator.next();
			if(deptMap.get(deptId).getSfxs()!=null&&deptMap.get(deptId).getSfxs()==1){
				if(deptMap.get(deptId).getFullcode()!=null&&!deptMap.get(deptId).getFullcode().equalsIgnoreCase(fgwtzc)){
					CsxmPojo csxmPojo = new CsxmPojo();
					csxmPojo.setDeptName(deptMap.get(deptId).getDeptname());
					csxmPojos.add(csxmPojo);
					deptPojoMap.put(deptId,csxmPojo);
				}
			}
		}
		//待审核项目
		List<Pjbaseinfo> unPassPjbaseinfos = entityDao.findBySql(unPassSql);
		if(unPassPjbaseinfos!=null&&!unPassPjbaseinfos.isEmpty()){
			for(Pjbaseinfo pjbaseinfo:unPassPjbaseinfos){
				projectids.add(pjbaseinfo.getProjectid());
				if(pjbaseinfo.getNextauditdept()==null){
					continue;
				}
				CsxmPojo csxmPojo = deptPojoMap.get(pjbaseinfo.getNextauditdept());
				if(csxmPojo==null){
					continue;
				}
				csxmPojo.setUnPassCount(csxmPojo.getUnPassCount()+1);
				if(pjbaseinfo.getXmztz()!=null&&pjbaseinfo.getXmztz()<1000d){//1000万以下 
					if(pjbaseinfo.getXmfl()!=null&&pjbaseinfo.getXmfl()==1){//基本建设类
						csxmPojo.setUnPassUnderJbjsCount(csxmPojo.getUnPassUnderJbjsCount()+1);
					}
					else if(pjbaseinfo.getXmfl()!=null&&pjbaseinfo.getXmfl()==2){//更新改造类
						csxmPojo.setUnPassUnderGxgzCount(csxmPojo.getUnPassUnderGxgzCount()+1);
					}
				}
				else if(pjbaseinfo.getXmztz()!=null&&pjbaseinfo.getXmztz()>=1000d){//1000万以上
					if(pjbaseinfo.getXmfl()!=null&&pjbaseinfo.getXmfl()==1){//基本建设类
						csxmPojo.setUnPassUpJbjsCount(csxmPojo.getUnPassUpJbjsCount()+1);
					}
					else if(pjbaseinfo.getXmfl()!=null&&pjbaseinfo.getXmfl()==2){//更新改造类
						csxmPojo.setUnPassUpGxgzCount(csxmPojo.getUnPassUpGxgzCount()+1);
					}
				}
			}
		}
		
		//审核通过项目
		Map<Integer,Pjauditlog> pjauditlogMap = new HashMap<Integer, Pjauditlog>();
		System.out.println(passSql);
		List<Pjauditlog> pjauditlogs = entityDao.findBySql(passSql);//未筛选的审核日志
		List<Pjauditlog> passPjauditlogs = new ArrayList<Pjauditlog>();//筛选过的审核日志
		
		if(pjauditlogs!=null&&!pjauditlogs.isEmpty()){
			for(Pjauditlog pjauditlog:pjauditlogs){
				CsxmPojo csxmPojo = null;
				if(projectids.contains(pjauditlog.getPjbaseinfo().getProjectid())){
					continue;
				}
				if(!pjauditlogMap.containsKey(pjauditlog.getPjbaseinfo().getProjectid())){
					pjauditlogMap.put(pjauditlog.getPjbaseinfo().getProjectid(), pjauditlog);
				}
				else if(pjauditlogMap.get(pjauditlog.getPjbaseinfo().getProjectid()).getPjauditlogid()>pjauditlog.getPjauditlogid()){
					pjauditlogMap.put(pjauditlog.getPjbaseinfo().getProjectid(), pjauditlog);
				}
				else{
					pjauditlog = pjauditlogMap.get(pjauditlog.getPjbaseinfo().getProjectid());
				}
			}
		}
		
		Iterator<Integer> iter = pjauditlogMap.keySet().iterator();
		while (iter.hasNext()) {
			Integer projectid = (Integer) iter.next();
			Pjauditlog pjauditlog = pjauditlogMap.get(projectid);
			CsxmPojo csxmPojo = deptPojoMap.get(pjauditlog.getSysDeptBySenddeptid().getDeptId());
			if(csxmPojo==null){
				continue;
			}
			csxmPojo.setPassCount(csxmPojo.getPassCount()+1);
			if(pjauditlog.getPjbaseinfo().getXmztz()!=null&&pjauditlog.getPjbaseinfo().getXmztz()<1000d){//1000万以下 
				if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==1){//基本建设类
					csxmPojo.setPassUnderJbjsCount(csxmPojo.getPassUnderJbjsCount()+1);
				}
				else if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==2){//更新改造类
					csxmPojo.setPassUnderGxgzCount(csxmPojo.getPassUnderGxgzCount()+1);
				}
			}
			else if(pjauditlog.getPjbaseinfo().getXmztz()!=null&&pjauditlog.getPjbaseinfo().getXmztz()>=1000d){//1000万以上
				if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==1){//基本建设类
					csxmPojo.setPassUpJbjsCount(csxmPojo.getPassUpJbjsCount()+1);
				}
				else if(pjauditlog.getPjbaseinfo().getXmfl()!=null&&pjauditlog.getPjbaseinfo().getXmfl()==2){//更新改造类
					csxmPojo.setPassUpGxgzCount(csxmPojo.getPassUpGxgzCount()+1);
				}
			}
			
		}
		
		for(CsxmPojo csxmPojo:csxmPojos){
			sumCsxmPojo.setUnPassCount(sumCsxmPojo.getUnPassCount()+csxmPojo.getUnPassCount());
			sumCsxmPojo.setUnPassUnderGxgzCount(sumCsxmPojo.getUnPassUnderGxgzCount()+csxmPojo.getUnPassUnderGxgzCount());
			sumCsxmPojo.setUnPassUnderJbjsCount(sumCsxmPojo.getUnPassUnderJbjsCount()+csxmPojo.getUnPassUnderJbjsCount());
			sumCsxmPojo.setUnPassUpGxgzCount(sumCsxmPojo.getUnPassUpGxgzCount()+csxmPojo.getUnPassUpGxgzCount());
			sumCsxmPojo.setUnPassUpJbjsCount(sumCsxmPojo.getUnPassUpJbjsCount()+csxmPojo.getUnPassUpJbjsCount());
			
			sumCsxmPojo.setPassCount(sumCsxmPojo.getPassCount()+csxmPojo.getPassCount());
			sumCsxmPojo.setPassUnderGxgzCount(sumCsxmPojo.getPassUnderGxgzCount()+csxmPojo.getPassUnderGxgzCount());
			sumCsxmPojo.setPassUnderJbjsCount(sumCsxmPojo.getPassUnderJbjsCount()+csxmPojo.getPassUnderJbjsCount());
			sumCsxmPojo.setPassUpGxgzCount(sumCsxmPojo.getPassUpGxgzCount()+csxmPojo.getPassUpGxgzCount());
			sumCsxmPojo.setPassUpJbjsCount(sumCsxmPojo.getPassUpJbjsCount()+csxmPojo.getPassUpJbjsCount());
		}
		sumCsxmPojo.setDeptName("合计");
		csxmPojos.add(sumCsxmPojo);
		return csxmPojos;
	}
	
}
