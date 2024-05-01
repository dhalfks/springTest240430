package com.ezen.www.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/board/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService bsv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/insert")
	public String insert(BoardVO bvo) {
		int isOk = bsv.insert(bvo);
		return "index";
	}
	
	@GetMapping("/list")
	public String list( Model m) {
		List<BoardVO> list = bsv.getList();
		//가져온 리스트 => /board/list.jsp 로 전달
		m.addAttribute("list", list);
		return "/board/list";
	}
	
	@GetMapping({"/detail","/modify"})
	public void detail(@RequestParam("bno")int bno, Model m) {
		BoardVO bvo = bsv.getDetail(bno);
		m.addAttribute("bvo", bvo);
	}
	
	//RedirectAttributes : redirect 로 보내는 객체
	@PostMapping("/modify")
	public String modify(BoardVO bvo, RedirectAttributes re) {
		int isOk = bsv.modify(bvo);
		re.addAttribute("bno", bvo.getBno());
		return "redirect:/board/detail";
	}
	
	@GetMapping("/remove")
	public String remove (@RequestParam("bno")int bno) {
		int isOk = bsv.remove(bno);
		return "redirect:/board/list";
	}

}
