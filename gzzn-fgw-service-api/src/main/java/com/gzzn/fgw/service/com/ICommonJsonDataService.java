package com.gzzn.fgw.service.com;

import java.util.List;
import java.util.Map;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.CommonJsonDataPojo;

/**
 * 
 * <p>Title: ICommonJsonDataService</p>
 * <p>Description:  获取通用JSON接口 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-28 lhq  new
 */
public interface ICommonJsonDataService {
	
	/**
	 * 查找项目信息数据JSON串
	 * @param con
	 * @param sort
	 * @return
	 */
	public String findProjectInfoJson(Condition con, Sort sort);
	
	/**
	 * 获取行业类别的树形list：按照上级ID、ID排序
	 * @param nocheck  父节点的单选或复选按钮是否显示: true不显示, false显示
	 * @return
	 */
	public String findXmsbHylbJson(Boolean nocheck);
	

	/**
	 * 获取项目类型的树形list：按照上级ID、ID排序
	 * @param nocheck  父节点的单选或复选按钮是否显示: true不显示, false显示
	 * @return
	 */
	public String findXmsbXmlxJson(Boolean nocheck);
	

	/**
	 * 获取资金性质的树形list：按照上级ID、ID排序
	 * @param nocheck  父节点的单选或复选按钮是否显示: true不显示, false显示
	 * @return
	 */
	public String findXmsbZjxzJson(Boolean nocheck);
	
	/**
	 * 获取辖区的树形list：按照上级ID、ID排序
	 * @param nocheck  父节点的单选或复选按钮是否显示: true不显示, false显示
	 * @return
	 */
	public String findSysXqJson(Condition con, Sort sort, Boolean nocheck);
	
	
	/**
	 * 获取单位信息的树形list：按照上级ID、ID排序
	 * @param con
	 * @param sort
	 * @param nocheck 父节点的单选或复选按钮是否显示: true不显示, false显示
	 * @return
	 */
	public String findSysOrgJson(Condition con, Sort sort, Boolean nocheck);
	
	
	/**
	 * 获取部门信息的树形list：按照上级ID、ID排序
	 * @param con
	 * @param sort
	 * @param nocheck 父节点的单选或复选按钮是否显示: true不显示, false显示
	 * @return
	 */
	public String findSysDeptJson(Condition con, Sort sort, Boolean nocheck);
	
	/**
	 * 获取机构、部门的组合树，为了区分是机构的id还是部门的id，特在机构id前面加上g
	 * @param conOrg
	 * @param sortOrg
	 * @param nocheckOrg 机构信息的单选或复选按钮是否显示: true不显示 false显示
	 * @param conDept
	 * @param sortDept
	 * @param nocheckDept 部门信息的单选或复选按钮是否显示: true不显示 false显示
	 * @return
	 */
	public String findOrgDeptTreeJson(Condition conOrg, Sort sortOrg, Boolean nocheckOrg,Condition conDept, Sort sortDept, Boolean nocheckDept);
	
	/**
	 * 查询数据字典信息
	 * @param con
	 * @param sort
	 * @return
	 */
	public List<SysDictionaryitems> findDictItemsList(Condition con, Sort sort);
	
	/**
	 * 获取部门信息的树形list
	 * @param con
	 * @param sort
	 * @param nocheck
	 * @return
	 */
	public String findDictItemsJson(Condition con, Sort sort, Boolean nocheck);
	
	
	/**
	 * 获取填报人信息Json
	 * @param params
	 * @return
	 */
	public String findReportUserJson(CommonJsonDataPojo params, SysUser sysUser);
	
	/**
	 * 获取填报人信息List
	 * 下拉框，列出填报人的姓名，值从PJ_BULID_INFO、SYS_USER动态生成
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findReportUserList(CommonJsonDataPojo params);
	
	/**
	 * 获取填报单位信息Json
	 * @param params
	 * @return
	 */
	public String findReportOrgJson(CommonJsonDataPojo params, SysOrganization sysOrganization);
	
	/**
	 * 获取填报单位信息List
	 * 下拉框，列出填报单位的名称，值从PJ_BULID_INFO、SYS_USER、SYS_ORGANIZATION动态生成
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findReportOrgList(CommonJsonDataPojo params);
	
	/**
	 * 获取填报单位、填报人List
	 * 列出填报单位、填报人信息，值从PJ_BULID_INFO表中生成
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findReportOrgUserList(CommonJsonDataPojo params);
	
	
}
