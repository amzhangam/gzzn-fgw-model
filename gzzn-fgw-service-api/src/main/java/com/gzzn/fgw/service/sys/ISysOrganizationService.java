package com.gzzn.fgw.service.sys;

import java.util.Map;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.service.sys.pojo.SysOrgPojo;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: ISysOrganizationService</p>
 * <p>Description: 机构信息维护接口   </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-4 下午7:48:13 amzhang  new
 */
public interface ISysOrganizationService extends IPagedService<SysOrgPojo> {
	
	/**
	 * 根据条件分页查询机构信息
	 * @param page
	 * @param condition
	 * @param sort
	 * @return
	 */
	public PageUtil<SysOrgPojo> findList(PageUtil<SysOrgPojo> page, Condition condition, Sort sort);
	
	/**
	 * 获取单位类型Map
	 * @return
	 */
	public Map<Object,String> getDWLXMap();
	
	/**
	 * 获取单位性质Map
	 * @return
	 */
	public Map<Object,String> getDWXZMap();
	
	/**
	 * 获取机构信息的树形list：按照上级ID、ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findSysOrgTreeJson(Boolean nocheck,String workunitstype);
	
	/**
	 * 保存或更新实体
	 * @param obj
	 * @return
	 */
	public void saveOrUpdate(SysOrganization obj);
	
	/**
	 * 获取所属地区信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findAreaTreeJson(Boolean nocheck);
	
	/**
	 * 获取单位性质信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findUnitNatureTreeJson(Boolean nocheck);
	
	/**
	 * 获取单位类型
	 * @param nocheck
	 * @return
	 */
	public String findUnitTypeTreeJson(Boolean nocheck);
	
	/**
	 * 获取单位状态
	 * @param nocheck
	 * @return
	 */
	public String findUnitStatusTreeJson(Boolean nocheck);

}
