package com.hello.forum.beans.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.forum.member.vo.MemberVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT를 생성하고 검증하는 클래스.
 */
@Component
public class JsonWebTokenProvider {

	@Value("${app.jwt.issuer:hello-spring}")
	private String issuer;

	@Value("${app.jwt.secret-key:spring-security-key-random-token-key")
	private String secretKey;

	/**
	 * 토큰을 생성한다.
	 * 
	 * @param expireAt 토큰의 유효기간
	 * @param memberVO 토큰 본문에 첨부할 사용자의 정보
	 * @return JWT 토큰
	 */
	public String generateToken(Duration expireAt, MemberVO memberVO) {

		Date now = new Date();
		// Token 유효기간
		Date expiry = new Date(now.getTime() + expireAt.toMillis());

		// Token 암/복호화 대칭키
		SecretKey key = Keys
				.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));

//		Jwts.builder().issuer(this.issuer).issuedAt(now).expiration(expiry)
//				.subject(memberVO.getEmail()).claim("user", memberVO).header()
//				.add("typ", "JWT").and().compact();

		return Jwts.builder()//
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)//
				.setIssuer(this.issuer)//
				.setIssuedAt(now)//
				.setExpiration(expiry)//
				.setSubject(memberVO.getEmail())//
				.setClaims(Map.of("user", memberVO))//
				.signWith(key).compact();
	}

	/**
	 * JWT에서 사용자의 정보를 조회한다. 만약, 잘못된 토큰일 경우 예외가 발생된다.
	 * 
	 * @param token HttpServletRequest header로 전달된 token 정보
	 * @return 회원 정보
	 * @throws JsonProcessingException
	 */
	public MemberVO getUserFromToken(String token)
			throws JsonProcessingException {
		// Token 암/복호화 대칭키
		SecretKey key = Keys
				.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));

//		Claims claims = Jwts.parser().verifyWith(key)
//					.build()
//					.parseSignedClaims(token)
//					.getPayload();

		Jws<Claims> jwsClaims = Jwts.parser()//
				.requireIssuer(this.issuer).setSigningKey(key)//
				.build()//
				.parseClaimsJws(token);//

		Claims claims = jwsClaims.getBody();

		ObjectMapper om = new ObjectMapper();
		Object user = claims.get("user");
		String json = om.writeValueAsString(user);

		MemberVO memberVO = om.readValue(json, MemberVO.class);

		return memberVO;
	}

}
