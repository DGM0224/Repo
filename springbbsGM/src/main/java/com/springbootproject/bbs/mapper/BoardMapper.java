package com.springbootproject.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.springbootproject.bbs.domain.Board;

@Mapper
public interface BoardMapper {
	
	// 게시판 리스트 불러오기
	public List<Board> boardList(@Param("categoryCode") int categoryCode, @Param("startRow") int startRow, @Param("num") int num,
								@Param("type") String type, @Param("keyword") String keyword);
	
	// 전체 게시글 수 불러오기
	public int getBoardCount(@Param("categoryCode") int categoryCode, @Param("type") String type, @Param("keyword") String keyword);

	// 게시판 상세보기
	public Board getBoard(@Param("categoryCode") int categoryCode, @Param("boardNo") int boardNo);
	
	// 게시글 조회수
	public void incrementReadCount(@Param("categoryCode") int categoryCode, @Param("boardNo") int boardNo);
	
	// 게시판 작성 요청
	public void insertBoard(Board board);
	
	// 게시글 비밀번호 확인
	public String isPassCheck(int boardNo);
	
	// 게시글 수정 요청
	public void updateBoard(Board board);
	
	// 게시글 삭제 요청
	public void deleteBoard(int boardNo);
}
