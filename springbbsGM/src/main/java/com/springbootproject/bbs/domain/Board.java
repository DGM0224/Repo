package com.springbootproject.bbs.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	// DTO
	
	// DB 테이블 - 게시판
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardPass;
	private Timestamp boardRegDate;
	private int boardView;
	private String memberId;
	private String boardFile1;
	private int boardLike;
	private int boardDislike;
	
	// DB 테이블 - 카테고리
	private int categoryCode;
	private String categoryName;

}
