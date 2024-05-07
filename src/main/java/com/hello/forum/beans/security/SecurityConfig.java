package com.hello.forum.beans.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hello.forum.beans.security.handler.LoginFailureHandler;
import com.hello.forum.beans.security.handler.LoginSuccessHandler;
import com.hello.forum.member.dao.MemberDao;

/**
 * <pre>
 * Spring Security 의 전반적인 설정이 이루어지는 곳.
 * 
 * Spring Security에 필요한 Bean의 생성.
 * Spring Security의 보안정책을 설정.
 * </pre>
 */
@Configuration
@EnableWebSecurity // Spring Security Filter 정책 설정을 위한 Annotation
public class SecurityConfig {

	@Autowired
	private MemberDao memberDao;

	/**
	 * 사용자 세부정보 서비스에 대한 Spring Bean 생성.
	 * 
	 * @return SecurityUserDetailsService 의 bean
	 */
	@Bean
	UserDetailsService userDetailsService() {
		return new SecurityUserDetailsService(this.memberDao);
	}

	/**
	 * 암호 인코더에 대한 Spring Bean 생성
	 * 
	 * @return SecuritySHA 의 bean
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new SecuritySHA();
	}

	/**
	 * Spring Security Filter가 동작해야할 방식(순서)를 정의
	 * 
	 * @param http HttpSecurity 필터 전략
	 * @return SpringSecurityFilterChain Spring Security가 동작해야할 순서
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 로그인(필터) 정책 설정.
		// 우리 애플리케이션은 Form 기반으로 로그인을 하며
		// 로그인이 완료되면, /board/search로 이동을 해야한다.
		// 로그인 페이지 변경.
		http.formLogin(formLogin -> formLogin
				// Spring Security 인증이 성공할 경우, LoginSuccessHandler가 동작되도록 설정.
				.successHandler(new LoginSuccessHandler())
				// Spring Security 인증이 실패할 경우 LoginFailureHandler가 동작되도록 설정
				.failureHandler(new LoginFailureHandler())
				// Spring Security Login URL 변경
				.loginPage("/member/login")
				// Spring Security Login 처리 URL 변경
				// SecurityAuthenticationProvider 실행 경로 지정
				.loginProcessingUrl("/member/login-proc")
				// 로그인ID가 전달될 파라미터 이름
				.usernameParameter("email")
				// 로그인PW가 전달될 파라미터 이름
				.passwordParameter("password"));

		// CSRF 방어로직 무효화.
		http.csrf(csrf -> csrf.disable());

		return http.build();
	}

}
