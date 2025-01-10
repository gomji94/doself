package doself.admin.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import doself.admin.declare.controller.DeclareController;
import doself.admin.member.controller.MemberController;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {
	
	private final DeclareController declareController;
	private final MemberController memberController;
	
	// 매일 자정에 실행
	@Scheduled(cron = "0 0 0 * * ?")
	public void runAtMidnight() {
		
		declareController.everydayCheck();
	}
	
	// 매년 1월1일 실행
	@Scheduled(cron = "0 0 0 1 1 ?")
	public void runAtYear() {
		memberController.everyYearCheck();
	}
}
