package com.hello.forum.menu.dao;

import java.util.List;

import com.hello.forum.menu.vo.MenuVO;

public interface MenuDao {

	public static final String NAME_SPACE = "com.hello.forum.menu.dao.MenuDao";

	public List<MenuVO> selectAllMenu();

}
