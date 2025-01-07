package doself.user.login.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.member.domain.Member;
import doself.admin.member.mapper.MemberMapper;
import doself.user.login.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	private final LoginMapper loginMapper;
	private final MemberMapper memberMapper;

	@Override
	public Map<String, Object> matchedMember(String mbrId, String mbrPw) {
		
		boolean isMatched = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Member memberInfo = loginMapper.getMemberInfoById(mbrId);
		if(memberInfo != null) {
			String checkPw = memberInfo.getMbrPw();
			if(checkPw.equals(mbrPw)) {
				isMatched = true;
				resultMap.put("memberInfo", memberInfo);
			}
		}
		resultMap.put("isMatched", isMatched);
		
		return resultMap;
	} 

}
