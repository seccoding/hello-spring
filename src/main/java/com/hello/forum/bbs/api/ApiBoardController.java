package com.hello.forum.bbs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.bbs.service.BoardService;
import com.hello.forum.bbs.vo.BoardListVO;
import com.hello.forum.bbs.vo.BoardVO;
import com.hello.forum.bbs.vo.SearchBoardVO;
import com.hello.forum.beans.security.SecurityUser;
import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class ApiBoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/boards")
	public ApiResponse getBoardList(SearchBoardVO searchBoardVO) {
		BoardListVO boardListVO = this.boardService
				.searchAllBoard(searchBoardVO);

		return ApiResponse.OK(boardListVO.getBoardList(),
				boardListVO.getBoardCnt(), 1, false);
	}

	@GetMapping("/boards/{id}")
	public ApiResponse getBoard(@PathVariable int id) {
		BoardVO boardVO = this.boardService.getOneBoard(id, true);
		return ApiResponse.OK(boardVO, boardVO == null ? 0 : 1);
	}

	@DeleteMapping("/boards/{id}")
	public ApiResponse deleteBoard(@PathVariable int id,
			Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberVO memberVO = ((SecurityUser) userDetails).getMemberVO();

		BoardVO boardVO = this.boardService.getOneBoard(id, false);

		if (!memberVO.getEmail().equals(boardVO.getMemberVO().getEmail())) {
			return ApiResponse.FORBIDDEN("삭제할 권한이 없습니다.");
		}

		boolean isSuccess = this.boardService.deleteOneBoard(id);
		return ApiResponse.OK(isSuccess);
	}

}
