package com.sekulicd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.couchbase.client.protocol.views.Query;
import com.sekulicd.domain.User;
import com.sekulicd.repo.UserRepository;

@Component("userService")
public class UserServiceImpl implements UserServices{

    @Autowired
    private UserRepository userepository;
    
	public UserRepository getUserepository() {
		return userepository;
	}

	public void setUserepository(UserRepository userepository) {
		this.userepository = userepository;
	}

	@Override
	public List<User> getAllUsers() {
//		User a = new User("a", "a", "a");
//		User b = new User("b", "b", "b");
//		List<User> list = new ArrayList<User>();
//		list.add(a);
//		list.add(b);
		Query query = new Query();
        query.setLimit(20);
        return userepository.all(query);
//		return list;
	}

	@Override
	public void add(User u) {
		userepository.save(u);
	}

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		return userepository.findOne(id);
	}

}
