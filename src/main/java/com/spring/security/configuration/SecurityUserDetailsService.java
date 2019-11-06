package com.spring.security.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.dto.User;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

	private static Map<String, User> users = new HashMap<>();

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return users.get(username);
	}

	static {
		users.put("user1",
				new User("user1", "", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, new ArrayList<>()));
	}

}