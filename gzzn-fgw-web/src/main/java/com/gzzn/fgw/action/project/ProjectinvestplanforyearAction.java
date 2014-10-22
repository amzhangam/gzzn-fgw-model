package com.gzzn.fgw.action.project;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.FileSupportAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.Projectinvestplanforyear;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.project.IProjectinvestplanforyearService;
import com.gzzn.fgw.service.project.pojo.PjplanQueryParam;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.ValidateUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.exception.CustomException;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: ProjectinvestplanforyearAction</p>
 * <p>Description:  年度计划终稿管理 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-12 lhq  new
 */
@ParentPackage("struts-common")
@Namespace(value = "/project/pjplanyear")
public class ProjectinvestplanforyearAction extends FileSupportAction<Projectinvestplanforyear> {
	protected static Logger logger = LoggerFactory.getLogger(ProjectinvestplanforyearAction.class);

	private String id;//编辑或删除的id，多个间用@隔开
	private PjplanQueryParam params;//用于存储查询参数	
	private Projectinvestplanforyear obj;//年度计划终稿对象
	private String message;//返回页面信息
	private PageUtil<Projectinvestplanforyear> page=new PageUtil<Projectinvestplanforyear>();//页面数据集合
	
	private String fileName;//文件名称
	private String fileUrl;//文件路径
	
	@Autowired
	private IProjectinvestplanforyearService service;
	@Autowired
	private ICommonService commonService;
	
	/**
	 * 查询年度计划终稿列表
	 * @return
	 */
	@Action("list")
	public String list(){
		long begin = System.currentTimeMillis();
		
		Condition con = new Condition();
		if(params != null){
			if(StringUtils.isNotEmpty(params.getStartTime())){
				con.add("investplanyear", Operator.GE, Integer.parseInt(params.getStartTime()));
			}
			if(StringUtils.isNotEmpty(params.getEndTime())){
				con.add("investplanyear", Operator.LE, Integer.parseInt(params.getEndTime()));
			}
			if(StringUtils.isNotEmpty(params.getInvestplanname())) {
				con.add("investplanname", Operator.LIKE, params.getInvestplanname());
			}
		}
		Order order1 = new Order(Direction.DESC, "recordertime");
		Sort sort = new Sort(order1);
		service.loadData(Projectinvestplanforyear.class, page, con, sort);
		
		logger.info("查询年度计划终稿列表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**下载投资计划终稿附件*/
	@Action("downloadFile")
	public String downloadFile(){
		fileName = chTranscoding(fileName);//get方式进行中文转码
		String msg = super.doDownload(fileUrl, fileName.substring(0,fileName.lastIndexOf(".")));
		if(!msg.equalsIgnoreCase("success")){
			sendScriptMsg(msg);
		}
		return null;
	}
	
	/**下载模板数据附件*/
	@Action("downloadTempFile")
	public String downloadTempFile(){
		fileName = chTranscoding(fileName);//get方式进行中文转码
		String msg = super.doDownloadTempFile(fileUrl, fileName.substring(0,fileName.lastIndexOf(".")));
		if(!msg.equalsIgnoreCase("success")){
			sendScriptMsg(msg);
		}
		return null;
	}
	

	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(Projectinvestplanforyear.class, Integer.parseInt(id));
		}
		if(obj==null){
			obj = new Projectinvestplanforyear();
		}
		if(obj.getInvestplanyear()==null){
			obj.setInvestplanyear(Integer.valueOf(DateUtil.format(new Date(), "yyyy")));
		}
		
		return "success";
	}
	

	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" })},
			interceptorRefs = {
			@InterceptorRef(value = "MyFileUploadInterceptor", params = { "maximumSize", "10485760" }),
			@InterceptorRef(value = "defaultStack")
			})
	public String save(){
		setMessage("保存数据成功");
		if(obj==null){
			throw new CustomException("文件大小超出文件限制,只允许上传小于10M的文件. ");
		}
		//存储上传的附件信息
		if(files!=null && files.size()>0){
			setFileStorePath(getFileStorePath()+"/pjplanyear");//年度计划终稿服务器上保存的地址
			super.doUpload();//将用户上传的文件保存到服务上
			StringBuffer fileName = new StringBuffer();//文件名称
			StringBuffer filePath = new StringBuffer();//文件地址
			if(fileUploadeds.size()>0){
				for (Map<String, String>  map: fileUploadeds) {
					fileName.append(CommonFiled.STRING_SEPARATE + map.get("fname"));
					filePath.append(CommonFiled.STRING_SEPARATE + map.get("fpath"));
				}
				obj.setFilename(fileName.toString().substring(1));//文件名称
				obj.setFileurl(filePath.toString().substring(1));//文件地址
			}
			//判断文件名称、文件地址是否超出了指定长度
			if(fileName!=null && !ValidateUtil.isStrByteLenNotOut(fileName.toString().substring(1), 50)){
				delFile(obj.getFileurl());
				throw new CustomException("投资计划终稿文件名称超过50个字符. ");
			}else if(filePath!=null && !ValidateUtil.isStrByteLenNotOut(filePath.toString().substring(1), 255)){
				delFile(obj.getFileurl());
				throw new CustomException("投资计划终稿文件URL超过255个字符. ");
			}
		}
		obj.setRecorderid(getLoginUser().getUserId());
		obj.setRecordername(getLoginUser().getUserName());
		obj.setRecordertime(new Timestamp(System.currentTimeMillis()));
		commonService.saveOrUpdate(obj);
		
		logObject = new LogObject("年度计划终稿", obj.getInvestplanid(), obj.getInvestplanname(), null);

		return "success";
	}
	
	/**
	 * 删除文件
	 * @param fileurl
	 */
	private void delFile(String fileurl){
		File delFile = null;
		for(String j : fileurl.split(CommonFiled.STRING_SEPARATE_ESCAPE)){
			delFile = new File(getFileStoreRoot() + j);
			if(delFile.exists()){
				delFile.delete();//删除文件
			}
		}
	}
	
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		setMessage("删除数据成功");
		try {
			Projectinvestplanforyear objplan = null;
			File delFile = null;
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					objplan = commonService.findOne(Projectinvestplanforyear.class, Integer.parseInt(i));
					if(objplan != null){
						if(StringUtils.isNotEmpty(objplan.getFileurl())){
							for(String j : objplan.getFileurl().split(CommonFiled.STRING_SEPARATE_ESCAPE)){
								delFile = new File(getFileStoreRoot() + j);
								if(delFile.exists()){
									delFile.delete();//删除文件
								}
								/**if(delFile.exists()){
									boolean flag = delFile.delete();//删除文件
									outPutMsg(flag,flag?"":"删除文件失败！");
								}else{
									setMessage("服务器找不到您要删除的文件！");
								}*/
							}
						}
						commonService.delete(objplan);
					}
				}
				logObject = new LogObject("年度计划终稿，ids=" + id);
			}
		} catch (Exception e) {
			setMessage("删除数据失败");
		}

		return "success";
	}
	
	
	
	/**设置中文转码*/
	private String chTranscoding(String chName) {
		String retName = "";
		try {
			//keyword = new String(request.getParameter("q").getBytes("iso8859-1"), "utf-8");
			retName = new String(chName.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retName;
	}

	
	
	public PjplanQueryParam getParams() {
		return params;
	}
	public void setParams(PjplanQueryParam params) {
		this.params = params;
	}
	public PageUtil<Projectinvestplanforyear> getPage() {
		return page;
	}
	public void setPage(PageUtil<Projectinvestplanforyear> page) {
		this.page = page;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Projectinvestplanforyear getObj() {
		return obj;
	}

	public void setObj(Projectinvestplanforyear obj) {
		this.obj = obj;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
