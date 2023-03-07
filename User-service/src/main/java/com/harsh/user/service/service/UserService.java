package com.harsh.user.service.service;

import java.util.List;

import com.harsh.user.service.entities.User;

public interface UserService {
	
	User saveUser(User user);
	List<User> getAllUsers();
	User getUser(String userId);
	User updateUser(String userId, User user);
	void deleteUser(String userId);

}
