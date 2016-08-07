package com.riguz.commons.session;

public abstract class SessionManager {
	protected int expires;
	protected SessionIdGenerator idGenerator;

	public void init(){

	}

	public abstract Session createSession();

	public abstract void save(Session session);
	public abstract void remove(String sessionId);
	public abstract Session get(String sessionId);
	public abstract void update(Session session);
	public abstract void saveOrUpdate(Session session);

	public void setExpires(int seconds) {
		this.expires = seconds;
	}

	public int getExpires() {
		return this.expires;
	}

	public SessionIdGenerator getIdGenerator() {
		return this.idGenerator;
	}

	public void setIdGenerator(SessionIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
}
