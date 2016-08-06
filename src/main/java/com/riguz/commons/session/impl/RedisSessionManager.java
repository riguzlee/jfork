package com.riguz.commons.session.impl;

import com.google.common.base.Strings;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.riguz.commons.session.Session;
import com.riguz.commons.session.SessionIdGenerator;
import com.riguz.commons.session.SessionManager;

public class RedisSessionManager extends SessionManager{
	protected Cache cache;

	public RedisSessionManager(){
		this.cache = Redis.use();
		this.expires = 1800; // 30m
		this.idGenerator = new HashSessionIdGenerator();
	}

	public RedisSessionManager(String cacheName){
		this.cache = Redis.use(cacheName);
		this.expires = 1800; // 30m
	}

	public RedisSessionManager(String cacheName, int expires, SessionIdGenerator idGenerator){
		this.cache = Redis.use(cacheName);
		this.expires = expires;
		this.idGenerator = idGenerator;
	}

	@Override
	public Session createSession() {
		return new DefaultSession(this.idGenerator.nextSessionId(), this.expires);
	}

	@Override
	public void save(Session session) {
		this.checkSession(session);
		this.cache.setex(session.getSessionId(), this.expires, session);
	}

	@Override
	public void remove(String sessionId) {
		if(Strings.isNullOrEmpty(sessionId))
			throw new IllegalArgumentException("session id is null or empty");
		this.cache.del(sessionId);
	}

	@Override
	public Session get(String sessionId) {
		if(Strings.isNullOrEmpty(sessionId))
			throw new IllegalArgumentException("session id is null or empty");
		Session session = this.cache.get(sessionId);
		if(session == null)
			return null;
		if(session.isInvalidated()){
			this.remove(sessionId);
			return null;
		}
		session.setLastAccessTime(System.currentTimeMillis());
		this.update(session);
		return session;
	}

	@Override
	public void update(Session session) {
		this.checkSession(session);
		this.remove(session.getSessionId());
		this.save(session);
	}

	public Cache getCache() {
		return this.cache;
	}

	private void checkSession(Session session){
		if(session == null || Strings.isNullOrEmpty(session.getSessionId()))
			throw new IllegalArgumentException("Session is null or id lost");
	}
}
