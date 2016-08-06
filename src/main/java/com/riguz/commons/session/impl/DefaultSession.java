package com.riguz.commons.session.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.riguz.commons.session.Session;

public class DefaultSession extends Session implements Serializable{
	private static final long serialVersionUID = 54239986539376239L;
	protected Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	public DefaultSession(String sessionId, int maxInactiveInterval){
		this.sessionId = sessionId;
		this.maxInactiveInterval = maxInactiveInterval;
		this.createTime = System.currentTimeMillis();
		this.lastAccessTime = System.currentTimeMillis();
	}

	@Override
	public void put(String key, Object value) {
		this.attributes.put(key, value);

	}

	@Override
	public Object get(String key) {
		return this.attributes.get(key);
	}

	@Override
	public void remove(String key) {
		this.attributes.remove(key);

	}

	@Override
	public Set<String> getKeys() {
		return this.attributes.keySet();
	}

	@Override
	public void markAsInvalidated() {
		this.maxInactiveInterval = 0;

	}

	@Override
	public boolean isInvalidated() {
		return this.lastAccessTime + this.maxInactiveInterval < System.currentTimeMillis();
	}

}
