package com.gzzn.fgw.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonObject;
import com.gzzn.util.common.DateUtil;
import com.sun.jndi.toolkit.chars.BASE64Encoder;

/**
 * Title: FileSupportAction.java<br>
 * Description: 文件上传下载支撑Action<br>
 * Copyright: Copyright (c) 2014 ITDCL  All right reserved.<br>
 * Company: ITDCL<br>
 * @author YangGuangJian<br>
 * @version 1.0<br>
 * @history 1.2014-4-8 下午7:31:21  YangGuangJian    new<br>
 */
public class FileSupportAction<T> extends BaseAction<T>{
	
	protected List<File>   files;  
	protected List<String> filesContentType;  
	protected List<String> filesFileName; 
	
	@Value("${fileSupport.file.fileStoreRoot}")
	protected String  fileStoreRoot;
	@Value("${fileSupport.file.fileStorePath}")
	protected String  fileStorePath;
	protected List<Map<String, String>> fileUploadeds;
	protected List<Map<String, String>> fileUnUploadeds;
	
	/**
	 * 
	 * Description: 防止上传文件夹内文件过多,将文件夹后加'-1',满1000个后递增
	 * @param datePath
	 * @author chengzhi 2014-6-23 上午1:44:45
	 */
	public void parseFileStorePath(String datePath) {
		setFileStorePath(datePath+"-1");
		File file = new File(getFileStoreRoot()+getFileStorePath());
		if(!file.exists()){
		}else{
			String[] fs = file.list();
			if(fs.length==0){
			}else {
				String[] fss = file.getParentFile().list();
				Arrays.sort(fss);
				File f = new File(file.getParent()+"/"+fss[fss.length-1]);
				String[] fsss = f.list();
				if(fsss!=null && fsss.length>=1000){
					setFileStorePath(datePath+"-"+(Integer.parseInt(f.getName().substring(f.getName().lastIndexOf("-")+1))+1));
				}else{
					setFileStorePath(datePath+"-"+f.getName().substring(f.getName().lastIndexOf("-")+1));
				}
			} 
		}
	}
	
	/**
	 * 附件上传执行方法，方法执行成功后返回JSON字符串
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-8 下午7:44:05  YangGuangJian    new<br>
	 * @return 
	 */
	public String doUpload(){
		//setFileStorePath(fileStorePath+"/"+DateUtil.format(new Date(), "yyyyMM/dd"));
		setFileStorePath(fileStorePath+"/"+DateUtil.format(new Date(), "yyyyMM"));
		
		// 获得文件仓库路径
		String path=getFileStoreRoot()+fileStorePath;
		
		// 初始化文件上传成功信息
		fileUploadeds = new ArrayList<Map<String,String>>();
		fileUnUploadeds = new ArrayList<Map<String,String>>();
		
		// 开始上传
        if (files!=null && files.size()>0)
        {
        	// 判断路径是否已经存在
            File savedir=new File(path);
            if(!savedir.exists()) {
            	savedir.mkdirs(); 
            }
            
            // 循环文件列表，逐个上传
            for (int i = 0; i < files.size(); i++)
            {
            	String fName = filesFileName.get(i);
            	String fType = fName.substring(fName.lastIndexOf(".")+1, fName.length());
            	
            	// 文件过滤
            	if(!filesValidate(files.get(i), fType, fName)){
            		Map<String, String> info = new HashMap<String, String>();
            		info.put("fname", fName);
					info.put("fileNameNew", "");
					info.put("fpath", "");
					info.put("msg", "文件不符合要求");
					fileUnUploadeds.add(info);
            		continue;
            	}
            	// 创建文件名称
            	String fileNameNew = createFileName(fName, fType);
            	
            	// 创建文件对象
                File saveFile=new File(savedir, fileNameNew);
                try {
					FileUtils.copyFile(files.get(i), saveFile);
					
					// 设置文件上传成功信息
					Map<String, String> info = new HashMap<String, String>();
					info.put("fname", fName);
					info.put("fileNameNew", fileNameNew);
					info.put("fpath", fileStorePath+"/"+fileNameNew);
					fileUploadeds.add(info);
				} catch (IOException e) {
					Map<String, String> info = new HashMap<String, String>();
					info.put("fname", fName);
					info.put("fileNameNew", fileNameNew);
					info.put("fpath", fileStorePath+"/"+fileNameNew);
					info.put("msg", e.getMessage());
					fileUnUploadeds.add(info);
				}
            }
        }
		
        JSONArray uploaded = JSONArray.fromObject(fileUploadeds);
        JSONArray unUpload = JSONArray.fromObject(fileUnUploadeds);
        
        JsonObject json = new JsonObject();
        json.addProperty("successCount", fileUploadeds.size());
        json.addProperty("failedCount", fileUnUploadeds.size());
        json.addProperty("uploaded", uploaded.toString());
        json.addProperty("unUpload", unUpload.toString());
       
		return json.toString();
	}

	
	/**
	 * 文件过滤
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-9 上午9:08:31  YangGuangJian    new<br>
	 * @param file
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	protected boolean filesValidate(File file,String fileType,String fileName){
		return true;
	}
	
	
	/**
	 * 创建文件名称
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-9 上午8:54:21  YangGuangJian    new<br>
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	protected String createFileName(String fileName,String fileType){
		//String random = UUID.randomUUID().toString();
		Long random = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		return  random + "." + fileType;
	}
	
	
	/**
	 * 下载文件
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-9 上午10:58:43  YangGuangJian    new<br>
	 * @param filePath
	 * @return
	 */
	protected String doDownload(String filePath){
		return doDownload(filePath,null);
	}
	
	/**
	 * 下载文件
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-9 下午1:36:04  YangGuangJian    new<br>
	 * @param filePath
	 * @param saveFileName
	 * @return
	 */
	protected String doDownload(String filePath,String saveFileName){
		String msg = "success";
		String fileRealPath = getFileStoreRoot() + filePath;
		
		File downFile = new File(fileRealPath);
		return doDownload(downFile,saveFileName);
	}
	
	/**
	 * 下载工程内部的模板数据文件
	 * @author lhq <br>
	 * @version 1.0<br>
	 * @history 1.2014-7-1  lhq    new<br>
	 * @param filePath
	 * @param saveFileName
	 * @return
	 */
	protected String doDownloadTempFile(String filePath,String saveFileName){
		String msg = "success";
		String fileRealPath = getWebRootPath() + filePath;
		//System.out.println("===="+fileRealPath);
		
		File downFile = new File(fileRealPath);
		return doDownload(downFile,saveFileName);
	}
	
	
	/**
	 * 下载文件
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-9 下午1:37:38  YangGuangJian    new<br>
	 * @param downFile
	 * @return
	 */
	protected String doDownload(File downFile){
		return doDownload(downFile,null);
	}
	
	
	/**
	 * 下载文件
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-9 下午12:45:42  YangGuangJian    new<br>
	 * @param downFile
	 * @param saveFileName
	 * @return
	 */
	protected String doDownload(File downFile,String saveFileName){
		String msg = "success";
		
		if(!downFile.exists()){
			msg = "文件不存在！";
			return msg;
		}
		
		String fName = null;
		try {
			fName = downFile.getName();
			String fType = fName.substring(fName.lastIndexOf("."), fName.length());
			if(saveFileName!=null){
				fName = saveFileName + fType;
			}else{
				fName = downFile.getName();
			}
			String agent = request.getHeader("user-agent");//判断浏览器类型
			//System.out.println(agent);
			if (agent.contains("MSIE")) {//IE浏览器 --- URL编码
				fName = URLEncoder.encode(fName, "utf-8");
			} else if (agent.contains("Firefox")) {//火狐浏览器 --- Base64编码
				fName = base64EncodeFileName(fName);
			} else {
				fName = URLEncoder.encode(fName, "utf-8");
			}
		} catch (UnsupportedEncodingException e1) { }
		response.setHeader("Content-disposition","attachment;filename="+fName);
		response.setContentType("application/x-download"); 
		long fileLength=downFile.length(); 
		String length=String.valueOf(fileLength); 
		response.setHeader("Content_length",length); 
		
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(downFile);
			out = response.getOutputStream();
			byte buf[]=new byte[1024];
			int len = -1;
			while((len=in.read(buf))!=-1){
				out.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			msg = e.getMessage();
		} catch (IOException e) {
			msg = e.getMessage();
		}finally{
			
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {}
		}
		
		return msg;
	}
	
	
	
	/**
	 * 发送 JavaScript alert 消息
	 * @author YangGuangJian <br>
	 * @version 1.0<br>
	 * @history 1.2014-4-10 下午4:03:08  YangGuangJian    new<br>
	 * @param msg
	 */
	protected void sendScriptMsg(String msg){
		String str="<script type='text/javascript'>window.parent.alert('"+msg+"')</script>";
		super.outPutString(str, true, "text/html");
	}
	
	
	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

	/**
	 * @return the filesContentType
	 */
	public List<String> getFilesContentType() {
		return filesContentType;
	}

	/**
	 * @param filesContentType the filesContentType to set
	 */
	public void setFilesContentType(List<String> filesContentType) {
		this.filesContentType = filesContentType;
	}

	/**
	 * @return the filesFileName
	 */
	public List<String> getFilesFileName() {
		return filesFileName;
	}

	/**
	 * @param filesFileName the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}


	/**
	 * @return the fileUploadeds
	 */
	public List<Map<String, String>> getFileUploadeds() {
		return fileUploadeds;
	}


	/**
	 * @return the fileStorePath
	 */
	public String getFileStorePath() {
		return fileStorePath;
	}


	/**
	 * @return the fileStoreRoot
	 */
	public String getFileStoreRoot() {
		return fileStoreRoot;
	}
	

	/**
	 * @param fileStoreRoot the fileStoreRoot to set
	 */
	public void setFileStoreRoot(String fileStoreRoot) {
		this.fileStoreRoot = fileStoreRoot;
	}


	/**
	 * @param fileStorePath the fileStorePath to set
	 */
	public void setFileStorePath(String fileStorePath) {
		this.fileStorePath = fileStorePath;
	}


	/**
	 * @return the fileUnUploadeds
	 */
	public List<Map<String, String>> getFileUnUploadeds() {
		return fileUnUploadeds;
	}



	
}

