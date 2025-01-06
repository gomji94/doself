package doself.admin.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.admin.member.mapper.MemberMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;

	
	//멤버 검색 조회
	@Override
	public PageInfo<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
        
		int rowCnt = memberMapper.getCntMemberList();
		List<Member> memberList = memberMapper.getMemberList(searchType, searchKeyword, startDate, endDate, pageable);
		return new PageInfo<>(memberList, pageable, rowCnt);
    }

	//멤버 로그 검색 조회
	@Override
	public List<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return memberMapper.getMemberLogList(searchType, searchKeyword, startDate, endDate);
	}

	
	// 페이징
	@Override
	public PageInfo<Member> getCntMemberList(Pageable pageable) {
		
		int rowCnt = memberMapper.getCntMemberList();
		List<Member> memberList = memberMapper.getMember(pageable);
		return new PageInfo<>(memberList, pageable, rowCnt);
	}

	
}
