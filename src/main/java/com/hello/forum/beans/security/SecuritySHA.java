package com.hello.forum.beans.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hello.forum.beans.SHA;

/**
 * <pre>
 * Spring Security 의 인증과정에서 필요한 암호화 정책과 관련된 기능 수행.
 * UserDetailsService의 경우는 아이디(이메일)로 회원의 정보만 조회하고
 * PasswordEncoder(SecuritySHA)는 사용자가 입력한 비밀번호를 암호화하고 비교하는 기능 수행.
 * 
 * PasswordEncoder 인터페이스를 구현!
 * </pre>
 */
public class SecuritySHA extends SHA implements PasswordEncoder {

	private String salt;

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 로그인 비밀번호를 암호화하는 기능
	 * 
	 * @param rawPassword 암호화가 되지 않은 평문 비밀번호.
	 * @return 암호화된 비밀번호
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return super.getEncrypt(rawPassword.toString(), this.salt);
	}

	/**
	 * 로그인 비밀번호가 일치하는지 확인하는 기능
	 * 
	 * @param rawPassword     암호화가 되지 않은 평문 비밀번호.
	 * @param encodedPassword 암호화가 되어 있는 비밀번호. (DB에 저장된 비밀번호)
	 * @return rawPassword 를 암호화한 값과 encodedPassword가 일치하는지 여부
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String password = super.getEncrypt(rawPassword.toString(), this.salt);
		return password.equals(encodedPassword);
	}

}
