package com.springbootproject.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	// ID 중복 체크
	@RequestMapping("/overlapIdCheck")
	public String overlapIdCheck(Model model, @RequestParam("id") String id) {
		boolean overlapId = memberService.overlapIdCheck(id);
		model.addAttribute("id", id);
		model.addAttribute("overlapId", overlapId);
		return "member/overlapIdCheck";
	}
	
	// Nickname 중복 체크
	@RequestMapping("/overlapNicknameCheck")
	public String overlapNicknamsCheck(Model model, @RequestParam("nickname") String nickname) {
		boolean overlapNickname = memberService.overlapNicknameCheck(nickname);
		model.addAttribute("nickname", nickname);
		model.addAttribute("overlapNickname", overlapNickname);
		return "member/overlapNicknameCheck";
	}
	
	// 회원 가입
	@PostMapping("/joinResult")
	public String joinResult(Model model, Member member,
							@RequestParam("pass1") String pass1,
							@RequestParam("emailId") String emailId,
							@RequestParam("emailDomain") String emailDomain,
							@RequestParam("mobile1") String mobile1,
							@RequestParam("mobile2") String mobile2,
							@RequestParam("mobile3") String mobile3,
							@RequestParam(value="emailGet", required=false, defaultValue="false")boolean emailGet) {
		member.setPass(pass1);
		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-"  + mobile3);
		member.setEmailGet(Boolean.valueOf(emailGet));
	
		memberService.addMember(member);
		log.info("joinResult : " + member.getName());
	
		return "redirect:loginForm";
	}
	
	// 회원 수정 폼
	@GetMapping("/memberUpdateForm")
	public String updateForm(Model model, HttpSession session) {
		return "member/memberUpdateForm";
	}
	
	// 회원 수정 처리
	@PostMapping("/memberUpdateResult")
	public String memberUpdateInfo(Model model, Member member,
									@RequestParam("pass1") String pass1,
									@RequestParam("emailId") String emailId,
									@RequestParam("emailDomain") String emailDomain,
									@RequestParam("mobile1") String mobile1,
									@RequestParam("mobile2") String mobile2,
									@RequestParam("mobile3") String mobile3,
									@RequestParam(value="emailGet", required=false, defaultValue="false")boolean emailGet) {
		member.setPass(pass1);
		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-" + mobile3);
		member.setEmailGet(Boolean.valueOf(emailGet));
	
		memberService.updateMember(member);
		log.info("memberUpdateResult : " + member.getId());
	
		model.addAttribute("member", member);
	
		return "redirect:boardList";
	}

}
