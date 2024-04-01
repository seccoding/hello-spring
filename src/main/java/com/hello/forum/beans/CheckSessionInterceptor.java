package com.hello.forum.beans;

import org.springframework.web.servlet.HandlerInterceptor;

import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.utils.StringUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckSessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER_");

		if (memberVO == null) {

			// 원래 가려했던 URL 정보. -> 현재 요청중인 URL을 확인.
			// GET, POST, PUT, DELETE, FETCH 등등..
			String httpMethod = request.getMethod().toLowerCase();
			String uri = request.getRequestURI();
//			String url = request.getRequestURL().toString();
			String queryString = request.getQueryString();

			System.out.println("HttpMethod: " + httpMethod);
			System.out.println("RequestURI: " + uri);
			System.out.println("QueryString: " + queryString);
//			System.out.println("RequestURL: " + url);

			if (httpMethod.equals("get")) {

				String nextUrl = uri;
				if (!StringUtils.isEmpty(queryString)) {
					nextUrl += "?" + queryString;
				}

				// Model 로 로그인 이후에 요청할 주소를 보내준다.
				request.setAttribute("nextUrl", nextUrl);

			}

			RequestDispatcher rd = request.getRequestDispatcher(
					"/WEB-INF/views/member/memberlogin.jsp");
			rd.forward(request, response);
			return false;
		}

		return true;
	}

}
