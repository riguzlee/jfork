package com.riguz.jfork.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSecurityPermission<M extends BaseSecurityPermission<M>> extends Model<M> implements IBean {

	public void setPermissionId(java.lang.String permissionId) {
		set("permission_id", permissionId);
	}

	public java.lang.String getPermissionId() {
		return get("permission_id");
	}

	public void setDynamicAccess(java.lang.String dynamicAccess) {
		set("dynamic_access", dynamicAccess);
	}

	public java.lang.String getDynamicAccess() {
		return get("dynamic_access");
	}

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

}