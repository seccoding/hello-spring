package com.hello.forum.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AuthUtil {

	private AuthUtil() {
	}

	public static boolean hasRole(String role) {
		// Authentication을 SecurityContext에서 가지고 와야함.
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		return authentication.getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_" + role));
	}

}
