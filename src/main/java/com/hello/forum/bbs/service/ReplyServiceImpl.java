package com.hello.forum.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.forum.bbs.dao.ReplyDao;
import com.hello.forum.bbs.vo.ReplyVO;
import com.hello.forum.bbs.vo.SearchReplyVO;
import com.hello.forum.exceptions.PageNotFoundException;
import com.hello.forum.utils.AuthUtil;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;

	@Override
	public List<ReplyVO> getAllReplies(SearchReplyVO searchReplyVO) {
		return this.replyDao.getAllReplies(searchReplyVO);
	}

	@Transactional
	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		return this.replyDao.createNewReply(replyVO) > 0;
	}

	@Transactional
	@Override
	public boolean deleteOneReply(int replyId, String email) {
		ReplyVO replyVO = this.replyDao.getOneReply(replyId);

		if (!email.equals(replyVO.getEmail()) && !AuthUtil.hasRole("ADMIN")) {
			throw new PageNotFoundException();
		}
		return this.replyDao.deleteOneReply(replyId) > 0;
	}

	@Transactional
	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		ReplyVO originalReplyVO = this.replyDao
				.getOneReply(replyVO.getReplyId());

		if (!originalReplyVO.getEmail().equals(replyVO.getEmail())
				&& !AuthUtil.hasRole("ADMIN")) {
			throw new PageNotFoundException();
		}

		return this.replyDao.modifyOneReply(replyVO) > 0;
	}

	@Transactional
	@Override
	public boolean recommendOneReply(int replyId, String email) {
		ReplyVO replyVO = this.replyDao.getOneReply(replyId);

		if (email.equals(replyVO.getEmail())) {
			throw new PageNotFoundException();
		}

		return this.replyDao.recommendOneReply(replyId) > 0;
	}

}
