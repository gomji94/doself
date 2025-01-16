package doself.user.members.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.common.mapper.CommonMapper;
import doself.file.domain.Files;
import doself.file.mapper.FilesMapper;
import doself.file.util.FilesUtils;
import doself.user.members.domain.FeedList;
import doself.user.members.domain.Members;
import doself.user.members.domain.PointList;
import doself.user.members.domain.TicketList;
import doself.user.members.mapper.MembersMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class MembersServiceImpl implements MembersService {

	private final MembersMapper membersMapper;
	private final CommonMapper commonMapper;
	private final FilesUtils filesUtils;
	private final FilesMapper filesMapper;
	
	// 회원 검증
	@Override
	public Map<String, Object> matchedMember(String memberId, String memberPw) {
		
		boolean isMatched = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Members memberInfo = membersMapper.getMemberInfoById(memberId);
		if(memberInfo != null) {
			String checkPw = memberInfo.getMemberPw();
			if(checkPw.equals(memberPw)) {
				isMatched = true;
				resultMap.put("memberInfo", memberInfo);
			}
		}
		resultMap.put("isMatched", isMatched);
		return resultMap;
	}
	
	// 회원 정보 조회
	@Override
	public Members getMemberInfoById(String memberId) {
		return membersMapper.getMemberInfoById(memberId);
	}
	
	// 회원 수정
	@Override
	public void modifyMember(Members member, MultipartFile file) {
		Files fileInfo = filesUtils.uploadFile(file);
		if(fileInfo != null) {
			String formattedKeyNum = commonMapper.getPrimaryKey("file_", "files", "file_idx");
			fileInfo.setFileIdx(formattedKeyNum);
			filesMapper.addfile(fileInfo);
		}
		member.setProfileFileIdx(fileInfo.getFileIdx());
		
		membersMapper.modifyMember(member);
	}
	
	// 회원삭제
	@Override
	public void removeMemberById(String memberId) {
		membersMapper.removeMemberById(memberId);
	}
	
	//회원 티켓정보 조회
	@Override
	public PageInfo<TicketList> getTicketHistory(String memberId, Pageable pageable,String startDate, String endDate) {
		int rowCnt = membersMapper.getCntTicketHistory(memberId,startDate,endDate);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("rowPerPage", pageable.getRowPerPage());
		paramMap.put("offset", pageable.getOffset());
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		List<TicketList> ticketList = membersMapper.getTicketListById(paramMap);
		return new PageInfo<>(ticketList, pageable, rowCnt);
	}
	
	//회원 포인트정보 조회
	@Override
	public PageInfo<PointList> getPointHistory(String memberId, Pageable pageable, String startDate, String endDate) {
		int rowCnt = membersMapper.getCntPointHistory(memberId, startDate, endDate);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("rowPerPage", pageable.getRowPerPage());
		paramMap.put("offset", pageable.getOffset());
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		List<PointList> pointList = membersMapper.getPointListById(paramMap);

		return new PageInfo<>(pointList, pageable, rowCnt);
	}
	
	//회원 피드리스트 조회
	@Override
	public List<FeedList> getMemberFeedListById(String memberId) {
		
		
		return membersMapper.getMemberFeedListById(memberId);
	}

	
	

	
	
		
		


	
	
}
