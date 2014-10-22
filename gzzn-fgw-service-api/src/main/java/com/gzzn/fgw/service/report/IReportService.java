package com.gzzn.fgw.service.report;

import java.util.List;
import java.util.Map;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.pojo.ReportPojoOld;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.service.sys.pojo.CsxmPojo;
import com.gzzn.util.web.PageUtil;

/**
 * 项目报表维护接口,因为对应的数据源
 * {@link com.gzzn.fgw.model.pojo.ReportPojo }
 * 是一个自写封装类，故需要重写增删查改CRUD
 * <p/>
 * <i>Copyright (c) 2014 ITDCL  All right reserved.</i>
 * 
 * @author lzq
 * @version 1.0 <br/>
 * 修改记录（日期+内容）:<br/>
 * ——2014-02-28 09:40:48 lzq new
 */
public interface IReportService{

	/**
	 * 查询分页
	 * 
	 * @param page 根据page分页要求检索相关信息
	 * @param cond 必须按照ProjectInvest属性设置条件
	 * @param sort 排序，为空则按默认排序
	 * @return 结果返回到page中	
	 * */
	PageUtil<ReportPojoOld> findList(PageUtil<ReportPojoOld> page,Condition condition, Sort sort); 
	
	/**
	 * 查询符合条件的全部记录
	 * 
	 * @param cond 必须按照ReportPojo属性设置
	 * @param sort 排序，为空则按默认排序
	 * @return 返回集合	
	 * */
	List<ReportPojoOld> findList(Condition cond, Sort sort); 
	

	
	
	/**
	 * 2014-5-5 LHQ加入
	 * 按照指定条件查询分页
	 * @param page 根据page分页要求检索相关信息
	 * @param condition
	 * @return 结果返回到page中	
	 */
	PageUtil<Projectinvest> findStatPageList(PageUtil<Projectinvest> page,Condition con, Sort sort); 
	
	/**
	 * 2014-5-5 LHQ加入
	 * 查询符合条件的全部记录
	 * @param cond
	 * @return
	 */
	List<Projectinvest> findStatList(Condition con, Sort sort); 
	
	public List<CsxmPojo> findList(String unPassSql,String passSql,Map<Integer,SysDept> deptMap,String fgwtzc);
	
	
	
	
	
	
}
