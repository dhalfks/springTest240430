package com.ezen.test.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.test.domain.MemberVO;
import com.ezen.test.repository.MemberDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberDAO mdao;
	
	final BCryptPasswordEncoder passwordEncoder;
	final HttpServletRequest request;

	@Override
	public int insert(MemberVO mvo) {
		// 아이디가 중복되는 경우 회원가입 실패
		// 아이디만 주고, DB에서 일치하는 mvo 객체를 리턴 => 일치하는 객체가 있다면 가입실패 / 없으면 가능.
		MemberVO tempMvo = mdao.getUser(mvo.getId()); 
		if(tempMvo != null) {
			//기존 아이디가 있는 경우
			return 0;
		}
		//아이디가 중복되지 않는다면... 회원가입 진행.
		//password가 null이거나 값이 없다면... 가입 불가
		if(mvo.getId() == null || mvo.getId().length() == 0) {
			return 0;
		}
		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
			return 0;
		}
		
		//회원가입 진행
		//password 암호화하여 가입
		//encode() : 암호화  / matches(입력된비번, 암호화된 비번) => true / false 
		
//		String pw = mvo.getPw();
//		String encodePW = passwordEncoder.encode(pw);
//		mvo.setPw(encodePW);
		
		mvo.setPw(passwordEncoder.encode(mvo.getPw()));
		
		//회원가입 
		
		int isOk = mdao.insert(mvo);
		
		return isOk;
	}

	@Override
	public MemberVO isUser(MemberVO mvo) {
		// 로그인 유저 확인
		MemberVO tempMvo = mdao.getUser(mvo.getId()); //회원가입 했을 때 썼던 메서드 활용
		
		//해당 아이디가 없으면..
		if(tempMvo == null) {
			return null;
		}
		
		//matches(원래비번, 암호화된 비번) 비교
		if(passwordEncoder.matches(mvo.getPw(), tempMvo.getPw())) {
			return tempMvo;
		}
		
		return null;
	}

	@Override
	public void lastLoginUpdate(String id) {
		mdao.lastLoginUpdate(id);
		
	}

	@Override
	public void modify(MemberVO mvo) {
		// pw 여부에 따라 변경사항을 나누어서 처리
		// pw가 없다면 기존값 설정 / 있다면 암호화처리하여 수정
		if(mvo.getPw()==null || mvo.getPw().length() == 0) {
			MemberVO sesMvo = (MemberVO)request.getSession().getAttribute("ses");
			mvo.setPw(sesMvo.getPw());
		}else {
			String setPw = passwordEncoder.encode(mvo.getPw());
			mvo.setPw(setPw);
		}
		log.info(">>> pw 수정 후 mvo >> {}", mvo);
		
		mdao.update(mvo);
		
	}

	@Override
	public void delete(String id) {
		mdao.delete(id);
		
	}

}
