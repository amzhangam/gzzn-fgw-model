package com.gzzn.fgw.action.com;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.model.pojo.CommonJsonDataPojo;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.com.ICommonJsonDataService;
import com.gzzn.fgw.util.IConstants;


/**
 * 
 * <p>Title: CommonJsonDataAction</p>
 * <p>Description:  获取通用JSON数据类：普通JSON串、TreeJson串 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-27 lhq  new
 */
@Namespace(value = "/com")
public class CommonJsonDataAction extends BaseAction<TreeNodesPojo> {
	
	protected static Logger logger = LoggerFactory.getLogger(CommonJsonDataAction.class);
	
	private CommonJsonDataPojo params;//查询参数
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ICommonJsonDataService service;
	
	
	
	/**
	 * 获取项目信息：项目名称、项目ID
	 * @return
	 */
	@Action("getProjectInfoJson")
	public String getProjectInfoJson(){
		long begin=System.currentTimeMillis();
		
		Condition con = new Condition();
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getKeyWord())){
				con.add("projectname", Operator.LIKE, params.getKeyWord());
			}
		}
		con.add("pjstatus",Operator.GT,IConstants.XMZT_0);
		con.add("deleted",Operator.ISNULL,null);
		con.add("projectcode",Operator.ISNOTNULL,null);
		con.add("projectcode",Operator.NOTLIKE,"PJ%");
		Sort sort = new Sort(Direction.ASC, "projectname");
		
		outPutJSON(service.findProjectInfoJson(con, sort));
		logger.debug("Ajax获取项目信息JSON树，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 获取行业类别JSON树
	 * @return
	 */
	@Action("getXmsbHylbJson")
	public String getXmsbHylbJson(){
		long begin=System.currentTimeMillis();
		
		outPutJSON(service.findXmsbHylbJson(params==null||params.getNocheck()==null?false:params.getNocheck())); 
		
		logger.debug("Ajax获取行业类别JSON树，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 获取项目类型JSON树
	 * @return
	 */
	@Action("getXmsbXmlxJson")
	public String getXmsbXmlxJson(){
		long begin=System.currentTimeMillis();
		
		outPutJSON(service.findXmsbXmlxJson(params==null||params.getNocheck()==null?false:params.getNocheck())); 
		
		logger.debug("Ajax获取项目类型JSON树，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 获取资金性质JSON树
	 * @return
	 */
	@Action("getXmsbZjxzJson")
	public String getXmsbZjxzJson(){
		long begin=System.currentTimeMillis();
		
		outPutJSON(service.findXmsbZjxzJson(params==null||params.getNocheck()==null?false:params.getNocheck())); 
		
		logger.debug("Ajax获取资金性质JSON树，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 获取辖区信息JSON树
	 * @return
	 */
	@Action("getSysXqJson")
	public String getSysXqJson(){
		long begin=System.currentTimeMillis();
		
		Condition con = this.initSysXqCon();
		Order order1 = new Order(Direction.ASC, "xqId");
		Order order2 = new Order(Direction.ASC, "sjxq.xqId");
		Sort sort = new Sort(order1, order2);
		
		outPutJSON(service.findSysXqJson(con, sort, params==null||params.getNocheck()==null?false:params.getNocheck()));
		logger.debug("Ajax获取辖区信息JSON树，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	private  Condition initSysXqCon(){
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getKeyWord())){
				con.add("xqmc", Operator.LIKE, params.getKeyWord());
			}
			if(StringUtils.isNotEmpty(params.getXqlx())){
				con.add("xqlx",Operator.ISNOTNULL,null);
				con.add("xqlx", Operator.IN, Arrays.asList(params.getXqlx().split(",")));
			}
		}
		return con;
	}
	
	/**
	 * 获取所属单位信息
	 * @return
	 */
	@Action("getSysOrgJson")
	public String getSysOrgJson(){
		long begin=System.currentTimeMillis();
		Condition con = this.initSysOrgCon();
		Sort sort = new Sort(Direction.ASC, "organizationId");//organizationName
		
		outPutJSON(service.findSysOrgJson(con, sort, params==null||params.getNocheckOrg()==null?false:params.getNocheckOrg()));
		
		logger.debug("Ajax获取单位信息Json串，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	private  Condition initSysOrgCon(){
		Condition con = new Condition();
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getKeyWord())){
				con.add("organizationName", Operator.LIKE, params.getKeyWord());
			}
			if(StringUtils.isNotEmpty(params.getWorkunitstype())){
				con.add("workunitstype",Operator.ISNOTNULL,null);
				con.add("workunitstype", Operator.IN, Arrays.asList(params.getWorkunitstype().split(",")));
			}
			if(StringUtils.isNotEmpty(params.getWorkunitsstatus())){
				con.add("workunitsstatus",Operator.ISNOTNULL,null);
				con.add("workunitsstatus", Operator.IN, Arrays.asList(params.getWorkunitsstatus().split(",")));
			}
			if(StringUtils.isNotEmpty(params.getXqId())){
				con.add("sysXq.xqId",Operator.ISNOTNULL,null);
				con.add("sysXq.xqId", Operator.IN, Arrays.asList(params.getXqId().split(",")));
			}
		}
		return con;
	}
	
	/**
	 * 获取部门信息
	 * @return
	 */
	@Action("getSysDeptJson")
	public String getSysDeptJson(){
		long begin=System.currentTimeMillis();
		
		Condition con = this.initSysDeptCon();
		
		con.add("sfxs", Operator.ISNOTNULL,null);
		
		con.add("sfxs", Operator.EQ,1);
		
		Sort sort = new Sort(Direction.ASC, "levelnumber");
		
		outPutJSON(service.findSysDeptJson(con, sort, params==null||params.getNocheckDept()==null?false:params.getNocheckDept()));
		
		logger.debug("Ajax获取部门信息Json串，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	private  Condition initSysDeptCon(){
		Condition con = new Condition();
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getKeyWord())){
				con.add("deptname", Operator.LIKE, params.getKeyWord());
			}
			if(StringUtils.isNotEmpty(params.getOrganizationId())){
				con.add("sysOrganization.organizationId",Operator.ISNOTNULL,null);
				con.add("sysOrganization.organizationId", Operator.EQ, Integer.valueOf(params.getOrganizationId()));
			}
		}
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
		return con;
	}
	
	/**
	 * 获取单位、部门组成的树
	 * @return
	 */
	@Action("getOrgDeptTreeJson")
	public String getOrgDeptTreeJson(){
		long begin=System.currentTimeMillis();
		
		Condition conOrg = this.initSysOrgCon();
		Sort sortOrg = new Sort(Direction.ASC, "organizationName");//organizationId organizationName
		
		Condition conDept = this.initSysDeptCon();
		conDept.add("sfxs", Operator.ISNOTNULL,null);
		conDept.add("sfxs", Operator.EQ,1);
		Sort sortDept = new Sort(Direction.ASC, "levelnumber");
		
		outPutJSON(service.findOrgDeptTreeJson(conOrg, sortOrg, params==null||params.getNocheckOrg()==null?true:params.getNocheckOrg(),
					conDept, sortDept, params==null||params.getNocheckDept()==null?false:params.getNocheckDept()));
		
		logger.debug("Ajax获取单位、部门组成的树，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 根据名称获取数据字典信息
	 */
	@Action("getDictItemsJson")
	public String getDictItemsJson(){
		long begin=System.currentTimeMillis();
		
		Condition con = new Condition();
		con.add("status", Operator.EQ, "1");//状态：1  有效，0 无效
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getDictName())){
				con.add("name", Operator.EQ, params.getDictName());
			}
		}
		Sort sort = new Sort(Direction.ASC, "sortorder");
		
		outPutJSON(service.findDictItemsJson(con, sort, params==null||params.getNocheckDept()==null?false:params.getNocheckDept()));
		
		logger.debug("Ajax获取数据字典"+ (params!=null && params.getDictName()!=null?"【名称="+params.getDictName()+"】":"") +" 信息，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 获取填报人信息
	 * @return
	 */
	@Action("getReportUserJson")
	public String getReportUserJson(){
		long begin=System.currentTimeMillis();
		outPutJSON(service.findReportUserJson(params,null));
		
		logger.debug("Ajax获取填报人信息Json串，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	
	/**
	 * 获取填报单位信息
	 * @return
	 */
	@Action("getReportOrgJson")
	public String getReportOrgJson(){
		long begin=System.currentTimeMillis();
		outPutJSON(service.findReportOrgJson(params,null));
		
		logger.debug("Ajax获取填报单位信息Json串，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
		return null;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取自动填充下拉框数据
	 * @return
	 */
	public String getKeyword() {
		String keyword = "";
		try {
			keyword = new String(request.getParameter("q").getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return keyword;
	}

	public CommonJsonDataPojo getParams() {
		return params;
	}

	public void setParams(CommonJsonDataPojo params) {
		this.params = params;
	}

	
}
