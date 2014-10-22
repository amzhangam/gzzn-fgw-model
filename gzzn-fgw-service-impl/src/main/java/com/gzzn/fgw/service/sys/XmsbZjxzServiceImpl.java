package com.gzzn.fgw.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.XmsbZjxz;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;

/**
 * <p>Title: XmsbZjxzServiceImpl</p>
 * <p>Description: 资金性质实现类   </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-18 下午4:40:38 amzhang  new
 */
@Service
@Transactional
public class XmsbZjxzServiceImpl extends AbstractService<XmsbZjxz> implements
		IXmsbZjxzService {

	@Resource
	private IEntityDao entityDao;
	
	@Override
	public String findXmsbZjxzTreeJson(Boolean nocheck) {
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, "0");//使用状态：0 正常 ，1 删除
//		if(StringUtils.isNotEmpty(organizationId)){
//			con.add("sysOrganization.organizationId", Operator.EQ, Integer.parseInt(organizationId));//使用状态：1 正常 ，0 删除
//			con.add("sysOrganization.organizationId",Operator.ISNOTNULL,null);
//		}
		Order order1 = new Order(Direction.ASC, "zjxzId");
		Order order2 = new Order(Direction.ASC, "xmsbZjxz.zjxzId");
		Sort sort = new Sort(order1, order2);
		List<XmsbZjxz> list = entityDao.find(XmsbZjxz.class, con, sort);
		
		//获取全部的上级分类信息
		Map<Integer,XmsbZjxz> zjxzUpMap = getXmsbZjxzUpMap();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(XmsbZjxz obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getZjxzId()));
			objTree.setpId(obj.getXmsbZjxz()==null?null:String.valueOf(obj.getXmsbZjxz().getZjxzId()));
			objTree.setName(obj.getZjxzmc());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getZjxzId()!=null && zjxzUpMap.containsKey(obj.getZjxzId())){
				objTree.setNocheck(true);
			}
			listTree.add(objTree);
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 查找上级分类信息Map
	 * @return
	 */
	private Map<Integer,XmsbZjxz> getXmsbZjxzUpMap(){
		Map<Integer,XmsbZjxz> map = new HashMap<Integer, XmsbZjxz>();
		
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, "0");//使用状态：0 正常 ，1 删除
		List<XmsbZjxz> list = entityDao.find(XmsbZjxz.class, con);
		for(XmsbZjxz obj : list){
			if(obj.getXmsbZjxz()!=null && !map.containsKey(obj.getXmsbZjxz().getZjxzId())){
				map.put(obj.getXmsbZjxz().getZjxzId(), obj.getXmsbZjxz());
			}
		}
		return map;
	}
	
	/**
	 * 根据上级资金性质ID返回上级对象
	 * @param list
	 * @param organUpId
	 * @return
	 */
	public XmsbZjxz getXmsbZjxzUpObj(List<XmsbZjxz> list,Integer zjxzUpId){
		if(zjxzUpId>0){
			for(XmsbZjxz zjxzUpObj : list){
				if(zjxzUpId.equals(zjxzUpObj.getZjxzId())){
					return zjxzUpObj;
				}
			}
		}
		
		return null;
	}
}
