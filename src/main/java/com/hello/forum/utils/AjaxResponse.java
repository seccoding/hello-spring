package com.hello.forum.utils;

import java.util.HashMap;
import java.util.Map;

public class AjaxResponse {

	private Map<String, Object> response;

	public AjaxResponse() {
		this.response = new HashMap<>();
	}

	public AjaxResponse append(String key, Object value) {
		this.response.put(key, value);
		return this;
	}

	public Map<String, Object> getData() {
		return this.response;
	}

}
