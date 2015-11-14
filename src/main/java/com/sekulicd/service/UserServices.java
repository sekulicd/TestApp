package com.sekulicd.service;

import java.util.List;

import com.sekulicd.domain.*;

public interface UserServices {
	List<User> getAllUsers();
	void add(User u);
	User find(String id);
}
