package com.hello.forum.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.forum.beans.SHA;
import com.hello.forum.member.dao.MemberDao;
import com.hello.forum.member.vo.MemberVO;
import com.hello.forum.utils.StringUtils;

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

	@Override
	public MemberVO getMember(MemberVO memberVO) {
		// 1. 이메일로 저장되어 있는 salt를 조회한다.
		String storedSalt = this.memberDao.selectSalt(memberVO.getEmail());

		// 만약, salt값이 null 이라면, 회원정보가 없는 것이므로 사용자에게 예외를 전달한다.
		if (StringUtils.isEmpty(storedSalt)) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}

		// 2. salt 값이 있을 경우, salt를 이용해 sha 암호화 한다.
		String password = memberVO.getPassword();
		password = this.sha.getEncrypt(password, storedSalt);
		memberVO.setPassword(password);

		// 3. DB에서 암호화된 비밀번호와 이메일을 비교해 회원 정보를 가져온다.
		MemberVO member = this.memberDao
				.selectMemberByEmailAndPassword(memberVO);

		// 만약, 회원 정보가 null 이라면 회원 정보가 없는 것이므로 사용자에게 예외를 전달한다.
		if (member == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}

		return member;
	}

	@Override
	public boolean deleteMe(String email) {
		return this.memberDao.deleteMemberByEmail(email) > 0;
	}

}
