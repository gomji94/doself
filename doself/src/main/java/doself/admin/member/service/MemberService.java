package doself.admin.member.service;

import java.util.List;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;

public interface MemberService {
	
	// 멤버 검색조회
	public List<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate);
	public List<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate);
	List<MemberLog> getMemberLogList();
}
