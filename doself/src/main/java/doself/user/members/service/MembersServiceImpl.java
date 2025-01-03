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

	
	
}
