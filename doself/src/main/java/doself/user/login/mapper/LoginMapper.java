package doself.user.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.member.domain.Member;


@Mapper
public interface LoginMapper {

	// 특정회원 조회
	Member getMemberInfoById(String mbrId);

	// 회원가입
	int createMember(Member member);
	
	// 생년월일로 연령대 번호 받아오기
	int getAgeByBirthDate(String birthDate);
	
	// 
	void createMemberLoginLog(String keyValue, String memberId, String memberIp);
}
