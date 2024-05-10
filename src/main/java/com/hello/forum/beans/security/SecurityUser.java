package com.hello.forum.beans.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hello.forum.member.vo.MemberVO;

/**
 * <pre>
 * Spring Security가 인증된 사용자의 정보를 보관할 클래스. 
 * UserDetails 인터페이스를 구현해야함.
 * </pre>
 */
public class SecurityUser implements UserDetails {

	/**
	 * <pre>
	 * UserDetails 인터페이스는
	 * 회원의 로그인아이디(이메일)와 비밀번호만 관리하기에
	 * 회원의 구체적인 정보는 알 수 없음.
	 * 
	 * 사용자의 구체적인 정보를 알기 위해
	 * MemberVO를 멤버변수로 정의.
	 * </pre>
	 */
	private MemberVO memberVO;

	public SecurityUser(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	/**
	 * <pre>
	 * 로그인한 회원의 접근권한을 설정.
	 * 실무환경에서 아래 메소드를 만들기 위해
	 * URL별, C/R/U/D 권한을 별도로 관리.
	 * 
	 * 예> /boards
	 *      C: USER, ADMIN
	 *      R: USER, ADMIN
	 *      U: USER, ADMIN
	 *      D: ADMIN
	 * 어떤 사용자가 /boards의 D를 수행하려면
	 * D 에 대한 권한이 있는지 체크.
	 * </pre>
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("CREATE"));
//		authorities.add(new SimpleGrantedAuthority("READ"));
//		authorities.add(new SimpleGrantedAuthority("UPDATE"));
//		authorities.add(new SimpleGrantedAuthority("DELETE"));
		/**
		 * memberVO.getAdminYN() == Y ==> ROLE_ADMIN memberVO.getAdminYN() == N
		 * ==> ROLE_USER
		 */
		String role = "ROLE_USER";
		if (this.memberVO.getAdminYn().equals("Y")) {
			role = "ROLE_ADMIN";
		}

		authorities.add(new SimpleGrantedAuthority(role));

		return authorities;
	}

	/**
	 * 현재 사용자가 로그인에 사용한 비밀번호
	 */
	@Override
	public String getPassword() {
		return this.memberVO.getPassword();
	}

	/**
	 * 현재 사용자가 로그인에 사용한 이메일 정보
	 */
	@Override
	public String getUsername() {
		return this.memberVO.getEmail();
	}

	/**
	 * 로그인 사용자 계정이 만료되지 않았는지 여부
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 로그인 사용자 계정이 잠금처리 되지 않았는지 여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 로그인 사용자의 계정 비밀번호 변경일이 만료되지 않았는지 여부
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 로그인 사용자 계정이 유효한지 여부.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
