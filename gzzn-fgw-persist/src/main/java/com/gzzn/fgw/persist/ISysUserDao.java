package com.gzzn.fgw.persist;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gzzn.fgw.model.SysUser;

public interface ISysUserDao extends CrudRepository<SysUser, Integer> {

	public List<SysUser> findByLoginNameAndLoginPwd(String loginName, String loginPwd);

}
