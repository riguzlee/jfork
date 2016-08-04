package com.riguz.jfork;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Hello world!
 *
 */
public class App{
	public static void main( String[] args ){
		String pwd = BCrypt.hashpw("123456", BCrypt.gensalt(12));
		System.out.println("123456:\n" + pwd);
	}
}
