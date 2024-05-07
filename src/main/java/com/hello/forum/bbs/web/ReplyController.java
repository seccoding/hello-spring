package com.hello.forum.bbs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.forum.bbs.service.ReplyService;
import com.hello.forum.bbs.vo.ReplyVO;
import com.hello.forum.bbs.vo.SearchReplyVO;
import com.hello.forum.utils.AjaxResponse;

@RestController
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	@GetMapping("/ajax/board/reply/{boardId}")
	public AjaxResponse getAllReplies(@PathVariable int boardId,
			SearchReplyVO searchReplyVO) {
		searchReplyVO.setBoardId(boardId);
		List<ReplyVO> replyList = this.replyService
				.getAllReplies(searchReplyVO);

		searchReplyVO.setPageCount(replyList.size());

		return new AjaxResponse().append("count", replyList.size())
				.append("replies", replyList).append("paginate", searchReplyVO);
	}

	@PostMapping("/ajax/board/reply/{boardId}")
	public AjaxResponse doCreateNewReplies(@PathVariable int boardId,
			ReplyVO replyVO, Authentication authentication) {

		replyVO.setBoardId(boardId);
		replyVO.setEmail(authentication.getName());

		boolean isSuccess = this.replyService.createNewReply(replyVO);
		return new AjaxResponse().append("result", isSuccess);
	}

	@GetMapping("/ajax/board/reply/delete/{replyId}")
	public AjaxResponse doDeleteReplies(@PathVariable int replyId,
			Authentication authentication) {
		boolean isSuccess = this.replyService.deleteOneReply(replyId,
				authentication.getName());

		return new AjaxResponse().append("result", isSuccess);
	}

	@PostMapping("/ajax/board/reply/modify/{replyId}")
	public AjaxResponse doModifyReplies(@PathVariable int replyId,
			ReplyVO replyVO, Authentication authentication) {

		replyVO.setReplyId(replyId);
		replyVO.setEmail(authentication.getName());

		boolean isSuccess = this.replyService.modifyOneReply(replyVO);
		return new AjaxResponse().append("result", isSuccess);
	}

	@GetMapping("/ajax/board/reply/recommend/{replyId}")
	public AjaxResponse doRecommendReplies(@PathVariable int replyId,
			Authentication authentication) {

		boolean isSuccess = this.replyService.recommendOneReply(replyId,
				authentication.getName());

		return new AjaxResponse().append("result", isSuccess);
	}

}
