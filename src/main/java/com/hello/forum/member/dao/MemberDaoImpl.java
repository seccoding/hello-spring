package com.hello.forum.member.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hello.forum.member.vo.MemberVO;

@Repository
public class MemberDaoImpl extends SqlSessionDaoSupport implements MemberDao {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public int getEmailCount(String email) {
		return getSqlSession()
				.selectOne(MemberDao.NAME_SPACE + ".getEmailCount", email);
	}

	@Override
	public int createNewMember(MemberVO memberVO) {
		return getSqlSession().insert(MemberDao.NAME_SPACE + ".createNewMember",
				memberVO);
	}

	@Override
	public String selectSalt(String email) {
		return getSqlSession().selectOne(MemberDao.NAME_SPACE + ".selectSalt",
				email);
	}

	@Override
	public MemberVO selectMemberByEmailAndPassword(MemberVO memberVO) {
		return getSqlSession().selectOne(
				MemberDao.NAME_SPACE + ".selectMemberByEmailAndPassword",
				memberVO);
	}

}
