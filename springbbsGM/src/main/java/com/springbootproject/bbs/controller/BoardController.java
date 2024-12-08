package com.springbootproject.bbs.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootproject.bbs.domain.Board;
import com.springbootproject.bbs.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 게시판 리스트 불러오기
	@GetMapping({"/", "/boardList"})
	public String boardList(Model model) {
		model.addAttribute("bList", boardService.boardList(1));
		return "views/boardList";
	}
	
	// 게시글 상세보기
	@GetMapping("/boardDetail")
	public String getBoard(Model model, @RequestParam("boardNo") int boardNo) {
		model.addAttribute("board", boardService.getBoard(1, boardNo));
		return "views/boardDetail";
	}
	
	// 게시글 작성
	@GetMapping("/addBoard")
	public String addBoard() {
		return "views/writeForm";
	}

	// 게시글 작성 요청 
	@PostMapping("/addBoard")
	public String addBoard(Board board) {
		boardService.addBoard(1, board);
		return "redirect:boardList";
	}
	
	// 게시글 수정
	@PostMapping("/updateForm")
	public String updateBoard(Model model,
	HttpServletResponse response, PrintWriter out,
	@RequestParam("boardNo") int boardNo, @RequestParam("rPass") String rPass) {

	boolean isPassCheck = boardService.isPassCheck(boardNo, rPass);
	if(! isPassCheck) {
		response.setContentType("text/html; charset=utf-8");
		out.println("<script>");
		out.println(" alert('비밀번호가 맞지 않습니다.');");
		out.println(" history.back();");
		out.println("</script>");
		return null;
	}
	
	Board board = boardService.getBoard(1, boardNo);
	model.addAttribute("board", board);
	return "views/updateForm";
	}
	
	// 게시글 수정 요청
	@PostMapping("/update")
	public String updateBoard(Board board, 
			HttpServletResponse response, PrintWriter out) {

		boolean isPassCheck = boardService.isPassCheck(board.getBoardNo(), board.getBoardPass());
		if(! isPassCheck) {
		response.setContentType("text/html; charset=utf-8");
		out.println("<script>");
		out.println(" alert('비밀번호가 맞지 않습니다.');");
		out.println(" history.back();");
		out.println("</script>");
	
		return null;
		}
	
		boardService.updateBoard(1, board);
		return "redirect:boardList";
	}
	
	// 게시글 삭제 요청
	@PostMapping("/delete")
	public String deleteBoard(
	HttpServletResponse response, PrintWriter out,
	@RequestParam("boardNo") int boardNo, @RequestParam("rPass") String rPass) {
	
	boolean isPassCheck = boardService.isPassCheck(boardNo, rPass);
	if(! isPassCheck) {
	response.setContentType("text/html; charset=utf-8");
	out.println("<script>");
	out.println(" alert('비밀번호가 맞지 않습니다.');");
	out.println(" history.back();");
	out.println("</script>");
	return null;
	}

	boardService.deleteBoard(boardNo);
	return "redirect:boardList";
	}
}
