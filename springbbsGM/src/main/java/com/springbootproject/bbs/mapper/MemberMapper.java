package com.springbootproject.bbs.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootproject.bbs.domain.Member;

@Mapper
public interface MemberMapper {
	
	// 회원 조회
	public Member getMember(String id);

}
