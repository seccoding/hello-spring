package com.hello.forum.beans.security.jwt;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.beans.SHA;
import com.hello.forum.member.dao.MemberDao;
import com.hello.forum.member.vo.MemberVO;

@RestController
public class JwtController {

	@Autowired
	private JsonWebTokenProvider jsonWebTokenProvider;

	@Autowired
	private MemberDao memberDao;

	/**
	 * JsonWebToken 발급
	 * 
	 * @param memberVO 로그인을 위한(인증을 위한) 이메일과 비밀번호
	 * @return 인증된 결과 (JWT)
	 */
	@PostMapping("/auth/token")
	public ResponseEntity<Map<String, Object>> createNewJsonWebToken(
			@RequestBody MemberVO memberVO) {

		String email = memberVO.getEmail();
		MemberVO member = this.memberDao.getMemberByEmail(email);

		if (member == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(Map.of("message", "아이디 또는 비밀번호가 존재하지 않습니다."));
		}

		String password = memberVO.getPassword();
		String salt = member.getSalt();

		SHA sha = new SHA();
		String encryptedPassword = sha.getEncrypt(password, salt);

		if (encryptedPassword.equals(member.getPassword())) {
			String jwt = jsonWebTokenProvider
					.generateToken(Duration.ofHours(12), member);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(Map.of("token", jwt));
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Map.of("message", "아이디 또는 비밀번호가 존재하지 않습니다."));
	}

}
