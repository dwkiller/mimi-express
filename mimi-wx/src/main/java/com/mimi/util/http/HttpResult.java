package com.mimi.util.http;

import java.io.InputStream;

public class HttpResult {
	private int code;
	private String body;
	private InputStream content;
	
	public HttpResult(int code,String body) {
		this.code=code;
		this.body=body;
	}
	
	public HttpResult(int code,InputStream content) {
		this.code=code;
		this.content=content;
	}
	
	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}


	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}