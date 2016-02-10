package com.sekulicd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sekulicd.domain.UserT;
import com.sekulicd.service.UserTServices;

@RestController
@RequestMapping("/user")
public class UserTController {
	
	@Autowired
	private UserTServices userTServices;
	
	@RequestMapping(value ="/list", produces =MediaType.APPLICATION_JSON_VALUE )
	public List<UserT> getAll()
	{	
		return userTServices.getAll();
	}
	
	@RequestMapping("/{id}")
	public UserT getUserById(@PathVariable String id)
	{	
		return userTServices.find(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String updateUser(@RequestBody UserT userT) {
		return userTServices.update(userT) ? "Updated" : "Not Updated";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable String id) {
		userTServices.delete(id);
		return "User " + id + " deleted";
	}
	
	@RequestMapping("/singUp/{username}/{firstName}/{lastName}/{password}")
	public void singUp(@PathVariable String username, @PathVariable String firstName, @PathVariable String lastName, @PathVariable String password)
	{	
		UserT newUser = new UserT(username,firstName,lastName, password);
		userTServices.save(newUser);
	}

}
