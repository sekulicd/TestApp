package com.sekulicd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.couchbase.client.protocol.views.Query;
import com.sekulicd.domain.UserT;
import com.sekulicd.repo.UserTRepository;

@Component("userTServiceImpl")
public class UserTServiceImpl implements UserTServices {

	@Autowired
	private UserTRepository userTRepository;

	public UserTRepository getUserTRepository() {
		return userTRepository;
	}

	public void setUserTRepository(UserTRepository userTRepository) {
		this.userTRepository = userTRepository;
	}

	@Override
	public List<UserT> getAll() {
		Query query = new Query();
		query.setLimit(20);
		return userTRepository.all(query);
	}

	@Override
	public void save(UserT u) {
		userTRepository.save(u);
	}

	@Override
	public UserT find(String id) {
		// TODO Auto-generated method stub
		return userTRepository.findOne(id);
	}

	@Override
	public void delete(String id) {
		userTRepository.delete(id);
	}

	@Override
	public boolean exist(String id) {
		UserT user = this.find(id);
		if(user != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean update(UserT userT) {
		UserT user = this.find(userT.getUsername());
		if(user != null)
		{
			this.save(userT);
			return true;
		}
		return false;
	}

}
