package com.semgt.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.semgt.base.BaseService;
import com.semgt.dao.IUserDao;
import com.semgt.model.User;

@Service("userService")
public class UserServiceImpl extends BaseService implements IUserService{
	@Resource(name = "userDao")
	private IUserDao userDao;

	public User qryUserByUsername(String username) {
		return userDao.qryUserByUsername(username);
	}
	
	public User qryUserById(String userId) {
		return userDao.qryUser(userId);
	}

	public void addUser(User user) {
		userDao.addUser(user);
	}

	public void updLoginTime(Integer userId) {
		userDao.updLoginTime(userId);
	}
}
