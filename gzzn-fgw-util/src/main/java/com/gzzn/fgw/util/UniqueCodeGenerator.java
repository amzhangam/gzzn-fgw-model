package com.gzzn.fgw.util;


/**
 * 
 * <p>Title: UniqueCodeGenerator</p>
 * <p>Description: 唯一编码生成器</p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author yjf
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-11-30 下午3:07:45  yjf    new
 */
public abstract class UniqueCodeGenerator {

	public static String getUniqueCode() {
		return ("" + System.currentTimeMillis()+Math.random()).replace(".", "");
	}
	
	public static void main(String[] args) {
		System.out.println(UniqueCodeGenerator.getUniqueCode());
	}
}
