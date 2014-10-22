package com.gzzn.fgw.service.sys;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysDx;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.util.web.PageUtil;
/**
 * <p>Title: ISysRoleService</p>
 * <p>Description: 信息维护接口  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-10 下午2:38:31 amzhang  new
 */
public interface ISysDxService extends IPagedService<SysDx> {
	
	/**
	 * 发送项目短信：根据条件分页查询项目信息
	 * @param page
	 * @param sysParams
	 * @return
	 */
	public PageUtil<Pjbaseinfo> findPjbaseinfoList(PageUtil<Pjbaseinfo> page, SysQueryParam sysParams, SysUser sysUser);
	
	/**
	 * 发送项目短信：项目信息查询条件组装
	 * @param sysParams
	 * @return
	 */
	public Condition initPjbaseinfoCon(SysQueryParam sysParams);
	
	/**
	 * 发送项目短信：项目信息排序
	 * @return
	 */
	public Sort initPjbaseinfoSort();
	
	

}
