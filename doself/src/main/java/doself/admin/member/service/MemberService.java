package doself.admin.member.service;

import java.util.List;
import java.util.Map;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface MemberService {
	
	// 멤버 검색조회
	PageInfo<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	PageInfo<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	// 특정 회원 조회
	Member getMemberInfoByMbrId(String mbrId);
	
	// 특정 회원 정보 수정
	void modifyMember(Member member);
	
	// 회원 삭제
	void deleteMember(String mbrId);
	
	//회원 등급 조회
	List<Map<String, Object>> getMemberMgCodeList();
	//연령대 카테고리 검색
	List<Map<String, Object>> getAgeCategoryList();

	//매년 1월1일 나이 체크하여 연령대 값 변경
	void everyYearCheck();
}
