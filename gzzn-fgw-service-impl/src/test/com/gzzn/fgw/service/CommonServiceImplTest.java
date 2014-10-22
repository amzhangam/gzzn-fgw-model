package com.gzzn.fgw.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.model.SysUser;

@TransactionConfiguration(defaultRollback=false)
public class CommonServiceImplTest extends SpringTest{
	
	@Resource
	private ICommonService commonService;
	
//	@Test
	public void testSaveT() {
		
	}

//	@Test
	public void testSaveIterableOfT() {
		
	}

//	@Test
	public void testUpdate() {
		
	}

	@Test
	public void testSaveOrUpdate() {
//		SysUser user = new SysUser();
//		user.setLoginName("yang");
//		user.setLoginPwd("abc334324");
//		commonService.saveOrUpdate(user);
	}

//	@Test
	public void testFindOneClassOfTSerializable() {
		
	}

//	@Test
	public void testFindAll() {
		
	}

//	@Test
	public void testDeleteClassOfTSerializable() {
		
	}

	@Test
	public void testDeleteT() {
//		List<Integer> idList = new ArrayList<Integer>();
//		idList.add(1l);
//		idList.add(2l);
//		Integer [] ids = new Integer[]{24L,25L};
//		commonService.delete(SysUser.class, 26L);
	}

//	@Test
	public void testDeleteIterableOfQextendsT() {
		
	}

//	@Test
	public void testDeleteAll() {
		
	}

//	@Test
	public void testCountClassOfT() {
		
	}

//	@Test
	public void testCountClassOfTCondition() {
		
	}

//	@Test
	public void testFindClassOfTCondition() {
		
	}

//	@Test
	public void testFindClassOfTConditionSort() {
		
	}

//	@Test
	public void testFindClassOfTConditionIntInt() {
		
	}

//	@Test
	public void testFindClassOfTConditionSortIntInt() {
		
	}

//	@Test
	public void testFindOneClassOfTCondition() {
		
	}

//	@Test
	public void testFindOneClassOfTStringObject() {
		
	}

}
