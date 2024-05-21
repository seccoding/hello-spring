package com.hello.forum.bbs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.bbs.service.BoardService;
import com.hello.forum.bbs.vo.BoardListVO;
import com.hello.forum.bbs.vo.SearchBoardVO;
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

}
