package doself.admin.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.util.Pageable;

@Mapper
public interface MemberMapper {

	List<MemberLog> getMemberLogList();
	
	// 검색조건에 맞는 회원 출력
	List<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	// 검색조건에 맞는 회원로그 출력
	List<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//페이징
	// 멤버 총 갯수
	int getCntMemberList();
	// 멤버 조회
	List<Member> getMember(Pageable pageable);
}
