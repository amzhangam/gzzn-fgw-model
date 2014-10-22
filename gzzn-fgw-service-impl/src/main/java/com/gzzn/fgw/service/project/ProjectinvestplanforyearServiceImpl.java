package com.gzzn.fgw.service.project;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.model.Projectinvestplanforyear;
import com.gzzn.fgw.service.AbstractService;

/**
 * 
 * <p>Title: ProjectinvestplanforyearServiceImpl</p>
 * <p>Description:  年度计划终稿管理实现类 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-12 lhq  new
 */
@Service
@Transactional
public class ProjectinvestplanforyearServiceImpl extends
		AbstractService<Projectinvestplanforyear> implements
		IProjectinvestplanforyearService {
	
	@Resource
	private IEntityDao entityDao;

	

}
