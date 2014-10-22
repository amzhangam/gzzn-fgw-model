package com.gzzn.fgw.interceptor;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.gzzn.fgw.action.project.ProjectinvestplanforyearAction;
import com.gzzn.util.common.LogUtil;
import com.gzzn.util.exception.CustomException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -5318834050025993327L;
	private static final Logger log = Logger.getLogger(ExceptionInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String msgName = "此次操作";
		String methodName = invocation.getProxy().getMethod();
		try {
			return invocation.invoke();
		} catch (DataAccessException ex) {
			errorHandle(ex,msgName + "中数据库操作失败！");
		} catch (NullPointerException ex) {
			errorHandle(ex,msgName + "中调用了未经初始化的对象或者是不存在的对象！");
		} catch (IOException ex) {
			errorHandle(ex,msgName + "中IO异常！");
		} catch (ClassNotFoundException ex) {
			errorHandle(ex,msgName + "中指定的类不存在！");
		} catch (ArithmeticException ex) {
			errorHandle(ex,msgName + "中数学运算异常！");
		} catch (ArrayIndexOutOfBoundsException ex) {
			errorHandle(ex,msgName + "中数组下标越界!");
		} catch (IllegalArgumentException ex) {
			errorHandle(ex,msgName + "中方法的参数错误！");
		} catch (ClassCastException ex) {
			errorHandle(ex,msgName + "中类型强制转换错误！");
		} catch (SecurityException ex) {
			errorHandle(ex,msgName + "中违背安全原则异常！");
		} catch (SQLException ex) {
			errorHandle(ex,msgName + "中操作数据库异常！");
		} catch (NoSuchMethodError ex) {
			errorHandle(ex,msgName + "中方法末找到异常！");
		} catch (InternalError ex) {
			errorHandle(ex,msgName + "中Java虚拟机发生了内部错误");
		} catch (CustomException ex) {
			errorHandle(ex,msgName+ "中"+ex.getMessage());
		}catch (Exception ex) {
			errorHandle(ex,msgName + "中程序内部错误，操作失败！"+ex.getMessage());
		}
		return "error";
	}
	
	/**
	 * 错误后的处理
	 * @param errMsg
	 */
	private void errorHandle(Throwable t,String errMsg){
		log.error(errMsg, t);
		throw new CustomException(errMsg);
	}
	
}
