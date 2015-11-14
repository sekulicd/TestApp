package com.sekulicd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sekulicd.domain.User;
import com.sekulicd.service.UserServices;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServices userService;
	
	@RequestMapping(value ="/list", produces =MediaType.APPLICATION_JSON_VALUE )
	public List<User> getAll()
	{	
		return userService.getAllUsers();
	}
	
	@RequestMapping("/{id}")
	public User getUserById(@PathVariable String id)
	{	
		return userService.find(id);
	}
	
	@RequestMapping("/save/{username}/{firstName}/{lastName}")
	public void saveUser(@PathVariable String username, @PathVariable String firstName, @PathVariable String lastName)
	{	
		User newUser = new User(username,firstName,lastName);
		userService.add(newUser);
	}
	
//	@RequestMapping("/test/{id}")
//	public ResponseEntity<User> test(@PathVariable String id)
//	{	
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.set("Access-Control-Allow-Origin", "http://localhost");
//		return new ResponseEntity<User>(userService.find(id), responseHeaders, HttpStatus.CREATED);
//	}

}
