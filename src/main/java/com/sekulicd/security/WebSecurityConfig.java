package com.sekulicd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//http://stackoverflow.com/questions/549/the-definitive-guide-to-form-based-website-authentication?rq=1
@EnableWebMvcSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
  @Autowired
  TokenAuthenticationProvider tokenAuthenticationProvider;
  @Autowired
  UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
  @Autowired
  CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  @Autowired
  AuthenticationFilter authenticationFilter;
 
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  http.
      csrf().disable().
      sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
      and().
      authorizeRequests().antMatchers("/signup/**").permitAll().anyRequest().authenticated().
      and().
      exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
	  http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);
	  //http.authenticationProvider(tokenAuthenticationProvider).authenticationProvider(usernamePasswordAuthenticationProvider);
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(tokenAuthenticationProvider).authenticationProvider(usernamePasswordAuthenticationProvider);
  }  
  
  @Bean(name="authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }
  
}