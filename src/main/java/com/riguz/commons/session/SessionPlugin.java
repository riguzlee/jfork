package com.riguz.commons.session;

import com.jfinal.plugin.IPlugin;

public class SessionPlugin implements IPlugin{
	private SessionManager sessionManager;

	public SessionPlugin(SessionManager sessionManager){
		this.sessionManager = sessionManager;
	}

	@Override
	public boolean start() {
		SessionKit.setSessionManager(this.sessionManager);
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
