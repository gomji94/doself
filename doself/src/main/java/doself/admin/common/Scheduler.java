package doself.admin.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import doself.admin.declare.controller.DeclareController;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {
	
	private final DeclareController declareController;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void runAtMidnight() {
		
		declareController.everydayCheck();
	}
}
