package com.riguz.commons.session;

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

    public static void addSession(Session session) {
        sessionManager.save(session);
    }

    public static void removeSession(String sessionId) {
        sessionManager.remove(sessionId);
    }

    public static long getSessionTimeout() {
        return sessionManager.getExpires();
    }
}
