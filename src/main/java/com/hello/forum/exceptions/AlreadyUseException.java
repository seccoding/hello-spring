package com.hello.forum.exceptions;

public class AlreadyUseException extends RuntimeException {

	private static final long serialVersionUID = 4971923447159745554L;

	private String email;

	public AlreadyUseException(String email) {
		super("이미 사용중인 이메일입니다.");
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}
