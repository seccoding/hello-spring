package com.hello.forum.exceptions;

public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7315087600868100103L;

	public PageNotFoundException() {
		super("페이지를 찾을 수 없습니다.");
	}

}
