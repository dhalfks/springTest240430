package com.ezen.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.test.domain.MemberVO;
import com.ezen.test.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	private final MemberService msv;
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		log.info(">>>> mvo >> {}", mvo);
		int isOk = msv.insert(mvo);
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(MemberVO mvo, HttpServletRequest request, Model m) {
		log.info(">>>> mvo >> {}", mvo);
		
		//mvo 객체가 DB의 값과 일치하는 객체 가져오기
		MemberVO loginMvo = msv.isUser(mvo);
		log.info(">>>> loginMvo >> {}", loginMvo);
		
		if(loginMvo != null) {
			//로그인 성공시.
			HttpSession ses = request.getSession();
			ses.setAttribute("ses", loginMvo); //세션에 로그인 객체 저장
			ses.setMaxInactiveInterval(10*60); //로그인 유지 시간
		}else {
			//로그인 실패시.
			m.addAttribute("msg_login", "1");
		}
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model m) {
		// 라스트로그인 업데이트 => 세션 객체 삭제 => 세션끊기
		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
		msv.lastLoginUpdate(mvo.getId());
		
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate();
		
		m.addAttribute("msg_logout", "1");
		return "index";
	}
	
	@GetMapping("/modify")
	public void modify() { }
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo) {
		log.info(">>>> mvo >> {}", mvo);
		
		msv.modify(mvo);
		return "redirect:/member/logout";
	}
	
	@GetMapping("/remove")
	public String remove(HttpServletRequest request, RedirectAttributes re) {
//		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
//		msv.delete(mvo.getId());
		msv.delete(getId(request));
		re.addFlashAttribute("msg_remove", "1");
		return "redirect:/member/logout";
	}
	
	private String getId(HttpServletRequest request) {
		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
		return mvo.getId();
	}

}
