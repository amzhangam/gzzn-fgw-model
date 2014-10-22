package com.gzzn.fgw.interceptor;

import java.io.File;
import java.util.Arrays;

import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.gzzn.util.exception.CustomException;
import com.opensymphony.xwork2.ValidationAware;
/**
 * Title:MyFileUploadInterceptor<br>
 * Description: 自定义文件下载拦截器<br>
 * Copyright: Copyright (c) 2014 ITDCL  All right reserved.<br>
 * Company: ITDCL<br>
 * @author chengzhi<br>
 * @version 1.0<br>
 * 修改记录:下面填写修改的内容以及修改的日期<br>
 * 1.2014-6-26 下午7:32:13  chengzhi    new<br>
 */
public class MyFileUploadInterceptor extends FileUploadInterceptor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4524039204341586494L;

	@Override
	protected boolean acceptFile(Object action, File file, String filename, String contentType, String inputName, ValidationAware validation)throws CustomException {
		boolean fileIsAcceptable = false;
        if (file == null) {
        	throw new CustomException("文件不存在");
        } else if (maximumSize != null && maximumSize < file.length()) {
            String errMsg =  "文件大小超出文件限制,只允许上传小于"+((long) (maximumSize/1024/1024))+"M的文件.";
            throw new CustomException(errMsg);
            
        } else if ((!allowedTypesSet.isEmpty()) && !allowedTypesSet.contains(allowedTypesSet)) {
            String errMsg = "文件类型不符合,只允许上传"+(Arrays.toString(allowedTypesSet.toArray()))+"文件类型.";
            throw new CustomException(errMsg);
            
        } else if ((!allowedExtensionsSet.isEmpty()) && !allowedExtensionsSet.contains(filename.substring(filename.lastIndexOf(".")).toLowerCase())) {
        	String errMsg = "文件后缀名不符合,只允许后缀名为"+(Arrays.toString(allowedExtensionsSet.toArray()))+"的文件上传.";
        	 throw new CustomException(errMsg);
        	 
        } else {
            fileIsAcceptable = true;
        }
        return fileIsAcceptable;
	}
	
}
