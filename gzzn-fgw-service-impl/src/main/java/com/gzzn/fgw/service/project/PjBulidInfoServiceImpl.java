package com.gzzn.fgw.service.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.jdbc.IJdbcDao;
import com.gzzn.fgw.model.PjBulidInfo;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.project.pojo.PjBulidInfoStatPojo;
import com.gzzn.fgw.service.project.pojo.PjplanQueryParam;
import com.gzzn.fgw.util.IConstants;

/**
 * <p>Title: IPjBulidInfoService</p>
 * <p>Description:  区县项目情况实现类</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-06-05 lhq  new
 */
@Service
@Transactional
public class PjBulidInfoServiceImpl extends AbstractService<PjBulidInfo>
		implements IPjBulidInfoService {

	@Resource
	private IEntityDao entityDao;
	@Resource
	private IJdbcDao jdbcDao;
	
	
	@Override
	public List<PjBulidInfoStatPojo> findStatByXQ(List<SysXq> listXq, PjplanQueryParam params) {
		//获取按产业分类统计好了的数据
		//List<Map<String, Object>> listMap = findListBySQL("xqId", null, params);
		List<Map<String, Object>> listMap = findListBySQL("xmsd", null, params);
		
		List<PjBulidInfoStatPojo> list = new ArrayList<PjBulidInfoStatPojo>();
		PjBulidInfoStatPojo obj = null;
		Map<String,Object> map = null;
		Object xmsd = null;
		for(SysXq objXq : listXq){
			obj = new PjBulidInfoStatPojo(objXq);
			Iterator<Map<String,Object>> iteMap = listMap.iterator();
			while (iteMap.hasNext()) {
				map = iteMap.next();
				xmsd = map.get("xmsd");
				//if(map.get("xqId")!=null && objXq.getXqId().equals(Integer.valueOf(map.get("xqId").toString()))){
				if(xmsd!=null && objXq.getXqmc().equals(xmsd.toString())){
					 statMapToObj(obj, map);//将Map的数据统计至Obj中
					 iteMap.remove();
				}
			}
			list.add(obj);
		}
		return list;
	}
	
	@Override
	public PjBulidInfoStatPojo findStatByTotal(List<SysDictionaryitems> listCylb, PjplanQueryParam params) {
		//获取按产业分类统计好了的数据
		List<Map<String, Object>> listMap = findListBySQL("cylb", null, params);
		
		PjBulidInfoStatPojo obj = new PjBulidInfoStatPojo("合计", "合计");;
		for(Map<String, Object> map : listMap){
			statMapToObjTotal(obj, map);
		}
		
		return obj;
	}

	@Override
	public List<PjBulidInfoStatPojo> findStatByCylb(List<SysDictionaryitems> listCylb, PjplanQueryParam params) {
		//获取按产业分类统计好了的数据
		List<Map<String, Object>> listMap = findListBySQL("cylb", null, params);
		
		List<PjBulidInfoStatPojo> list = new ArrayList<PjBulidInfoStatPojo>();
		PjBulidInfoStatPojo obj = null;
		Map<String,Object> map = null;
		Object cylb = null;
		for(SysDictionaryitems objCylb: listCylb){
			obj = new PjBulidInfoStatPojo("按产业分类", objCylb.getItemtext());
			Iterator<Map<String,Object>> iteMap = listMap.iterator();
			while (iteMap.hasNext()) {
				map = iteMap.next();
				cylb = map.get("cylb");
				if(cylb!=null && objCylb.getItemtext().equalsIgnoreCase(cylb.toString())){
					 statMapToObj(obj, map);//将Map的数据统计至Obj中
					 iteMap.remove();
				}
			}
			list.add(obj);
		}
		return list;
	}

	@Override
	public List<PjBulidInfoStatPojo> findStatByTzlx(List<SysDictionaryitems> listTzlx, PjplanQueryParam params) {
		//获取”按投资额大小分类“统计好了的数据
		List<Map<String, Object>> listMap = findListBySQL("tzlx", null, params);
		
		List<PjBulidInfoStatPojo> list = new ArrayList<PjBulidInfoStatPojo>();
		PjBulidInfoStatPojo obj = null;
		Map<String,Object> map = null;
		Object tzlx = null;
		for(SysDictionaryitems objTzlx: listTzlx){
			obj = new PjBulidInfoStatPojo("按投资额大小分类", objTzlx.getItemtext());
			Iterator<Map<String,Object>> iteMap = listMap.iterator();
			while (iteMap.hasNext()) {
				map = iteMap.next();
				tzlx = map.get("tzlx");
				if(tzlx!=null && objTzlx.getItemtext().equalsIgnoreCase(tzlx.toString())){
					 statMapToObj(obj, map);//将Map的数据统计至Obj中
					 iteMap.remove();
				}
			}
			list.add(obj);
		}
		return list;
	}

	
	
	@Override
	public List<Map<String, Object>> findListBySQL(String type,List<SysDictionaryitems> list, PjplanQueryParam params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("  select  ").append(type).append(",");
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_0).append("', 1, 0)) lXNum, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_0).append("', pjinvestsum, 0)) lXPjinvestsum, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_0).append("', expectfinishinvest, 0)) lXExpectfinishinvest, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_1).append("', 1, 0)) wGNum, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_1).append("', expectfinishinvest, 0)) wGExpectfinishinvest, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_1).append("', currentfinishinvest, 0)) wGCurrentfinishinvest, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_2).append("', 1, 0)) zJNum, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_2).append("', pjinvestsum, 0)) zJPjinvestsum, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_2).append("', planinvestsum, 0)) zJPlaninvestsum, "); 
		sql.append("          SUM(decode(cc.remark, '").append(IConstants.XMLB_NAME_2).append("', currentfinishinvest, 0)) zJCurrentfinishinvest "); 
		sql.append("  from( "); 
		
		//获取内部基本数据查询
		//sql.append(this.getBasicSql(list, xqId, organizationId)); 
		sql.append(this.getBasicSql2(list, params));//xqId实际传输过来的是区县名称，格式如：广州市,越秀区,天河区,白云区
		
		sql.append("  )cc group by ").append(type); 
		
		return jdbcDao.queryForList(sql.toString());
	}
	
	/**
	 * 获取基本的sql
	 * @param list 从数据字典中获取的"投资额大小"数据
	 * @param xqId 县区ID,多个间使用,隔开
	 * @return
	 */
	private String getBasicSql2(List<SysDictionaryitems> list, PjplanQueryParam params){
		StringBuilder sql = new StringBuilder();
		
		sql.append("     select tbdw,xmsd,cylb,"); 
		sql.append("     		case "); 
		sql.append("     		 when pjinvestsum>=500000 then '50亿以上项目' "); 
		sql.append("     		 when  pjinvestsum>=300000 and pjinvestsum<500000   then '30-50亿项目' "); 
		sql.append("     		 when  pjinvestsum>=100000 and pjinvestsum<300000   then '10-30亿项目' "); 
		sql.append("     		 when  pjinvestsum>=10000 and pjinvestsum<100000   then '1-10亿项目' "); 
		sql.append("     		 when  pjinvestsum>=5000 and pjinvestsum<10000   then '5000万-1亿项目' "); 
		sql.append("     		 when  pjinvestsum>=1000 and pjinvestsum<5000   then '1000万-5000万项目' ");
		sql.append("     		 else  '其它' "); 
		sql.append("     		end tzlx, "); 
		sql.append("     		remark,pjinvestsum,expectfinishinvest,planinvestsum,currentfinishinvest ");
		sql.append("     from( "); 
							  //-- 计划总投资(亿元)、到2013年底累计完成投资、2014年计划投资(万元)、2014年以来完成投资(亿元) 
		sql.append("          select t.user_id,t.organization_id,t.tbdw,t.xmsd,t.cylb,t.remark,t.pjinvestsum,t.expectfinishinvest,t.planinvestsum,t.currentfinishinvest from  pj_bulid_info t where 1=1   "); 
		
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getXqmc())){//格式如：广州市,越秀区,天河区,白云区
				sql.append("     and t.xmsd in('").append(params.getXqmc().replaceAll(",", "','")).append("')");
			}
			if(StringUtils.isNotEmpty(params.getOrganizationId())){//填报单位ID
				sql.append("     and t.organization_id in(").append(params.getOrganizationId()).append(")");
			}
			if(StringUtils.isNotEmpty(params.getUserId())){//填报人ID
				sql.append("     and t.user_id in(").append(params.getUserId()).append(")");
			}
//			if(StringUtils.isNotEmpty(params.getPjinvestSumType())){//总投资类型：空、全部；1、千万以上；2、千万以下
//				if(params.getPjinvestSumType().equals("1")){
//					sql.append("     and t.pjinvestsum>=1000 ");
//				}else{
//					sql.append("     and t.pjinvestsum<1000 ");
//				}
//			}
			if(StringUtils.isNotEmpty(params.getPjinvestSumType())){//总投资类型：全部；1、1千万以下；2、1千万以上5千万以下；3.5千万以上
				if(params.getPjinvestSumType().equals("1")){//1000万以下
					sql.append("     and t.pjinvestsum<1000 ");
				}
				else if(params.getPjinvestSumType().equals("2")){//1000万以上5000万以下
					sql.append("     and t.pjinvestsum<5000 and t.pjinvestsum>=1000");
				}
				else{//5000万以上
					sql.append("     and t.pjinvestsum>=5000 ");
				}
			}
			if(StringUtils.isNotEmpty(params.getPfsj())){//批复年份
				sql.append(" and t.pfsj is not null and t.pfsj like '"+params.getPfsj()+"%'");
			}
		}
		
		sql.append("        ) "); 
		
		return sql.toString();
	}
	
	/**
	 * 获取基本的sql
	 * @param list 从数据字典中获取的"投资额大小"数据
	 * @param xqId 县区ID,多个间使用,隔开
	 * @return
	 */
	private String getBasicSql(List<SysDictionaryitems> list, PjplanQueryParam params){
		StringBuilder sql = new StringBuilder();
		
		sql.append("     select bb.xq_id xqId,bb.xqmc,aa.cylb,"); 
		sql.append("     		case "); 
		sql.append("     		 when pjinvestsum>=500000 then '50亿以上项目' "); 
		sql.append("     		 when  pjinvestsum>=300000 and pjinvestsum<500000   then '30-50亿项目' "); 
		sql.append("     		 when  pjinvestsum>=100000 and pjinvestsum<300000   then '10-30亿项目' "); 
		sql.append("     		 when  pjinvestsum>=10000 and pjinvestsum<100000   then '1-10亿项目' "); 
		sql.append("     		 when  pjinvestsum>=5000 and pjinvestsum<10000   then '5000万-1亿项目' "); 
		sql.append("     		 when  pjinvestsum>=1000 and pjinvestsum<5000   then '1000万-5000万项目' "); 
		sql.append("     		 else  '其它' "); 
		sql.append("     		end tzlx, "); 
		
		/**if(list!=null && list.size()>0){
			sql.append("        case "); 
			for(SysDictionaryitems obj : list){
				if(obj.getMinvalue()!=null && obj.getMinvalue()>0){
					sql.append("      when pjinvestsum>").append(obj.getMinvalue());
					if(obj.getMaxvalue()!=null && obj.getMaxvalue()>0){
						sql.append("  and pjinvestsum<=").append(obj.getMaxvalue());
					}
					sql.append("      then ").append(obj.getItemtext());
				}
			}
			sql.append("        end tzlx, "); 
		}*/
		
		sql.append("     		aa.remark,aa.pjinvestsum,aa.expectfinishinvest,aa.planinvestsum,aa.currentfinishinvest ");
		sql.append("     from( "); 
							  //-- 计划总投资(亿元)、到2013年底累计完成投资、2014年计划投资(万元)、2014年以来完成投资(亿元) 
		sql.append("          select t.user_id,t.organization_id,t.cylb,t.remark,t.pjinvestsum,t.expectfinishinvest,t.planinvestsum,t.currentfinishinvest from  pj_bulid_info t where 1=1 "); 
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getOrganizationId())){//填报单位ID
				sql.append("     and t.organization_id in(").append(params.getOrganizationId()).append(")");;
			}
			if(StringUtils.isNotEmpty(params.getUserId())){//填报人ID
				sql.append("     and t.user_id in(").append(params.getUserId()).append(")");;
			}
		}
		sql.append("     )aa left join( "); 
		sql.append("          select g.organization_id,g.organization_name,q.xq_id,q.xqmc from sys_organization g left join sys_xq q on g.xq_id=q.xq_id "); 
		sql.append("     )bb on aa.organization_id=bb.organization_id where 1=1"); 
		
		
		if(params!=null){
			if(StringUtils.isNotEmpty(params.getXqId())){//区县ID
				sql.append("     and bb.xq_id in(").append(params.getXqId()).append(")");
			}
			if(StringUtils.isNotEmpty(params.getXqmc())){//格式如：广州市,越秀区,天河区,白云区
				sql.append("     and t.xmsd in('").append(params.getXqmc().replaceAll(",", "','")).append("')");
			}
		}
		
		return sql.toString();
	}
	

	/**
	 * 将Map的数据统计至Obj中
	 * @param obj
	 * @param map
	 */
	private void statMapToObj(PjBulidInfoStatPojo obj ,Map<String,Object> map){
		 obj.setlXNum(((Number)map.get("lXNum")).intValue());
		 obj.setlXPjinvestsum(((Number)map.get("lXPjinvestsum")).doubleValue()*0.0001);
		 obj.setlXExpectfinishinvest(((Number)map.get("lXExpectfinishinvest")).doubleValue()*0.0001);
		 
		 obj.setwGNum(((Number)map.get("wGNum")).intValue());
		 obj.setwGExpectfinishinvest(((Number)map.get("wGExpectfinishinvest")).doubleValue()*0.0001);
		 obj.setwGCurrentfinishinvest(((Number)map.get("wGCurrentfinishinvest")).doubleValue()*0.0001);
		 
		 obj.setzJNum(((Number)map.get("zJNum")).intValue());
		 obj.setzJPjinvestsum(((Number)map.get("zJPjinvestsum")).doubleValue()*0.0001);
		 obj.setzJPlaninvestsum(((Number)map.get("zJPlaninvestsum")).doubleValue()*0.0001);
		 obj.setzJCurrentfinishinvest(((Number)map.get("zJCurrentfinishinvest")).doubleValue()*0.0001);
	}
	
	/**
	 * 将Map的数据统计至Obj中
	 * @param obj
	 * @param map
	 */
	private void statMapToObjTotal(PjBulidInfoStatPojo obj ,Map<String,Object> map){
		 obj.setlXNum(obj.getlXNum() + ((Number)map.get("lXNum")).intValue());
		 obj.setlXPjinvestsum(obj.getlXPjinvestsum() + ((Number)map.get("lXPjinvestsum")).doubleValue()*0.0001);
		 obj.setlXExpectfinishinvest(obj.getlXExpectfinishinvest() + ((Number)map.get("lXExpectfinishinvest")).doubleValue()*0.0001);
		 
		 obj.setwGNum(obj.getwGNum() + ((Number)map.get("wGNum")).intValue());
		 obj.setwGExpectfinishinvest(obj.getwGExpectfinishinvest() + ((Number)map.get("wGExpectfinishinvest")).doubleValue()*0.0001);
		 obj.setwGCurrentfinishinvest(obj.getwGCurrentfinishinvest() + ((Number)map.get("wGCurrentfinishinvest")).doubleValue()*0.0001);
		 
		 obj.setzJNum(obj.getzJNum() + ((Number)map.get("zJNum")).intValue());
		 obj.setzJPjinvestsum(obj.getzJPjinvestsum() + ((Number)map.get("zJPjinvestsum")).doubleValue()*0.0001);
		 obj.setzJPlaninvestsum(obj.getzJPlaninvestsum() + ((Number)map.get("zJPlaninvestsum")).doubleValue()*0.0001);
		 obj.setzJCurrentfinishinvest(obj.getzJCurrentfinishinvest() + ((Number)map.get("zJCurrentfinishinvest")).doubleValue()*0.0001);
	}

	@Override
	public Map<String, Object> findIndusGBcode(String nameOrCode) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select t.indus_id,t.indus_name,t.indus_code,t.indus_level ");
		sql.append(" from indus_class_gbcode t where t.indus_name=? or t.indus_code=? ");
		
		List<Map<String, Object>> list = jdbcDao.queryForList(sql.toString(), new Object[]{nameOrCode, nameOrCode});
		
		return list!=null && list.size()>0?list.get(0):null;
	}
	
	

}
