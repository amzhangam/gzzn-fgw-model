package com.gzzn.fgw.webUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title: CommonFiled</p>
 * <p>Description: web模块中的一些静态变量</p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改记录（内容+日期）<br/>
 * 1.2013-8-20下午5:52:52  ChengZhi    new<br/>
 * 2.2014-03-07 18:53:00 lzq 完善注释<br/>
 */
public class CommonFiled {
	/**String 分隔符*/
	public static final String STRING_SEPARATE="|";
	/**String 分隔符 转义*/
	public static final String STRING_SEPARATE_ESCAPE="\\|";

	
	/**session用户名*/
	public static final String SESSION_LOGIN_NAME = "sessionLoginName";
	/**session用户*/
	public static final String SESSION_LOGIN_USER = "sessionLoginUser";
	/**session用户jSON*/
	public static final String SESSION_LOGIN_USER_JSON = "sessionLoginUserJson";
	/**session用户权限*/
	public static final String SESSION_LOGIN_USER_PERMISSION = "sessionLoginUserPermission";
	/**session用户菜单JSon*/
	public static final String SESSION_LOGIN_USER_MENU_JSON = "sessionLoginUserMenuJson";

	/*用户权限system表*/
	public static final String SESSION_SYSTEM_MAP = "systemMap";
	/*用户权限module表*/
	public static final String SESSION_MODULE_MAP = "moduleMap";
	/*用户权限object表*/
	public static final String SESSION_OBJECT_MAP = "objectMap";
	/*操作描述对照表*/
	public static final String SESSION_LOG_MAP = "logMap";

	/**用户登录URL地址*/
	public static final String ACTION_LOGIN_URI = "sysUserAct!login";
	/**注销用户URL地址*/

	public static final String ACTION_LOGOUT_URI = "sysUserAct!logOut";
	
	/*
	 * session缓存公共数据表的Key名
	 */
	/**对应数据字典表SYS_DICTIONARYITEMS*/
	public static final String SESSION_DIRECTIONARYITEMS = "sessionDirectionaryitmes";
	
	/**
	 * 数据字典-年份
	 */
	public static final String SESSION_DIRECTIONARYITEMS_NF = "sessionDirectionaryitmesNf";
	
	/**
	 * 附件类型Map
	 */
	public static final String SESSION_FJLX_MAP = "sessionFjlxMap";
	
	/**
	 * 项目状态Map
	 */
	public static final String SESSION_XMZT_MAP = "sessionXmztMap";
	
	/**
	 * 项目进度Map
	 */
	public static final String SESSION_XMJD_MAP = "sessionXmjdMap";
	
	/**
	 * 项目分类Map
	 */
	public static final String SESSION_XMFL_MAP = "sessionXmflMap";
	
	/**
	 * 部门Map
	 */
	public static final String SESSION_DEPT_MAP = "sessionDeptMap";
	
	/**
	 * 辖区Map
	 */
	public static final String SESSION_XQ_MAP = "sessionXqMap";
	
	/**
	 * 项目类型Map
	 */
	public static final String SESSION_XMLX_MAP = "sessionXmlxMap";
	
	/**
	 * 意见模板Map
	 */
	public static final String SESSION_YJMB_MAP = "sessionYjmbMap";
	
	/**
	 * 短信模板Map
	 */
	public static final String SESSION_DXMB_MAP = "sessionDxmbMap";

	/**
	 * 附件类型
	 */
	public static final String SESSION_FJ = "sessionFj";//所有附件
	
	public static final String SESSION_QQJZFJ = "sessionQqjzFj";//项目前期进展
	
	public static final String SESSION_LXPFFJ = "sessionLxpfFj";//立项批复
	public static final String SESSION_GHXZFJ = "sessionGhxzFj";//规划选址
	public static final String SESSION_YDYSFJ = "sessionYdysFj";//用地预审
	public static final String SESSION_HJYXFJ = "sessionHjyxFj";//环境影响评价
	public static final String SESSION_JNPGFJ = "sessionJnpgFj";//节能评估审查
	public static final String SESSION_KYPFFJ = "sessionKypfFj";//可研批复
	public static final String SESSION_CBSJFJ = "sessionCbsjFj";//初步设计及概算
	public static final String SESSION_ZBTBFJ = "sessionZbtbFj";//招标投标情况
	public static final String SESSION_ZDCQFJ = "sessionZdcqFj";//征地拆迁
	public static final String SESSION_QTQQFJ = "sessionQtqqFj";//其他前期工作
	
	public static final String SESSION_SBYJFJ = "sessionSbyjFj";//项目申报依据
	public static final String SESSION_XXJDFJ = "sessionXxjdFj";//工程形象进度
	public static final String SESSION_CZWTFJ = "sessionCzwtFj";//存在的问题
	public static final String SESSION_SMCLFJ = "sessionSmclFj";//项目书面材料
	
	public static final List<Integer> FJLX_QQJZ= new ArrayList<Integer>();////项目前期进展附件
	{
		FJLX_QQJZ.add(0);
		FJLX_QQJZ.add(1);
		FJLX_QQJZ.add(2);
		FJLX_QQJZ.add(3);
		FJLX_QQJZ.add(4);
		FJLX_QQJZ.add(5);
		FJLX_QQJZ.add(6);
		FJLX_QQJZ.add(7);
		FJLX_QQJZ.add(8);
		FJLX_QQJZ.add(9);
	}
	
}
