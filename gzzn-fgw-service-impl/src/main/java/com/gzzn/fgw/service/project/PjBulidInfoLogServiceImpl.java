package com.gzzn.fgw.service.project;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.model.PjBulidInfoLog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.util.common.DateUtil;

/**
 * <p>Title: IPjBulidInfoLogService</p>
 * <p>Description:  上报项目情况日志记录 实现类</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-08-08 lhq  new
 */
@Service
@Transactional
public class PjBulidInfoLogServiceImpl extends AbstractService<PjBulidInfoLog>
		implements IPjBulidInfoLogService {

	@Resource
	private IEntityDao entityDao;
	
	
	@Override
	public void saveLog(SysUser user, Integer operationType, String tbdw,
			String operationContent) {
		if (user!= null && StringUtils.isNotEmpty(operationContent)) {
			PjBulidInfoLog log = new PjBulidInfoLog();
			if(user.getSysOrganization()!=null){
				log.setSysOrganization(user.getSysOrganization());
			}
			if (user.getSysDept() != null) {
				log.setSysDept(user.getSysDept());
			}
			Date date = new Date();
			log.setSysUser(user);
			log.setOperationType(operationType);
			log.setTbdw(tbdw);
			log.setOperationContent(operationContent.replace("<时间>",  DateUtil.format(date, "yyyy-MM-dd HH:mm:ss")));
			log.setOperationDatetime(date);
			entityDao.save(log);
		}
	}

	
}
