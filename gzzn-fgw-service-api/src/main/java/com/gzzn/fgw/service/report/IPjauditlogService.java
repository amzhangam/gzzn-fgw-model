package com.gzzn.fgw.service.report;

import java.util.List;
import java.util.Set;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.util.web.PageUtil;

/**
 * 设计日志接口,因为对应的数据源
 * {@link com.gzzn.fgw.model.pojo.ReportPojo }
 * 是一个自写封装类，故需要重写增删查改CRUD
 * <p/>
 * <i>Copyright (c) 2014 ITDCL  All right reserved.</i>
 * 
 * @author lzq
 * @version 1.0 <br/>
 * 修改记录（日期+内容）:<br/>
 * ——2014-03-08 09:40:48 lzq new
 */
public interface IPjauditlogService {
	/**
	 * 查询Pjauditlog审计日志表中的相关的项目Id
	 * 
	 * @param cond 条件
	 * @param sort 排序，为空则按默认排序
	 * @return 结果	
	 * */
	Set<Integer> findProjectIdSet(Condition cond); 
	
//	/**
//	 * 根据条件分页查询项目信息
//	 * @param page
//	 * @param condition
//	 * @param sort
//	 * @return
//	 */
//	public PageUtil<Pjauditlog> findList(PageUtil<Pjauditlog> page, Condition condition, Sort sort);
	
	/**
	 * 获取分页数据
	 * @param page
	 * @param countSql 查询记录总数的sql
	 * @param objSql 查询对象的sql
	 * @return
	 */
	public PageUtil<Pjauditlog> findList(PageUtil<Pjauditlog> page,String countSql, String objSql);
	
	/**
	 * 
	 * @param countSql
	 * @param objSql
	 * @return
	 */
	public List<PjbaseinfoPojo> findList(String countSql, String objSql);
	
	/**
	 * 获取主管审核通过项目的分页数据,不包括就项目的数据
	 * @param page
	 * @param objSql
	 * @return
	 */
	public PageUtil<Pjbaseinfo> findPagePassList(PageUtil<Pjbaseinfo> page, String objSql,String directSelfSqlString,String from,Integer organizationId);
	
	
	public List<PjbaseinfoPojo> findAllExpList(String objSql,String directSelfSqlString,String from,Integer organizationId);

}
