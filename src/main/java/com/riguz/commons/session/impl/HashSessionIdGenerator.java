package com.riguz.commons.session.impl;

import com.fasterxml.uuid.Generators;
import com.riguz.commons.session.SessionIdGenerator;
import com.riguz.commons.util.EncryptUtil;

public class HashSessionIdGenerator implements SessionIdGenerator{
	protected String hashMethod;

	public HashSessionIdGenerator(){
		this.hashMethod = "SHA-256";
	}

	public HashSessionIdGenerator(String method){
		this.hashMethod = method;
	}

	protected String hash(String source){
		return EncryptUtil.encrypt(this.hashMethod, source);
	}

	@Override
	public String nextSessionId() {
		String uuid = Generators.timeBasedGenerator().generate().toString();
		return this.hash(uuid);
	}

}
