package doself.user.members.service;

import doself.user.members.domain.Members;
import doself.user.members.domain.TicketList;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface MembersService {

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	//회원 정보 수정
	public void modifyMember(Members member);

	//public int passwordChk(Members member);

	public boolean passwordChk(String memberId, String oldMemberPw);

	public boolean updatePassword(String memberId, String oldMemberPw);
	
	//회원 티켓정보 조회
	public PageInfo<TicketList> getTicketHistory(String memberId, Pageable pageable, String startDate, String endDate);

	// 회원탈퇴
	public void removeMemberById(String memberId);
	
	

	
}
