package doself.user.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import doself.user.members.domain.Members;

@Mapper
public interface MembersMapper {

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	//회원 수정
	public int modifyMember(Members member);
	
	public boolean passwordChk(String memberId, String oldMemberPw);
	
	public boolean updatePassword(String memberId, String newMemberPw);

	// 특정회원 탈퇴
	public int removeLoginHistoryById(String memberId);


	
	
}
