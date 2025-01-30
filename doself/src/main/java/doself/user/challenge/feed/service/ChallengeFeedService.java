package doself.user.challenge.feed.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import doself.user.challenge.feed.domain.AddChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeMemberWarning;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.domain.ChallengeTotalProgress;
import doself.user.challenge.feed.domain.ParticipateChallengeList;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface ChallengeFeedService {	
	// 로그인된 챌린지 멤버 아이디
	String getChallengeCodeByMemberId(String challengeMemberId);
	
	// 로그인된 사용자가 참여 중인 챌린지 리스트 가져오기
	List<ParticipateChallengeList> getChallengeListByMemberId(String memberId);
	
	// 챌린지 피드 조회
	List<ChallengeFeed> getChallengeFeed(Map<String, Object> params);
	
	// 챌린지 수정 조회 화면
	ChallengeFeed getModifyChallengeFeed(String challengeFeedCode);
	
	// 피드 페이징
	PageInfo<ChallengeFeed> getChallengeFeedPage(String challengeCode, Pageable pageable);
	
	// 챌린지 진행 상태 조회
	List<ChallengeProgress> getProcessChallengeStatus(String challengeCode);

	// 챌린지 참여율 상위 3명 조회
	List<ChallengeTotalProgress> getTopParticipants(String challengeCode);
	
	// 투데이 디데이 계산
	Map<String, String> calculateDPlusAndDMinus(String challengeCode);
	
	// 챌린지 참여 멤버 리스트 조회
	List<ChallengeMemberList> getMemberList(String challengeCode);
	
	// 챌린지 참여 멤버수 조회
	int getCurrentMemberCount(String challengeCode);
	
	// 파일 처리
	String getFilePath(String fileName);
	
	// 챌린지 피드 생성
	void addChallengeFeed(MultipartFile files, AddChallengeFeed addChallengeFeed);
	
	// 관리자 챌린지 피드 기능 실행
	void adminChallengeFeed(AddChallengeFeed addChallengeFeed);
	
	// 챌린지 피드 수정
	void modifyChallengeFeed(MultipartFile files, AddChallengeFeed addChallengeFeed);
	
	// 챌린지 피드 코드
	AddChallengeFeed getChallengeFeedByCode(String challengeFeedCode);
	
	// 챌린지 피드 삭제
	public void deleteChallengeFeed(String challengeFeedCode, String memberId);
	
	// 챌린지 피드 댓글 등록
	boolean addChallengeFeedComment(ChallengeFeedComment challengeFeedComment);

	// 챌린지 피드 댓글 조회
	List<ChallengeFeedComment> getFeedCommentList(String challengeFeedCode);
	
	// 챌린지 피드 댓글 수정
	boolean modifyFeedComment(String challengeFeedCommentCode, String challengeFeedCommentContent);
	
	// 챌린지 피드 댓글 삭제
	boolean deleteFeedComment(String challengeFeedCommentCode);
	
	// 챌린지 정보 조회(그래프)
	ChallengeTotalProgress getChallengeTotalProgressInfo(String challengeCode);
	
	// 챌린지 피드 좋아요 증감
	void challengeFeedToggleLike(String challengeFeedCode, String memberId);
	
	// 챌린지 경고 리스트
	ChallengeMemberList getWarningList(String challengeCode);
	
	// 챌린지 경고 멤버 리스트
	List<ChallengeMemberList> getWarningMemberList(String challengeCode);
	
	// 챌린지 피드, 댓글
	List<ChallengeMemberList> getFeedAndCommentContentById(String memberId);
	
	// 챌린지 멤버 경고 리스트
	Map<String, Object> getChallengeMemberDetails(String challengeCode);
	
	// 챌린지 멤버 경고
	boolean warningChallengeMember(ChallengeMemberWarning warning, String loggedInMemberId);
}
