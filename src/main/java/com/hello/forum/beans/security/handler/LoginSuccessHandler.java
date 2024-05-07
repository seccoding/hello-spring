package com.hello.forum.beans.security.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hello.forum.beans.security.SecurityUser;
import com.hello.forum.member.vo.MemberVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>
 * Spring Security의 인증이 성공할 경우 실행되는 클래스.
 * 
 * 인증 후 처리가 필요할 경우 예를 들어, 
 * 1. 인증 응답데이터를 JSON으로 반환시켜야 한다.
 * 2. 로그인 시간을 DB에 기록을 해야한다.
 * 3. 로그인 로그를 기록해야 한다.
 * </pre>
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final Logger logger = LoggerFactory
			.getLogger(LoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(//
			HttpServletRequest request, // 인증 요청에 대한 정보
			HttpServletResponse response, // 인증 응답에 대한 정보
			Authentication authentication // 인증이 완료된 UserDetails 정보.
	) throws IOException, ServletException {

		logger.info("Spring Security Login 성공!");

		Object user = authentication.getPrincipal();

		if (user instanceof UserDetails) {
			MemberVO memberVO = ((SecurityUser) user).getMemberVO();
			logger.info("Email: " + memberVO.getEmail());
			logger.info("Name: " + memberVO.getName());
		} else {
			logger.info("Email: " + user);
		}

		// 로그를 찍은 이후, /board/search 로 이동시키기
		response.sendRedirect("/board/search");

	}

}
