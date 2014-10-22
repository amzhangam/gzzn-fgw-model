package com.gzzn.fgw.webUtil;

import java.util.ResourceBundle;

/**
 * 
 * @author lxb
 * 读取properties文件，获取所对应的key值
 *
 */
public class MyResourceBundle {
	
	private static ResourceBundle rb=null;
	
	static{
		//读取文件路径
		rb=ResourceBundle.getBundle("filePath");
	}
	
	/**
	 * 
	 * 方法描述：返回字符串
	 * 创建时间：2013-12-26下午03:05:01
	 * 创建人：lxb
	 * @param key
	 * @return
	 *
	 */
	public static String getString(String key){
		return rb.getString(key);
	}
	
	/**
	 * 
	 * 方法描述：返回整型
	 * 创建时间：2013-12-26下午03:05:09
	 * 创建人：lxb
	 * @param key
	 * @return
	 *
	 */
	public static int getInteger(String key){
		return Integer.parseInt(rb.getString(key));
	}

}
