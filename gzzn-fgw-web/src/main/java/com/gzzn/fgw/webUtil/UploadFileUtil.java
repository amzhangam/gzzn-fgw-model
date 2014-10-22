package com.gzzn.fgw.webUtil;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


public class UploadFileUtil {
	
	public static final Logger log = Logger.getLogger(UploadFileUtil.class);
	
	private static String realFilePath=ServletActionContext.getServletContext().getRealPath("/");
	
	/**
	 * 上传文件和名称生成
	 * @param file(上传的文件)
	 * @param fpath(上传的路径)
	 * @param fname(上传的文件名)
	 * @return 生成后的文件名
	 */
	public static String uploadFile(File file, String fpath, String fname) {
		String milliName=String.valueOf(System.currentTimeMillis());//按照毫秒命名
		String ext=fname.substring(fname.lastIndexOf(".")+1);
		File savefile = new File(new File(fpath), milliName + "." + ext);
        if (!savefile.getParentFile().exists())
            savefile.getParentFile().mkdirs();
        try {
			FileUtils.copyFile(file, savefile);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		log.info("上传文件路径："+fpath);
		return milliName + "." + ext;
	}
	
	/**
	 * 上传文件
	 * @param file(上传的文件)
	 * @param fileFullPath(即将生成的文件全路径)
	 */
	public static void uploadFile(File file, String fileFullPath) {
		File savefile = new File(fileFullPath);
		if (!savefile.getParentFile().exists())
			savefile.getParentFile().mkdirs();
		try {
			FileUtils.copyFile(file, savefile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 */
	public static void deleteFile(String filePath,String fileName){
		if(fileName==null||"".equals(fileName)){
			return;
		}
		File file=new File(filePath+fileName);
		if(file.exists()){
			file.delete();
			log.info("删除一个文件："+fileName);
		}
	}
	
	public static boolean checkFileName(String fname,String fpath) throws Exception {
		if (new File(fpath + fname).exists()) {
			return true;
		} else {
			log.info(fname + ":文件已经不存在");
			return false;
		}
	}
	
	/**
	 * 
	 * 方法描述：文件的下载
	 * 创建时间：2013-12-5上午10:01:00
	 * 创建人：lxb
	 * @param filePath
	 * @param fileName
	 * @param response
	 *
	 */
	public static void fileDownload(String filePath,String fileName,HttpServletResponse response){
		File file=new File(filePath+fileName);
		byte[] bytes;
		ServletOutputStream out;
		try {
			bytes = ReadFileByteUtil.getBytes(file);
			fileName= URLEncoder.encode(fileName,"UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName.trim());
			//response.setContentType("text/html");
			out = response.getOutputStream();
			out.write(bytes);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("下载文件路径："+filePath);
	}
	
	/**
	 * 
	 * 方法描述：获取预案文件路径
	 * 创建时间：2013-12-26下午03:06:46
	 * 创建人：lxb
	 * @return
	 *
	 */
	public static String getPlaFilePath(){
		return realFilePath+MyResourceBundle.getString("plaFilePath");
	}
	
	/**
	 * 
	 * 方法描述：获取预案中附件的文件路径
	 * 创建时间：2013-12-26下午03:32:46
	 * 创建人：lxb
	 * @return
	 *
	 */
	public static String getPlaAttFilePath(){
		return realFilePath+MyResourceBundle.getString("plaAttFilePath");
	}
	
	/**
	 * 
	 * 方法描述：获取工作动态文件路径
	 * 创建时间：2013-12-26下午15:33:00
	 * 创建人：lifan
	 * @param filePath
	 * @param fileName
	 * @param response
	 *
	 */
	 public static String getworkDynaPath(){
		 return realFilePath+MyResourceBundle.getString("workdynaFilePath");
	 }
	 /**
		 * 
		 * 方法描述：获取模板管理文件路径
		 * 创建时间：2013-12-26下午16:25:00
		 * 创建人：lifan
		 * @param filePath
		 * @param fileName
		 * @param response
		 *
		 */
	 public static String gettableTempPath(){
		 return realFilePath+MyResourceBundle.getString("tableTempFilePath");
	 }
}
