package com.riguz.commons.auth;

public class UserContext implements AutoCloseable {

    protected static final ThreadLocal<User> current = new ThreadLocal<User>();

    public UserContext(User user) {
        current.set(user);
    }

    public static User getCurrentUser() {
        return current.get();
    }

    public static boolean isUserAuthed(){
    	return current.get() != null;
    }
    @Override
	public void close() {
        current.remove();
    }
}