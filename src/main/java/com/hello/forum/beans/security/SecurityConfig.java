package com.hello.forum.beans.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hello.forum.beans.security.handler.LoginFailureHandler;
import com.hello.forum.beans.security.handler.LoginSuccessHandler;
import com.hello.forum.beans.security.jwt.JwtAuthenticationFilter;
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

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

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
	 * <pre>
	 * Spring Security가 절대 개입하지 말아야하는 URL들을 정의.
	 * 아래 URL에서 보여지는 페이지 내부에서는 Security Taglib을 사용할 수 없다.
	 * </pre>
	 * 
	 * @return
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(
				AntPathRequestMatcher.antMatcher("/WEB-INF/views/**"))
		// CSRF 적용을 위해 Security 설정 필요.
//				.requestMatchers(
//						AntPathRequestMatcher.antMatcher("/member/login"))
//				.requestMatchers(
//						AntPathRequestMatcher.antMatcher("/member/regist/**"))
				.requestMatchers(AntPathRequestMatcher.antMatcher("/error/**"))
				.requestMatchers(
						AntPathRequestMatcher.antMatcher("/favicon.ico"))
				.requestMatchers(AntPathRequestMatcher
						.antMatcher("/member/**-delete-me"))
				.requestMatchers(AntPathRequestMatcher.antMatcher("/js/**"))
				.requestMatchers(AntPathRequestMatcher.antMatcher("/css/**"));
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

		// Spring Security가 개입할 URL 패턴 정의
		http.authorizeHttpRequests(httpRequest -> httpRequest
				.requestMatchers(
						AntPathRequestMatcher.antMatcher("/board/search"))
				.permitAll() // /board/search는 Security 인증 여부와 관계없이 접근 허용한다.
				.requestMatchers(
						AntPathRequestMatcher.antMatcher("/ajax/menu/list"))
				.permitAll() // /ajax/menu/list는 Security 인증 여부와 관계없이 접근 허용한다.
				.requestMatchers(
						AntPathRequestMatcher.antMatcher("/member/login"))
				.permitAll()
				.requestMatchers(
						AntPathRequestMatcher.antMatcher("/member/regist/**"))
				.permitAll()
				.requestMatchers(AntPathRequestMatcher
						.antMatcher("/ajax/member/regist/available"))
				.permitAll()
				.requestMatchers(
						AntPathRequestMatcher.antMatcher("/auth/token"))
				.permitAll()
				.requestMatchers(AntPathRequestMatcher
						.antMatcher("/board/excel/download2"))
				.hasRole("ADMIN")
				.requestMatchers(AntPathRequestMatcher
						.antMatcher("/ajax/board/delete/massive"))
				.hasRole("ADMIN")
				.requestMatchers(AntPathRequestMatcher
						.antMatcher("/ajax/board/excel/write"))
				.hasRole("ADMIN") //
				.anyRequest() // 그 외 나머지 URL들은 Security 인증이 반드시
								// 필요하며, 인증이 안되어있다면
								// 로그인 페이지로 이동시킨다.
				.authenticated());

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
//		http.csrf(csrf -> csrf.disable());

		// /auth/token URL에서는 CSRF 검사를 하지 않음.
		http.csrf(csrf -> csrf.ignoringRequestMatchers(
				AntPathRequestMatcher.antMatcher("/auth/token")));

		http.addFilterAfter(this.jwtAuthenticationFilter,
				BasicAuthenticationFilter.class);

		return http.build();
	}

}
