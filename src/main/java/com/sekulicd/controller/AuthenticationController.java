package com.sekulicd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sekulicd.domain.UserT;
import com.sekulicd.security.TokenResponse;
import com.sekulicd.service.UserTServices;

@RestController
public class AuthenticationController {
	
	@Autowired
	private UserTServices userTServices;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestBody UserT userT) {
		if(!userTExist(userT))
		{
			userTServices.add(userT);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			TokenResponse tokenResponse = new TokenResponse(authentication.getDetails().toString());
			return tokenResponse.getToken();
		}
		return "Account already exist";
	}
	 
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody String logIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		TokenResponse tokenResponse = new TokenResponse(authentication.getDetails().toString());
		return tokenResponse.getToken();
	}
	
	public boolean userTExist(UserT userT)
	{
		UserT user = userTServices.find(userT.getUsername());
		if(user != null)
		{
			return true;
		}
		return false;
	}
}
