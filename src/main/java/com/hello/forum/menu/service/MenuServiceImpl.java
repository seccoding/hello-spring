package com.hello.forum.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.forum.menu.dao.MenuDao;
import com.hello.forum.menu.vo.MenuVO;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<MenuVO> getAllMenu() {
		return this.menuDao.selectAllMenu();
	}

}
