package com.comparison.service.service;

import java.util.List;

import com.comparison.service.model.User;

public interface UserService {
	User addUser(User user);
	List<User> getAllUser();
}
