package com.hello.forum.utils;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiResponse {

	private int status;
	private String statusMessage;
	private int pages;
	private boolean next;
	private Object errors;
	private int count;
	private Object body;

	public static ApiResponse OK(Object body) {
		ApiResponse response = new ApiResponse();
		response.status = HttpStatus.OK.value();
		response.statusMessage = HttpStatus.OK.getReasonPhrase();
		response.body = body;
		response.count = body == null ? 0 : 1;

		return response;
	}

	public static ApiResponse OK(Object body, int count) {
		ApiResponse response = OK(body);
		response.count = count;
		return response;
	}

	public static ApiResponse OK(Object body, int count, int pages,
			boolean next) {
		ApiResponse response = OK(body, count);
		response.pages = pages;
		response.next = next;

		return response;
	}

	/**
	 * 권한이 충분하지 않을 때 사용.
	 * 
	 * @param errorMessage
	 * @return
	 */
	public static ApiResponse FORBIDDEN(String errorMessage) {
		ApiResponse response = new ApiResponse();
		response.status = HttpStatus.FORBIDDEN.value();
		response.statusMessage = HttpStatus.FORBIDDEN.getReasonPhrase();
		response.errors = errorMessage;
		return response;
	}

	/**
	 * 필수 파라미터를 빼먹었을 때 사용.
	 * 
	 * @param errorMessages
	 * @return
	 */
	public static ApiResponse BAD_REQUEST(List<String> errorMessages) {
		ApiResponse response = new ApiResponse();
		response.status = HttpStatus.BAD_REQUEST.value();
		response.statusMessage = HttpStatus.BAD_REQUEST.getReasonPhrase();
		response.errors = errorMessages;

		return response;
	}

	public ApiResponse() {

	}

	public int getStatus() {
		return status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public int getPages() {
		return pages;
	}

	public boolean getNext() {
		return next;
	}

	public Object getErrors() {
		return errors;
	}

	public int getCount() {
		return count;
	}

	public Object getBody() {
		return body;
	}

}
