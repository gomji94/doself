package doself.user.login.service;

import java.util.Map;

public interface LoginService {

	// 특정회원 검증
	Map<String, Object> matchedMember(String mbrId, String mbrPw);
	
}
