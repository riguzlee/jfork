package com.riguz.commons.session;

import java.util.Set;

public abstract class Session {
	protected String sessionId;
	protected long createTime;
	protected long lastAccessTime;
	protected int maxInactiveInterval;

	public abstract void put(String key, Object value);
	public abstract Object get(String key);
	public abstract void remove(String key);
	public abstract Set<String> getKeys();
	public abstract void markAsInvalidated();
	public abstract boolean isInvalidated();

	public String getSessionId() {
		return this.sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getLastAccessTime() {
		return this.lastAccessTime;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public int getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
}
