package com.gzzn.fgw.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.util.common.MessageUtil;
//import com.gzzn.util.json.GsonIntegerTypeAdapter;
import com.gzzn.util.json.JSONUtil;
import com.gzzn.util.web.HTMLFilter;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * <p>Title: BaseAction</p>
 * <p>Description:ModelDriven 实现getModel()方法,struts2就能自动把数据赋给模型对象,页面直接取值不需要model.xxx<p>
 * SessionAware 通过的Map对象来注入session<p>
 * ServletRequestAware 得到request对象<p>
 * ServletResponseAware 得到Respone对象<p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午5:50:14  ChengZhi    new<p>
 */
@ParentPackage(value = "struts-default")
public class BaseAction<T> implements ServletRequestAware, ServletResponseAware, SessionAware,ParameterAware,
		ModelDriven<T> {

	protected static Logger logger = LoggerFactory.getLogger(BaseAction.class);

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	
	protected LogObject logObject;//记录日志的对象
	
	private String isShowAlone;//用于控制页面是否为单位显示
	
	
	public LogObject getLogObject() {
		return logObject;
	}

	public void setLogObject(LogObject logObject) {
		this.logObject = logObject;
	}

	public String getIsShowAlone() {
		return isShowAlone;
	}

	public void setIsShowAlone(String isShowAlone) {
		this.isShowAlone = isShowAlone;
	}

	/**
	 * 返回已登录用户
	 * 
	 * @return
	 */
	public SysUser getLoginUser() {
		return (SysUser) session.get(CommonFiled.SESSION_LOGIN_USER);
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 获取HttpSession的session作用域
	 * @return
	 */
	public HttpSession getHttpSession() {
		return request.getSession();
	}

	/**
	 * 对请求进行编码
	 */
	public void setCharacterEncodingForUtf8() {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
	}

//	/**
//	 * GSON:jdbc查询结果转换成Json串
//	 * @param Long->String,Date->"yyyy-MM-dd HH:mm:ss",Integer->String
//	 * @return
//	 */
//	public String toJDBCJsonStr(List<Map<String, Object>> list) {
//		GsonBuilder gsonBuilder = new GsonBuilder();
//		gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
//		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm");
//		gsonBuilder.registerTypeAdapter(Integer.class, new GsonIntegerTypeAdapter());
//		return gsonBuilder.create().toJson(list);
//	}

	/**
	 * 获取WebRoot路径
	 * @return
	 */
	public String getWebRootPath() {
		return ServletActionContext.getServletContext().getRealPath("/");
	}

	/**
	 * 将字符JSON串返回至浏览器(多用于Ajax返回),不使用缓存
	 * @param ajString 需要返回的数据(JSON串)
	 */
	public void outPutJSON(String ajaxString) {
		outPutJSON(ajaxString, true);
	}

	/**
	 * 将字符JSON串返回至浏览器(多用于Ajax返回)
	 * ajString 需要返回的数据(JSON串)
	 * @param noCache  是否使用缓存,true不使用
	 */
	public void outPutJSON(String ajaxString, boolean noCache) {
		outPutString(ajaxString, noCache, "text/html");
	}

	/**
	 * 将字符串返回至浏览器(多用于JSON串或Ajax返回)
	 * @param ajString 需要返回的数据(JSON串)
	 * @param noCache 是否使用缓存,true不使用
	 * @param type 返回类型,参见ajax返回类型说明:text/javascript或application/json为json数据;text/xml或application/xml为xml数据;text/html  
	 */
	public void outPutString(String ajString, boolean noCache, String type) {
		response.setContentType(type + ";charset=utf-8");
		try {
			setCharacterEncodingForUtf8();
			if (noCache) {
				response.setHeader("pragma", "no-cache");
				response.setHeader("cache-control", "no-cache");
			}
			PrintWriter pw = response.getWriter();
			logger.debug("out put json data:{}", ajString);
			pw.write(ajString);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回字符串到浏览器
	 * @param str
	 */
	public void outPutString(String str) {
		outPutString(str, true, "text/html");
	}

	/**
	 * 将操作消息,返回数据条数,操作返回数据组装成json串
	 * @param json 操作返回数据
	 * @param count 返回数据条数
	 * @param msgFlag 操作类型(成功:true,失败:false)
	 * @param msgCode 操作消息代码
	 * @return
	 */
	public String assemblyJson(String json, int count, Boolean msgFlag, int... msgCode) {
		//LogUtil.getLogger().debug(MessageUtil.getMsg(msgCode));
		return assemblyJson(json, count, MessageUtil.getMsg(msgCode), msgFlag);
	}

	/**
	 * 将操作消息,返回数据条数,操作返回数据组装成json串
	 * @param json 操作返回数据
	 * @param count 返回数据条数
	 * @param msg 操作消息
	 * @param msgFlag 操作类型(成功:true,失败:false)
	 * @return
	 */
	public String assemblyJson(String json, int count, String msg, Boolean msgFlag) {
		return "{\"count\":" + count + ",\"msg\":\"" + msg + "\",\"flag\":" + msgFlag
				+ ",\"result\":" + json + "}";
	}

	/**
	 * 将对象以json的形式返回到客户端
	 * @param obj 操作数据
	 * @param msgFlag 操作类型(成功:true,失败:false)
	 * @param msgCode 操作消息代码
	 */
	public <T> void outPutObject(T obj, Boolean msgFlag, int... msgCode) {
		outPutJSON(assemblyJson(JSONUtil.getJSONObject(obj, null).toString(), 1, msgFlag, msgCode));
	}

	/**
	 * 将对象以json的形式返回到客户端
	 * @param obj 操作数据
	 * @param msgFlag 操作类型(成功:true,失败:false)
	 * @param msg 操作消息代码
	 */
	public <T> void outPutObject(T obj, Boolean msgFlag, String msg) {
		outPutJSON(assemblyJson(JSONUtil.getJSONObject(obj, null).toString(), 1, msg, msgFlag));
	}

	/**
	 * 将对象集合以json的形式返回到客户端
	 * @param list 操作数据
	 * @param msgFlag 操作类型(成功:true,失败:false)
	 * @param msgCode 操作消息代码
	 */
	public <T> void outPutList(Collection<T> list, Boolean msgFlag, int... msgCode) {
		outPutJSON(assemblyJson(JSONUtil.getJSONArray(list, null).toString(), list.size(), msgFlag,
				msgCode));
	}

	/**
	 * 将对象以json的形式返回到客户端,操作类型:成功(true)
	 * @param obj 对象
	 */
	public <T> void outPutObject(T obj) {
		outPutObject(obj, true, MessageUtil.MSG_SUCCESS);
	}

	/**
	 * 将对象集合以json的形式返回到客户端,操作类型:成功(true)
	 * @param list 集合
	 */
	public <T> void outPutList(Collection<T> list) {
		outPutList(list, true, MessageUtil.MSG_SUCCESS);
	}

	/**
	 * 将操作消息以json的形式返回到客户端
	 * @param msgCode 操作消息
	 * @param msgFlag 操作类型:true为成功,false是失败
	 */
	public void outPutMsg(Boolean msgFlag, int... msgCode) {
		outPutJSON(assemblyJson(null, 1, msgFlag, msgCode));
	}

	/**
	 * 将操作消息以json的形式返回到客户端
	 * @param msgFlag 操作类型:true为成功,false是失败
	 * @param msg 操作消息
	 */
	public void outPutMsg(Boolean msgFlag, String msg) {
		outPutJSON(assemblyJson(null, 1, msg, msgFlag));
	}

	/**
	 * 将操作消息以json的形式返回到客户端,操作类型(false失败)
	 * @param msgCode 操作消息
	 */
	public void outPutError(int... msgCode) {
		//LogUtil.getLogger().debug(msgCode);
		outPutJSON(assemblyJson("\"error\"", 0, false, msgCode));
	}

	/**
	 * 将操作消息以json的形式返回到客户端,操作类型(false失败)
	 * @param msg 操作消息
	 */
	public void outPutError(String msg) {
		outPutJSON(assemblyJson("\"error\"", 0, msg, false));
	}

	/**
	 * 将操作消息以json的形式返回到客户端,操作类型(false失败)
	 * @param msgCode 操作消息
	 */
	public void outPutErrorLogin(int... msgCode) {
		outPutJSON(assemblyJson("\"login\"", 0, false, msgCode));
	}

	/**
	 * 将操作消息以json的形式返回到客户端,操作类型(false失败)
	 * @param msg 操作消息
	 */
	public void outPutErrorLogin(String msg) {
		outPutJSON(assemblyJson("\"login\"", 0, msg, false));
	}

	public void outJsonString(String str) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public SysUser getUser(){
		SysUser sysUser=(SysUser)this.getHttpSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		return sysUser;
	}
	
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String downFileNameTranscode(String fileName){
		try {
			String agent = request.getHeader("user-agent");//判断浏览器类型
			if (agent.contains("MSIE")) {//IE浏览器 --- URL编码
				fileName = URLEncoder.encode(fileName, "utf-8");
			} else if (agent.contains("Firefox")) {//火狐浏览器 --- Base64编码
				fileName = base64EncodeFileName(fileName);
			} else {
				fileName = URLEncoder.encode(fileName, "utf-8");
			}
		} catch (UnsupportedEncodingException e1) { }
		
		return fileName;
	}
	
	/**
	 * 火狐要用base64解决编码问题
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String base64EncodeFileName(String fileName) {
		//BASE64Encoder base64Encoder = new BASE64Encoder();
		sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
		try {
			//return "=?UTF-8?B?"+ new String(base64Encoder.encode(fileName.getBytes("UTF-8"))) + "?=";
			return "=?UTF-8?B?"+ new String(base64Encoder.encode(fileName.getBytes("UTF-8"))).replaceAll("[\\t\\n\\r]", "") + "?=";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		throw new RuntimeException(e);
		}
	}
	
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		Map<String, String[]> map = request.getParameterMap();
		
		for(Entry<String, String[]> e :map.entrySet()){
			String [] aa = e.getValue();
			for(int i=0;i<aa.length;i++){
				aa[i] = HTMLFilter.filter(aa[i]);
			}
			e.setValue(aa);
			parameters.put(e.getKey(), e.getValue());
		}
	}

	
	
}
