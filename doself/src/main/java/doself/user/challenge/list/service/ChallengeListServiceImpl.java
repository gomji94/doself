package doself.user.challenge.list.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.common.mapper.CommonMapper;
import doself.file.domain.Files;
import doself.file.mapper.FilesMapper;
import doself.file.util.FilesUtils;
import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.domain.AddChallengeMember;
import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.mapper.ChallengeListMapper;
import doself.util.CardPageInfo;
import doself.util.CardPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
//jdbc가 추가된 상태에서만 트랜잭셔널 어노테이션 실행됨
//전체 적용하려면 여기에 작성 || 특정 메소드에만 설정하고 싶으면 메소드 상단에 배치
@Transactional
//log.이 가능한 이유는 아래의 어노테이션 때문
@RequiredArgsConstructor
@Slf4j
public class ChallengeListServiceImpl implements ChallengeListService {

	private final ChallengeListMapper challengeListMapper;
	private final CommonMapper commonMapper;
	private final FilesUtils filesUtils;
	private final FilesMapper filesMapper;
	
	// 챌린지 조회 리스트 반환
	@Override
	public List<ChallengeList> getChallengeList() {
		List<ChallengeList> challengeList = challengeListMapper.getChallengeList();
		if (challengeList == null || challengeList.isEmpty()) {
	        //log.warn("챌린지 리스트 정보가 없습니다.");
	        return challengeList;
	    }
		
		// 현재 대기중 || 진행중인 챌린지만 조회되게 함(진행중/진행중(리더 양도))
		challengeList.forEach(challengeInfo -> {
	        String challengeStatus = challengeInfo.getChallengeStatus();
	        if("cs_002".equals(challengeStatus) || "cs_003".equals(challengeStatus) || "cs_004".equals(challengeStatus));
	    });
		
		challengeList.forEach(challenge -> {
	        int currentMemberCount = challengeListMapper.getCurrentMemberCount(challenge.getChallengeCode());
	        log.info("챌린지 코드: {}, 참여 멤버 수: {}", challenge.getChallengeCode(), currentMemberCount);
	        challenge.setChallengeCurrentMember(currentMemberCount); // 현재 멤버 수 반영
	    });
		
		return challengeList;
	}

	// 특정 챌린지 카드 조회
	@Override
	public ChallengeDetailView getChallengeListView(String challengeCode) {
		ChallengeDetailView challengeDetail = challengeListMapper.selectChallengeDetail(challengeCode);
	    if (challengeDetail != null) {
	        // 참여 멤버 수를 가져와서 설정
	        int currentMemberCount = challengeListMapper.getCurrentMemberCount(challengeCode);
	        challengeDetail.setChallengeCurrentMember(currentMemberCount);
	    }
	    return challengeDetail;
	}

	// 챌린지 생성(등록)
	@Override
	public void addChallenge(MultipartFile files, AddChallenge addChallenge) {
		
		Files fileInfo = filesUtils.uploadFile(files);
		if(fileInfo != null) {
			String formattedKeyNum = commonMapper.getPrimaryKey("file_", "files", "file_idx");
			fileInfo.setFileIdx(formattedKeyNum);
			filesMapper.addfile(fileInfo);
		}
		
		// 코드 번호 생성 앞자리, 테이블명, 컬럼명
		String formattedKeyNum = commonMapper.getPrimaryKey("cg_", "challenge_group", "cg_num");
		addChallenge.setChallengeCode(formattedKeyNum);
		
		// 챌린지 난이도 리스트 번호로 받아오기(숫자 타입으로 데이터 받아와서 문자열로 변환 후 전송)
		String addChallengeResult = String.format("ctl_%03d", Integer.parseInt(addChallenge.getChallengeTopicLevelCode()));
		addChallenge.setChallengeTopicLevelCode(addChallengeResult);
		
		// 종료일 설정 (시작일 + 14일)
		LocalDate startDate = addChallenge.getChallengeStartDate().toInstant()
		       							  .atZone(ZoneId.systemDefault())
		       							  .toLocalDate();
		LocalDate endDate = startDate.plusDays(14);
	    
	    addChallenge.setChallengeGroupLike(0);			// 챌린지 좋아요(기본값 0)
	    addChallenge.setChallengeStatusCode("cs_004");  // 챌린지 상태 분류 번호(초기 기본값 cs_004)
	    addChallenge.setChallengeRewardCheck("");		// 보상 지급 여부(빈값) → 완료시 적합 여부 판단 후 입력
	    addChallenge.setChallengeEndDate(Date.valueOf(endDate));	// 챌린지 종료일(시작일 기준 +14일)
	    addChallenge.setChallengeFileIdx(fileInfo.getFileIdx());
	    
		//log.info(">>> location/service >>> addChallenge : {}", addChallenge);
		
		challengeListMapper.addChallenge(addChallenge);
	}
	
	// 챌린지 멤버 추가
	@Override
	public boolean addChallengeMember(AddChallengeMember addChallengeMember) {
		String challengeStatusCode = challengeListMapper.selectChallengeStatus(addChallengeMember);
		if (challengeStatusCode == null || challengeStatusCode.isEmpty()) {
	        throw new IllegalArgumentException("유효하지 않은 챌린지 상태 코드입니다.");
	    }
	    addChallengeMember.setChallengeStatusCode(challengeStatusCode);
	    log.info(">>> location/controller >>> challengeCode : {}", addChallengeMember);
		
		// 참여 중인 멤버인지 확인
	    List<AddChallengeMember> existingMembers = challengeListMapper.getChallengeMembers(addChallengeMember.getChallengeCode());
	    for (AddChallengeMember member : existingMembers) {
	        if (member.getChallengeMemberId().equals(addChallengeMember.getChallengeMemberId())) {
	            return false; // 이미 참여 중
	        }
	    }
	    
	    // 현재 멤버 수와 최대 멤버 수 체크
	    int currentMemberCount = challengeListMapper.getCurrentMemberCount(addChallengeMember.getChallengeCode());
	    int maxMemberCount = challengeListMapper.getMaxMemberCount(addChallengeMember.getChallengeCode());
	    if (currentMemberCount >= maxMemberCount) {
	        throw new IllegalArgumentException("인원 초과로 챌린지에 참여할 수 없습니다.");
	    }
	    
	    String formattedKeyNum = commonMapper.getPrimaryKey("cgm_", "challenge_group_member", "cgm_num");
	    addChallengeMember.setChallengeMemberCode(formattedKeyNum);
	    
	    // 상태 코드 유효성 검사
	    List<Map<String, Object>> statusList = getChallengeStatusList();
	    Map<Integer, String> statusMap = statusList.stream()
	        .collect(Collectors.toMap(
	            map -> ((Number) map.get("challengeStatusCode")).intValue(),
	            map -> map.get("challengeStatusInfo").toString()
	        ));
	    Integer parsedStatusCode = Integer.valueOf(addChallengeMember.getChallengeStatusCode().replace("cs_", ""));
	    if (!statusMap.containsKey(parsedStatusCode)) {
	        throw new IllegalArgumentException("유효하지 않은 챌린지 상태 코드입니다.");
	    }
	    
	    // 챌린지 상태 업데이트
	    updateChallengeStatus(addChallengeMember.getChallengeCode(), addChallengeMember.getChallengeStatusCode());
	    
	    // 유효한 상태 코드로 설정
	    addChallengeMember.setChallengeStatusCode(String.format("cs_%03d", parsedStatusCode));

	    challengeListMapper.addChallengeMember(addChallengeMember);
	    return true;
	}
	
	// 이미 참여 중인지 확인
	@Override
	public boolean isAlreadyParticipated(AddChallengeMember addChallengeMember) {
	    List<AddChallengeMember> existingMembers = challengeListMapper.getChallengeMembers(addChallengeMember.getChallengeCode());
	    return existingMembers.stream()
	            .anyMatch(member -> member.getChallengeMemberId().equals(addChallengeMember.getChallengeMemberId()));
	}
	
	// 챌린지 상태 업데이트
	@Override
	public void updateChallengeStatus(String challengeCode, String statusCode) {
		challengeListMapper.updateChallengeStatus(challengeCode, statusCode);
	}
	
	// 상태 업데이트 로직 예시
	@Override
	public void updateChallengeStatuses() {
		// 진행 중인 챌린지 리스트 조회
	    List<ChallengeList> challenges = challengeListMapper.getChallengeList();

	    for (ChallengeList challenge : challenges) {
	        // 참여 멤버 수 조회
	        int currentMemberCount = getCurrentMemberCount(challenge.getChallengeCode());
	        log.info("챌린지 코드: {}, 현재 멤버 수: {}", challenge.getChallengeCode(), currentMemberCount);

	        // 챌린지 시작일과 현재 날짜 비교
	        LocalDate startDate = challenge.getChallengeStartDate().toInstant()
	                                       .atZone(ZoneId.systemDefault())
	                                       .toLocalDate();
	        LocalDate currentDate = LocalDate.now();
	        log.info("챌린지 코드: {}, 시작일: {}, 현재 날짜: {}", challenge.getChallengeCode(), startDate, currentDate);

	        // 조건: 멤버 수 >= 5 && 시작일 == 현재 날짜
	        if (currentMemberCount >= 5 && startDate.equals(currentDate)) {
	            // 챌린지 상태 업데이트
	            updateChallengeStatus(challenge.getChallengeCode(), "cs_002");
	            log.info("챌린지 상태 업데이트 완료: 챌린지 코드 {}, 상태 코드 {}", challenge.getChallengeCode(), "cs_002");
	        } else {
	            log.info("업데이트 조건 불충족: 챌린지 코드 {}, 멤버 수: {}, 시작일 조건: {}", 
	                challenge.getChallengeCode(), currentMemberCount, startDate.equals(currentDate));
	        }
	    }
	}
	
	// 챌린지 리스트 페이지
	@Override
	public CardPageInfo<ChallengeList> getChallengeList(CardPageable cardPageable) {
	    int rowCnt = challengeListMapper.getCntChallengeList();
	    List<ChallengeList> challengeList = challengeListMapper.getChallengeList(cardPageable);
	    //log.info(">>> location/service >>> challengeList : {}", challengeList);
	    
	    return new CardPageInfo<>(challengeList, cardPageable, rowCnt);
	}

	// 챌린지 생성 시, 이름 중복 체크
	@Override
	public boolean isNameDuplicate(String challengeName) {
		return challengeListMapper.isNameDuplicate(challengeName);
	}

	// 챌린지 주제 리스트
	@Override
	public List<Map<String, String>> getChallengeTopicList() {
		List<Map<String, String>> topicList = challengeListMapper.getChallengeTopicList();
	    //log.info(">>> location/service >>> topicList: {}", topicList); // 로그로 확인
	    return topicList;
	}

	// 챌린지 난이도 리스트
	@Override
	public List<Map<String, String>> getChallengeLevelList() {
		List<Map<String, String>> levelList = challengeListMapper.getChallengeLevelList();
		//log.info(">>> location/service >>> levelList: {}", levelList); // 로그로 확인
	    return levelList;
	}

	// 챌린지 상태 리스트
	@Override
	public List<Map<String, Object>> getChallengeStatusList() {
	    List<Map<String, Object>> statusList = challengeListMapper.getChallengeStatusList();
	    //log.info(">>> location/service >>> statusList: {}", statusList); // 로그로 확인
	    return statusList;
	}

	// 챌린지 참여 멤버수
	@Override
	public int getCurrentMemberCount(String challengeCode) {
	    return challengeListMapper.getCurrentMemberCount(challengeCode);
	}


//	// 파일 삭제
//	@Override
//	public void deleteFile(Files files) {
//		String path = files.getFilePath();
//		Boolean isDelete = filesUtils.deleteFileByPath(path);
//		if(isDelete) filesMapper.deleteFileByIdx(files.getFileIdx());   // 값이 true로 넘어온다면 delete 쿼리문 실행
//	}
}
