package com.gzzn.fgw.service.project;

import java.util.List;
import java.util.Map;

import com.gzzn.fgw.model.PjBulidInfo;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.service.project.pojo.PjBulidInfoStatPojo;
import com.gzzn.fgw.service.project.pojo.PjplanQueryParam;

/**
 * <p>Title: IPjBulidInfoService</p>
 * <p>Description:  区县项目情况维护接口</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-06-05 lhq  new
 */
public interface IPjBulidInfoService extends IPagedService<PjBulidInfo> {
	
	/**
	 * 按区县地区统计：返回的是组装后的POJO
	 * @param listXq
	 * @return
	 */
	public List<PjBulidInfoStatPojo> findStatByXQ(List<SysXq> listXq, PjplanQueryParam params);
	
	/**
	 * 获取类别的合计值：返回的是组装后的POJO
	 * @param listCylb
	 * @param xqId
	 * @return
	 */
	public PjBulidInfoStatPojo findStatByTotal(List<SysDictionaryitems> listCylb, PjplanQueryParam params);
	
	/**
	 * 按产业分类统计：返回的是组装后的POJO
	 * @param listCylb
	 * @param xqId
	 * @return
	 */
	public List<PjBulidInfoStatPojo> findStatByCylb(List<SysDictionaryitems> listCylb, PjplanQueryParam params);
	
	
	/**
	 * 按投资额大小分类统计：返回的是组装后的POJO
	 * @param listTzlx
	 * @param xqId
	 * @return
	 */
	public List<PjBulidInfoStatPojo> findStatByTzlx(List<SysDictionaryitems> listTzlx, PjplanQueryParam params);
	
	
	/**
	 * 根据sql查询统计数据 
	 * @param type 类型：xqId - 区县统计、cylb -按产业分类、tzlx - 按投资额大小分类
	 * @param list 当type=tzlx时，传入数据字典中的“投资额大小”数据
	 * @param xqId 过滤县区id信息
	 * @return
	 */
	public List<Map<String,Object>> findListBySQL(String type,List<SysDictionaryitems> list, PjplanQueryParam params);
	
	
	/**
	 * 查找行业分类（国标行业）编码
	 * @param nameOrCode
	 * @return
	 */
	public Map<String, Object> findIndusGBcode(String nameOrCode);
	

}
