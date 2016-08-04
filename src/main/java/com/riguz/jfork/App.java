package com.riguz.jfork;

import org.mindrot.jbcrypt.BCrypt;

import com.riguz.commons.util.EncryptUtil;
import com.riguz.jfork.app.config.Constants;

/**
 * Hello world!
 *
 */
public class App{
	public static void main( String[] args ){
		String pwd = BCrypt.hashpw("123456", BCrypt.gensalt(12));
		System.out.println("123456:\n" + pwd);
		String k1 = Constants.ENCRYPT_RAND + "$2a$12$hj8IsPnfJo5BzxxUGYYMIe.kCJpyaobmIuhd6p9d7FTwgHM/6waZq";
		String k2 = EncryptUtil.encrypt("sha-512", k1);
		System.out.println(k2);
	}
}
