package doself.user.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import doself.user.members.domain.Members;

@Mapper
public interface MembersMapper {

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	// 특정회원 수정
	int modifyMember(Members member);
	
	// 특정회원 탈퇴
	int removeLoginHistoryById(String memberId);
	
	
	
}
