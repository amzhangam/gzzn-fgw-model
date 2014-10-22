package com.gzzn.fgw.service.sys;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.SysProjectlogPojo;
import com.gzzn.util.web.PageUtil;


/**
 * <p>Title: SysProjectlogServiceImpl</p>
 * <p>Description: 项目日志实现类  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-13 上午9:38:49 amzhang  new
 */
@Service
@Transactional
public class SysProjectlogServiceImpl extends AbstractService<SysProjectlogPojo> implements
		ISysProjectlogService {

	@Resource
	private IEntityDao entityDao;
	
	//读取用户、读取用户单位信息
	private static Map<Integer, SysUser> readerUserCache = new HashMap<Integer, SysUser>();
	private static Map<Integer, SysOrganization> readerOrgCache = new HashMap<Integer, SysOrganization>();
		
	
	public PageUtil<SysProjectlogPojo> findList(
			PageUtil<SysProjectlogPojo> page, Condition con, Sort sort) {
		page.setCount((int) entityDao.count(SysProjectlog.class, con));//记录总条数
		page.setList(getLogPojoList(entityDao.find(SysProjectlog.class, con, sort,page.getOffset(),page.getSize())));
		return page;
	}

	@Override
	public List<SysProjectlogPojo> getLogPojoList(List<SysProjectlog> list) {
		List<SysProjectlogPojo> listPojo = new ArrayList<SysProjectlogPojo>();
		SysProjectlogPojo objPojo = null; 
		
		if(list!=null && list.size()>0){
			for(SysProjectlog obj :list){
				objPojo = new SysProjectlogPojo();
				PojoCopyUtil.copySameTypeField(obj, objPojo);//类型相同的对象进行复制
				//操作内容
				if(obj.getOperationContent()!=null){
					try {
						objPojo.setOperationContent(new String(obj.getOperationContent(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				//读取用户ID
				if(obj.getReaderId()!=null && obj.getReaderId()>0){
					objPojo.setReadUserObj(this.getReaderUserObj(obj.getReaderId()));
				}
				//读取用户单位ID号
				if(obj.getReaderOrgId()!=null && obj.getReaderOrgId()>0){
					objPojo.setReadOrgObj(this.getReaderOrgObj(obj.getReaderOrgId())); 
				}
				listPojo.add(objPojo);
			}
			readerUserCache.clear();//使用完成后，需要清除
			readerOrgCache.clear();//使用完成后，需要清除
		}
		return listPojo;
	}
	
	/**
	 * 根据用户id查找用户信息，放入缓存中
	 * @param readerId
	 * @return
	 */
	private SysUser getReaderUserObj(Integer readerId) {
		if(readerUserCache.containsKey(readerId)){
			return readerUserCache.get(readerId);
		}
		SysUser obj = entityDao.findOne(SysUser.class, readerId);
		if(obj != null){
			readerUserCache.put(readerId, obj);
		}
		
		return obj;
	}
	
	/**
	 * 根据单位id查找单位信息，放入缓存中
	 * @param readerOrgId
	 * @return
	 */
	private SysOrganization getReaderOrgObj(Integer readerOrgId) {
		if(readerOrgCache.containsKey(readerOrgId)){
			return readerOrgCache.get(readerOrgId);
		}
		SysOrganization obj = entityDao.findOne(SysOrganization.class, readerOrgId);
		if(obj != null){
			readerOrgCache.put(readerOrgId, obj);
		}
		
		return obj;
	}
	
	
	


}
