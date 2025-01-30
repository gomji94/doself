package doself.user.challenge.feed.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.common.mapper.CommonMapper;
import doself.file.domain.Files;
import doself.file.mapper.FilesMapper;
import doself.file.util.FilesUtils;
import doself.user.challenge.feed.domain.AddChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeMemberWarning;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.domain.ChallengeTotalProgress;
import doself.user.challenge.feed.domain.ParticipateChallengeList;
import doself.user.challenge.feed.mapper.AdminChallengeFeedMapper;
import doself.user.challenge.feed.mapper.ChallengeFeedMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
//jdbc가 추가된 상태에서만 트랜잭셔널 어노테이션 실행됨
//전체 적용하려면 여기에 작성 || 특정 메소드에만 설정하고 싶으면 메소드 상단에 배치
//log.이 가능한 이유는 아래의 어노테이션 때문
@RequiredArgsConstructor
@Slf4j
public class ChallengeFeedServiceImpl implements ChallengeFeedService {

	private final ChallengeFeedMapper challengeFeedMapper;
	// 요청 데이터 한번만 가져오도록 캐싱 적용
	private final ConcurrentMap<String, List<ChallengeProgress>> challengeProgressCache = new ConcurrentHashMap<>();
	private final CommonMapper commonMapper;
	private final FilesUtils filesUtils;
	private final FilesMapper filesMapper;
	private final AdminChallengeFeedMapper adminChallengeFeedMapper;
	
	// 로그인된 챌린지 멤버 아이디
	@Override
	@Transactional
	public String getChallengeCodeByMemberId(String challengeMemberId) {
	    return challengeFeedMapper.getChallengeCodeByMemberId(challengeMemberId);
	}
	
	// 로그인된 사용자가 참여 중인 챌린지 리스트 가져오기
	@Override
	@Transactional
	public List<ParticipateChallengeList> getChallengeListByMemberId(String memberId) {
		// 사용자가 참여한 챌린지 리스트 조회
	    List<ParticipateChallengeList> challengeList = challengeFeedMapper.getChallengeListByMemberId(memberId);
	    
	    // 현재 멤버 수를 각 챌린지에 설정
	    challengeList.forEach(challenge -> {
	        int currentMemberCount = challengeFeedMapper.getCurrentMemberCount(challenge.getChallengeCode());
	        challenge.setChallengeCurrentMember(currentMemberCount);
	    });

	    return challengeList;
	}
	
	// 피드 페이징
	@Override
	@Transactional
	public PageInfo<ChallengeFeed> getChallengeFeedPage(String challengeCode, Pageable pageable) {
		int rowCnt = challengeFeedMapper.getChallengeFeedCount(challengeCode);
		
		Map<String, Object> params = new HashMap<>();
		params.put("challengeCode", challengeCode);
		params.put("pageable", pageable);
		
		//List<ChallengeFeed> feeds = challengeFeedMapper.getChallengeFeed(params);
		List<ChallengeFeed> challengeFeedList = challengeFeedMapper.getChallengeListByChallengeCode(params);
		
		return new PageInfo<>(challengeFeedList, pageable, rowCnt);
	}
	
	// 챌린지 피드
	@Override
	@Transactional
	public List<ChallengeFeed> getChallengeFeed(Map<String, Object> params) {
        return challengeFeedMapper.getChallengeFeed(params);
    }

	// 참여 멤버 상위 3명 표시
	@Override
	@Transactional
	public List<ChallengeTotalProgress> getTopParticipants(String challengeCode) {
		List<ChallengeTotalProgress> memberList = challengeFeedMapper.getTopParticipants(challengeCode);

//	    log.info(">>> location/serviceImpl >>> getTopParticipants >>> memberList: {}", memberList);

	    return memberList;
	}
	
	// 챌린지 경고 리스트
	@Override
	public ChallengeMemberList getWarningList(String challengeCode) {
		return challengeFeedMapper.getWarningList(challengeCode);
	}

	// 챌린지 멤버 조회
	@Override
	@Transactional
	public List<ChallengeMemberList> getMemberList(String challengeCode) {
		return challengeFeedMapper.getMemberList(challengeCode);
	}
	
	// 챌린지 경고 멤버 리스트 조회
	@Override
	public List<ChallengeMemberList> getWarningMemberList(String challengeCode) {
		return challengeFeedMapper.getWarningMemberList(challengeCode);
	}
	
	// 챌린지 멤버 경고 리스트 조회
	@Override
	public Map<String, Object> getChallengeMemberDetails(String challengeCode) {
	    Map<String, Object> response = new HashMap<>();

	    // 멤버 리스트 조회
	    List<ChallengeMemberList> memberList = challengeFeedMapper.getWarningMemberList(challengeCode);
	    response.put("memberList", memberList);
	    
	    log.info(">>> location/serviceImpl >>> getTopParticipants >>> memberList: {}", memberList);

	    return response;
	}
	
	// 챌린지 피드, 댓글 조회
	@Override
	public List<ChallengeMemberList> getFeedAndCommentContentById(String memberId) {
	    return challengeFeedMapper.getFeedAndCommentContentById(memberId);
	}

	// 챌린지 멤버 경고
	@Override
	@Transactional
    public boolean warningChallengeMember(ChallengeMemberWarning warning, String loggedInMemberId) {
		String formattedKeyNum = commonMapper.getPrimaryKey("lcmwl_", "challenge_member_feed", "lcmwl_num");
	    warning.setMemberWarningCode(formattedKeyNum);
	    warning.setWarningDate(new Date());

	    int result = challengeFeedMapper.warningChallengeMember(warning);
        return result > 0;
    }
//	warningCnt > 0 ? true : false;
	
	
	@Override
	@Transactional
	public List<ChallengeProgress> getProcessChallengeStatus(String challengeCode) {

		List<ChallengeProgress> challengeProgressList = challengeFeedMapper.getChallengeProgress(challengeCode);
	    
	    //log.info(">>> location/serviceImpl >>> challengeProgressList: {}", challengeProgressList);

	    return challengeProgressList;
	}
	
	// 불필요한 캐시 초기화
	@Transactional
	public void clearChallengeProgressCache(String challengeCode) {
	    challengeProgressCache.remove(challengeCode);
	}

	@Override
	@Transactional
	public Map<String, String> calculateDPlusAndDMinus(String challengeCode) {
		ChallengeProgress progress = challengeFeedMapper.getChallengeProgressByCode(challengeCode);
		
		log.info(">>> location/serviceImpl >>> progress: {}", progress);
		
	    if (progress == null) {
	        return Map.of("dPlus", "N/A", "dMinus", "N/A");
	    }
	    
	    LocalDate today = LocalDate.now();
	    LocalDate startDate = progress.getChallengeStartDate().toInstant()
	                                  .atZone(ZoneId.of("Asia/Seoul"))
	                                  .toLocalDate();

	    // 종료일 계산: 시작일 + 14일
	    LocalDate endDate = startDate.plusDays(14);

	    long dPlus = ChronoUnit.DAYS.between(startDate, today);
	    long dMinus = ChronoUnit.DAYS.between(today, endDate);

	    Map<String, String> dateCalculations = Map.of(
            "dPlus", "D+" + Math.max(0, dPlus),
            "dMinus", "D-" + Math.max(0, dMinus)
        );
	    
	    log.info(">>> Service >>> calculateDPlusAndDMinus >>> dateCalculations: {}", dateCalculations);
	    
	    return dateCalculations;
	}

	// 챌린지 참여 멤버수 조회
	@Override
	@Transactional
	public int getCurrentMemberCount(String challengeCode) {
	    return challengeFeedMapper.getCurrentMemberCount(challengeCode);
	}

	// 파일 처리
	@Override
	@Transactional
	public String getFilePath(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
            return "/images/default-image.png"; // 기본 이미지
        }
        return "/uploaded_files/" + fileName; // 실제 경로 반환
	}

	// 챌린지 피드 생성
	@Override
	@Transactional
	public void addChallengeFeed(MultipartFile files, AddChallengeFeed addChallengeFeed) {
		Files fileInfo = filesUtils.uploadFile(files);
		if(fileInfo != null) {
			String formattedKeyNum = commonMapper.getPrimaryKey("file_", "files", "file_idx");
			fileInfo.setFileIdx(formattedKeyNum);
			filesMapper.addfile(fileInfo);
		}
		
		// 코드 번호 생성 앞자리, 테이블명, 컬럼명
		String formattedKeyNum = commonMapper.getPrimaryKey("cmf_", "challenge_member_feed", "cmf_num");
		addChallengeFeed.setChallengeFeedCode(formattedKeyNum);
		addChallengeFeed.setChallengeFeedFileIdx(fileInfo.getFileIdx());
	    addChallengeFeed.setChallengeFeedDate(new Date());  // 현재 시간
	    addChallengeFeed.setChallengeFeedLike(0); 			// 기본값 0
	    addChallengeFeed.setChallengeFeedWarningCheck("N"); // 기본값 'N'

	    challengeFeedMapper.addChallengeFeed(addChallengeFeed);
	}
	
	// 챌린지 피드생성후 관리자 기능 실행
	@Transactional
	public void adminChallengeFeed(AddChallengeFeed addChallengeFeed) {
		 
		
		// 관리자
		
		// ...(*￣０￣)ノ챌린지내 개인기록	...(*￣０￣)ノ
			
		// 챌린지 피드생성시 실행
		// 해당아이디의 해당챌린지의 해당날짜에 데이터의 수(챌린지피드 수) 조회
		
		 int challengeFeedCount = adminChallengeFeedMapper.getChallengeFeedCountByChallengeFeed(addChallengeFeed);
		 log.info("challengeFeedCount = {}",challengeFeedCount);
		  // 챌린지내 개인기록 해당아이디, 해당챌린지, 해당날짜에 데이터가 있는지 조회 
		 int isDataMemberStat = adminChallengeFeedMapper.isDataMemberStat(addChallengeFeed);
		  log.info("isDataMemberStat = {}",isDataMemberStat);
		  // 챌린지 번호 
		 String cgNum = addChallengeFeed.getChallengeCode();
		 
		  // 해당 챌린지의 난이도 별 개수 확인 
		 int topicLevel = adminChallengeFeedMapper.getTopicLevel(cgNum); // 개인기록 키생성 
		  String memberStatKey = commonMapper.getPrimaryKey("cmss_", "challenge_member_score_stats", "cmss_num");
		  
		  Map<String, Object> memberStatMap = new HashMap<String, Object>();
		  memberStatMap.put("challengeFeed", addChallengeFeed);
		  memberStatMap.put("memberStatKey", memberStatKey); 
		  memberStatMap.put("topicLevel", topicLevel); 
		  memberStatMap.put("challengeFeedCount", challengeFeedCount);
		  
		  // 챌린지내 개인기록 한개도 존재하지 않을시 insert 
		  if(isDataMemberStat == 0) {
		  
			  adminChallengeFeedMapper.createPersonalStat(memberStatMap); 
		  } 
		  //한개이상 존재할때 update
		  else {			
			  
			  adminChallengeFeedMapper.updatePersonalStat(memberStatMap); 
		  }	  
				  
				  
		  // ...(*￣０￣)ノ챌린지 개인 점수...(*￣０￣)ノ
		  
		  // 년도,월 분리 
			
			  Date date = addChallengeFeed.getChallengeFeedDate(); 
			  // Calendar 객체로 날짜 처리
			  Calendar calendar = Calendar.getInstance(); calendar.setTime(date); 
			  // 년도와 월 추출 
			  int year = calendar.get(Calendar.YEAR); 
			  int month = calendar.get(Calendar.MONTH)+1; 
			  // 해당멤버의 누적달성률 추출 
			  double achievementRate = adminChallengeFeedMapper.getAchievementRate(addChallengeFeed); // 해당멤버의 누적참여율 추출
			  double participationRate = adminChallengeFeedMapper.getParticipationRate(addChallengeFeed); 
			  // 챌린지 보상기준에 따른 달성률 점수 추출 
			  int achievementScore = adminChallengeFeedMapper.getAchievementScore(achievementRate); 
			  // 챌린지 보상기준에 따른 참여율 점수 추출 
			  int participationScore = adminChallengeFeedMapper.getParticipationScore(participationRate);
			  
			  
			  Map<String, Object> memberScoreMap = new HashMap<String, Object>();
			  memberScoreMap.put("challengeFeed", addChallengeFeed);
			  memberScoreMap.put("year", year); 
			  memberScoreMap.put("month", month);
			  memberScoreMap.put("achievementRate", achievementRate);
			  memberScoreMap.put("participationRate", participationRate);
			  memberScoreMap.put("achievementScore", achievementScore);
			  memberScoreMap.put("participationScore", participationScore);
			  
			  log.info("memberScoreMap@#@#{}",memberScoreMap);
			  
			  // 해당아이디의 해당챌린지의 챌린지개인점수 데이터가 있는지 조회 
			  int isDataMemberScore = adminChallengeFeedMapper.isDataMemberScore(memberScoreMap);
			  
			  // 챌린지 개인점수 키 생성 
			  String memberScoreKey = commonMapper.getPrimaryKey("cpsl_", "challenge_personal_score_list", "cpsl_num");
			  memberScoreMap.put("memberScoreKey", memberScoreKey);
			  log.info("isDataMemberScore {}",isDataMemberScore);
			  // 데이터가 없으면 insert 
			  if(isDataMemberScore == 0) {
			  
				  adminChallengeFeedMapper.createPersonalScore(memberScoreMap); 
			  } 
			  // 데이터가 있으면 update
			  else {
			  
				  adminChallengeFeedMapper.updatePersonalScore(memberScoreMap); 
			  } 
			  // 랭킹 update
			  adminChallengeFeedMapper.updateMemberScoreRank();
			  
			  
			  
			  // ...(*￣０￣)ノ챌린지 별 기록 ...(*￣０￣)ノ
			  
			  // 해당날짜 해당챌린지의 데이터가 있는지 조회 
			  int isDataChallengeStat = adminChallengeFeedMapper.isDataChallengeStat(addChallengeFeed);
			  
			  // 해당날짜 해당챌린지의 일일참여율 조회 
			  double challengeParticipationRate = adminChallengeFeedMapper.getChallengeParticipationRate(addChallengeFeed); 
			  // 해당날짜 해당챌린지의 일일당설률 조회 
			  double challengeAchievementRate = adminChallengeFeedMapper.getChallengeAchievementRate(addChallengeFeed);
			  
			  Map<String, Object> challengeStatMap = new HashMap<String, Object>();
			  
			  challengeStatMap.put("challengeFeed", addChallengeFeed);
			  challengeStatMap.put("challengeParticipationRate",
			  challengeParticipationRate);
			  
			  challengeStatMap.put("challengeAchievementRate", challengeAchievementRate);
			  
			  // 없으면 insert 
			  if(isDataChallengeStat == 0) { 
				  //챌린지 기록 키생성 
				  String challengeStatKey = commonMapper.getPrimaryKey("ctps_","challenge_today_participation_stats", "ctps_num");
				  challengeStatMap.put("challengeStatKey", challengeStatKey);
			  
				  adminChallengeFeedMapper.createChallengeStat(challengeStatMap); 
			  } 
			  // 있으면 update
			  else {
			  
				  adminChallengeFeedMapper.updateChallengeStat(challengeStatMap); 
			  }
			  
			  
			  
			  
			  // ...(*￣０￣)ノ챌린지 점수...(*￣０￣)ノ
			  
			  Map<String, Object> challengeScore = new HashMap<String, Object>();
			  
			  // 해당챌린지의 데이터가 있는지 조회 
			  int isDataChallengeScore = adminChallengeFeedMapper.isDataChallengeScore(addChallengeFeed);
			  
			  // 챌린지 난이도 가져오기 
			  String challengeLevel = adminChallengeFeedMapper.getChallengeLevel(addChallengeFeed); 
			  // 챌린지보상기준에 따른 난이도 점수 
			  int challengeLevelScore = adminChallengeFeedMapper.getChallengeLevelScore(challengeLevel); 
			  // 해당챌린지의 누적달성률 추출 
			  double challengeCumulativeAchievementRate = adminChallengeFeedMapper.getChallengeCumulativeAchievementRate(addChallengeFeed);
			  // 해당챌린지의 누적참여율 추출 
			  double challengeCumulativeParticipationRate = adminChallengeFeedMapper.getChallengeCumulativeParticipationRate(addChallengeFeed); 
			  // 챌린지보상기준에 따른 누적달성률 점수 
			  int challengeAchievementScore = adminChallengeFeedMapper.getChallengeAchievementScore(challengeCumulativeAchievementRate); 
			  // 챌린지보상기준에 따른 누적참여율 점수 
			  int challengeParticipationScore = adminChallengeFeedMapper.getChallengeParticipationScore(challengeCumulativeParticipationRate);
			  
			  challengeScore.put("challengeFeed", addChallengeFeed);
			  challengeScore.put("year", year); 
			  challengeScore.put("month", month);
			  challengeScore.put("challengeLevel", challengeLevel);
			  challengeScore.put("challengeLevelScore", challengeLevelScore);
			  challengeScore.put("challengeCumulativeAchievementRate", challengeCumulativeAchievementRate);
			  challengeScore.put("challengeCumulativeParticipationRate",challengeCumulativeParticipationRate);
			  challengeScore.put("challengeAchievementScore", challengeAchievementScore);
			  challengeScore.put("challengeParticipationScore", challengeParticipationScore);
			  
			  
			  // 데이터가 존재하지 않으면 insert
			  
			  if(isDataChallengeScore == 0) { 
				  //챌린지점수 기본키 생성 
				  String challengeScoreKey = commonMapper.getPrimaryKey("mcsl_", "monthly_challenge_score_list", "mcsl_num"); challengeScore.put("memberScoreKey", challengeScoreKey);
			  
				  adminChallengeFeedMapper.createChallengeScore(challengeScore); 
			  } 
			  // 데이터가 존재하면 update 
			  else {
			  
				  adminChallengeFeedMapper.updateChallengeScore(challengeScore); 
			  }
			  
			  // 랭킹 update 
			  adminChallengeFeedMapper.updateChallengeScoreRank();
			  
			  
			  // ...(*￣０￣)ノ리더가 경고한 내역...(*￣０￣)ノ (리더가 경고누를 때 판별)
			  
			  // 해당챌린지 해당멤버 경고 3번받으면 해당챌린지 퇴장
			  
			  // 퇴장당하지 않은 멤버중 경고 3번받은 멤버 조회 
			  List<Map<String, Object>> accumulatedWarningMember = adminChallengeFeedMapper.getAccumulatedWarningMember();
			  
			  // 챌린지 퇴장 처리 (insert) 
			  for(Map<String, Object> e : accumulatedWarningMember) {
				  String cgmNumKey = commonMapper.getPrimaryKey("cgm_",
				  "challenge_group_member", "cgm_num"); e.put("cgmNumKey", cgmNumKey);
				  
				  adminChallengeFeedMapper.createChallengeMemberCsNum(e); 
			  }
					 
				 
	}

	// 챌린지 피드 수정
	@Override
	public void modifyChallengeFeed(MultipartFile files, AddChallengeFeed addChallengeFeed) {
	    if (files != null && !files.isEmpty()) {
	        Files uploadedFile = filesUtils.uploadFile(files);
	        if (uploadedFile != null) {
	        	String challengeFeedFileIdx = addChallengeFeed.getChallengeFeedFileIdx();
	        	Files challengeFeedFile = filesMapper.getFileInfoByIdx(challengeFeedFileIdx);
	        	boolean isFileDelete = filesUtils.deleteFileByPath(challengeFeedFile.getFilePath());
	            
	        	if(isFileDelete) {
	        		uploadedFile.setFileIdx(challengeFeedFileIdx);
	        		filesMapper.updateFileByIdx(uploadedFile);
	        	}
	        }
	    }
	    
	    String challengeFeedCode = addChallengeFeed.getChallengeFeedCode();
	    addChallengeFeed.setChallengeFeedCode(challengeFeedCode);
		
		//log.info(">>> location/serviceImpl >>> addChallengeFeed: {}", addChallengeFeed);
		
		challengeFeedMapper.modifyChallengeFeed(addChallengeFeed);
	}

	// 챌린지 피드 코드
	@Override
	public AddChallengeFeed getChallengeFeedByCode(String challengeFeedCode) {
		Map<String, Object> params = new HashMap<>();
	    params.put("challengeFeedCode", challengeFeedCode);
	    return challengeFeedMapper.getChallengeFeedByCode(params);
	}

	// 챌린지 피드 삭제
	@Override
	public void deleteChallengeFeed(String challengeFeedCode, String memberId) {
		// 댓글 삭제
	    challengeFeedMapper.deleteCommentsByFeedCode(challengeFeedCode);

	    // 피드 삭제
	    int rowsDeleted = challengeFeedMapper.deleteChallengeFeed(challengeFeedCode, memberId);
	    if (rowsDeleted == 0) {
	        throw new RuntimeException("피드 삭제에 실패했습니다.");
	    }
	}

	// 챌린지 피드 댓글 등록
	@Override
	public boolean addChallengeFeedComment(ChallengeFeedComment challengeFeedComment) {
		String formattedKeyNum = commonMapper.getPrimaryKey("cfc_", "challenge_feed_comments", "cfc_num");
		challengeFeedComment.setChallengeFeedCommentCode(formattedKeyNum);
	    challengeFeedComment.setChallengeFeedCommentDate(new Date()); // 현재 날짜 설정
	    challengeFeedComment.setChallengeFeedCommentLike(0); 		  // 좋아요 기본값 0
	    challengeFeedComment.setChallengeFeedCommentCaution("N");	  // 경고 기본값 N

	    int createCommentCnt = challengeFeedMapper.addChallengeFeedComment(challengeFeedComment);
	    
	    return createCommentCnt > 0 ? true : false;
	}
	
	// 챌린지 피드별 댓글 리스트
	@Override
	public List<ChallengeFeedComment> getFeedCommentList(String challengeFeedCode) {
		List<ChallengeFeedComment> feedCommentList = challengeFeedMapper.getFeedCommentList(challengeFeedCode);
		return feedCommentList;
	}

	// 챌린지 피드 댓글 수정
	@Override
	public boolean modifyFeedComment(String challengeFeedCommentCode, String challengeFeedCommentContent) {
		int modifyCnt = challengeFeedMapper.modifyFeedComment(challengeFeedCommentCode, challengeFeedCommentContent);
		return modifyCnt > 0 ? true : false;
	}

	// 챌린지 피드 댓글 삭제
	@Override
	public boolean deleteFeedComment(String challengeFeedCommentCode) {
		int deleteCnt = challengeFeedMapper.deleteFeedComment(challengeFeedCommentCode);
		return deleteCnt > 0 ? true : false;
	}

	// 챌린지 피드 수정
	@Override
	public ChallengeFeed getModifyChallengeFeed(String challengeFeedCode) {
		
		return challengeFeedMapper.getModifyChallengeFeed(challengeFeedCode);
	}

	// 챌린지 정보 조회(그래프)
	@Override
	@Transactional
	public ChallengeTotalProgress getChallengeTotalProgressInfo(String challengeCode) {
		// 챌린지 진행 상태 정보 조회
	    ChallengeTotalProgress progressInfo = challengeFeedMapper.getChallengeTotalProgressInfo(challengeCode);

	    // 현재 멤버 수 설정
	    int currentMemberCount = challengeFeedMapper.getCurrentMemberCount(challengeCode);
	    if (progressInfo != null) {
	        progressInfo.setChallengeCurrentMember(currentMemberCount);

	        // 상태 코드 변환
	        String statusCode = progressInfo.getChallengeStatusCode();
	        if ("cs_002".equals(statusCode) || "cs_003".equals(statusCode)) {
	            progressInfo.setChallengeStatusCode("진행중");
	        } else if ("cs_004".equals(statusCode)) {
	            progressInfo.setChallengeStatusCode("대기중");
	        }
	    }

	    log.info(">>> location/serviceImpl >>> progressInfo: {}", progressInfo);
		return progressInfo;
	}

	// 챌린지 피드 좋아요 증감
	@Override
	public void challengeFeedToggleLike(String challengeFeedCode, String memberId) {
	    int rowsUpdated = challengeFeedMapper.toggleLike(challengeFeedCode, memberId);
	    if (rowsUpdated == 0) {
	        throw new RuntimeException("좋아요 상태 업데이트에 실패했습니다.");
	    }
	}

}
