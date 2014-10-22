package com.gzzn.fgw.service;

import java.util.List;

import com.gzzn.fgw.model.pojo.TreeNodesPojo;

/**
 * 
 * <p>Title: ITreeListOrJsonService</p>
 * <p>Description: 树型数据（LIST或JSON）接口  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-1-9 上午10:53:43 amzhang  new
 */
public interface ITreeListOrJsonService {
	
	
	/**
	 * 获取机构信息的树形list：按照上级ID、ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示 true不显示 false显示
	 * @return
	 */
	public String getSysOrgTreeJson(Boolean nocheck);
	
	/**
	 * 获取部门信息的树形list：按照上级ID、ID排序
	 * @param organizationId
	 * @param nocheck 父节点的单选或复选按钮是否显示 true不显示 false显示
	 * @return
	 */
	public String getSysDeptTreeJson(String organizationId, Boolean nocheck);
	
	/**
	 * 获取机构、部门的组合树，为了区分是机构的id还是部门的id，特在机构id前面加上g
	 * @param nocheckOrg 机构信息的单选或复选按钮是否显示 true不显示 false显示
	 * @param nocheckDept 部门信息的单选或复选按钮是否显示 true不显示 false显示
	 * @return
	 */
	public String getOrgDeptTreeJson(Boolean nocheckOrg,Boolean nocheckDept);
	
	/**
	 * 查找机构信息树
	 * @param nocheckOrg casDeptInfo=false时，机构信息父节点的单选或复选按钮是否显示 true不显示 false显示；
	 *  				 casDeptInfo=true 时，机构信息的单选或复选按钮是否显示 true不显示 false显示；
	 * @param casDeptInfo 是否级联部门树信息
	 * @param nocheckDept 部门信息父节点的单选或复选按钮是否显示 true不显示 false显示
	 * @return
	 */
	public List<TreeNodesPojo> findSysOrgTree(Boolean nocheckOrg, Boolean casDeptInfo,Boolean nocheckDept);
	
	/**
	 * 查找部门信息树
	 * @param organizationId
	 * @param casDeptInfo 部门信息是否被级联
	 * @param nocheckDept 父节点的单选或复选按钮是否显示 true不显示 false显示
	 * @return
	 */
	public List<TreeNodesPojo> findSysDeptTree(String organizationId, Boolean casDeptInfo,Boolean nocheckDept);

	
	
	
	
}
