package doself.admin.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import doself.admin.challenge.controller.ChallengeController;
import doself.admin.declare.controller.DeclareController;
import doself.admin.member.controller.MemberController;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {
	
	private final DeclareController declareController;
	private final MemberController memberController;
	private final ChallengeController challengeController;
	
	// 매일 자정에 실행
	@Scheduled(cron = "0 0 0 * * ?")
	public void runAtMidnight() {
		
		// 부정회원 제제일 지난 사람 회원으로 등급 변경
		declareController.everydayCheck();
		
		// 챌린지 상태 완료인 값 보상 지급
		challengeController.everydayCheck();
		
	}
	
	// 매년 1월1일 실행
	@Scheduled(cron = "0 0 0 1 1 ?")
	public void runAtYear() {
		memberController.everyYearCheck();
	}
}
