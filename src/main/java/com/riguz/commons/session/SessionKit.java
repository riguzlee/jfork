package com.riguz.commons.session;

import com.riguz.commons.auth.UserContext;

public class SessionKit {
	private static SessionManager sessionManager;

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static void setSessionManager(SessionManager sessionManager) {
        SessionKit.sessionManager = sessionManager;
    }

    public static Session getSession(String sessiongId) {
        return sessionManager.get(sessiongId);
    }

    public static Session getNewSession() {
        return sessionManager.createSession();
    }

    public static void save(Session session) {
        sessionManager.saveOrUpdate(session);
    }

    public static void removeSession(String sessionId) {
        sessionManager.remove(sessionId);
    }

    public static long getSessionTimeout() {
        return sessionManager.getExpires();
    }

    public static void set(String key, Object value){
    	String sessionId = UserContext.getSessionId();
    	Session session = getSession(sessionId);
    	if(session == null){
    		session = sessionManager.createSession();
    		session.setSessionId(sessionId);
    		sessionManager.save(session);
    	}
    	session.put(key, value);
    	save(session);
    }
}
