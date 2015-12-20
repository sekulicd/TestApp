package com.sekulicd.service;

import java.util.List;

import com.sekulicd.domain.*;

public interface UserTServices {
	List<UserT> getAllUserTs();
	void add(UserT u);
	UserT find(String id);
}
