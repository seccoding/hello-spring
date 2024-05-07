package com.hello.forum.beans.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * UserDetailsService 와 PasswordEncoder를 이용해서
 * 로그인을 요청한 사용자의 인증을 처리한다.
 * 
 * UserDetailsService : SecurityUserDetailsService
 * PasswordEncoder : SecuritySHA
 * 
 * 인증에 성공할 경우, SecurityContext에 인증정보를 저장해야 한다.
 * 
 * AuthenticationProvider 인터페이스를 구현해야한다!
 * </pre>
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

	/**
	 * SecurityUserDetailsService
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * SecuritySHA
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Spring Security 인증 수행.
	 * 
	 * @param authentication 로그인에 사용된 인증 정보들.
	 * @return 아이디와 비밀번호가 일치하는 회원의 정보 (UsernamePasswordAuthenticationToken)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		// 인증 시작.
		// 로그인에 사용된 아이디 (이메일)
		String email = authentication.getName();

		// 로그인에 사용된 비밀번호
		String password = authentication.getCredentials().toString();

		// UserDetailsService에서 이메일정보로 회원의 정보를 조회한다.
		// 만약, 회원의 정보가 존재하지 않다면, UsernameNotFoundException이 던져진다.
		// UserDetails ==> SecurityUser
		UserDetails userDetails = this.userDetailsService
				.loadUserByUsername(email);

		// 비밀번호 암호화를 위해서 DB에 저장되어있던 salt를 조회한다.
		// salt 값은 MemberVO 내부에 있고
		// Spring Security 과정 내부에서는 UserDetails에 있고
		// UserDetails를 상속한 SecurityUser 가 있다!
		// userDetails를 SecurityUser로 변환하여 salt를 얻어온다.
		String salt = ((SecurityUser) userDetails).getMemberVO().getSalt();

		// PasswordEncoder로 로그인에 사용된 비밀번호를 암호화.
		// Spring Security 과정 내부에서 PasswordEncoder는 SecuritySHA로 정의.
		// SecuritySHA에는 암호화를 위한 salt 변수를 가지고 있다.
		// 조회된 salt값을 SecuritySHA에게 할당을 해야한다.
		// PasswordEncoder를 SecuritySHA로 변환하여 salt를 할당한다.
		((SecuritySHA) this.passwordEncoder).setSalt(salt);

		// passwordEncoder를 이용해서 암호화된 비밀번호와, 입력한 비밀번호가 같은지 비교.
		boolean isMatchPassword = this.passwordEncoder.matches(password,
				userDetails.getPassword());

		// 만약 같지 않다면, BadCredentialsException을 던진다.
		if (!isMatchPassword) {
			throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		// 같다면, UsernamePasswordAuthenticationToken을 반환.

		// Security Context에 인증 정보를 저장!
		return new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());
	}

	/**
	 * <pre>
	 * 인증 요청을 처리할 인증 방식 지정.
	 * 인증 요청을 처리할 인증 필터 타입 지정.
	 * </pre>
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// UsernamePasswordAuthenticationToken : 아이디 / 비밀번호 인증 방식
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
