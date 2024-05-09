package com.ezen.www.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.UserVO;
import com.ezen.www.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/**")
@Controller
public class UserController {
	
	private final UserService usv;
	private final BCryptPasswordEncoder bcEncoder;
	
	//controller mapping과 jsp 경로가 같으면 void 가능.
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(UserVO uvo) {
		log.info(">>>> uvo >> {} ", uvo);
		uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
		int isOk = usv.register(uvo);
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String loginPost(HttpServletRequest request, RedirectAttributes re){
		//로그인 실패시 다시 로그인 페이지로 돌아와 오류 메시지 전송
		//다시 로그인 유도
		log.info(">>> errMsg >> {}", request.getAttribute("errMsg").toString());
		re.addAttribute("email", request.getAttribute("email"));
		re.addAttribute("errMsg", request.getAttribute("errMsg"));
		return "redirect:/user/login";
	}
	
	@GetMapping("/list")
	public String list(Model m) {
		List<UserVO> userList = usv.getList();
		m.addAttribute("userList", userList);
		return "/user/list";
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
		
	@PostMapping("/modify")
	public String modify(UserVO uvo, HttpServletRequest request, HttpServletResponse response) {
		if(uvo.getPwd().isEmpty() || uvo.getPwd().length() == 0 ) {
			//비번 없는 업데이트 진행
			int isOk = usv.modifyPwdEmpty(uvo);
		}else {
			//비번을 다시 인코딩하여 업데이트를 진행
			uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
			int isOk = usv.modify(uvo);
		}
		
		logout(request, response);
		return "redirect:/";
	}
	
	@GetMapping("/remove")
	public String delete(Principal principal, HttpServletRequest request, HttpServletResponse response) {
		String email = principal.getName();  //email
		int isOk = usv.delete(email);
		logout(request, response);
		return "redirect:/";
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = 
				SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		new SecurityContextLogoutHandler()
			.logout(request, response, authentication);
	}
	

}
