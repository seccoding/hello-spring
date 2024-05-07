package com.hello.forum.beans.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hello.forum.member.dao.MemberDao;
import com.hello.forum.member.vo.MemberVO;

/**
 * <pre>
 * Spring Security에서 로그인(인증)처리를 할 때
 * 사용자의 세부정보(상세정보)를 조회하는 기능.
 * 
 * UserDetailsService 인터페이스 구현이 필요.
 * </pre>
 */
public class SecurityUserDetailsService implements UserDetailsService {

//	@Autowired
	/**
	 * <pre>
	 * SecurityUserDetailsService 클래스는 Spring Bean 이 아니기 때문에
	 * Autowired를 쓸 수 없음!!!
	 * 생성자나 Setter를 이용해서 MemberDao 를 받아와야 한다.
	 * </pre>
	 */
	private MemberDao memberDao;

	public SecurityUserDetailsService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	/**
	 * <pre>
	 * Spring Security 가 로그인 요청을 수행하면
	 * AuthenticationProvider(인증공급자)에서 로그인 아이디(이메일)로
	 * 회원의 정보를 조회하는 것을 요청한다.
	 * 이 때, loadUserByUsername 이 호출되며, 파라미터로는 로그인 아이디(이메일)가 전달된다.
	 * 
	 * 만약, 로그인아이디(이메일)로 회원의 정보를 조회했을 때 조회된 결과가 없을 경우
	 * UsernameNotFoundException이 던져져야 한다.
	 * </pre>
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		MemberVO memberVO = this.memberDao.getMemberByEmail(username);

		if (memberVO == null) {
			throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		return new SecurityUser(memberVO);
	}

}
