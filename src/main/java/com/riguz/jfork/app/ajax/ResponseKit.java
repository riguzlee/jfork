package com.riguz.jfork.app.ajax;

import com.jfinal.core.Controller;

public class ResponseKit {
	public static final int DEFAULT_ERROR_CODE = 500;

	public static void error(Controller controller, String errorMessage){
		Response r = new Response(DEFAULT_ERROR_CODE, errorMessage);
		controller.renderJson(r);
	}

	public static void error(Controller controller, int code, String errorMessage){
		Response r = new Response(code, errorMessage);
		controller.renderJson(r);
	}

	public static void ok(Controller controller){
		Response r = new Response(DEFAULT_ERROR_CODE, "");
		controller.renderJson(r);
	}

	public static void ok(Controller controller, Object o){
		Response r = new Response(DEFAULT_ERROR_CODE, o);
		controller.renderJson(r);
	}
}
