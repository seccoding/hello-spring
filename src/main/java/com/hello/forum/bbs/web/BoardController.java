package com.hello.forum.bbs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hello.forum.bbs.service.BoardService;
import com.hello.forum.bbs.vo.BoardListVO;
import com.hello.forum.bbs.vo.BoardVO;

@Controller
public class BoardController {

	/*
	 * Bean Container에서 BoardService 타입의 객체를 찾아
	 * 아래 멤버변수에게 할당한다 (DI: Dependency Injection)
	 */
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list")
	public String viewBoardListPage(Model model) {
		// 1. 게시글의 건수와 게시글의 목록을 조회해서
		BoardListVO boardListVO = this.boardService.getAllBoard();
		
		// 2. /WEB-INF/views/board/boardlist.jsp 페이지에게 게시글의 건수와 게시글의 목록을 전달하고
		model.addAttribute("boardList", boardListVO);
		
		// 3. 화면을 보여준다.
		return "board/boardlist";
	}
	
	/**
	 * 게시글 작성페이지를 보여주는 URL
	 * @return
	 */
	@GetMapping("/board/write") // 브라우저에서 링크를 클릭, 브라우저 URL을 직접 입력.
	public String viewBoardWritePage() {
		return "board/boardwrite";
	}
	
	/**
	 * 스프링 애플리케이션을 개발할 때 같은 URL을 정의할 수 없다.
	 * Method가 다를 경우엔 예외적으로 허용한다.
	 * GET /board/write
	 * POST /board/write
	 * 
	 * 글 등록페이지에서 게시글을 작성하고 "저장"버튼을 클릭하면
	 * 데이터베이스에 글 정보를 저장(insert)해야한다.
	 * 
	 * 사용자가 작성한 글 정보를 알아야 한다.
	 * 1. Servlet like (HttpServletRequest 객체)
	 * 2. @RequestParam (Servlet like -> 조금 더 편하게 사용)
	 * 3. Command Object : 보편적으로 많이 사용하는 방법 > 파라미터 처리가 매우 편하다!!!
	 * 4. @PathVariable
	 * 
	 * @return
	 */
	@PostMapping("/board/write")
	public String doBoardWrite( /*Spring이 알맞은 파라미터를 자동으로 보내준다. Servlet like: HttpServletRequest request*/  
			/*컨트롤러로 전송된 파라미터를 하나씩 받아오는 방법. 
			 * @RequestParam으로 정의된 파라미터는 필수 파라미터!!!
			 * 컨트롤러로 전송되는 파라미터의 개수가 몇개 없을 때, 예: 3개 미만 */ 
//			@RequestParam String subject,
//			@RequestParam String email,
//			@RequestParam String content
			/*
			 * Command Object
			 * 파라미터로 전송된 이름과 BoardVO의 멤버변수의 이름과 같은 것이 있다면
			 * 해당 멤버변수에 파라미터의 값을 할당해준다!!! (setter 이용)
			 * */
			BoardVO boardVO
			) {
		System.out.println("글 등록 처리를 해야합니다.");
		/* Servlet Like
		 * HttpServletRequest를 이용.
		 * - Interceptor에서 이용
		 * - Filter에서 이용.
		 * */
//		String subject = request.getParameter("subject");
//		String email = request.getParameter("email");
//		String content = request.getParameter("content");
//		System.out.println("제목: " + subject);
//		System.out.println("이메일: " + email);
//		System.out.println("내용: " + content);
		
//		System.out.println("제목: " + boardVO.getSubject());
//		System.out.println("이메일: " + boardVO.getEmail());
//		System.out.println("내용: " + boardVO.getContent());
		
		boolean isCreateSuccess = this.boardService.createNewBoard(boardVO);
		if (isCreateSuccess) {
			System.out.println("글 등록 성공!");
		}
		else {
			System.out.println("글 등록 실패!");
		}
		
		// board/boardlist 페이지를 보여주는 URL로 이동처리.
		// redirect:/board/list
		// 스프링은 브라우저에게 /board/list로 이동하라는 명령을 전송.
		// 명령을 받은 브라우저는 /board/list로 URL을 이동시킨다.
		// /board/list로 브라우저가 요청을 하게 되면
		// 스프링 컨트롤러에서 /board/list URL에 알맞은 처리를 진행한다.
		return "redirect:/board/list";
	}
	
	// browser에서 URL을 http://localhost:8080/board/view?id=1
	// URL ? <-- Query Parameter
	// ?id=1 <-- Parameter Key: id, Parameter Value: 1
	// ?id=1&subject=abc <-- Parameter Key: id, Parameter Value: 1 / Parameter Key: subject, Parameter Value: abc
	@GetMapping("/board/view")
	public String viewBoardDetailPage(@RequestParam int id, Model model) {
		
		// 1. boardService에게 파라미터로 전달받은 id 값을 보내준다.
		// 2. boardService는 파라미터로 전달받은 id의 게시글 정보를 조회해서 반환해주면
		BoardVO boardVO = this.boardService.getOneBoard(id);
		
		// 3. boardview 페이지에 데이터를 전송해준다.
		model.addAttribute("boardVO", boardVO);
		
		// 4. 화면을 보여준다.
		return "board/boardview";
	}
	
}












