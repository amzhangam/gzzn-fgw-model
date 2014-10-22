package com.gzzn.fgw.action.xmyb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import javassist.expr.NewArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbXmyb;
import com.gzzn.fgw.model.XmsbXmybfj;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.project.IProjectbaseinfoService;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.service.project.pojo.ProjectbaseinfoParam;
import com.gzzn.fgw.service.sys.ISysDeptService;
import com.gzzn.fgw.service.sys.ISysDictionaryitemsService;
import com.gzzn.fgw.service.sys.ISysOrganizationService;
import com.gzzn.fgw.service.sys.ISysXqService;
import com.gzzn.fgw.service.sys.IXmsbHylbService;
import com.gzzn.fgw.service.sys.IXmsbXmlxService;
import com.gzzn.fgw.service.sys.IXmsbZjxzService;
import com.gzzn.fgw.service.xmyb.IXmsbXmybService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.FjObject;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.webUtil.ReadFileByteUtil;
import com.gzzn.fgw.webUtil.UploadFileUtil;
import com.gzzn.util.exception.CustomException;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysDeptAction</p>
 * <p>Description:  项目月报信息维护 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/xmyb")
public class XmsbXmybAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(XmsbXmybAction.class);
	
	private String projectTreeJson;//树以JSON格式存储
	private String id;//编辑或删除的id，多个间用@隔开
	private XmsbXmyb obj;//对象
	private XmsbXmyb queryObj;//对象
	private String message;//返回页面信息
	private PageUtil<XmsbXmyb> page = new PageUtil<XmsbXmyb>();
	private List<File> uploadXmyb; //项目月报
	private List<String> uploadXmybFileName; 
	private List<String> uploadXmybContentType; 
	
	private String fpath = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"upload.dir");//上传文件的路径
	
	private InputStream stream; //用于下载
	
	private String preString = "UploadFile";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	private SimpleDateFormat sdfFull = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static SimpleDateFormat sdfOnlyYear = new SimpleDateFormat("yyyy");
	
	private Date date = new Date();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IXmsbXmybService service;
	@Autowired
	private IProjectbaseinfoService projectbaseinfoService;
	 
	//进入机构信息列表界面
	@Action("list")
	public String list(){
		Condition con = new Condition();
		if(queryObj != null){
			if(StringUtils.isNotEmpty(queryObj.getNf())){
				con.add("nf",Operator.ISNOTNULL,null);
				con.add("nf", Operator.EQ, queryObj.getNf());
			}
			if(StringUtils.isNotEmpty(queryObj.getYf())){
				con.add("yf",Operator.ISNOTNULL,null);
				con.add("yf", Operator.EQ, queryObj.getYf().length()==1?"0"+queryObj.getYf():queryObj.getYf());
			}
			if(StringUtils.isNotEmpty(queryObj.getNr())){
				con.add("nr",Operator.ISNOTNULL,null);
				con.add("nr", Operator.LIKE, queryObj.getNr());
			}
			if(queryObj.getXmybzt()!=null){
				con.add("xmybzt", Operator.EQ, queryObj.getXmybzt());
			}
			if(queryObj.getPjbaseinfo()!=null && StringUtils.isNotEmpty(queryObj.getPjbaseinfo().getProjectname())){
				con.add("pjbaseinfo",Operator.ISNOTNULL,null);
				con.add("pjbaseinfo.projectname", Operator.LIKE, queryObj.getPjbaseinfo().getProjectname());
			}
			if(queryObj.getPjbaseinfo()!=null && StringUtils.isNotEmpty(queryObj.getPjbaseinfo().getProjectcode())){
				con.add("pjbaseinfo",Operator.ISNOTNULL,null);
				con.add("pjbaseinfo.projectcode", Operator.LIKE, queryObj.getPjbaseinfo().getProjectcode());
			}
		}
		
		con.add("pjbaseinfo.deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
		Order order1 = new Order(Direction.ASC, "pjbaseinfo.projectid");
		Order order2 = new Order(Direction.ASC, "nf");
		Order order3 = new Order(Direction.ASC, "yf");
		Order order4 = new Order(Direction.ASC, "xmybzt");
		Sort sort = new Sort(order1, order2,order3,order4);
		service.findList(page, con, sort);
		
		return "success";
	}
	
	@Action("getProjectTreeJson")
	public String getProjectTreeJson(){
		
		projectTreeJson = projectbaseinfoService.findPjbaseinfoTreeJson();
		outPutJSON(projectTreeJson);
		return null;
	}
	
	@Action("edit")
	public String edit(){
		List<Pjbaseinfo> pjbaseinfos = projectbaseinfoService.findPjbaseinfos();
		request.setAttribute("pjbaseinfos",pjbaseinfos);
		
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(XmsbXmyb.class, Integer.parseInt(id));
		}
		else{
			obj = new XmsbXmyb();
		}
		
		return "success";
	}
	
	@Action("view")
	public String view(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(XmsbXmyb.class, Integer.parseInt(id));
		}
		
		return "success";
	}
	
	 /**
     * 验证项目名称是否重复
     * @return
     */
	@Action("checkRepeat")
    public String checkRepeat(){
    	id = request.getParameter("id");
    	String projectid = request.getParameter("projectid");
    	String nf = request.getParameter("nf");
    	String yf = request.getParameter("yf");
    	
    	Pjbaseinfo myPjbaseinfo = commonService.findOne(Pjbaseinfo.class,Integer.valueOf(projectid));
		uploadProcess(myPjbaseinfo,getLoginUser(),uploadXmyb,uploadXmybFileName,uploadXmybContentType,IConstants.FJLX_0);
//		if(!b){
//			outPutError("上传文件太大了，请不要超过50M");
//			return null;
//		}
		
    	if(id!=null && !id.trim().equals("")){
    		Condition con = new Condition();
    		con.add("nf", Operator.ISNOTNULL,null);
    		con.add("yf", Operator.ISNOTNULL,null);
    		con.add("pjbaseinfo.projectid", Operator.ISNOTNULL,null);
    		con.add("nf", Operator.EQ,nf);
    		con.add("yf", Operator.EQ,yf);
    		con.add("pjbaseinfo.projectid", Operator.EQ,Integer.valueOf(projectid));
    		con.add("xmybId", Operator.NE,Integer.valueOf(id));
			
			List<XmsbXmyb> entitys = (List<XmsbXmyb>) commonService.find(XmsbXmyb.class,con);
			if(entitys!=null&&!entitys.isEmpty()){
				outPutError("项目状态申报重复");
			}else{
				outPutMsg(true,"OK");
			}
    	}else{
    		Condition con = new Condition();
    		con.add("nf", Operator.ISNOTNULL,null);
    		con.add("yf", Operator.ISNOTNULL,null);
    		con.add("pjbaseinfo.projectid", Operator.ISNOTNULL,null);
    		con.add("nf", Operator.EQ,nf);
    		con.add("yf", Operator.EQ,yf);
    		con.add("pjbaseinfo.projectid", Operator.EQ,Integer.valueOf(projectid));
			
    		List<XmsbXmyb> entitys = (List<XmsbXmyb>) commonService.find(XmsbXmyb.class,con);
			if(entitys!=null&&!entitys.isEmpty()){
				outPutError("项目状态申报重复");
			}else{
				outPutMsg(true,"OK");
			}
    	}
    	return null;
    }
	
	@GzznLog(LogType.SAVE)
	@Action(
			value = "tempSave", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String tempSave(){
		message = "暂存数据成功";
		if(obj==null){
			throw new CustomException("文件大小超出文件限制,只允许上传小于10M的文件. ");
		}
		
		try {
			if(obj.getXmybzt()==null||obj.getXmybzt()==0){
				obj.setXmybzt(IConstants.XMYBZT_0);//暂存
			}
			commonService.saveOrUpdate(obj);
			Pjbaseinfo myPjbaseinfo = commonService.findOne(Pjbaseinfo.class,obj.getPjbaseinfo().getProjectid());
			uploadProcess(myPjbaseinfo,getLoginUser(),uploadXmyb,uploadXmybFileName,uploadXmybContentType,IConstants.FJLX_0);
			
			logObject = new LogObject("项目月报信息暂存",obj.getXmybId(),obj.getPjbaseinfo().getProjectname()+" " + obj.getNf()+"年"+obj.getYf()+"月",obj.getPjbaseinfo());
		} catch (Exception e) {
			e.printStackTrace();
			message = "暂存数据失败";
		}

		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(
			value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "提交数据成功";
		try {
			obj.setXmybzt(IConstants.XMYBZT_1);//提交
			commonService.saveOrUpdate(obj);
			Pjbaseinfo myPjbaseinfo = commonService.findOne(Pjbaseinfo.class,obj.getPjbaseinfo().getProjectid());
			uploadProcess(myPjbaseinfo,getLoginUser(),uploadXmyb,uploadXmybFileName,uploadXmybContentType,IConstants.FJLX_0);
			
			logObject = new LogObject("项目月报信息提交",obj.getXmybId(),obj.getPjbaseinfo().getProjectname()+" " + obj.getNf()+"年"+obj.getYf()+"月",obj.getPjbaseinfo());
		} catch (Exception e) {
			e.printStackTrace();
			message = "提交数据失败";
		}
		
		return "success";
	}
	
	//删除
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					commonService.delete(XmsbXmyb.class, Integer.parseInt(i));
				}
				logObject = new LogObject("删除项目月报信息，ids=" + id);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public XmsbXmyb getObj() {
		return obj;
	}
	public void setObj(XmsbXmyb obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<XmsbXmyb> getPage() {
		return page;
	}
	public void setPage(PageUtil<XmsbXmyb> page) {
		this.page = page;
	}

	@GzznLog
	//删除附件
	@Action(value = "deleteFj")
	public String deleteFj(){
		
		
//		String fjlx = (String) ServletActionContext.getRequest().getParameter("fjlx");
		
		String fjId = (String) ServletActionContext.getRequest().getParameter("fjId");
		
//		if(fjlx!=null){
			
			try {
				if (StringUtils.isNotEmpty(fjId)) {
					XmsbXmybfj fjObj = commonService.findOne(XmsbXmybfj.class, Integer.parseInt(fjId));
					if(fjObj!=null){
						String fjName = fjObj.getFjlj();
						if(fjName!=null){
							commonService.delete(fjObj);
							fjName = fjName.substring(fjName.indexOf("_")+1);
							File dirs = new File(fpath);
							if(dirs.exists()){
								File[] files = dirs.listFiles();
								if(files.length>0){
									for(File file:files){
										String fileNameString = file.getName();
										if(fileNameString.equals(fjName)){
											file.delete();
											break;
										}
									}
								}
							}
						}
					}
					logObject = new LogObject("删除申报项目附件，ids=" + id);
					
					outPutMsg(true,"OK");
					
				}
				else{
					outPutError("附件不存在");
				}
			} catch (Exception e) {
				logger.error("",e);
				message = "删除数据失败";
				outPutError("删除附件失败");
			}
//		}
		
		return null;
	}

	private boolean uploadProcess(Pjbaseinfo pjbaseinfo,SysUser user,
			List<File> uploadFjs,List<String> uploadFjFileNames,List<String> uploadFjContentTypes,
			Integer fjlx
			){
		if(uploadFjs!=null&&!uploadFjs.isEmpty()){
			int i=0;
			for(File file:uploadFjs){
				System.out.println("file.length()="+file.length());
				if(file.length()>40*1024*1024){
					return false;
				}
				int index = uploadFjFileNames.get(i).indexOf(".");
				String extend = uploadFjFileNames.get(i).substring(index+1);
				long fjId = System.currentTimeMillis();
				XmsbXmybfj xmsbXmyb = new XmsbXmybfj();
				xmsbXmyb.setXmsbXmyb(obj);
				String fileName = uploadFjFileNames.get(i);
				if(fileName!=null&&fileName.getBytes().length>50){
					fileName = fileName.substring(0,10)+"......"+ "." + extend;
				}
				xmsbXmyb.setFjmc(fileName);
//				UploadFileUtil.uploadFile(file, fpath, "UploadFile"+sdf.format(date)+"_" + fjId + "." + extend);
				
				
				String declartimeString = pjbaseinfo.getDeclartime()==null?null:sdfOnlyYear.format(pjbaseinfo.getDeclartime());
				String mypathString  = fpath;
				if(declartimeString!=null){
					mypathString += declartimeString + "\\"+pjbaseinfo.getProjectid()+"\\";
				}
				Random rnd = new Random();
				int num = 100 + rnd.nextInt(900);
				String newFullPathFile = mypathString+sdfFull.format(date)+num + "." + extend;
				UploadFileUtil.uploadFile(file, newFullPathFile);
				xmsbXmyb.setFjlj(newFullPathFile);
				
				try {
					commonService.saveOrUpdate(xmsbXmyb);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
		return true;
	}
	
	@Action("checkFile")
	public void checkFile() throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			XmsbXmybfj vo = this.commonService.findOne(XmsbXmybfj.class, Integer.valueOf(id));
			
			//检测下载文件是否存在
			File file = new File(vo.getFjlj());
			boolean flag = file.exists();
			
			if (!flag) {
				outJsonString("{\"info\":\"false\"}");
			} else {
				outJsonString("{\"info\":\"true\"}");
			}
		}
	}

	@GzznLog
	//文件下载
	@Action("download")
	public void download() {
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				XmsbXmybfj vo = this.commonService
						.findOne(XmsbXmybfj.class, Integer.valueOf(id));

				File file = new File(vo.getFjlj());
				byte[] bytes = ReadFileByteUtil.getBytes(file);
				String fileName = URLEncoder.encode(vo.getFjmc(), "UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				ServletOutputStream out = response.getOutputStream();
				out.write(bytes);
				out.close();
				logObject = new LogObject("下载了一份附件，附件名称为：" + fileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public List<File> getUploadXmyb() {
		return uploadXmyb;
	}

	public void setUploadXmyb(List<File> uploadXmyb) {
		this.uploadXmyb = uploadXmyb;
	}

	public List<String> getUploadXmybFileName() {
		return uploadXmybFileName;
	}

	public void setUploadXmybFileName(List<String> uploadXmybFileName) {
		this.uploadXmybFileName = uploadXmybFileName;
	}

	public List<String> getUploadXmybContentType() {
		return uploadXmybContentType;
	}

	public void setUploadXmybContentType(List<String> uploadXmybContentType) {
		this.uploadXmybContentType = uploadXmybContentType;
	}

	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public String getPreString() {
		return preString;
	}

	public void setPreString(String preString) {
		this.preString = preString;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IXmsbXmybService getService() {
		return service;
	}

	public void setService(IXmsbXmybService service) {
		this.service = service;
	}

	public XmsbXmyb getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(XmsbXmyb queryObj) {
		this.queryObj = queryObj;
	}

}
