package com.gzzn.fgw.service.sys;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.service.sys.pojo.SysDeptPojo;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: ISysDeptService</p>
 * <p>Description: 部门信息维护接口   </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-19 下午7:18:24 amzhang  new
 */
public interface ISysDeptService extends IPagedService<SysDeptPojo> {
	

	/**
	 * 根据条件分页查询机构信息
	 * @param page
	 * @param condition
	 * @param sort
	 * @return
	 */
	public PageUtil<SysDeptPojo> findList(PageUtil<SysDeptPojo> page, Condition condition, Sort sort);
	
	/**
	 * 获取部门信息的树形list：按照上级ID、ID排序
	 * @param organizationId
	 * @param nocheck 父节点的单选或复选按钮是否显示 true不显示 false显示
	 * @return
	 */
	public String findSysDeptTreeJson(Boolean nocheck);
	

}
