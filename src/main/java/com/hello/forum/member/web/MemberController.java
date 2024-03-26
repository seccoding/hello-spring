package com.hello.forum.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hello.forum.member.service.MemberService;
import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.utils.StringUtils;
import com.hello.forum.utils.ValidationUtils;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/member/regist")
	public String viewRegistMemberPage() {
		return "member/memberregist";
	}

	// http://localhost:8080/member/regist/available?email=aaa@aaa.com
	@ResponseBody // 응답하는 데이터를 JSON으로 변환해 클라이언트에게 반환한다.
	@GetMapping("/member/regist/available")
	public Map<String, Object> checkAvailableEmail(@RequestParam String email) {

		// 사용가능한 이메일이라면 true
		// 아니라면 false
		boolean isAvailableEmail = this.memberService
				.checkAvailableEmail(email);

		/*
		 * { "email": "aaa@aaa.com", "available": false }
		 */
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("email", email);
		responseMap.put("available", isAvailableEmail);
		return responseMap;
	}

	@PostMapping("/member/regist")
	public String doRegistMember(MemberVO memberVO, Model model) {

		boolean isNotEmptyEmail = ValidationUtils.notEmpty(memberVO.getEmail());
		boolean isEmailFormat = ValidationUtils.email(memberVO.getEmail());
		boolean isNotEmptyName = ValidationUtils.notEmpty(memberVO.getName());
		boolean isNotEmptyPassword = ValidationUtils
				.notEmpty(memberVO.getPassword());
		boolean isEnoughSize = ValidationUtils.size(memberVO.getPassword(), 10);
		boolean isNotEmptyConfrimPassword = ValidationUtils
				.notEmpty(memberVO.getConfirmPassword());

		boolean isEqualsPassword = ValidationUtils.isEquals(
				memberVO.getPassword(), memberVO.getConfirmPassword());

		boolean isPasswordFormat = StringUtils
				.correctPasswordFormat(memberVO.getPassword());

		if (!isNotEmptyEmail) {
			model.addAttribute("errorMessage", "이메일을 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isEmailFormat) {
			model.addAttribute("errorMessage", "이메일 형태로 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isNotEmptyName) {
			model.addAttribute("errorMessage", "이름을 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isNotEmptyPassword) {
			model.addAttribute("errorMessage", "비밀번호를 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isEnoughSize) {
			model.addAttribute("errorMessage", "비밀번호는 최소 10자리 이상 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isPasswordFormat) {
			model.addAttribute("errorMessage",
					"비밀번호는 영어 대/소문자, 숫자를 포함하여 10자리 이상을 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isNotEmptyConfrimPassword) {
			model.addAttribute("errorMessage", "비밀번호 확인을 입력해주세요.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}
		if (!isEqualsPassword) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("memberVO", memberVO);
			return "member/memberregist";
		}

		boolean isSuccess = this.memberService.createNewMember(memberVO);

		if (isSuccess) {
			return "redirect:/member/login";
		}

		model.addAttribute("memberVO", memberVO);
		return "member/memberregist";
	}

	@GetMapping("/member/login")
	public String viewLoginPage() {
		return "member/memberlogin";
	}

	@ResponseBody
	@PostMapping("/member/login")
	public Map<String, Object> doLogin() {
		return null;
	}

}
