package com.hello.forum.beans.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hello.forum.beans.security.SecurityUser;
import com.hello.forum.member.vo.MemberVO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 사용자가 Api 요청을 할 경우 함께 전송된 JWT를 검증하는 역할.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JsonWebTokenProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();

		if (servletPath.startsWith("/api/")) {
			String jwt = request.getHeader("Authorization");

			MemberVO memberVO = jwtProvider.getUserFromToken(jwt);
			SecurityUser user = new SecurityUser(memberVO);

			Authentication auth = new UsernamePasswordAuthenticationToken(user,
					null, user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}

}
