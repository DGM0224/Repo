package com.springbootproject.bbs.service;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.bbs.domain.Board;
import com.springbootproject.bbs.domain.Reply;
import com.springbootproject.bbs.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	private static final int PAGE_SIZE = 10;
	private static final int PAGE_GROUP = 10;
	
	// 게시판 리스트 불러오기
	public Map<String, Object> boardList(int categoryCode, int pageNum, String type, String keyword) {
		log.info("BoardService : boardList(int categoryCode, int pageNum, String type, String keyword)");
		
		int currentPage = pageNum;
		
		int startRow = (currentPage - 1) * PAGE_SIZE;
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		
		int listCount = boardMapper.getBoardCount(categoryCode, type, keyword);
		
		List<Board> boardList = boardMapper.boardList(categoryCode, startRow, PAGE_SIZE, type, keyword);
		
		int pageCount = listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1);
		
		int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1 - (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
		
		int endPage = startPage + PAGE_GROUP - 1;
		
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		
		Map<String, Object> modelMap = new HashMap<>();
				
		modelMap.put("bList", boardList);
		modelMap.put("pageCount", pageCount);
		modelMap.put("startPage", startPage);
		modelMap.put("endPage", endPage);
		modelMap.put("currentPage", currentPage);
		modelMap.put("listCount", listCount);
		modelMap.put("pageGroup", PAGE_GROUP);
		modelMap.put("searchOption", searchOption);
		if(searchOption) {
			modelMap.put("type", type);
			modelMap.put("keyword", keyword);
		}
		
		return modelMap;
	}
	
	// 게시판 상세보기
	public Board getBoard(int categoryCode, int boardNo, boolean isCount) {
		log.info("BoardService : getBoard(int categoryCode, int boardNo, boolean isCount)");
				
		if(isCount) {
			boardMapper.incrementReadCount(categoryCode, boardNo);
		}
		
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
	
	// 댓글 불러오기
	public List<Reply> replyList(int boardNo) {
		return boardMapper.replyList(boardNo);
	}
	
	// 좋아요/싫어요 업데이트 및 갱신
	public Map<String, Integer> recommend(int boardNo, String recommend) {
		boardMapper.updateRecommend(boardNo, recommend);
		Board board = boardMapper.getRecommend(boardNo);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardLike", board.getBoardLike());
		map.put("boardDislike", board.getBoardDislike());
		return map;
	}
	
}
