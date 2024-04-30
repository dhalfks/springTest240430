package com.ezen.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.test.domain.CommentVO;
import com.ezen.test.repository.BoardDAO;
import com.ezen.test.repository.CommentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

	private final CommentDAO cdao;
	private final BoardDAO bdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("comment service in");
		int isOk  = cdao.post(cvo);
		if(isOk >0) {
			isOk *= bdao.cmtCount(cvo.getBno(), 1);			
		}
		return isOk;
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("comment service in");
		return cdao.getList(bno);
	}

	@Override
	public int modify(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.update(cvo);
	}

	@Override
	public int delete(int cno, int bno) {
		// TODO Auto-generated method stub
		int isOk = cdao.delete(cno);
		if(isOk >0) {
			int cnt = -1;
			isOk *= bdao.cmtCount(bno, cnt);
		}
		return isOk;  
	}
}
