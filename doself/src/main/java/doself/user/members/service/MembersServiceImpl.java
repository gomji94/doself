package doself.user.members.service;

import java.util.List;

import org.springframework.stereotype.Service;

import doself.user.members.domain.Members;
import doself.user.members.domain.TicketList;
import doself.user.members.mapper.MembersMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MembersServiceImpl implements MembersService {

	private final MembersMapper membersMapper;
	
	// 회원 정보 조회
	@Override
	public Members getMemberInfoById(String memberId) {
		return membersMapper.getMemberInfoById(memberId);
	}
	
	// 회원 수정
	@Override
	public void modifyMember(Members member) {
		
		membersMapper.modifyMember(member);
	}

	@Override
	public boolean passwordChk(String memberId, String oldMemberPw) {
	    return membersMapper.passwordChk(memberId, oldMemberPw);
	}
		 
	@Override
	public boolean updatePassword(String memberId, String newMemberPw) {
		return membersMapper.updatePassword(memberId, newMemberPw);
	}

	//회원 티켓정보 조회
	@Override
	public PageInfo<TicketList> getTicketHistory(String memberId, Pageable pageable) {
		int rowCnt = membersMapper.getCntTicketHistory();
		List<TicketList> ticketList = membersMapper.getTicketListById(memberId, pageable);
		return new PageInfo<>(ticketList, pageable, rowCnt);
	}




	
	
}
