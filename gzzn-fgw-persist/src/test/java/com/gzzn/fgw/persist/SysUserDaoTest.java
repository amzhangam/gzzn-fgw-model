package com.gzzn.fgw.persist;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gzzn.fgw.persist.ISysUserDao;
import com.gzzn.fgw.model.SysUser;
//import com.gzzn.util.security.Md5Encrypt;
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SysUserDaoTest extends SpringTest {

	@Autowired
	private ISysUserDao userDao;

	@Test
	public void crudUserTest() {
		Assert.notNull(userDao);

		SysUser user = new SysUser();
		user.setLoginName("loginName");
		user.setEmail("email");
		userDao.save(user);

		user = userDao.findOne(-1);
		Assert.notNull(user);
		Assert.isTrue("loginName".equals(user.getLoginName()));
		Assert.isTrue("email".equals(user.getEmail()));

		userDao.delete(user);
		user = userDao.findOne(-1);
		Assert.isNull(user);
	}

	@Test
	public void customMethodsTest() {
		Assert.notNull(userDao);

		SysUser user = new SysUser();
		user.setLoginName("yangjf");
		user.setEmail("email");
		userDao.save(user);

	}
	
	@Test
	public void testGetUserInfoByNamePwd() {
//		List<SysUser> list = userDao.findByLoginNameAndLoginPwd("gbjd-01", Md5Encrypt.getPassMD5("123456"));
//		System.out.println(list.toString());
	}

}
