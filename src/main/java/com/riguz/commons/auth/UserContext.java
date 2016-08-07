package com.riguz.commons.auth;

public class UserContext implements AutoCloseable {

    protected static final ThreadLocal<User> current = new ThreadLocal<User>();
    protected static final ThreadLocal<String> sessionId = new ThreadLocal<String>();

    public UserContext(String sessionId, User user) {
    	this.sessionId.set(sessionId);
        current.set(user);
    }

    public static User getCurrentUser() {
        return current.get();
    }

    public static String getSessionId(){
    	return sessionId.get();
    }

    public static boolean isUserAuthed(){
    	return current.get() != null;
    }
    @Override
	public void close() {
        current.remove();
    }
}