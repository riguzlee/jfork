package com.riguz.jfork.app.auth;

import java.util.List;

import com.riguz.commons.auth.User;

public interface Security {
	public List<String> findUserLoginSecurityGroup(String userId);
	public boolean hasPermission(String permission, User user);
	public boolean hasEntityPermission(String entity, String action, User user);
}
