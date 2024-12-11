package com.springbootproject.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springbootproject.bbs.domain.Member;
import com.springbootproject.bbs.service.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes("member")
@Slf4j
// SessionAttributes : Controller에서 model에 "이름"으로 추가시 세션에 등록해줌
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	// 로그인
	@PostMapping("/login")
	public String login(Model model, 
				@RequestParam("userId") String id,
				@RequestParam("pass") String pass,
				HttpSession session, HttpServletResponse response)
				throws ServletException, IOException {

		int result = memberService.login(id, pass);
		if(result == -1) { 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println(" alert('존재하지 않는 아이디 입니다.');");
			out.println(" history.back();");
			out.println("</script>");
	
			return null;
		} else if(result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println(" alert('비밀번호가 다릅니다.');");
			out.println(" location.href='loginForm'");
			out.println("</script>");
			return null;
		}
	
		Member member = memberService.getMember(id);
		session.setAttribute("isLogin", true);
	
		model.addAttribute("member", member);
		System.out.println("member.name : " + member.getName());
	
		return "redirect:/boardList";
	}
	
	// 로그아웃
	@GetMapping("/memberLogout")
	public String logout(HttpSession session) {
		log.info("MemberController.logout(HttpSession session)");
		session.invalidate();
	
		return "redirect:/loginForm";
	}

}
