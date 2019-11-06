package com.spring.security.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {

	public User(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private Map<String, List<String>> bookingEntityPermission;

	public Map<String, List<String>> getBookingEntityPermission() {
		return bookingEntityPermission;
	}

	public void setBookingEntityPermission(Map<String, List<String>> bookingEntityPermission) {
		this.bookingEntityPermission = bookingEntityPermission;
	}

	public Map<String, List<String>> getLegalEntityPermission() {
		return legalEntityPermission;
	}

	public void setLegalEntityPermission(Map<String, List<String>> legalEntityPermission) {
		this.legalEntityPermission = legalEntityPermission;
	}

	private Map<String, List<String>> legalEntityPermission;

}
