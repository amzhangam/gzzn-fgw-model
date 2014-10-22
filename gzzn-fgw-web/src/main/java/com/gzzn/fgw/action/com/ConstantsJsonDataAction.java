package com.gzzn.fgw.action.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.util.IConstants;

/**
 * <p>Title: ConstantsJsonDataAction</p>
 * <p>Description:  获取逻辑常量JSON数据类</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-06-05 lhq  new
 */
@Namespace(value = "/com")
public class ConstantsJsonDataAction extends BaseAction<TreeNodesPojo> {
	
	protected static Logger logger = LoggerFactory.getLogger(ConstantsJsonDataAction.class);
	
	private List<TreeNodesPojo> listTree = null;
	
	/**
	 * 辖区类型：0-	国家、1-	省、2-市、3-区、4-街、5-社区
	 * @return
	 */
	@Action("getXQLXJson")
	public String getXQLXJson(){
		listTree = new ArrayList<TreeNodesPojo>();
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.XQLX_0), IConstants.XQLX_NAME_0));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.XQLX_1), IConstants.XQLX_NAME_1));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.XQLX_2), IConstants.XQLX_NAME_2));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.XQLX_3), IConstants.XQLX_NAME_3));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.XQLX_4), IConstants.XQLX_NAME_4));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.XQLX_5), IConstants.XQLX_NAME_5));
		
		outPutJSON(new Gson().toJson(listTree));
		return null;
	}
	
	/**
	 * 区县项目情况：重点项目标注（0-非重点项目；1为省重点项目；2为市重点项目）
	 * @return
	 */
	@Action("getZDXMBZJson")
	public String getZDXMBZJson(){
		listTree = new ArrayList<TreeNodesPojo>();
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ZDXMBZ_0), IConstants.ZDXMBZ_NAME_0));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ZDXMBZ_1), IConstants.ZDXMBZ_NAME_1));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ZDXMBZ_2), IConstants.ZDXMBZ_NAME_2));
		
		outPutJSON(new Gson().toJson(listTree));
		return null;
	}
	
	/**
	 * 单位类型：1-业主单位；2-主管单位；3-发改委单位；4-区县发展和改革局
	 * @return
	 */
	@Action("getOrganTypeJson")
	public String getOrganTypeJson(){
		listTree = new ArrayList<TreeNodesPojo>();
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ORGAN_TYPE_1), IConstants.ORGAN_TYPE_NAME_1));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ORGAN_TYPE_2), IConstants.ORGAN_TYPE_NAME_2));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ORGAN_TYPE_3), IConstants.ORGAN_TYPE_NAME_3));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.ORGAN_TYPE_4), IConstants.ORGAN_TYPE_NAME_4));
		
		outPutJSON(new Gson().toJson(listTree));
		return null;
	}
	
	/**
	 * 用户类型：1-业主用户；2-主管单位用户；3-市发改委用户；4-区县发改委用户；5-超级管理员
	 * @return
	 */
	@Action("getUserTypeJson")
	public String getUserTypeJson(){
		listTree = new ArrayList<TreeNodesPojo>();
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.USER_TYPE_1), IConstants.USER_TYPE_NAME_1));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.USER_TYPE_2), IConstants.USER_TYPE_NAME_2));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.USER_TYPE_3), IConstants.USER_TYPE_NAME_3));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.USER_TYPE_4), IConstants.USER_TYPE_NAME_4));
		//listTree.add(new TreeNodesPojo(String.valueOf(IConstants.USER_TYPE_5), IConstants.USER_TYPE_NAME_5));
		
		outPutJSON(new Gson().toJson(listTree));
		return null;
	}
	
	/**
	 * 用户状态：1-待审批；2-审批通过；3-审批不通过；4-销户
	 * @return
	 */
	@Action("getUserStatusJson")
	public String getUserStatusJson(){
		listTree = new ArrayList<TreeNodesPojo>();
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_1), IConstants.SHENHE_STATUS_NAME_1));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_2), IConstants.SHENHE_STATUS_NAME_2));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_3), IConstants.SHENHE_STATUS_NAME_3));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_4), IConstants.SHENHE_STATUS_NAME_4));
		
		outPutJSON(new Gson().toJson(listTree));
		return null;
	}
	
	/**
	 * 工作单位状态【用于审核业主注册信息】：1-待审批；2-审批通过；3-审批不通过
	 * @return
	 */
	@Action("getUnitStatusJson")
	public String getUnitStatusJson(){
		listTree = new ArrayList<TreeNodesPojo>();
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_1), IConstants.SHENHE_STATUS_NAME_1));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_2), IConstants.SHENHE_STATUS_NAME_2));
		listTree.add(new TreeNodesPojo(String.valueOf(IConstants.SHENHE_STATUS_3), IConstants.SHENHE_STATUS_NAME_3));
		
		outPutJSON(new Gson().toJson(listTree));
		return null;
	}
	
	
}
