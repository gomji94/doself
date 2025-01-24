package doself.user.login.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.member.domain.Member;
import doself.user.login.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	private final LoginMapper loginMapper;

	// 로그인
	@Override
	public Map<String, Object> matchedMember(String mbrId, String mbrPw) {
		
		boolean isMatched = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Member memberInfo = loginMapper.getMemberInfoById(mbrId);
		if(memberInfo != null) {
			String checkDeleted = memberInfo.getIsDeleted();
			String checkPw = memberInfo.getMbrPw();
			
			if(checkDeleted.equals("active")) {
				if(checkPw.equals(mbrPw)) {
					isMatched = true;
					resultMap.put("memberInfo", memberInfo);
				}			
			}
		}
		resultMap.put("isMatched", isMatched);
		
		return resultMap;
	}
	
	//아이디 중복체크
	@Override
	public boolean isMemberById(String mbrId) {
		
		return loginMapper.isMemberById(mbrId);
	} 
	
	//회원가입
		@Override
		public void createMember(Member member) {
			String birthDate = member.getMbrBirthDate();
			
			int age = loginMapper.getAgeByBirthDate(birthDate);
			String acNum="";
			// 연령대 판별
			if(age <= 6) {
				acNum = "ac_001";
			}
			else if(age <= 13) {
				acNum = "ac_002";
			}
			else if(age <= 18) {
				acNum = "ac_003";
			}
			else if(age <= 34) {
				acNum = "ac_004";
			}
			else if(age <= 49) {
				acNum = "ac_005";
			}
			else if(age <= 64) {
				acNum = "ac_006";
			}
			else {
				acNum = "ac_007";
			}
			
			member.setAcNum(acNum);
			
			loginMapper.createMember(member);
		}


}
