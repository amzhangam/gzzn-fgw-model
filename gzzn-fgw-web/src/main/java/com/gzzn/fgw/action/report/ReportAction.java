package com.gzzn.fgw.action.report;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.expExcel.ReportExpExcel;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.ReportPojoOld;
import com.gzzn.fgw.service.report.IPjauditlogService;
import com.gzzn.fgw.service.report.IReportService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.util.web.PageUtil;
/**
 * 报表action
 * <p/>
 * <i>Copyright (c) 2014 ITDCL  All right reserved.</i> 
 * 
 * @author lzq
 * @version 1.0
 *
 * 修改记录:(日期+修改内容)<br/>
 * ——2014-03-05 09:51:28 lzq  new
 */
@Namespace("/report")
public class ReportAction extends BaseAction<ReportPojoOld> {	
	/*基本属性*/
	private ReportPojoOld params;//用于存储查询参数		
	private PageUtil<ReportPojoOld> page=new PageUtil<ReportPojoOld>();//页面数据集合
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();//项目类型（值：新开工项目、续建项目、预备项目）
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();//项目状态（值：草稿、待处长审核等）
	 
	/*业务操作类*/
	@Autowired
	private IReportService reportService;	
	@Resource 
	IPjauditlogService pjauditlogService ;
	
	/****Action定义****/	
	/**
	 * 不写Result注解，success字符按默认模板路径规则处理返回
	 * 故这里按照默认模板路径规则，返回/WEB-INF/content/report/list.jsp界面模板
	 * 
	 * @return "success"
	 */
	@Action("list")
	public String list(){
		Condition cond =getCondition();
		Sort sort = new Sort(Direction.DESC,"pjbaseinfo.startyear");
		reportService.findList(page, cond, sort);	
		return "success";
	}
	
	/**
	 * 请求方式必须要以.json结尾，否则输出内容会被struts2封装	
	 * 
	 */
	@Action("exportExcel")
	public void exportExcel() throws Exception{ 
		setCharacterEncodingForUtf8();
		String fileName = System.currentTimeMillis() + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName.trim());
		
		Condition cond = getCondition();  
		Sort sort = new Sort(Direction.DESC,"pjbaseinfo.startyear");		
		List<ReportPojoOld> expdata=reportService.findList(cond, sort);
		
		//导出结果到输出流	
		OutputStream out = response.getOutputStream();	
		new ReportExpExcel().expExcelFile(expdata, getXmztMap(),out);	
		out.close();
		expdata.clear(); //清除内存
	}
		
	/*私有方法定义*/
	/**
	 * 取出在用户登录时通过方法{@link com.gzzn.fgw.action.sys.LoginAction.findDirectionaryitems()
	 * 存入的{@link CommonFiled.SESSION_DIRECTIONARYITEMS}状态表。并按照name取出对应的记录，
	 * 然后检索每一条记录，组成键值对
	 * 
	 * @param map 结果存放的集合
	 * @param name 筛选状态字段表的条件
	 * @return map 结果存放的集合
	 */
	private Map<Integer, String> getDictionaryitemsToMap(Map<Integer,String> map,String name){
		@SuppressWarnings("unchecked")
		List<SysDictionaryitems> list = (List<SysDictionaryitems>) getHttpSession().getAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS);
		if(list!=null&&!list.isEmpty()){
			for(SysDictionaryitems dictionaryitems:list){
				if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(name)){
					map.put(Integer.valueOf(dictionaryitems.getItemvalue()),dictionaryitems.getItemtext());
				}
			}
		}
		return map;
	}
	
	/**
	 * 所有条件都是针对com.gzzn.fgw.model.Projectinvest实体类,
	 * 故属性也是其对应的属性	 
	 */ 
	  private Condition getCondition(){
		  String pass= request.getParameter("p");	//是否审核通过(1:通过，0：不通过，null：全部)		
		  Condition cond=new Condition();
		  SysUser user=getLoginUser();	
		  
		  if(user.getUserType()==1){//1.业主
			  System.err.print("业主");
			  cond.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationId"
					   , Operator.EQ, user.getSysOrganization().getOrganizationId());			  
		  } 
		  else if(user.getUserType()==2) {//2.主管单位
			  cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId"
					   , Operator.EQ, user.getSysOrganization().getOrganizationId());  			
			  if(pass!=null){					
				     if(pass.equals("1")){//通过
						  cond.add("pjbaseinfo.pjstatus", Operator.GT, 1);
					  }else{//不通过
						  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 0);							  
						  cond.add("pjbaseinfo", Operator.IN,pjauditlogService.findProjectIdSet(
								  new Condition("pjauditType", Operator.EQ, 1)));	
					  }
			  }
		  }else if(user.getUserType()==3 && user.getSysDept().getDeptId()!=12){//3.发改委非投资处部门	
		        Condition condtion2=new Condition("pjauditUnits",Operator.EQ,user.getSysDept().getDeptId());		      				  
	  		    if(pass!=null){					
					  if(pass.equals("1")){//通过
						  cond.add("pjbaseinfo.pjstatus", Operator.GT, 5);
						  cond.add("pjbaseinfo.projectid", Operator.IN,pjauditlogService.findProjectIdSet(condtion2));
					  }else{	//不通过
						  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 0);						  
						  cond.add("pjbaseinfo.projectid", Operator.IN,pjauditlogService.findProjectIdSet(
								  new Condition("pjauditType", Operator.EQ, 2).and(condtion2)));					  
					  }
			    }else{ 
			    	cond.add("pjbaseinfo.projectid", Operator.IN,pjauditlogService.findProjectIdSet(condtion2));
				 }		  
		  }else{//4.发改委投资处部门	
			  if(pass!=null){					
				  if(pass.equals("1")){//通过
					    cond.add("pjbaseinfo.pjstatus", Operator.GT, 9);
				  }else{//不通过
						cond.add("pjbaseinfo.pjstatus", Operator.EQ, 5);					  
					   }
			 }	
			 cond.add("pjbaseinfo.projectid", Operator.IN,pjauditlogService.findProjectIdSet(
		        		new Condition("pjauditUnits",Operator.EQ,user.getSysDept().getDeptId())));
		  }
		  cond.add("pjbaseinfo.deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
		  
		if(params != null){				
			if(StringUtils.isNotEmpty(params.getProjectName())) {
				cond.add("pjbaseinfo.projectname", Operator.LIKE, params.getProjectName());
			} 
			if(params.getProjectType()!=0){
				cond.add("pjbaseinfo.projecttype", Operator.EQ, params.getProjectType());
			}			
			if(params.getStartYear()!=0){
				cond.add("pjbaseinfo.startyear", Operator.GE, params.getStartYear());
			}
			if(params.getEndYear()!=0){
				cond.add("pjbaseinfo.endyear", Operator.LE, params.getEndYear());
			}	
			if(StringUtils.isNotEmpty(params.getOrganizationName())) {
				cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationName", Operator.LIKE, params.getOrganizationName());
			}
			if(StringUtils.isNotEmpty(params.getManageUnitsName())) {
				cond.add("pjbaseinfo.manageunitsname", Operator.LIKE, params.getManageUnitsName());
			}
			if(StringUtils.isNotEmpty(params.getProjectContent())) {
				cond.add("pjbaseinfo.projectcontent", Operator.LIKE, params.getProjectContent());
			}			
		}
		return cond;
	  }
	/*get、set方法*/
	public Map<Integer, String> getXmlxMap() {
		xmlxMap = getDictionaryitemsToMap(xmlxMap, IConstants.DICTIONARY_ITEM_XMLX);
		return xmlxMap;
	}

	public ReportPojoOld getParams() {
		return params;
	}

	public void setParams(ReportPojoOld params) {
		this.params = params;
	}

	public PageUtil<ReportPojoOld> getPage() {
		return page;
	}

	public void setPage(PageUtil<ReportPojoOld> page) {
		this.page = page;
	}

	public void setXmlxMap(Map<Integer, String> xmlxMap) {
		this.xmlxMap = xmlxMap;
	}
 
	public Map<Integer, String> getXmztMap() {
		xmztMap = getDictionaryitemsToMap(xmztMap, IConstants.DICTIONARY_ITEM_XMZT);
		return xmztMap;
	}

	public void setXmztMap(Map<Integer, String> xmztMap) {
		this.xmztMap = xmztMap;
	}
 
}
