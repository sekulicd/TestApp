package com.sekulicd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import com.sekulicd.domain.UserT;
import com.sekulicd.service.UserTServices;
import com.sekulicd.util.StringUtil;

@Component("usernamePasswordAuthenticationProvider")
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private TokenService tokenService;
	
	@Autowired
	UserTServices userTServices;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        if (StringUtil.isStringNullOrEmpty(username) || StringUtil.isStringNullOrEmpty(password)) {
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        UserT userT = userTServices.find(username);
        if(userT != null)
        {
        	if(!userT.getPassword().equals(password))
			{
        		throw new BadCredentialsException("Wrong Password");
			}
        }
        AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(username,password,null);
        
        String newToken = tokenService.generateNewToken();
        resultOfAuthentication.setToken(newToken);
        tokenService.store(newToken, resultOfAuthentication);
        return resultOfAuthentication;
    }

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}