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

	
	
	
}
