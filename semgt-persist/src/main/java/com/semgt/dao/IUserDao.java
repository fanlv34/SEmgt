package com.semgt.dao;

import org.springframework.stereotype.Repository;

import com.semgt.model.User;

@Repository("userDao")
public interface IUserDao {
	int addUser(User record);
	User qryUser(String userid);
	User qryUserByUsername(String username);
	void updLoginTime(Integer userId);
}