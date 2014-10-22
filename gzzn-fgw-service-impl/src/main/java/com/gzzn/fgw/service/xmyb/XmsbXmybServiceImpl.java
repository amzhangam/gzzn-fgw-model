package com.gzzn.fgw.service.xmyb;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.XmsbXmyb;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysDeptServiceImpl</p>
 * <p>Description: 实现类 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-4 下午7:51:28 amzhang  new
 * 2.2014-03-08 PM 14:20:58 lzq update
 */
@Service
@Transactional
public class XmsbXmybServiceImpl extends AbstractService<XmsbXmyb> implements
		IXmsbXmybService {
	@Resource
	private IEntityDao entityDao;
	
	@Override
	public PageUtil<XmsbXmyb> findList(PageUtil<XmsbXmyb> page,Condition condition, Sort sort) {
		
		page.setCount((int) entityDao.count(XmsbXmyb.class, condition)); 
		
		//查询一页的机构信息
		List<XmsbXmyb> listData = entityDao.find(XmsbXmyb.class, condition, sort,page.getOffset(),page.getLimitNum());
		
		
		page.setList(listData);
		
		return page;
	}

	
}
