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
import com.gzzn.fgw.service.sys.ISysRoleService;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;

/**
 * <p>Title: SysXqServiceImpl</p>
 * <p>Description: 辖区信息实现类   </p>
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
public class SysXqServiceImpl extends AbstractService<SysXq> implements
		ISysXqService {

	@Resource
	private IEntityDao entityDao;
	
	@Override
	public String findSysXqTreeJson(Boolean nocheck) {
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, "0");//使用状态：0 正常 ，1 删除
//		if(StringUtils.isNotEmpty(organizationId)){
//			con.add("sysOrganization.organizationId", Operator.EQ, Integer.parseInt(organizationId));//使用状态：1 正常 ，0 删除
//			con.add("sysOrganization.organizationId",Operator.ISNOTNULL,null);
//		}
		Order order1 = new Order(Direction.ASC, "xqId");
		Order order2 = new Order(Direction.ASC, "sjxq.xqId");
		Sort sort = new Sort(order1, order2);
		List<SysXq> list = entityDao.find(SysXq.class, con, sort);
		
		//获取全部的上级分类信息
		Map<Integer,SysXq> xmlbUpMap = getSysXqUpMap();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		for(SysXq obj : list){
			TreeNodesPojo objTree = new TreeNodesPojo();
			objTree.setId(String.valueOf(obj.getXqId()));
			objTree.setpId(obj.getSjxq()==null?null:String.valueOf(obj.getSjxq().getXqId()));
			objTree.setName(obj.getXqmc());
			//是否隐藏父节点，当前节点是否为父节点
			if(nocheck && obj.getXqId()!=null && xmlbUpMap.containsKey(obj.getXqId())){
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
	private Map<Integer,SysXq> getSysXqUpMap(){
		Map<Integer,SysXq> map = new HashMap<Integer, SysXq>();
		
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, "0");//使用状态：0 正常 ，1 删除
		List<SysXq> list = entityDao.find(SysXq.class, con);
		for(SysXq obj : list){
			if(obj.getSjxq()!=null && !map.containsKey(obj.getSjxq().getXqId())){
				map.put(obj.getSjxq().getXqId(), obj.getSjxq());
			}
		}
		return map;
	}
}
