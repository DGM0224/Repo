package com.springbootproject.bbs.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootproject.bbs.domain.Member;

@Mapper
public interface MemberMapper {
	
	// 회원 조회
	public Member getMember(String id);
	
	// 닉네임 조회
	public Member getMember2(String nickname);

	// 회원 가입
	public void addMember(Member member);
	
	// 비밀번호 확인
	public String memberPassCheck(String id);
	
	// 회원 수정
	public void updateMember(Member member);
}
