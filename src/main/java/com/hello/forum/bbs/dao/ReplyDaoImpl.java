package com.hello.forum.bbs.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hello.forum.bbs.vo.ReplyVO;

@Repository
public class ReplyDaoImpl extends SqlSessionDaoSupport implements ReplyDao {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return getSqlSession()
				.selectList(ReplyDao.NAME_SPACE + ".getAllReplies", boardId);
	}

	@Override
	public ReplyVO getOneReply(int replyId) {
		return getSqlSession().selectOne(ReplyDao.NAME_SPACE + ".getOneReply",
				replyId);
	}

	@Override
	public int createNewReply(ReplyVO replyVO) {
		return getSqlSession().insert(ReplyDao.NAME_SPACE + ".createNewReply",
				replyVO);
	}

	@Override
	public int deleteOneReply(int replyId) {
		return getSqlSession().update(ReplyDao.NAME_SPACE + ".deleteOneReply",
				replyId);
	}

	@Override
	public int modifyOneReply(ReplyVO replyVO) {
		return getSqlSession().update(ReplyDao.NAME_SPACE + ".modifyOneReply",
				replyVO);
	}

	@Override
	public int recommendOneReply(int replyId) {
		return getSqlSession()
				.update(ReplyDao.NAME_SPACE + ".recommendOneReply", replyId);
	}

}
