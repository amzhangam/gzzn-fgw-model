package com.gzzn.fgw.service.project;

import com.gzzn.fgw.model.PjBulidInfoLog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.IPagedService;

/**
 * <p>Title: IPjBulidInfoLogService</p>
 * <p>Description:  上报项目情况日志记录 接口</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-08-08 lhq  new
 */
public interface IPjBulidInfoLogService extends IPagedService<PjBulidInfoLog> {
	
	/**
	 * 记录 上报项目情况日志 信息
	 * @param user 用户信息
	 * @param operationType 操作类型：1上传导入数据；2 自检上报数据；3 覆盖重复数据
	 * @param tbdw 填报单位
	 * @param operationContent 操作内容
	 */
	public void saveLog(SysUser user, Integer operationType, String tbdw, String operationContent);
	

}
