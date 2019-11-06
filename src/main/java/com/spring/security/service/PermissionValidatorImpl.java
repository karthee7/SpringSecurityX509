package com.spring.security.service;

import org.springframework.security.core.Authentication;

import com.spring.security.configuration.CustomMethodSecurityExpressionRoot;
import com.spring.security.dto.Employee;

public class PermissionValidatorImpl extends CustomMethodSecurityExpressionRoot implements PermissionValidator {

	public PermissionValidatorImpl(Authentication authentication) {
		super(authentication);
	}

	@Override
	public boolean isMember(Employee OrganizationId, String type, String role) {
		// logic to be implemented for autorization
		return true;
	}

}
