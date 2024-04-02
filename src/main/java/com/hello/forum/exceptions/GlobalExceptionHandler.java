package com.hello.forum.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * "Base Packge (com.hello.forum)" 아래에서 발생하는 처리되지 않은 모든 예외들을 ControllerAdvice 가
 * 처리해준다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * PageNotFoundException 이 발생했을 때, 동작하는 메소드.
	 * 
	 * @param pnfe ControllerAdvice 까지 처리되지 않은 PageNotFoundException 객체
	 * @return 에러페이지.
	 */
	@ExceptionHandler(PageNotFoundException.class)
	public String viewPageNotFoundPage(PageNotFoundException pnfe,
			Model model) {

		model.addAttribute("message", pnfe.getMessage());

		return "error/404";
	}

}
