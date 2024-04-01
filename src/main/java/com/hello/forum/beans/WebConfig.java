package com.hello.forum.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Spring Interceptor, Servlet Filter, MVC 설정.
@Configurable
@EnableWebMvc // MVC와 관련된 여러가지 기능들이 활성화 된다.
				// MVC와 관련된 설정들은 이 파일에 작성해야한다.
				// 그 중 하나가 파라미터 유효성 검사.
public class WebConfig implements WebMvcConfigurer {

	@Value("${app.authentication.check-url-pattern:/**}")
	private String authCheckUrlPattern;

	@Value("${app.authentication.ignore-url-patterns:}")
	private List<String> authCheckIgnoreUrlPatterns;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**") // /js/로 시작하는 모든 URL
				.addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/css/**") // /css/로 시작하는 모든 URL
				.addResourceLocations("classpath:/static/css/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CheckSessionInterceptor())
				.addPathPatterns(this.authCheckUrlPattern)
				.excludePathPatterns(this.authCheckIgnoreUrlPatterns);

		registry.addInterceptor(new BlockDuplicateLoginInterceptor())
				.addPathPatterns("/member/login", "/member/regist");
	}

	// Filter 등록.
//	@Bean
//	FilterRegistrationBean<Filter> filter() {
//
//		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//		filterRegistrationBean.setFilter(new SessionFilter());
//		filterRegistrationBean.setUrlPatterns(
//				List.of("/board/write", "/board/modify/*", "/board/delete/*"));
//		return filterRegistrationBean;
//
//	}

}
