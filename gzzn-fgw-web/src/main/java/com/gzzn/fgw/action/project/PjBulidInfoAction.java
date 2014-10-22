package com.gzzn.fgw.action.project;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.expExcel.PjBuildInfoExpExcel;
import com.gzzn.fgw.expExcel.PjBulidInfoListExpExcel;
import com.gzzn.fgw.implExcel.PjBulidInfoImpExcel;
import com.gzzn.fgw.model.PjBulidInfo;
import com.gzzn.fgw.model.PjBulidInfoTemp;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUserRole;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.pojo.CommonJsonDataPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.com.ICommonJsonDataService;
import com.gzzn.fgw.service.project.IPjBulidInfoLogService;
import com.gzzn.fgw.service.project.IPjBulidInfoService;
import com.gzzn.fgw.service.project.pojo.PjBulidInfoStatPojo;
import com.gzzn.fgw.service.project.pojo.PjplanQueryParam;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: PjBulidInfoAction</p>
 * <p>Description:  上报项目情况维护</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-06-05 lhq  new
 */
@Namespace(value = "/buildImpl")
public class PjBulidInfoAction extends BaseAction<PjBulidInfo> {
	
	protected static Logger logger = LoggerFactory.getLogger(PjBulidInfoAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private PjplanQueryParam params;//用于存储查询参数	
	private PjBulidInfo obj;//项目建设情况对象
	private String message;//返回页面信息
	private String reportOrgJson;//获取“填报单位”下拉框信息
	private String reportUserJson;//获取“填报人”下拉框信息
	private List<String> msg;//返回导入、自检上报时的出错信息 
	private PageUtil<PjBulidInfo> page=new PageUtil<PjBulidInfo>();//页面数据集合
	private List<PjBulidInfoTemp> listTemp;//返回自检上报界面的临时数据
	private List<PjBulidInfoStatPojo> listStat;//报表统计信息
	private Map<String,String> expMap = new HashMap<String, String>();//导出数据时使用
	
	//导入Excel数据
	private File impFile;
	private String impFileContentType;
	private String impFileFileName;
	@Value("${clearup.temp.dir}")
	private String  tempDir;//保存临时文件目录
	
	private InputStream excelFile;//excel导出文件流
	private String downloadFileName;//excel导出文件名称
	private String title1;//导出报表时表头名称1
	private String title2;//导出报表时表头名称2
	
	@Autowired
	private IPjBulidInfoService service;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ICommonJsonDataService commonJsonDataService;
	@Autowired
	private PjBulidInfoImpExcel pjBulidInfoImpExcel;
	@Autowired
	private IPjBulidInfoLogService logService;
	
	/**
	 * 进入下载模板界面
	 * @return
	 */
	@Action("downTemp")
	public String downTemp(){
		return "success";
	}

	/**
	 * 导入上报项目情况数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GzznLog
	@Action("dataImpl")
	public String dataImpl(){
		long begin = System.currentTimeMillis();
		String stuTypeName = "";
		Condition con = this.initImplCon();
		if(params!=null && params.getStuType()!=null){
			if(params.getStuType().equalsIgnoreCase("impl")){//导入数据
				stuTypeName = "当前状态为导入数据 ，";
				//request.setAttribute("msg", pjBulidInfoImpExcel.parseFile(impFile, getHttpSession(), "PjBulidInfoTemp", getFileSavePath()));
				Map<String,Object> retnMap = pjBulidInfoImpExcel.parseFileMap(impFile, getHttpSession(), "PjBulidInfoTemp", getTempDir());
				Boolean flag =  (Boolean) retnMap.get("flag");
				msg =  (List<String>) retnMap.get("msg");
				String tbdw = (String) retnMap.get("tbdw");
				if(flag.equals(true)){//数据已正常导入
					//删除数据
					listTemp = commonService.find(PjBulidInfoTemp.class, con);
					for(PjBulidInfoTemp objTemp : listTemp){
						commonService.delete(objTemp);
					}
					//保存数据
					listTemp = (List<PjBulidInfoTemp>) retnMap.get("list");
					int[] msgCount =  (int[]) retnMap.get("msgCount");
					for (PjBulidInfoTemp objTemp : listTemp) {
						commonService.save(objTemp);//保存数据
						msgCount[2]++;
					}
					msg.add("导入数据完成,其中错误数据&nbsp;&nbsp;<b>" + msgCount[0]
							+ "</b>&nbsp;&nbsp;条,成功导入&nbsp;&nbsp;<b>"
							+ msgCount[2] + "</b>&nbsp;&nbsp;条,耗时&nbsp;&nbsp;"
							+ ((System.currentTimeMillis() - begin) / 1000)
							+ "&nbsp;&nbsp;秒");
					
				   logService.saveLog(getLoginUser(), 1, tbdw, "【"+tbdw+"】在 <时间> 上传文件【"+ impFileFileName +"】成功，其中错误数据 "+ msgCount[0] +" 条,成功导入"+ msgCount[2] + " 条");
				}
				logObject = new LogObject("上报项目情况导入数据，上传人登录帐号："+ getLoginUser().getLoginName() +"，上传人真实姓名："+ getLoginUser().getUserName());
			
			}else if(params.getStuType().equalsIgnoreCase("saveRepeart")){//覆盖重复数据
				stuTypeName = "当前状态为覆盖重复数据 ，";
				int fgNum=0;
				Map<String,SysOrganization> sysOrgMap = new HashMap<String, SysOrganization>();//单位信息
				if (StringUtils.isNotEmpty(id)) {
					PjBulidInfoTemp objTemp = null;
					PjBulidInfo obj = null;
					Long bulidIdObj = null;
					for (String i : id.split("@")) {
						objTemp = commonService.findOne(PjBulidInfoTemp.class, Long.valueOf(i)); 
						if(objTemp!=null){//数据从临时库进入正式库，获取重复数据
							obj = this.getRepeatObj(objTemp);
							if(obj!=null && this.isSysOrgMap(sysOrgMap, objTemp.getTbdw()).equals(true)){
								bulidIdObj = obj.getBulidId();
								PojoCopyUtil.copySameTypeField(objTemp, obj);
								obj.setSysOrganization(sysOrgMap.get(objTemp.getTbdw()));
								obj.setBulidId(bulidIdObj);
								commonService.saveOrUpdate(obj);
								commonService.delete(objTemp);
								
								fgNum++;
							}
						}
					}
					logObject = new LogObject("上报项目情况覆盖重复数据，覆盖数据人登录帐号："+ getLoginUser().getLoginName() +"，覆盖数据人真实姓名："+ getLoginUser().getUserName());
					//logObject = new LogObject("上报项目情况覆盖重复数据，ids=" + bulidIdStr.toString().substring(1)); 
					logService.saveLog(getLoginUser(), 3, "", (getLoginUser().getSysOrganization()==null?"":"【"+getLoginUser().getSysOrganization().getOrganizationName()+"】")+"在 <时间> 覆盖了 "+ fgNum +" 条重复数据");
				}
				params.setStuType(null);//设置该参数，使页面返回至初始状态
			}
		}
		//返回临时表中存在的数据
		
		listTemp = commonService.find(PjBulidInfoTemp.class, con);
		
		logger.info("导入上报项目情况数据，"+ stuTypeName +"耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**
	 * 审核上报项目数据
	 */
	@GzznLog
	@Action(value = "dataAudit",results={@Result(name = "success", location = "./dataImpl.jsp")})
	public String dataAudit(){
		long begin = System.currentTimeMillis();
		int[] msgCount = new int[3];//失败条数、重复数据条数、成功条数
		listTemp = new ArrayList<PjBulidInfoTemp>();
		try {
			msg = new ArrayList<String>();
			Map<String,SysXq> xqMap = this.getXqMap();//辖区信息，数据量小
			Map<String,SysDictionaryitems> xmlxbmMap = this.getSDItemsMap("项目类型编码");//项目类型编码信息，数据量小
			Map<String,SysDictionaryitems> xmlbbmMap = this.getSDItemsMap("项目类别编码");//项目类别编码信息，数据量小
			Map<String,SysDictionaryitems> cylbMap = this.getSDItemsMap("产业类别");//产业类别信息，数据量小
			Map<String,SysDictionaryitems> tzlxMap = this.getSDItemsMap("投资类型");//投资类型信息，数据量小
			Map<String,SysOrganization> sysOrgMap = new HashMap<String, SysOrganization>();//单位信息
			Map<String,Map<String, Object>> indusMap = new HashMap<String, Map<String,Object>>();//所属行业信息
			
			//将审核通过的数据存入PjBulidInfo表中
			PjBulidInfoTemp objTemp = null;
			PjBulidInfo obj = null;
			if (StringUtils.isNotEmpty(id)) {
				//将临时表中的数据保存到正式库中
				for (String i : id.split("@")) {
					objTemp = commonService.findOne(PjBulidInfoTemp.class, Long.valueOf(i)); 
					if(objTemp!=null){
						//System.out.println(xqMap.size() +"==="+ xmlxbmMap.size() +"==="+ sysOrgMap.size() +"==="+ indusMap.size() );
						if(this.isSysOrgMap(sysOrgMap, objTemp.getTbdw()).equals(false)){ 
							msg.add("【"+objTemp.getProjectname() + "】的【填报单位】在库中未查询到.");
							msgCount[0]++;
						}else if(!xmlxbmMap.containsKey(objTemp.getXmlx())){
							msg.add("【"+objTemp.getProjectname() + "】的【项目类型】在库中未查询到.");
							msgCount[0]++;
						}else if(!xmlbbmMap.containsKey(objTemp.getRemark())){
							msg.add("【"+objTemp.getProjectname() + "】的【项目类别】在库中未查询到.");
							msgCount[0]++;
						}else if(!cylbMap.containsKey(objTemp.getCylb())){
							msg.add("【"+objTemp.getProjectname() + "】的【产业类别】在库中未查询到.");
							msgCount[0]++;
						}else if(!tzlxMap.containsKey(objTemp.getTzlx())){
							msg.add("【"+objTemp.getProjectname() + "】的【投资类型】在库中未查询到.");
							msgCount[0]++;
						}else if(!xqMap.containsKey(objTemp.getXmsd())){
							msg.add("【"+objTemp.getProjectname() + "】的【项目属地】在库中未查询到.");
							msgCount[0]++;
						}else if(this.isIndusMap(indusMap, objTemp.getSshy()).equals(false)){
							msg.add("【"+objTemp.getProjectname() + "】的【所属行业】在库中未查询到.");
							msgCount[0]++;
						}else if(this.isRepeatData(objTemp)){
							listTemp.add(objTemp);
							msgCount[1]++;
						}else{
							obj = new PjBulidInfo();
							PojoCopyUtil.copySameTypeField(objTemp, obj);
							obj.setBulidId(null);
							obj.setSysOrganization(sysOrgMap.get(objTemp.getTbdw()));
							commonService.save(obj);
							commonService.delete(objTemp);
							msgCount[2]++;
						}
						
					}
				}
				logObject = new LogObject("上报项目情况自检上报数据，上报数据人登录帐号："+ getLoginUser().getLoginName() +"，上报数据人真实姓名："+ getLoginUser().getUserName());
				
				logService.saveLog(getLoginUser(), 2, "", (getLoginUser()
						.getSysOrganization() == null ? "" : "【"+ getLoginUser()
						.getSysOrganization().getOrganizationName() +"】")
						+ "在 <时间> 自检上报数据，其中错误数据 " + msgCount[0] + " 条，重复数据 "+ msgCount[1] +" 条，成功数据 "+ msgCount[2] +" 条");
			}
			msg.add("自检上报数据完成,其中错误数据&nbsp;&nbsp;<b>" + msgCount[0]
					+ "</b>&nbsp;&nbsp;条,重复数据&nbsp;&nbsp;<b>" + msgCount[1]
					+ "</b>&nbsp;&nbsp;条，成功数据&nbsp;&nbsp;<b>" + msgCount[2]
					+ "</b>&nbsp;&nbsp;条,耗时&nbsp;&nbsp;"
					+ ((System.currentTimeMillis() - begin) / 1000)
					+ "&nbsp;&nbsp;秒");
		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
			message = "自检上报数据失败";
		}
		
		logger.info("导入上报项目情况数据，当前状态为自检上报数据 ，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		if(listTemp!=null && listTemp.size()>0){
			params.setStuType("saveRepeart");//下一步：保存重复数据
			return "success";
		}else{
			params.setStuType("audit");
			message = "";
			for(String str:msg){
				message += "<p>"+ str +"</p>";
			}
			return dataImpl();
		}
	}
	
	/**
	 * 检测是否为重复数据：单位ID【2014-6-24之后不要了】、项目名称、项目属地
	 * @param obj
	 * @return
	 */
	private Boolean isRepeatData(PjBulidInfoTemp obj){
		PjBulidInfo objRepeat = this.getRepeatObj(obj);
		return objRepeat==null?false:true;
	}
	
	private PjBulidInfo getRepeatObj(PjBulidInfoTemp obj){
		Condition con = new Condition();
		//con.add("sysOrganization", Operator.ISNOTNULL, null);
		//con.add("sysOrganization", Operator.EQ, getLoginUser().getSysOrganization());
		//con.add("sysOrganization", Operator.EQ, obj.getSysOrganization());
		//con.add("sysOrganization.organizationName", Operator.EQ, obj.getTbdw());
		con.add("projectname", Operator.ISNOTNULL, null);
		con.add("projectname", Operator.EQ, obj.getProjectname());
		con.add("xmsd", Operator.ISNOTNULL, null);
		con.add("xmsd", Operator.EQ, obj.getXmsd());
		return commonService.findOne(PjBulidInfo.class, con);
	}
	/**
	 * 通过单位名称检查单位是否存在
	 * @return
	 */
	private Boolean isSysOrgMap(Map<String,SysOrganization> sysOrgMap, String organizationName){
		Boolean flag = true;
		if(!sysOrgMap.containsKey(organizationName)){//sysOrgMap.get(organizationName)==null
			SysOrganization obj = this.getSysOrgByName(organizationName);
			if(obj!=null){
				sysOrgMap.put(organizationName, obj);
			}else{
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 通过单位名称检查单位是否存在
	 * @return
	 */
	private Boolean isIndusMap(Map<String,Map<String, Object>> indusMap, String sshy){
		Boolean flag = true;
		if(!indusMap.containsKey(sshy)){//indusMap.get(sshy)==null
			Map<String, Object> mapSshy = service.findIndusGBcode(sshy);
			if(mapSshy!=null){
				indusMap.put(sshy, mapSshy);
			}else{
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 根据单位名称查找单位信息
	 * @param organizationName
	 * @return
	 */
	private SysOrganization getSysOrgByName(String organizationName){
		Condition con = new Condition();
		con.add("organizationName", Operator.ISNOTNULL, null);
		con.add("organizationName", Operator.EQ, organizationName);
		return commonService.findOne(SysOrganization.class, con);
	}
	
	
	
	/**
	 * 导入数据的查询条件
	 * @return
	 */
	private  Condition initImplCon(){
		Condition con = new Condition();
		//con.add("sysOrganization", Operator.ISNOTNULL, null);
		//con.add("sysOrganization", Operator.EQ, getLoginUser().getSysOrganization());
		con.add("sysUser", Operator.ISNOTNULL, null);
		con.add("sysUser.userId", Operator.EQ, getLoginUser().getUserId());
		
		return con;
	}
	

	/**
	 * 查询上报项目情况列表
	 * @return
	 */
	@Action("list")
	public String list(){
		long begin = System.currentTimeMillis();
		
		Condition con = this.initCon();
		Order order1 = new Order(Direction.ASC, "projectname");
		Sort sort = new Sort(order1);
		service.loadData(PjBulidInfo.class, page, con, sort);
		
		reportOrgJson = commonJsonDataService.findReportOrgJson(null, getLoginUser().getSysOrganization());//获取填报单位下拉框数据 
		reportUserJson = commonJsonDataService.findReportUserJson(null, getLoginUser());//获取填报人下拉框数据
		
		logger.info("查询上报项目情况列表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**
	 * 查询填报项目管理列表
	 * @return
	 */
	@Action("reportList")
	public String reportList(){
		long begin = System.currentTimeMillis();
		
		Condition con = this.initCon();
		Order order1 = new Order(Direction.ASC, "projectname");
		Sort sort = new Sort(order1);
		service.loadData(PjBulidInfo.class, page, con, sort);
		
		reportOrgJson = commonJsonDataService.findReportOrgJson(null, getLoginUser().getSysOrganization());//获取填报单位下拉框数据 
		reportUserJson = commonJsonDataService.findReportUserJson(null, getLoginUser());//获取填报人下拉框数据
		
		Map<String,PjBulidInfoStatPojo> mapLinkStat = getyhtjInfo();//获取按上报用户统计信息
		request.setAttribute("mapLinkStat",mapLinkStat);
		
		logger.info("查询填报项目管理列表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**
	 * 获取按上报用户统计信息
	 */
	private Map<String,PjBulidInfoStatPojo> getyhtjInfo(){
		//1、获取数据
		Condition con = this.initCon();
		List<PjBulidInfo> listData = commonService.find(PjBulidInfo.class, con, new Sort(Direction.ASC, "projectname"));
		
		//2、获取MapKey的相关数据
		CommonJsonDataPojo paramsPojo = new CommonJsonDataPojo();
		if(params!=null){
			paramsPojo.setOrganizationId(params.getOrganizationId());
			paramsPojo.setUserId(params.getUserId());
		}
		List<Map<String,Object>> listKey = commonJsonDataService.findReportOrgUserList(paramsPojo);
		//System.out.println("进来啦======"+listData.size()+"======="+listKey.size());
		
		//3、根据MapKey来汇总上报用户统计
		Map<String,PjBulidInfoStatPojo> mapLinkStat = new LinkedHashMap<String,PjBulidInfoStatPojo>();
		PjBulidInfo objData = null;
		Object orgId = null,userId=null;
		for(Map<String,Object> mapKey: listKey){
			orgId = mapKey.get("organization_id");
			userId = mapKey.get("user_id");
			if(orgId!=null && userId!=null){
				mapLinkStat.put(orgId +"_"+ userId, new PjBulidInfoStatPojo(String.valueOf(mapKey.get("organization_name")), String.valueOf(mapKey.get("user_name"))));
				Iterator<PjBulidInfo> iteMap = listData.iterator();
				while (iteMap.hasNext()) {
					objData = iteMap.next();
					if(objData.getSysOrganization()!=null && objData.getSysOrganization().getOrganizationId()!=null
						&& objData.getSysUser()!=null && objData.getSysUser().getUserId()!=null &&
						mapLinkStat.containsKey(objData.getSysOrganization().getOrganizationId() +"_"+ objData.getSysUser().getUserId())){
						mapLinkStat.get(orgId +"_"+ userId).setlXNum(mapLinkStat.get(orgId +"_"+ userId).getlXNum()+1);
						iteMap.remove();
					}
				}
				//System.out.println(orgId +"_"+ userId +"====="+ mapLinkStat.get(orgId +"_"+ userId).getsType1()+"====="+ mapLinkStat.get(orgId +"_"+ userId).getsType2() +"====="+ mapLinkStat.get(orgId +"_"+ userId).getlXNum() );
			}
		}
		return mapLinkStat;
	}
	
	
	
	/**
	 * 删除填报的信息
	 * @return
	 */
	@GzznLog
	@Action(value = "delete", results = {
			@Result(name = "list", location = "list.ac", type = "redirectAction", params = {
					"message", "${message}", "encode", "true" }),
			@Result(name = "success", location = "reportList.ac", type = "redirectAction", params = {
					"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					commonService.delete(PjBulidInfo.class, Long.parseLong(i)); 
				}
				if(params!=null && params.getStuType()!=null && params.getStuType().equals("1")){
					logObject = new LogObject("删除上报项目信息，ids=" + id);
				}else{
					logObject = new LogObject("删除填报项目管理信息，ids=" + id);
				}
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		if(params!=null && params.getStuType()!=null && params.getStuType().equals("1")){
			return "list";
		}else{
			return "success";
		}
	}
	
	
	//导出Excel
	@GzznLog
	@Action(value = "exportListExcel", results = { @Result(name="excel", type="stream",
		  params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String exportExcel(){
		Condition con = this.initCon();
		 List<PjBulidInfo> list = commonService.find(PjBulidInfo.class, con, new Sort(Direction.ASC, "projectname"));
		 //downloadFileName = JxlExcelCellUtil.setExcelFileName("上报项目数据__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 downloadFileName = downFileNameTranscode("上报项目数据__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 excelFile = new PjBulidInfoListExpExcel().expExcelFile(list, this.getXqMap());
		 logObject = new LogObject("按国家发改委格式导出上报项目列表数据。");
		 return "excel";
	}
	
	/**
	 * 查询条件
	 * @return
	 */
	private  Condition initCon(){
		Condition con = new Condition();
		/** //如果当前用户所属单位类型=4，则其只能查看本单位上传的数据
		if(getLoginUser().getSysOrganization()!=null 
				&& getLoginUser().getSysOrganization().getWorkunitstype().equals(4)){
			if(params==null){
				params = new PjplanQueryParam();
			}
			params.setOrganizationId(getLoginUser().getSysOrganization().getOrganizationId().toString());
		}*/
		// 如果当前用户所属角色为：发改委-投资处、发改委-领导、系统管理员的话，其可以查看全部数据，反之只能查看自己上传的数据
		Set<SysUserRole> setRole = getLoginUser().getSysUserRoles();
		String roleName = "";
		Boolean flag = false;//当前用户是否具备查看全部数据的权限：false-不具备；true-具备
		for(SysUserRole objRole : setRole){
			roleName = objRole.getSysRole().getRoleName();
			if(roleName.equals("发改委-投资处")||roleName.equals("发改委-领导")||roleName.equals("系统管理员")){
				flag = true;//当前用户具备查看全部数据的权限
				break;
			}
		}
		if(!flag){
			if(params==null){
				params = new PjplanQueryParam();
			}
			params.setUserId(getLoginUser().getUserId().toString());
		}
		
		//其它的相关条件
		if(params != null){
			if(StringUtils.isNotEmpty(params.getProjectname())){
				con.add("projectname", Operator.LIKE, params.getProjectname());
			}
			if(StringUtils.isNotEmpty(params.getPfsj())){
				con.add("pfsj", Operator.LIKE, params.getPfsj());
			}
			if(StringUtils.isNotEmpty(params.getOrganizationName())){
				con.add("sysOrganization.organizationName", Operator.LIKE, params.getOrganizationName());
			}
			if(StringUtils.isNotEmpty(params.getOrganizationId())){
				con.add("sysOrganization",Operator.ISNOTNULL,null);
				con.add("sysOrganization.organizationId", Operator.IN, Arrays.asList(params.getOrganizationId().split(",")));
			}
			if(StringUtils.isNotEmpty(params.getUserId())){
				con.add("sysUser",Operator.ISNOTNULL,null);
				con.add("sysUser.userId", Operator.IN, Arrays.asList(params.getUserId().split(",")));
			}
			if(StringUtils.isNotEmpty(params.getXqmc())){
				con.add("xmsd", Operator.IN, Arrays.asList(params.getXqmc().split(",")));
			}
			/**if(StringUtils.isNotEmpty(params.getXqId())){
				con.add("sysOrganization.sysXq",Operator.ISNOTNULL,null);
				con.add("sysOrganization.sysXq.xqId", Operator.IN, Arrays.asList(params.getXqId().split(",")));
			}*/
			if(StringUtils.isNotEmpty(params.getCylb())){
				con.add("cylb", Operator.IN, Arrays.asList(params.getCylb().split(",")));
			}
			if(StringUtils.isNotEmpty(params.getZdxmbz())) {
				con.add("zdxmbz", Operator.IN, Arrays.asList(params.getZdxmbz().split(",")));
			}
			if(StringUtils.isNotEmpty(params.getRemark())) {
				con.add("remark", Operator.LIKE, params.getRemark());
			}
			//总投资（万元）：区间[params.getPjinvestSum(),params.getPjinvestSum2()]
			if(params.getPjinvestSum()!=null && params.getPjinvestSum()>=0){
				con.add("pjinvestsum", Operator.GE, params.getPjinvestSum());
			}
			if(params.getPjinvestSum2()!=null && params.getPjinvestSum2()>=0){
				con.add("pjinvestsum", Operator.LE, params.getPjinvestSum2());
			}	
		}
		return con;
	}
	
	
	/**
	 * 汇总统计
	 * @return
	 */
	@Action("sumStat")
	public String sumStat(){
		long begin = System.currentTimeMillis();
		//系统默认按上报统计
		if(params==null){
			params = new PjplanQueryParam();
			params.setStuType("2");
		}
		getSumStatList();

		reportOrgJson = commonJsonDataService.findReportOrgJson(null, getLoginUser().getSysOrganization());//获取填报单位下拉框数据 
		reportUserJson = commonJsonDataService.findReportUserJson(null, getLoginUser());//获取填报人下拉框数据
		
		logger.info("上报项目按产业投资额汇总报表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);//上报项目汇总统计报表
		return "success";
	}
	
	/**
	 * 汇总统计:按区县统计
	 * @return
	 */
	@Action("xqStat")
	public String xqStat(){
		long begin = System.currentTimeMillis();
		//系统默认按上报统计
		if(params==null){
			params = new PjplanQueryParam();
			params.setStuType("1");
		}
		getSumStatList();
		

		reportOrgJson = commonJsonDataService.findReportOrgJson(null, getLoginUser().getSysOrganization());//获取填报单位下拉框数据 
		reportUserJson = commonJsonDataService.findReportUserJson(null, getLoginUser());//获取填报人下拉框数据
		
		logger.info("上报项目按区县统计报表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**导出报表功能*/
	@Action(value = "exportExcel", results = { @Result(name="excel", type="stream",
			  params = {
				"bufferSize", "1024",
				"inputName", "excelFile",
				"contentType","application/vnd.ms-excel",
				"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String expExcel(){
		 long begin = System.currentTimeMillis();
		 getSumStatList();
		 String name = params.getStuType().equalsIgnoreCase("1")?"上报项目按区县统计":"上报项目按产业及投资额统计";
		 //downloadFileName = JxlExcelCellUtil.setExcelFileName(name +"__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 downloadFileName = downFileNameTranscode(name +"__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 excelFile = new PjBuildInfoExpExcel().expExcelFile(listStat, expMap);
		 logger.info("导出上报项目汇总统计，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}
	
	private void getSumStatList(){
		/** //如果当前用户所属单位类型=4，则其只能查看本单位上传的数据
		if(getLoginUser().getSysOrganization()!=null 
				&& getLoginUser().getSysOrganization().getWorkunitstype().equals(4)){
			if(params==null){
				params = new PjplanQueryParam();
			}
			params.setOrganizationId(getLoginUser().getSysOrganization().getOrganizationId().toString());
			//params.setXqId(getLoginUser().getSysOrganization().getSysXq().getXqId().toString());
		}*/
		// 如果当前用户所属角色为：发改委-投资处、发改委-领导、系统管理员的话，其可以查看全部数据，反之只能查看自己上传的数据
		Set<SysUserRole> setRole = getLoginUser().getSysUserRoles();
		String roleName = "";
		Boolean flag = false;//当前用户是否具备查看全部数据的权限：false-不具备；true-具备
		for(SysUserRole objRole : setRole){
			roleName = objRole.getSysRole().getRoleName();
			if(roleName.equals("发改委-投资处")||roleName.equals("发改委-领导")||roleName.equals("系统管理员")){
				flag = true;//当前用户具备查看全部数据的权限
				break;
			}
		}
		if(!flag){
			if(params==null){
				params = new PjplanQueryParam();
			}
			params.setUserId(getLoginUser().getUserId().toString());
		}
		
		//其它的相关条件
		if(params.getStuType().equalsIgnoreCase("1")){
			expMap.put("stuType", "1");
			listStat = new ArrayList<PjBulidInfoStatPojo>();
			
			List<PjBulidInfoStatPojo> list = service.findStatByXQ(this.findXqList(), params);
			listStat.add(this.calTheTotal("全市",list));
			listStat.addAll(list); 
			
		}else if(params.getStuType().equalsIgnoreCase("2")){
			expMap.put("stuType", "2");
			listStat = new ArrayList<PjBulidInfoStatPojo>();
			
			List<SysDictionaryitems> listCylb = this.findSDItemsList("产业类别");
			//listStat.add(service.findStatByTotal(listCylb, params.getXqId(), params.getOrganizationId()));
			listStat.add(service.findStatByTotal(listCylb, params));
			expMap.put("合计", "1");
			//listStat.addAll(service.findStatByCylb(listCylb, params.getXqId(), params.getOrganizationId()));
			listStat.addAll(service.findStatByCylb(listCylb, params));
			expMap.put("按产业分类", String.valueOf(listCylb.size()));
			
			List<SysDictionaryitems> listSysDic = this.findSDItemsList("投资额大小");
			//listStat.addAll(service.findStatByTzlx(listSysDic, params.getXqId(), params.getOrganizationId()));
			listStat.addAll(service.findStatByTzlx(listSysDic, params));
			expMap.put("按投资额大小分类", String.valueOf(listSysDic.size()));
		}
		//导出表头的名称
		expMap.put("title1", title1);
		expMap.put("title2", title2);
	}
	
	
	/**
	 * 获取县区List信息
	 * @return
	 */
	private List<SysXq> findXqList(){
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//使用状态：0 正常 ，1 删除
		//con.add("sjxq", Operator.ISNOTNULL, null);
		//con.add("sjxq.xzqydm", Operator.EQ, "440100");
		return commonService.find(SysXq.class, con, new Sort(Direction.ASC, "xqId"));
	}
	
	/**
	 * 获取县区Map信息,key为辖区名称
	 * @return
	 */
	private Map<String,SysXq> getXqMap(){
		Map<String,SysXq> map = new HashMap<String, SysXq>();
		List<SysXq> list = this.findXqList();
		for(SysXq obj : list){
			map.put(obj.getXqmc(), obj);
		}
		return map;
	}
	
	/**
	 * 获取数据字典List信息
	 * @return
	 */
	private List<SysDictionaryitems> findSDItemsList(String dictName){
		Condition con = new Condition();
		con.add("status", Operator.EQ, "1");//状态：1  有效，0 无效
		if(StringUtils.isNotEmpty(dictName)){
			con.add("name", Operator.ISNOTNULL, null);
			con.add("name", Operator.EQ, dictName);
		}
		return commonService.find(SysDictionaryitems.class, con, new Sort(Direction.ASC, "id"));
	}
	
	/**
	 * 获取数据字典Map信息,key为辖区名称Itemtext
	 * @return
	 */
	private Map<String,SysDictionaryitems> getSDItemsMap(String dictName){
		Map<String,SysDictionaryitems> map = new HashMap<String, SysDictionaryitems>();
		List<SysDictionaryitems> list = this.findSDItemsList(dictName);
		for(SysDictionaryitems obj : list){
			map.put(obj.getItemtext(), obj);
		}
		return map;
	}
	
	
	
	/***
	 * 计算出统计合计
	 * @param name
	 * @param list
	 * @return
	 */
	private PjBulidInfoStatPojo calTheTotal(String name,List<PjBulidInfoStatPojo> list){
		SysXq sysXq = new SysXq();
		sysXq.setXqmc(name);
		PjBulidInfoStatPojo objAll = new PjBulidInfoStatPojo(sysXq);
		for (PjBulidInfoStatPojo obj : list) {
			 objAll.setlXNum(objAll.getlXNum() + obj.getlXNum());
			 objAll.setlXPjinvestsum(objAll.getlXPjinvestsum() + obj.getlXPjinvestsum());
			 objAll.setlXExpectfinishinvest(objAll.getlXExpectfinishinvest() + obj.getlXExpectfinishinvest());
			 
			 objAll.setwGNum(objAll.getwGNum() + obj.getwGNum());
			 objAll.setwGExpectfinishinvest(objAll.getwGExpectfinishinvest() + obj.getwGExpectfinishinvest());
			 objAll.setwGCurrentfinishinvest(objAll.getwGCurrentfinishinvest() + obj.getwGCurrentfinishinvest());
			 
			 objAll.setzJNum(objAll.getzJNum() + obj.getzJNum());
			 objAll.setzJPjinvestsum(objAll.getzJPjinvestsum() + obj.getzJPjinvestsum());
			 objAll.setzJPlaninvestsum(objAll.getzJPlaninvestsum() + obj.getzJPlaninvestsum());
			 objAll.setzJCurrentfinishinvest(objAll.getzJCurrentfinishinvest() + obj.getzJCurrentfinishinvest());
		}
		return objAll;
	}
	
	
	
	
	
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PjplanQueryParam getParams() {
		return params;
	}

	public void setParams(PjplanQueryParam params) {
		this.params = params;
	}

	public PjBulidInfo getObj() {
		return obj;
	}

	public void setObj(PjBulidInfo obj) {
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PageUtil<PjBulidInfo> getPage() {
		return page;
	}

	public void setPage(PageUtil<PjBulidInfo> page) {
		this.page = page;
	}

	public File getImpFile() {
		return impFile;
	}


	public void setImpFile(File impFile) {
		this.impFile = impFile;
	}


	public String getImpFileContentType() {
		return impFileContentType;
	}


	public void setImpFileContentType(String impFileContentType) {
		this.impFileContentType = impFileContentType;
	}


	public String getImpFileFileName() {
		return impFileFileName;
	}


	public void setImpFileFileName(String impFileFileName) {
		this.impFileFileName = impFileFileName;
	}

	public List<PjBulidInfoTemp> getListTemp() {
		return listTemp;
	}


	public void setListTemp(List<PjBulidInfoTemp> listTemp) {
		this.listTemp = listTemp;
	}

	public List<PjBulidInfoStatPojo> getListStat() {
		return listStat;
	}

	public void setListStat(List<PjBulidInfoStatPojo> listStat) {
		this.listStat = listStat;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public List<String> getMsg() {
		return msg;
	}

	public void setMsg(List<String> msg) {
		this.msg = msg;
	}
	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getReportUserJson() {
		return reportUserJson;
	}

	public void setReportUserJson(String reportUserJson) {
		this.reportUserJson = reportUserJson;
	}

	public String getReportOrgJson() {
		return reportOrgJson;
	}

	public void setReportOrgJson(String reportOrgJson) {
		this.reportOrgJson = reportOrgJson;
	}

}
