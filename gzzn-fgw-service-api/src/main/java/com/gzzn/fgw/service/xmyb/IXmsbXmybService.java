package com.gzzn.fgw.service.xmyb;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.XmsbXmyb;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: ISysDeptService</p>
 * <p>Description: 项目月报信息维护接口   </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-19 下午3:48:13 amzhang  new
 */
public interface IXmsbXmybService extends IPagedService<XmsbXmyb> {
	

	/**
	 * 根据条件分页查询项目月报信息
	 * @param page
	 * @param condition
	 * @param sort
	 * @return
	 */
	public PageUtil<XmsbXmyb> findList(PageUtil<XmsbXmyb> page, Condition condition, Sort sort);
	

}
