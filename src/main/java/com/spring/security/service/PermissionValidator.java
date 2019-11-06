package com.spring.security.service;

import com.spring.security.dto.Employee;

/**
 * All the interface method can be used in pre/post authorize spring security
 * annotation
 * 
 * @author Karthee
 *
 */
public interface PermissionValidator {

	public boolean isMember(Employee OrganizationId, String type, String role);

}
