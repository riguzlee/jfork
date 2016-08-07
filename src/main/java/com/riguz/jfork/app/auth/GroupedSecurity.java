package com.riguz.jfork.app.auth;

import java.util.List;

import com.riguz.commons.auth.User;
import com.riguz.jfork.app.service.SecurityService;

public class GroupedSecurity implements Security{
	private SecurityService securityService = new SecurityService();
	public static final String ADMIN_ACTION = "_ADMIN";

	@Override
	public List<String> findUserLoginSecurityGroup(String userId) {
		return this.securityService.findSecurityGroupByUserId(userId);
	}

	@Override
	public boolean hasPermission(String permission, User user) {
		return this.securityService.hasPermission(permission, user);
	}

	@Override
	public boolean hasEntityPermission(String entity, String action, User user) {
		String adminPermission = entity + ADMIN_ACTION;
		String permission = entity + action;
		return this.hasPermission(adminPermission, user) || this.hasPermission(permission, user);
	}

}
