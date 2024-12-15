package com.springbootproject.bbs.ajax;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.bbs.service.BoardService;

@RestController
public class BoardAjaxController {
	
	@Autowired
	private BoardService boardService;
	
	
	@PostMapping("/recommend.ajax")
	public Map<String, Integer> recommend(@RequestParam("boardNo") int boardNo, @RequestParam("recommend") String recommend) {
		return boardService.recommend(boardNo, recommend);
	}

}
