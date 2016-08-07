package com.riguz.jfork.app.ajax;

import java.io.Serializable;

public class Response implements Serializable{
	private static final long serialVersionUID = -283690211420324331L;
	protected int error;
	protected Object detail;

	public Response(int error, String detail) {
		super();
		this.error = error;
		this.detail = detail;
	}

	public Response(int error, Object detail) {
		super();
		this.error = error;
		this.detail = detail;
	}

	public int getError() {
		return this.error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public Object getDetail() {
		return this.detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}
}
