package com.gzzn.fgw.util;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Title:PojoCopyUtil.java
 * @Description:中心与前置对应的pojo转换
 * @author yjfeng
 * @date Mar 19, 2010
 * @version V1.0
 */
public class PojoCopyUtil {

	/**
	 * @Description: 将对象src的属性值赋给obj对应的属性（这里只赋值对应类型相同的属性）
	 * @param src 源
	 * @param obj 目标--奖src的属性付给obj
	 * @return void
	 */
	public static void copySameTypeField(Object src, Object obj) {
		// 得到对象的所有方法
		Method[] srcMethods = src.getClass().getMethods();
		Method[] objMethods = obj.getClass().getMethods();
		for (Method m : objMethods) {
			// 过滤掉目标对象的非set方法
			if (!m.getName().startsWith("set")) {
				continue;
			}
			// 找到源对象中对应的get方法
			for (Method mm : srcMethods) {
				String name = mm.getName();// 方法名称
				if (name.startsWith("get") && name.substring(3).equals(m.getName().substring(3))) {
					try {
						Object o = mm.invoke(src);
						if (!(o instanceof Set)) {
							m.invoke(obj, o);// 调用源对象的get方法与目标对象的set方法，为目标对象属性赋值
						}
						break;
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
		}
	}

}
