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
	
	//특정 회원 조회
	Member getMemberInfoByMbrId(String mbrId);
	
	//특정 회원 수정
	int modifyMember(Member member);
	
	//회원 삭제
	int deleteMember(String mbrId);
	
	//회원등급 조회
	List<Map<String, Object>> getMemberMgCodeList();
	//연령대 카테고리 조회
	List<Map<String, Object>> getAgeCategoryList();
	
	//모든멤버 조회
	List<Member> getAllMemberList();
	
	// 매년 1월1일 나이값 체크하여 연령대 변경
	void updateAcNum(Member member);
}
