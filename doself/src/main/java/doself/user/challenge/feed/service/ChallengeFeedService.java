package doself.user.challenge.feed.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import doself.user.challenge.feed.domain.AddChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
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
	
	// 피드 페이징
	PageInfo<ChallengeFeed> getChallengeFeedPage(String challengeCode, Pageable pageable);
	
	// 챌린지 진행 상태 조회
	List<ChallengeProgress> getProcessChallengeStatus(String challengeCode);

	// 챌린지 진행도 계산
	int calculateTotalProgress(String challengeCode);
	
	// 챌린지 참여율 상위 3명 조회
	List<ChallengeMemberList> getTopParticipants(String challengeCode);
	
	// 투데이 디데이 계산
	Map<String, String> calculateDPlusAndDMinus(String challengeCode);
	
	// 챌린지 참여 멤버 리스트 조회
	List<ChallengeMemberList> getMemberList(String challengeCode);
	
	// 챌린지 피드 댓글 조회
	List<ChallengeFeedComment> getFeedCommentList(String challengeFeedCode);
	
	// 챌린지 참여 멤버수 조회
	int getCurrentMemberCount(String challengeCode);
	
	// 파일 처리
	String getFilePath(String fileName);
	
	// 멤버 점수 계산
	void updateMemberScores(String challengeCode);
	
	// 챌린지 피드 생성
	void addChallengeFeed(MultipartFile files, AddChallengeFeed addChallengeFeed);
	
	// 챌린지 피드 수정
	void modifyChallengeFeed(MultipartFile files, AddChallengeFeed addChallengeFeed);
	
	// 챌린지 피드 코드
	AddChallengeFeed getChallengeFeedByCode(String challengeFeedCode);
	
	// 챌린지 피드 삭제
	public void deleteChallengeFeed(String challengeFeedCode, String memberId);
	
	// 챌린지 피드 댓글 등록
	void addChallengeFeedComment(ChallengeFeedComment challengeFeedComment);
}
