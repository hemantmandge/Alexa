package com.ge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Value("${security.user.name}")
	private String serviceUser;
	
	@Value("${security.user.password}")
	private String servicePassword;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		  String username = authentication.getName().trim();
	      String password = (String) authentication.getCredentials();
	 
	 
	        if (username == null || !serviceUser.equalsIgnoreCase(username.trim())) {
	            throw new BadCredentialsException("Username not found.");
	        }
	 
	        if (!servicePassword.equals(password.trim())) {
	            throw new BadCredentialsException("Wrong password.");
	        }
	 
	        //Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ANOTHER");
	        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
	        updatedAuthorities.add(authority);
	        return new UsernamePasswordAuthenticationToken(new User(serviceUser, servicePassword, updatedAuthorities), authentication.getCredentials());
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}