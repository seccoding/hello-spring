package com.hello.forum.menu.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.beans.security.SecurityUser;
import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.menu.service.MenuService;
import com.hello.forum.menu.vo.MenuVO;
import com.hello.forum.utils.AjaxResponse;

@RestController
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * <pre>
	 * DB에서 조회한 메뉴 정보를 저장하는 변수. 
	 * 매번 DB에서 조회하는 트랜잭션을 최소화 하기 위함.
	 * </pre>
	 */
	private static List<MenuVO> cachedMenuList;

	@GetMapping("/ajax/menu/list")
	private AjaxResponse getMenuList(Authentication authentication) {

		if (cachedMenuList == null) {
			cachedMenuList = this.menuService.getAllMenu();
		}

		List<MenuVO> menuList = cachedMenuList.stream().filter((menu) -> {

			MemberVO memberVO = null;

			if (authentication != null) {
				UserDetails userDetails = (UserDetails) authentication
						.getPrincipal();
				memberVO = ((SecurityUser) userDetails).getMemberVO();
			}

			if (memberVO == null) {
				// 로그인을 안한 사용자.
				return menu.getRole().equals("ALL");
			} else if (memberVO != null && memberVO.getAdminYn().equals("N")) {
				// 로그인을 한 일반 사용자.
				return menu.getRole().equals("ALL")
						|| menu.getRole().equals("USER");
			}

			return true;
		}).collect(Collectors.toList());

		return new AjaxResponse().append("menu", menuList);
	}

}
