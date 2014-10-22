package com.gzzn.fgw.interceptor;

import org.apache.struts2.ServletActionContext;

import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.util.common.LogUtil;
import com.gzzn.util.common.MessageUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * <p>Title: CheckUserInterceptor</p>
 * <p>Description: 异常拦截器</p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午5:49:57  ChengZhi    new<p>
 */
public class CheckUserInterceptor extends BaseAction implements Interceptor {
	private static final long serialVersionUID = -5318834050025993327L;
	@Override
	public void init() {
		LogUtil.getLogger().debug(this.getClass().getName()+".init() 初始化");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		String[] uris = request.getRequestURI().split("/");
		String uri = uris[uris.length-1];
		LogUtil.getLogger().debug(uri);
		String methodName = invocation.getProxy().getMethod();
		
		int msgCode = 0;
		if(methodName.startsWith("find")|| methodName.startsWith("search") || methodName.startsWith("load")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_FIND;
		}else if(methodName.startsWith("add") || methodName.startsWith("save")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_ADD;
		}else if(methodName.startsWith("edit")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_EDIT;
		}else if(methodName.startsWith("delete") || methodName.startsWith("batchDelete")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_DELETE;
		}else if(methodName.startsWith("print")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_PRINT;
		}else if(methodName.startsWith("exp")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_EXP;
		}else if(methodName.startsWith("imp")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_IMP;
		}else if(methodName.startsWith("audit")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_AUDI;
		}else if(methodName.startsWith("login")){
			msgCode = MessageUtil.ACTION_MOTHODSTART_LOGIN;
		}else{
			msgCode = MessageUtil.ACTION_MOTHODSTART_OTHER;
		}
		
		request.setAttribute("msgCode",msgCode );
		
		//用户session过期
		if(!uri.startsWith(CommonFiled.ACTION_LOGIN_URI)){
			SysUser user = (SysUser) getHttpSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			if(null==user){
				errorHandle( MessageUtil.MSG_ERROR_LOGIN_SESSION);
				return null;
			}
		}
		
		return invocation.invoke();
		
		/*try {
			
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_DATAACCESS);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_NULLPOINTER);
		} catch (IOException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_IO);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_CLASSNOTFOUND);
		} catch (ArithmeticException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_ARITHMETIC);
		} catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_ARRAYINDEXOUTOFBOUNDS);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_ILLEGALARGUMENT);
		} catch (ClassCastException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_CLASSCAST);
		} catch (SecurityException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_SECURITY);
		} catch (SQLException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_SQL);
		} catch (QuerySyntaxException ex) {
			//ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_QUERYSYNTAX);
		} catch (NoSuchMethodError ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_NOSUCHMETHOD);
		} catch (InternalError ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_INTERNAL);
		} catch (JSONException ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_JSON);
		} catch (Exception ex) {
			ex.printStackTrace();
			errorHandle(msgCode,MessageUtil.EXCEPTION_OTHER);
		}
		return null;*/
	}

	/**
	 * 错误后的处理,日记记录,数据库记录
	 * @param errMsg
	 */
	private void errorHandle(int...msgCode){
		LogUtil.getLogger().error(MessageUtil.getMsg(msgCode));
		if(msgCode.length==1){
			outPutErrorLogin(msgCode);
		}else{
			outPutError(msgCode);
		}
	}

	@Override
	public void destroy() {
		LogUtil.getLogger().debug(this.getClass().getName()+".destroy() 销毁");
	}
	
	
}
