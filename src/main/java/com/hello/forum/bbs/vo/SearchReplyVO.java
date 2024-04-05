package com.hello.forum.bbs.vo;

import com.hello.forum.common.vo.PaginateVO;

public class SearchReplyVO extends PaginateVO {

	private int boardId;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

}
