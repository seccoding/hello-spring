package com.hello.forum.exceptions;

public class MakeXlsxFileException extends RuntimeException {

	private static final long serialVersionUID = -5339383163532987283L;

	public MakeXlsxFileException() {
		super("엑셀 파일을 만들 수 없습니다.");
	}

}
