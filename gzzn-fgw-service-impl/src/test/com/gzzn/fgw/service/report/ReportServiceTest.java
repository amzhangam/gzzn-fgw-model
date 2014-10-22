package com.gzzn.fgw.service.report;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.fgw.model.pojo.ReportPojo;
import com.gzzn.fgw.service.SpringTest;
import com.gzzn.util.web.PageUtil;

/**
 * <p>com.gzzn.fgw.expExcel.ReportExpExcel类的测试类</p>
 * <i>Copyright (c) 2014 ITDCL  All right reserved.</i> 
 * @author lzq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-25 下午5:51:28 lzq  new
 */
@Transactional
public class ReportServiceTest extends SpringTest { 
	@Resource
	private IReportService reportService;	
	
	/**
	 * 
	 * 测试findListTest()
	 * 
	 */
	@Test
	public void findListTest(){ 
		PageUtil<ReportPojo> page=new PageUtil<ReportPojo>();
		reportService.findList(page, null,null);
		System.out.println(page.getList().size()); 
	}
	/**
	 * 
	 * 测试findListTest1()
	 * 
	 */
	@Test
	public void findListTest1(){ 
		PageUtil<ReportPojo> page=new PageUtil<ReportPojo>();
		reportService.findList(page, null,null);
		for(ReportPojo obj:page.getList()){
			System.out.println(obj.getPjStatus()+obj.getPjinvestAdvice());
		}
		System.out.println(page.getList().size()); 
	}
	 
	 
}
