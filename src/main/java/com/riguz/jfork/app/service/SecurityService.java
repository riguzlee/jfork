package com.riguz.jfork.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Sqls;
import com.riguz.commons.auth.User;

public class SecurityService {
	private static Logger logger = LoggerFactory.getLogger(SecurityService.class.getName());

	public List<String> findSecurityGroupByUserId(String userId){
		return Db.query(Sqls.get("getUserLoginSecurityGroupByUserId"), userId);
	}

	public List<String> findPermissionsBySecurityGroup(String groupId){
		return Db.query(Sqls.get("getPermissionsBySecurityGroup"), groupId);
	}

	public boolean hasPermission(String permissionId, String groupId){
		if(Strings.isNullOrEmpty(groupId))
			return false;
		List<String> result = Db.query(Sqls.get("getPermissionsByGroupIdAndPermissionId"), groupId, permissionId);
		return !result.isEmpty();
	}

	public boolean hasPermission(String permissionId, User user){
		if(user == null)
			return false;
		List<String> result = Db.query(Sqls.get("getPermissionsByUserId"), user.getUserId(), permissionId);
		return !result.isEmpty();
	}
}
