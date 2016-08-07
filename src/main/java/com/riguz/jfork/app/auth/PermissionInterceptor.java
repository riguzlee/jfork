package com.riguz.jfork.app.auth;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.riguz.commons.auth.ServletKit;
import com.riguz.commons.auth.User;
import com.riguz.commons.auth.UserContext;
import com.riguz.jfork.app.ajax.ResponseKit;

public class PermissionInterceptor implements Interceptor{
	private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class.getName());
	private Prop config;
	private Security security = new GroupedSecurity();

	public PermissionInterceptor(String properties){
		this.config = PropKit.use(properties);
	}

	private String getLoginUrl(){
		String url = this.config.get("loginUrl", "login");
		return url;
	}

	private Set<String> joinPermission(String ...permissions){
		Set<String> permissionList = new HashSet<String>();
		for(String p:permissions){
			if(Strings.isNullOrEmpty(p))
				continue;
			for(String item:p.split(","))
				permissionList.add(item);
		}
		return permissionList;
	}

	private Set<String> getPermission(String controllerKey, String actionKey){
		String actionPermission = this.config.get(actionKey);
		String controllerPermission = this.config.get(controllerKey);
		String rootPermission = this.config.get("/");
		if(!Strings.isNullOrEmpty(actionPermission)){
			Set<String> permissionList = new HashSet<String>();
			//if the permission starts with @, then donot check the controller or root permission
			if(actionPermission.startsWith("@") && actionPermission.length() > 1){
				permissionList.add(actionPermission.substring(1));
				return permissionList;
			}
			//anonymous
			if(actionPermission.equals("*"))
				return permissionList;
		}

		//otherwise return a list of all permissions
		return this.joinPermission(rootPermission, controllerPermission, actionPermission);
	}

	private void handle403(Controller controller){
		if(ServletKit.isAjax(controller.getRequest()))
			ResponseKit.error(controller, "Permission denied.");
		else{
			if(!UserContext.isUserAuthed()){
				controller.redirect(this.getLoginUrl());
				return;
			}
			controller.renderError(403);
		}
	}

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Set<String> permissions = this.getPermission(inv.getControllerKey(), inv.getActionKey());
		logger.debug("=>Checking permission {}", permissions);
		User user = UserContext.getCurrentUser();
		boolean passed = true;
		for(String p:permissions){
			if(!this.security.hasPermission(p, user)){
				passed = false;
				logger.debug("No permission:{}, user {}", p, user);
			}
		}
		if(passed)
			inv.invoke();
		else
			this.handle403(controller);
	}

}
