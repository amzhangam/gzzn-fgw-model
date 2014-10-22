package com.gzzn.fgw.webUtil;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;


/**
 * 自定义标签库
 * 
 * 每位工程师都有保持代码优雅的义务 each engineer has a duty to keep the code elegant
 * 
 * @author slzs
 * @see http://hi.baidu.com/slzs_zyt/blog/item/f2f92c337e7049f51a4cffb8.html
 */
public class SubStr {
	
	public static String subStrLink(String str1,String str2, int maxByte, String endStr) {
		StringBuffer strBuf = new StringBuffer();
		if(str2 == null || "".equals(str2)){
			strBuf.append(str1);
		}else{
			strBuf.append(str1).append("(").append(str2).append(")");
		}
		
		return subString(strBuf.toString(), maxByte, endStr);
	}
	
	
	
	/**
	 * 按字节长度截取字符串
	 * 
	 * @param str
	 *            :源字符串
	 * @param maxbyte
	 *            : 字符串的byte限制
	 * @param endStr
	 *            : 如果超出最大byte，结尾省略字符串
	 * @return String Object
	 * @author slzs
	 * @seehttp://hi.baidu.com/slzs_zyt/blog/item/f2f92c337e7049f51a4cffb8.html
	 */
	public static String subString(String str, int maxByte, String endStr) {

		if (str == null || "".equals(str)) {

			// 如果源字符串为空或null，返回空字符串
			str = "";
		} else {

			// 计算字节长度
			int byteLength = 0;

			// 计算字符长度
			int charLength = 0;

			for (; charLength < str.length(); charLength++) {

				// 计算每个字符的字节数，每个汉字+2byte，其它+1
				byteLength = (int) str.charAt(charLength) > 256 ? byteLength + 2
						: byteLength + 1;

				// 超过最大限制字节时，按当前charLength截取字符串
				if (byteLength > maxByte) {

					// 当前长度减去结尾省略字符串的长度的一半（此处将省略字符串假设为半字节字符）
					charLength = charLength - endStr.length() / 2;

					// 截取字符串，加上省略字符串
					str = str.substring(0, charLength > 0 ? charLength : 0)
							+ endStr;

					// 跳出循环
					break;
				}
			}
		}

		return str;
	}

	/**
	 * 按指定长度将字符串换行
	 * @param str 字符串
	 * @param subLength 截取长度
	 * @return 
	 */
	public static String subStrLength(String str, int subLength) {
		if (str==null || str.isEmpty()) {
			str = "";
		} else {
			char[] chars = str.toCharArray();
			str = "";
			for (int i = 0; i < chars.length; i++) {
				if(chars[i]>256){
					subLength = (subLength/2)%2==0?subLength:subLength+1;
					if(i!=0&&i%(subLength/2)==0){
						str+="<br>"+String.valueOf(chars[i]);
					}else{
						str+=String.valueOf(chars[i]);
					}
				}else{
					if(i!=0&&i%subLength==0){
						str+="<br>";
					}else{
						str+=String.valueOf(chars[i]);
					}
				}
				
			}
		}
		return str;
	}

	
	/**
	 * 检测字符串年
	 * @param actor list集合
	 * @param args 检测字符串数组
	 * @return
	 */
	public static boolean contains(List<String> actor, String args){
		boolean flag = false;
		for (String string : args.split(",")) {
			flag = actor.contains(string);
			if(flag){
				break;
			}
		}
		return flag;
	}
	
	
}
