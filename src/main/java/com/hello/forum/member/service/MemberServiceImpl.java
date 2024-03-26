package com.hello.forum.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.forum.beans.SHA;
import com.hello.forum.member.dao.MemberDao;
import com.hello.forum.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SHA sha;

	@Autowired
	private MemberDao memberDao;

	@Override
	public boolean createNewMember(MemberVO memberVO) {
		int emailCount = this.memberDao.getEmailCount(memberVO.getEmail());

		if (emailCount > 0) {
			throw new IllegalArgumentException("Email이 이미 사용중입니다.");
		}

		String password = memberVO.getPassword();
		String salt = this.sha.generateSalt();
		password = this.sha.getEncrypt(password, salt);

		memberVO.setPassword(password);
		memberVO.setSalt(salt);

		int insertedCount = this.memberDao.createNewMember(memberVO);
		return insertedCount > 0;
	}

	@Override
	public boolean checkAvailableEmail(String email) {
		return this.memberDao.getEmailCount(email) == 0;
	}

}
