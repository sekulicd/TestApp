package com.sekulicd.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekulicd.util.StringUtil;

@Component("authenticationFilter")
public class AuthenticationFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	static String SIGNUP_URL = "/signup";
	static String LOGIN_URL = "/login";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpServletRequest httpRequest = (HttpServletRequest)(request);
		 HttpServletResponse httpResponse = (HttpServletResponse)(response);
		 String username = httpRequest.getHeader("X-Auth-Username");
		 String password = httpRequest.getHeader("X-Auth-Password");
		 String token = httpRequest.getHeader("X-Auth-Token");
		 String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
		 try {
			//request to get token
	        if (requestContainsuserAndPass(username, password) & requestToSignUp(httpRequest,resourcePath) || requestToLogIn(httpRequest,resourcePath)) {
	            logger.debug("Trying to authenticate user by X-Auth-Token method. Token: {}", token);
	            processUsernamePasswordAuthentication(username, password);
	        }
	        
	        //request with token
	        if (requestWithToken(token)) {
	            logger.debug("Trying to authenticate user {} by X-Auth-Username method", username);
	            processTokenAuthentication(token);
	        }

//	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null);
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        logger.debug("AuthenticationFilter is passing request down the filter chain");
	        //addSessionContextToLogging();
	        chain.doFilter(request, response);
	    } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
	        SecurityContextHolder.clearContext();
	        logger.error("Internal authentication service exception", internalAuthenticationServiceException);
	        httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    } catch (AuthenticationException authenticationException) {
	        SecurityContextHolder.clearContext();
	        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
	    } finally {
//	        MDC.remove(TOKEN_SESSION_KEY);
//	        MDC.remove(USER_SESSION_KEY);
	    }
	}
	private void processUsernamePasswordAuthentication(String username, String password) throws IOException {
		UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication resultOfAuthentication = tryToAuthenticate(requestAuthentication);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }
	
	private void processTokenAuthentication(String token) {
		PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        Authentication resultOfAuthentication = tryToAuthenticate(requestAuthentication);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }
    
    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }
	
	private boolean requestToSignUp(HttpServletRequest httpRequest, String resourcePath) {
		return SIGNUP_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equals("POST");
	}
	
	private boolean requestToLogIn(HttpServletRequest httpRequest, String resourcePath) {
		return LOGIN_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equals("GET");
	}
	
	private boolean requestContainsuserAndPass(String username, String password) {
		if(!StringUtil.isStringNullOrEmpty(username) & !StringUtil.isStringNullOrEmpty(password))
        {
        	return true;
        }
		return false;
	}
	
	private boolean requestWithToken(String token)
	{
		if(!StringUtil.isStringNullOrEmpty(token))
        {
        	return true;
        }
		return false;
	}
}
