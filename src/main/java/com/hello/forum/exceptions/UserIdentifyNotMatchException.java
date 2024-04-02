package com.hello.forum.exceptions;

public class UserIdentifyNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 406943432504844909L;

	public UserIdentifyNotMatchException() {
		super("이메일 또는 비밀번호가 일치하지 않습니다.");
	}

}
