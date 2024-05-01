package com.ezen.www.service;

import java.util.List;

import com.ezen.www.domain.BoardVO;

public interface BoardService {

	int insert(BoardVO bvo);

	List<BoardVO> getList();

	BoardVO getDetail(int bno);

	int modify(BoardVO bvo);

	int remove(int bno);

}
