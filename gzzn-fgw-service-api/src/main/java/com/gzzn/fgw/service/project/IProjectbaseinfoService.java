package com.gzzn.fgw.service.project;

import java.util.List;
import java.util.Map;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: ISysDeptService</p>
 * <p>Description: 申报项目信息维护接口   </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-19 下午3:48:13 amzhang  new
 */
public interface IProjectbaseinfoService extends IPagedService<PjbaseinfoPojo> {
	

//	/**
//	 * 根据条件分页查询项目信息
//	 * @param page
//	 * @param condition
//	 * @param sort
//	 * @return
//	 */
//	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page, Condition condition, Sort sort,SysUser user);
//	/**
//	 * 根据条件分页查询项目信息
//	 * @param page
//	 * @param condition
//	 * @param sort
//	 * @return
//	 */
//	public PageUtil<PjbaseinfoPojo> findNewProjectList(PageUtil<PjbaseinfoPojo> page, Condition condition, Sort sort,SysUser user);
	
	/**
	 * 根据条件分页查询项目信息
	 * @param page
	 * @param listData
	 * @param user
	 * @return
	 */
	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page, List<Pjbaseinfo> listData,SysUser user,Map<Integer,String> organMap);
	

	/**
	 * 根据单位类型获取对应的单位ID和单位名称。对应的数据库查询语句：
	 * <code>select distinct * from sys_organization a where a.workunitstype=2</code>
	 * 
	 * @param workUnitsType 对应上面SQL语句的a.workunitstype
	 * @return JSON数组
	 */
	public String findSysOrginationJson(Integer workUnitsType);

	/**
	 * 根据单位类型获取对应的单位ID和单位名称。对应的数据库查询语句：
	 * <code>select a.itemvalue, * from sys_dictionaryitems a where a.name='项目状态'</code>
	 * 
	 * @param  name 对应上面SQL语句的a.name
	 * @return JSON数组
	 */
	public String findSysDictionaryitemsJson(String name);
	
	/**
	 * 获取树形list：按照上级ID、ID排序
	 * @return
	 */
	public String findPjbaseinfoTreeJson();
	
	public List<Pjbaseinfo> findPjbaseinfos();
	
	/**
	 * 将项目转到项目评审管理系统(研评中心)
	 * @param pjbaseinfo
	 * @param fpath
	 * @throws Exception
	 */
	public void toxmps(Pjbaseinfo pjbaseinfo,String fpath,String organizationName)throws Exception;
	
//	public PageUtil<PjbaseinfoPojo> findListbySql(String sqlString, PageUtil<PjbaseinfoPojo> page, SysUser user);
	
//	/**
//	 * 将Projectinvest转成PjbaseinfoPojo对象，且各角色查看对应信息
//	 * @param listData
//	 * @param user
//	 * @param onlyNew true 只查新项目；false 新旧项目都查询
//	 * @return
//	 */
//	public List<PjbaseinfoPojo> getPjbaseinfoPojo(List<Projectinvest> listData, SysUser user,boolean onlyNew);
//	/**
//	 * 将Projectinvest转成PjbaseinfoPojo对象，且各角色查看对应信息
//	 * @param listData
//	 * @param user
//	 * @param onlyNew true 只查新项目；false 新旧项目都查询
//	 * @return
//	 */
//	public List<PjbaseinfoPojo> getPjbaseinfoPojo(List<Pjbaseinfo> listData, SysUser user,boolean onlyNew);
	
	/**
	 * 获取分页数据
	 * @param page
	 * @param countSql 查询记录总数的sql
	 * @param objSql 查询对象的sql
	 * @return
	 */
	public PageUtil<PjbaseinfoPojo> findList(PageUtil<PjbaseinfoPojo> page,String countSql, String objSql,Map<Integer,String> organMap);
	
	/**
	 * 不分页
	 * @param countSql
	 * @param objSql
	 * @return
	 */
	public List<PjbaseinfoPojo> findList(String countSql, String objSql,Map<Integer,String> organMap);
	
	/**
	 * 获取分页数据
	 * @param page
	 * @param countSql 查询记录总数的sql
	 * @param objSql 查询对象的sql
	 * @return
	 */
	public PageUtil<PjbaseinfoPojo> findListSh(PageUtil<PjbaseinfoPojo> page,String countSql, String objSql,Map<Integer,String> organMap);
	
	/**
	 * 不分页
	 * @param countSql
	 * @param objSql
	 * @return
	 */
	public List<PjbaseinfoPojo> findListSh(String countSql, String objSql,Map<Integer,String> organMap);

}
