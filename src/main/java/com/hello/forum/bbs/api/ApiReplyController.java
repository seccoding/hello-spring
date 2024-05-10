package com.hello.forum.bbs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.bbs.service.ReplyService;
import com.hello.forum.bbs.vo.ReplyVO;
import com.hello.forum.bbs.vo.SearchReplyVO;
import com.hello.forum.utils.AjaxResponse;

@RestController
@RequestMapping("/api")
public class ApiReplyController {

	@Autowired
	private ReplyService replyService;

	// @RequestBody : @PostMapping, @PutMapping
	@GetMapping("/reply/{boardId}") // /api/reply/{boardId}
	public AjaxResponse getAllReplies(@PathVariable int boardId,
			SearchReplyVO searchReplyVO) {
		searchReplyVO.setBoardId(boardId);
		List<ReplyVO> replyList = this.replyService
				.getAllReplies(searchReplyVO);

		searchReplyVO.setPageCount(replyList.size());

		return new AjaxResponse().append("count", replyList.size())
				.append("replies", replyList).append("paginate", searchReplyVO);
	}

}
