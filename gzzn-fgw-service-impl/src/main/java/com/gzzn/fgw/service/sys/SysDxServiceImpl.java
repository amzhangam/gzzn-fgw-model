package com.gzzn.fgw.service.sys;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysDx;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: SysRoleServiceImpl</p>
 * <p>Description: 信息实现类   </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-10 下午2:40:38 amzhang  new
 */
@Service
@Transactional
public class SysDxServiceImpl extends AbstractService<SysDx> implements
		ISysDxService {

	@Resource
	private IEntityDao entityDao;

	@Override
	public PageUtil<Pjbaseinfo> findPjbaseinfoList(PageUtil<Pjbaseinfo> page, SysQueryParam sysParams, SysUser sysUser) {
		Condition con = initPjbaseinfoCon(sysParams);//查询条件
		con.add("pjstatus", Operator.NE, 0);//项目状态不能为草稿
		Sort sort = initPjbaseinfoSort();//排序字段
		page.setCount((int) entityDao.count(Pjbaseinfo.class, con));

		List<Pjbaseinfo> list = entityDao.find(Pjbaseinfo.class, con, sort, page.getOffset(), page.getLimitNum());
		page.setList(list);
		return page;
	}
	
	public Condition initPjbaseinfoCon(SysQueryParam sysParams) {
		Condition con = new Condition();
		if (sysParams != null) {
			if (StringUtils.isNotEmpty(sysParams.getProjectName())) {//项目名称
				con.add("projectname", Operator.LIKE, sysParams.getProjectName());
			}
			if (StringUtils.isNotEmpty(sysParams.getProjectcode())) {//项目编码
				con.add("projectcode", Operator.LIKE, sysParams.getProjectcode());
			}
			if (StringUtils.isNotEmpty(sysParams.getXmyz())) {//项目业主
				con.add("sysOrganizationByDeclareunitsid", Operator.ISNOTNULL,null);
				con.add("sysOrganizationByDeclareunitsid.organizationName",Operator.LIKE, sysParams.getXmyz());
			}
			if (StringUtils.isNotEmpty(sysParams.getZjxz())) {//资金性质
				con.add("xmsbZjxz", Operator.ISNOTNULL, null);
				con.add("xmsbZjxz.zjxzId", Operator.IN, Arrays.asList(sysParams.getZjxz().split(",")));
			}
			if(StringUtils.isNotEmpty(sysParams.getStartTime())){//申报时间：开始时间
				con.add("declartime", Operator.GE, DateUtil.parse(sysParams.getStartTime(),"yyyy-MM-dd"));
			}
			if(StringUtils.isNotEmpty(sysParams.getEndTime())){//申报时间：结束时间
				con.add("declartime", Operator.LE, DateUtil.parse(sysParams.getEndTime(),"yyyy-MM-dd"));
			}
			if(StringUtils.isNotEmpty(sysParams.getZgdw())){//主管单位
				con.add("sysOrganizationByDirectorunitsid", Operator.ISNOTNULL,null);
				con.add("sysOrganizationByDirectorunitsid.organizationName", Operator.LIKE, sysParams.getZgdw());
			}
			if(StringUtils.isNotEmpty(sysParams.getXqId())){//所属区域
				con.add("sysXq", Operator.ISNOTNULL, null);
				con.add("sysXq.xqId", Operator.IN, Arrays.asList(sysParams.getXqId().split(",")));
			}
			if (StringUtils.isNotEmpty(sysParams.getDeptname())) {//当前处理部门
				con.add("nextauditdeptname", Operator.LIKE, sysParams.getDeptname());
			}
			if (StringUtils.isNotEmpty(sysParams.getXmlx())) {//项目类型
				con.add("xmsbXmlx", Operator.ISNOTNULL, null);
				con.add("xmsbXmlx.xmlxId", Operator.IN, Arrays.asList(sysParams.getXmlx().split(",")));
			}
			if (StringUtils.isNotEmpty(sysParams.getHylb())) {//行业类别
				con.add("xmsbHylb", Operator.ISNOTNULL, null);
				con.add("xmsbHylb.hylbId", Operator.IN, Arrays.asList(sysParams.getHylb().split(",")));
			}
			if (StringUtils.isNotEmpty(sysParams.getXmzt())) {//项目状态
				con.add("pjstatus", Operator.ISNOTNULL, null);
				con.add("pjstatus", Operator.IN, Arrays.asList(sysParams.getXmzt().split(",")));
			}
		}
		return con;
	}
	
	public Sort initPjbaseinfoSort() {
		Order order1 = new Order(Direction.ASC, "pjstatus");
		Order order2 = new Order(Direction.DESC, "declartime");
		Order order3 = new Order(Direction.DESC, "projectid");
		return new Sort(order1, order2,order3);
	}
	
	
	

}
