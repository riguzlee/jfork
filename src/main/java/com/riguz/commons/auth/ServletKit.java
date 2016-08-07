package com.riguz.commons.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

public class ServletKit {
	public static String getCookieByName(HttpServletRequest request, String cookieName){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(cookieName))
				return cookie.getValue();
		}
		return null;
	}

	public static String getCookieByNameOrParam(HttpServletRequest request, String cookieName){
		String value = getCookieByName(request, cookieName);
		if(!Strings.isNullOrEmpty(value))
			return value;
		return request.getParameter(cookieName);
	}

	public static boolean isAjax(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  &&
	    		"XMLHttpRequest".equals( request.getHeader("X-Requested-With"))   ) ;
	}
}
