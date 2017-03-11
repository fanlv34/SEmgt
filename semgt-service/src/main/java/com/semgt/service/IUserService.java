package com.semgt.service;

import com.semgt.model.User;

public interface IUserService {
	User qryUserByUsername(String username);

	User qryUserById(String userId);

	void addUser(User user);
	
	void updLoginTime(Integer userId);

}
