package com.hello.forum.bbs.dao;

import java.util.List;

import com.hello.forum.bbs.vo.ReplyVO;
import com.hello.forum.bbs.vo.SearchReplyVO;

public interface ReplyDao {

	public String NAME_SPACE = "com.hello.forum.bbs.dao.ReplyDao";

	public List<ReplyVO> getAllReplies(SearchReplyVO searchReplyVO);

	public ReplyVO getOneReply(int replyId);

	public int createNewReply(ReplyVO replyVO);

	public int deleteOneReply(int replyId);

	public int modifyOneReply(ReplyVO replyVO);

	public int recommendOneReply(int replyId);

}
