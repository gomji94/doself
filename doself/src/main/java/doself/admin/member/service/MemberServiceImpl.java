package doself.admin.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.admin.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;

	@Override
	public List<MemberLog> getMemberLogList() {
		
		return memberMapper.getMemberLogList();
	}

	//멤버 검색 조회
	@Override
	public List<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate) {
        
		return memberMapper.getMemberList(searchType, searchKeyword, startDate, endDate);
    }

	//멤버 로그 검색 조회
	@Override
	public List<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return memberMapper.getMemberLogList(searchType, searchKeyword, startDate, endDate);
	}

}
