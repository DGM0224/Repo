package com.springbootproject.bbs.service;




import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.bbs.domain.Board;
import com.springbootproject.bbs.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	// 게시판 리스트 불러오기
	public List<Board> boardList(int categoryCode) {
		log.info("BoardService : boardList(int categoryCode)");
		return boardMapper.boardList(categoryCode);	
	}
	
	// 게시판 상세보기
	public Board getBoard(int categoryCode, int boardNo) {
		log.info("BoardService : getBoard(int categoryCode, int boardNo)");
		return boardMapper.getBoard(categoryCode, boardNo);
	}

	// 게시글 작성
	public void addBoard(int categoryCode, Board board) {
		log.info("BoardService: addBoard(int categoryCode, Board board)");
		board.setCategoryCode(categoryCode);
		boardMapper.insertBoard(board);
	}
	
	// 게시글 비밀번호 확인
	public boolean isPassCheck(int boardNo, String rPass) {
		log.info("BoardService: isPassCheck(int boardNo, String rpass)");
		boolean result = false;
		String dbPass = boardMapper.isPassCheck(boardNo);
		if(dbPass.equals(rPass)) {
			result = true;
		}
		
		return result;
	}
	
	// 게시글 수정
	public void updateBoard(int categoryCode, Board board) {
		log.info("BoardService: updateBoard(int categoryCode, Board board)");
		board.setCategoryCode(categoryCode);
		boardMapper.updateBoard(board);
	}
	
	// 게시글 삭제 
	public void deleteBoard(int boardNo) {
		log.info("BoardService: deleteBoard(int boardNo)");
		boardMapper.deleteBoard(boardNo);
	}
	
}
