package com.sekulicd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.couchbase.client.protocol.views.Query;
import com.sekulicd.domain.UserT;
import com.sekulicd.repo.UserTRepository;

@Component("userTServiceImpl")
public class UserTServiceImpl implements UserTServices{

    @Autowired
    private UserTRepository userTRepository;
    
	public UserTRepository getUserTRepository() {
		return userTRepository;
	}

	public void setUserTRepository(UserTRepository userTRepository) {
		this.userTRepository = userTRepository;
	}

	@Override
	public List<UserT> getAllUserTs() {
		Query query = new Query();
        query.setLimit(20);
        return userTRepository.all(query);
	}

	@Override
	public void add(UserT u) {
		userTRepository.save(u);
	}

	@Override
	public UserT find(String id) {
		// TODO Auto-generated method stub
		return userTRepository.findOne(id);
	}

}
