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
	public int deleteMemberByEmail(String email) {
		return getSqlSession()
				.update(MemberDao.NAME_SPACE + ".deleteMemberByEmail", email);
	}

	@Override
	public MemberVO getMemberByEmail(String email) {
		return getSqlSession()
				.selectOne(MemberDao.NAME_SPACE + ".getMemberByEmail", email);
	}

}
