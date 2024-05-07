package com.hello.forum.beans.security.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>
 * Spring Security Login 에 실패했을 경우
 * 해당 이벤트를 감지해서 자동으로 실행되는 클래스.
 * 
 * AuthenticationFailureHandler 인터페이스를 구현!
 * </pre>
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		// 인증 실패 메시지를 로그인 페이지에 노출을 시키는 코드.
		String authenticationFailureMessage = exception.getMessage();

		// 로그인 페이지를 보여주고
		// 인증 실패 메시지를 보내준다.
		String loginPagePath = "/WEB-INF/views/member/memberlogin.jsp";

		RequestDispatcher rd = request.getRequestDispatcher(loginPagePath);
		request.setAttribute("message", authenticationFailureMessage);
		rd.forward(request, response);
	}

}
