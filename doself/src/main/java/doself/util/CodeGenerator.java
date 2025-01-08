package doself.util;

import java.time.LocalTime;
import java.util.Random;

// 포인트 상품 코드 생성 함수
public class CodeGenerator {
	
	public static String generateCode(String productCode) {
		
        // 1. 상품코드에서 숫자 부분 추출 (예: pepl_003 -> 0003)
        String numericPart = productCode.split("_")[1];
        numericPart = String.format("%04d", Integer.parseInt(numericPart));

        // 2. 무작위 알파벳 4개 생성
        String randomAlphabets = getRandomAlphabets(4);

        // 3. 현재 시간과 분 가져오기
        String currentTime = getCurrentTime();

        // 4. 완성된 코드 조합
        return "pepl-" + numericPart + "-" + randomAlphabets + "-" + currentTime;
    }

    private static String getRandomAlphabets(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            result.append(randomChar);
        }
        return result.toString();
    }

    private static String getCurrentTime() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        return String.format("%02d%02d", hour, minute);
    }

}
