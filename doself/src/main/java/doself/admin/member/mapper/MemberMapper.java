package doself.admin.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;

@Mapper
public interface MemberMapper {
	
	// 검색조건에 맞는 회원 출력
	List<Member> getMemberList(Map<String, Object> searchMap);
	
	// 검색조건에 맞는 회원로그 출력
	List<MemberLog> getMemberLogList(Map<String, Object> searchMap);
	
	//페이징
	// 멤버 총 갯수
	int getCntMemberList(Map<String, Object> searchMap);
	// 멤버로그 총 갯수
	int getCntMemberLogList(Map<String, Object> searchMap);
}
