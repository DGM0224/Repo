package com.springbootproject.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootproject.bbs.domain.Member;
import com.springbootproject.bbs.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 로그인 여부 
	public int login(String id, String pass) {
		int result = -1; // 기본값
		
		Member m = memberMapper.getMember(id);

		// 회원이 없는 경우 : -1
		if(m == null) {
			return result;
		}
		
		// 회원이 있고 비밀번호가 맞는 경우 : 1 비밀번호가 틀린경우 : 0
		if(passwordEncoder.matches(pass, m.getPass())) {
			result = 1;
		} else { 
			result = 0;
		}
		
		return result;		
	}
		
	// 회원 정보 
	public Member getMember(String id) {	
		
		return memberMapper.getMember(id);	
	}

	// id 중복 체크
	public boolean overlapIdCheck(String id) {
		Member member = memberMapper.getMember(id);
		log.info("overlapIdCheck - member : " + member);
		if(member == null) {
			return false;
		}
		return true;
	}
	
	// nickname 중복 체크
	public boolean overlapNicknameCheck(String nickname) {
		Member member = memberMapper.getMember2(nickname);
		log.info("overlapNicknameCheck - member : " + member);
		if(member == null) {
			return false;
		}
		return true;
	}
	
	// 회원 가입
	public void addMember(Member member) {
		member.setPass(passwordEncoder.encode(member.getPass()));
		log.info(member.getPass());
		memberMapper.addMember(member);
	}
	
	// 회원 비밀번호 확인
	public boolean memberPassCheck(String id, String pass) {
		String dbPass = memberMapper.memberPassCheck(id);
		boolean result = false;

		if(passwordEncoder.matches(pass, dbPass)) {
			result = true;
		}
		
		return result;
	}
	
	// 회원 수정
	public void updateMember(Member member) {	
		member.setPass(passwordEncoder.encode(member.getPass()));
		log.info(member.getPass());
		memberMapper.updateMember(member);
	}
	
	
}
