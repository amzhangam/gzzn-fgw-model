package com.gzzn.fgw.service.sys;

import java.util.List;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.pojo.SysProjectlogPojo;
import com.gzzn.util.web.PageUtil;
/**
 * <p>Title: ISysProjectlogService</p>
 * <p>Description: 项目日志维护接口  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-13 上午9:36:08 amzhang  new
 */
public interface ISysProjectlogService extends IPagedService<SysProjectlogPojo> {
	
	public PageUtil<SysProjectlogPojo> findList(PageUtil<SysProjectlogPojo> page, Condition con, Sort sort);
	
	/**
	 * 将list转换成对应的POJO
	 * @param list
	 * @return
	 */
	public List<SysProjectlogPojo> getLogPojoList(List<SysProjectlog> list);	 

}
