package com.hello.forum.member.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hello.forum.member.service.MemberService;
import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.utils.AjaxResponse;
import com.hello.forum.utils.StringUtils;
import com.hello.forum.utils.ValidationUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@GetMapping("/member/regist")
	public String viewRegistMemberPage() {
		return "member/memberregist";
	}

	@ResponseBody
	@GetMapping("/ajax/member/regist/available")
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

	@GetMapping("/member/logout")
	public String doLogout(HttpServletRequest request, //
			HttpServletResponse response, //
			Authentication authentication) {

		// Spring Security Logout!
		LogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, authentication);

		return "redirect:/board/search";
	}

	@ResponseBody
	@GetMapping("/ajax/member/delete-me")
	public AjaxResponse doDeleteMe(HttpServletRequest request, //
			HttpServletResponse response, //
			Authentication authentication) {
		// 현재 로그인되어있는 사용자의 정보
//		MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER_");
		boolean isSuccess = this.memberService
				.deleteMe(authentication.getName());

		if (isSuccess) {
			// Spring Security Logout!
			LogoutHandler logoutHandler = new SecurityContextLogoutHandler();
			logoutHandler.logout(request, response, authentication);
		}

		return new AjaxResponse().append("next",
				isSuccess ? "/member/success-delete-me"
						: "/member/fail-delete-me");
	}

	@GetMapping("/member/{result}-delete-me")
	public String viewDeleteMePage(@PathVariable String result) {
		result = result.toLowerCase();

		if (!result.equals("fail") && !result.equals("success")) {
			return "error/404";
		}

		return "member/" + result + "deleteme";
	}

}
