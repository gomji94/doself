package doself.user.members.service;

import org.springframework.stereotype.Service;

import doself.user.members.domain.Members;
import doself.user.members.mapper.MembersMapper;
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
		//int result = membersMapper.modifyMember(member);
		//return result;
		membersMapper.modifyMember(member);
	}

	@Override
	public boolean passwordChk(String memberId, String oldMemberPw) {
		System.out.println("**************"+ memberId + oldMemberPw); 
	    // DB에 저장된 비밀번호 가져오기
	    return membersMapper.passwordChk(memberId, oldMemberPw);
	}
		 
	@Override
	public boolean updatePassword(String memberId, String newMemberPw) {
		// 새 비밀번호 업데이트
		return membersMapper.updatePassword(memberId, newMemberPw);
	}




	
	
}
