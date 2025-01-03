package doself.user.members.service;

import doself.user.members.domain.Members;

public interface MembersService {

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	//회원 정보 수정
	public int modifyMember(Members member);

	public int passwordChk(Members member);
	
}
