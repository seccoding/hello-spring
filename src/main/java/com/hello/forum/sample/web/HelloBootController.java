package com.hello.forum.sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Servlet: HelloBootServlet.java
 *    web.xml
 *       <servlet>
 *          <servlet-name>HelloBootServlet</servlet-name>
 *          <servlet-class>com.hello.forum.sample.web.HelloBootServlet</servlet-class>
 *       </servlet>
 *       <servlet-mapping>
 *       	<servlet-name>HelloBootServlet</servlet-name>
 *          <url-pattern>/hello</url-pattern>
 *       </servlet-mapping>
 *   
 *   HelloBootServlet.java
 *      doGet(HttpServletRequest request, HttpServletResponse response) { ... }
 *      doPost(HttpServletRequest request, HttpServletResponse response) { ... }
 *      
 *   Browser > http://localhost:8080/project-name/hello
 */
// Spring이 인스턴스로 만들어주는 대상.
// 브라우저와 서버가 통신(데이터를 주고받는)할 수 있는 End-Point ==> Controller
@Controller // <-- Servlet
public class HelloBootController {

	private Logger logger = LoggerFactory.getLogger(HelloBootController.class);

	public HelloBootController() {
		// Spring 이 호출하다!!!! -> 생성된 객체를 Bean Container에 보관한다.
		logger.info("HelloBootController() 호출됨.");
		logger.info(this.toString());
	}

	@GetMapping("/hello") // @GetMapping doGet();
							// "/hello" <-- servlet-mapping > url-pattern 값
	public ResponseEntity<String> hello() {
		ResponseEntity<String> responseBody = new ResponseEntity<>(
				"Hello Boot Controller - Test", HttpStatus.OK);
		return responseBody;
	}

	@GetMapping("/hello-html")
	public ResponseEntity<String> helloHtml() {
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("    <head>");
		html.append("        <title>Hello Spring</title>");
		html.append("    </head>");
		html.append("    <body>");
		html.append("        <div>Hello, Spring Controller!</div>");
		html.append("    </body>");
		html.append("</html>");

		ResponseEntity<String> responseBody = new ResponseEntity<>(
				html.toString(), HttpStatus.OK);
		return responseBody;
	}

	@GetMapping("/hello-jsp")
	public String helloJSP(Model model) {

		// request.setAttribute("myname", "Spring Boot Sample Application");
		model.addAttribute("myname", "Spring Boot Sample Application");
		model.addAttribute("createDate", "2024-03-19");
		model.addAttribute("author", "ktds 23th");
		model.addAttribute("version", 1.0);

//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/helloboot.jsp");
//		rd.forward(request, response);

		// Spring (Boot) 1개의 Servlet 내장되어 있다.
		// 내장되어 있는 Servlet이 Controller를 호출.
		// 만약, Contoller가 반환시킨 데이터가 String 타입이라면
		// Servlet의 코드 (RequestDispatcher ~~~~~; rd.forward(request, response);를
		// 수행한다.
		// 파일의 이름이 반환되었을 경우
		// application.yml에 정의된 prefix, suffix를 붙인다.
		// /WEB-INF/views/helloboot.jsp
		return "helloboot";
	}

}
