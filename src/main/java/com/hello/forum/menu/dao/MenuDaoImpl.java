package com.hello.forum.menu.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hello.forum.menu.vo.MenuVO;

@Repository
public class MenuDaoImpl extends SqlSessionDaoSupport implements MenuDao {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public List<MenuVO> selectAllMenu() {
		return getSqlSession()
				.selectList(MenuDao.NAME_SPACE + ".selectAllMenu");
	}

}
