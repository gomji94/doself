package doself.admin.member.service;

import java.util.List;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface MemberService {
	
	// 멤버 검색조회
	PageInfo<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	PageInfo<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);

}
