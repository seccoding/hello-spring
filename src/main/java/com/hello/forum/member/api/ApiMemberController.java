package com.hello.forum.member.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.beans.security.SecurityUser;
import com.hello.forum.member.service.MemberService;
import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class ApiMemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/member")
	public ApiResponse getMyInfo(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberVO memberVO = ((SecurityUser) userDetails).getMemberVO();

		return ApiResponse.OK(memberVO, memberVO == null ? 0 : 1);
	}

}
