package com.ezen.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.www.domain.UserVO;
import com.ezen.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
	
	private final UserDAO udao;

	@Transactional
	@Override
	public int register(UserVO uvo) {
		// 권한 추가
		int isOk = udao.insert(uvo);
		return udao.insertAuthInit(uvo.getEmail());
	}

//	@Override
//	public int updateLastLogin(String authEmail) {
//		// TODO Auto-generated method stub
//		return udao.updateLastLogin(authEmail);
//	}
	
	@Override
	public List<UserVO> getList(){
		List<UserVO> userList = udao.getList();
		for(UserVO uvo : userList) {
			uvo.setAuthList(udao.selectAuths(uvo.getEmail()));
		}
		return userList;
	}

	@Override
	public int modifyPwdEmpty(UserVO uvo) {
		// TODO Auto-generated method stub
		return udao.modifyPwdEmpty(uvo);
	}

	@Override
	public int modify(UserVO uvo) {
		// TODO Auto-generated method stub
		return udao.modify(uvo);
	}

	@Transactional
	@Override
	public int delete(String email) {
		int isOk = udao.authDelete(email);
		isOk *= udao.delete(email);
		return isOk;
	}

}
