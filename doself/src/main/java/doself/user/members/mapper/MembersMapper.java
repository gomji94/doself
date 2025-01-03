package doself.user.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import doself.user.members.domain.Members;

@Mapper
public interface MembersMapper {

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	//회원 수정
	public int modifyMember(Members member);
	
	//회원 비밀번호 검증
	public int passwordChk(Members member);
	
	// 특정회원 탈퇴
	public int removeLoginHistoryById(String memberId);
	
	
}
