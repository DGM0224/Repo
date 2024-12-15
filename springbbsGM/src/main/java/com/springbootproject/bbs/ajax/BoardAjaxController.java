package com.springbootproject.bbs.ajax;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.bbs.domain.Member;
import com.springbootproject.bbs.domain.Reply;
import com.springbootproject.bbs.service.BoardService;
import com.springbootproject.bbs.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class BoardAjaxController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberService memberService;
	
	// 좋아요/싫어요 갱신 
	@PostMapping("/recommend.ajax")
	public Map<String, Integer> recommend(@RequestParam("boardNo") int boardNo, @RequestParam("recommend") String recommend) {
		return boardService.recommend(boardNo, recommend);
	}
	
	// 댓글 쓰기
	@PostMapping("/replyWrite.ajax")
	public List<Reply> addReply(Reply reply, HttpSession session) {
		
		Member member = (Member) session.getAttribute("member");		
		reply.setMemberId(member.getId());
		
		boardService.addReply(reply);	
		return boardService.replyList(reply.getBoardNo());
	}
	
	// 댓글 수정
	@PatchMapping("/replyUpdate.ajax")
	public List<Reply> updateReply(Reply reply) {
		boardService.updateReply(reply);
		return boardService.replyList(reply.getBoardNo());
	}
	
	@DeleteMapping("/replyDelete.ajax")
	public List<Reply> deleteReply(@RequestParam("replyNo") int replyNo, @RequestParam("boardNo") int boardNo) {
		boardService.deleteReply(replyNo);
		return boardService.replyList(boardNo);
	}
	

}
