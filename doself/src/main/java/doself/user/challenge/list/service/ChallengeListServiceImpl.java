package doself.user.challenge.list.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.common.mapper.CommonMapper;
import doself.file.domain.Files;
import doself.file.mapper.FilesMapper;
import doself.file.util.FilesUtils;
import doself.user.challenge.list.domain.AddChallenge;
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
		
		// 현재 진행중인 챌린지만 조회되게 함(cs_status → 진행중/진행중(리더 양도))
		challengeList.forEach(challengeInfo -> {
	        String challengeStatus = challengeInfo.getChallengeStatus();
	        if("cs_002".equals(challengeStatus) || "cs_003".equals(challengeStatus));
	    });
		return challengeList;
	}

	// 특정 챌린지 카드 조회
	@Override
	public ChallengeDetailView getChallengeListView(String challengeCode) {
		return challengeListMapper.selectChallengeDetail(challengeCode);
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
		
		// 챌린지 주제 리스트 번호로 받아오기
//		String addChallengeResult = String.format("ct_%03d", addChallenge.getChallengeTopicCode());
//		addChallenge.setChallengeTopicCode(addChallengeResult);
		
		// 챌린지 난이도 리스트 번호로 받아오기(숫자 타입으로 데이터 받아와서 문자열로 변환 후 전송)
		String addChallengeResult = String.format("ctl_%03d", Integer.parseInt(addChallenge.getChallengeTopicLevelCode()));
		addChallenge.setChallengeTopicLevelCode(addChallengeResult);
		
		// 종료일 설정 (시작일 + 14일)
		LocalDate startDate = addChallenge.getChallengeStartDate().toInstant()
		        .atZone(ZoneId.systemDefault())
		        .toLocalDate();
		LocalDate endDate = startDate.plusDays(14);
	    
	    // 기본값 설정
	    addChallenge.setChallengeCurrentMember(0);      // 챌린지 현재 멤버수(기본값 0)
	    addChallenge.setChallengeGroupLike(0);			// 챌린지 좋아요(기본값 0)
	    addChallenge.setChallengeStatusCode("cs_004");  // 챌린지 상태 분류 번호(초기 기본값 cs_004)
	    addChallenge.setChallengeRewardCheck("");		// 보상 지급 여부(빈값) → 완료시 적합 여부 판단 후 입력
	    addChallenge.setChallengeEndDate(Date.valueOf(endDate));
	    addChallenge.setChallengeFileIdx(fileInfo.getFileIdx());
	    
		log.info(">>> location/service >>> addChallenge : {}", addChallenge);
		
		challengeListMapper.addChallenge(addChallenge);
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
	    log.info(">>> location/service >>> topicList: {}", topicList); // 로그로 확인
	    return topicList;
	}

	// 챌린지 난이도 리스트
	@Override
	public List<Map<String, String>> getChallengeLevelList() {
		List<Map<String, String>> levelList = challengeListMapper.getChallengeLevelList();
		log.info(">>> location/service >>> levelList: {}", levelList); // 로그로 확인
	    return levelList;
	}
	
	

	// 파일 삭제
	@Override
	public void deleteFile(Files files) {
		String path = files.getFilePath();
		Boolean isDelete = filesUtils.deleteFileByPath(path);
		if(isDelete) filesMapper.deleteFileByIdx(files.getFileIdx());   // 값이 true로 넘어온다면 delete 쿼리문 실행
	}
}
