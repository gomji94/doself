package doself.admin.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.admin.member.mapper.MemberMapper;
import doself.user.login.mapper.LoginMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;
	private final LoginMapper loginMapper;

	
	//멤버 검색 조회
	@Override
	public PageInfo<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "mbrId" 	-> searchType = "m.mbr_id";
			case "mbrName" 	-> searchType = "m.mbr_name";
			case "mgName" 	-> searchType = "mg.mg_name";
			case "acName" 	-> searchType = "ac.ac_name";		
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = memberMapper.getCntMemberList(searchMap);		
		List<Member> memberList = memberMapper.getMemberList(searchMap);
		
		return new PageInfo<>(memberList, pageable, rowCnt);
    }

	//멤버 로그 검색 조회
	@Override
	public PageInfo<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {	
		case "mbrName" 	-> searchType = "m.mbr_name";		
	}
	
	Map<String, Object> searchMap = new HashMap<String, Object>();
	searchMap.put("searchType", searchType);
	searchMap.put("searchKeyword", searchKeyword);
	searchMap.put("startDate", startDate);
	searchMap.put("endDate", endDate);
	searchMap.put("pageable", pageable);
	
	int rowCnt = memberMapper.getCntMemberLogList(searchMap);
	List<MemberLog> memberLogList = memberMapper.getMemberLogList(searchMap);
	
		return new PageInfo<>(memberLogList, pageable, rowCnt);
	}

	// 특정회원정보 조회
	@Override
	public Member getMemberInfoByMbrId(String mbrId) {
		
		return memberMapper.getMemberInfoByMbrId(mbrId);
	}
	// 특정회원정보 수정
	@Override
	public void modifyMember(Member member) {
		
		memberMapper.modifyMember(member);
	}
	
	// 회원삭제
	@Override
	public void deleteMember(String mbrId) {
		
		memberMapper.deleteMember(mbrId);
	}


	// 회원등급 조회
	@Override
	public List<Map<String, Object>> getMemberMgCodeList() {
		
		return memberMapper.getMemberMgCodeList();
	}

	//연령대 카테고리
	@Override
	public List<Map<String, Object>> getAgeCategoryList() {
		
		return memberMapper.getAgeCategoryList();
	}

	//매년 1월1일에 나이 체크하여 연령대값 변경
	@Override
	public void everyYearCheck() {
		
		List<Member> allMemberList = memberMapper.getAllMemberList();
		for(Member member : allMemberList) {
			int age = loginMapper.getAgeByBirthDate(member.getMbrBirthDate());
			String acNum ="";
			
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
			memberMapper.updateAcNum(member);
		}
		
	}
	
}
