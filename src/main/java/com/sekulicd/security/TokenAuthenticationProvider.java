package com.sekulicd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.sekulicd.util.StringUtil;

@Component("tokenAuthenticationProvider")
public class TokenAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private TokenService tokenService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
		// TODO Auto-generated method stub
		String token = (String) authentication.getPrincipal();
		if (StringUtil.isStringNullOrEmpty(token)) 
		{
          throw new BadCredentialsException("Invalid token");
        }
        if (!tokenService.contains(token)) 
        {
          throw new BadCredentialsException("Invalid token or token expired");
        }
		return tokenService.retrieve(token);
	}

	@Override
	public boolean supports(Class<?> authentication) 
	{
		return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}
	
}