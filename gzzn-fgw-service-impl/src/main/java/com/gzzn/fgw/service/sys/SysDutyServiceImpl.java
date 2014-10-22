package com.gzzn.fgw.service.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.sys.ISysDutyService;
import com.gzzn.fgw.model.SysDuty;


/**
 * <p>Title: SysDutyService</p>
 * <p>Description:  职务信息实现类 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-3 下午4:25:50 amzhang  new
 */
@Service
@Transactional
public class SysDutyServiceImpl extends AbstractService<SysDuty> implements ISysDutyService {

	@Resource
	private IEntityDao entityDao;
}
