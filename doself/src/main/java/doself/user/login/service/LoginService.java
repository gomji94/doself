package doself.user.login.service;

import java.util.Map;

import doself.admin.member.domain.Member;

public interface LoginService {

	// 특정회원 검증
	Map<String, Object> matchedMember(String mbrId, String mbrPw);
	
	// 회원가입
	void createMember(Member member);
	
}
