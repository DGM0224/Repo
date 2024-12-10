package com.springbootproject.bbs.configurations;

import org.springframework.context.annotation.Bean;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	}
	
	// 스프링 시큐리티를 적용시 모든 요청 URL에서 인증을 시도해 로그인 창이 나타남
	// 별도로 인증하지 않도록 설정하면 사이트의 모든 페이지에 접근 가능		
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			// 모든 경우에 대해 접근 허용하게 설정
							
			// CSRF(cross site request forgery)는 웹사이트 취약점 공격을 방지하기 위해
			// 서버 상태를 변화시킬 수 있는 Post, Put, Patch, Delete 등의 요청을 보호하는 기술
			// 스프링 자체에서 해커들이 데이터를 못가져가게 자동 설정되어 있으나
			// 우리는 폼을 전송해야 하기에 csrf를 무시하게 설정해준 것
			.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
			// 위는 지정해준 url에 대해 csrf를 해제
			
			.csrf(csrf -> csrf.disable())
			// 모든 url에 대해서 csrf를 해제
		
			.logout(logout -> logout 
								.logoutUrl("/logout") // logout으로 요청이 오면 로그아웃시킴 근데 이거 기본설정으로 되어있음
								.logoutSuccessUrl("/loginForm") // 로그아웃 성공 리다이렉트 페이지
								.invalidateHttpSession(true)); // 기존 세션을 삭제할지 여부
			
		return http.build();		
	}

}
