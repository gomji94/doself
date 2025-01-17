package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Data : @Getter + @Setter + @ToString
@Data
public class ChallengeUser {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	String cgNum;
    String cgName;
    String cgFileIdx;
    String mbrId;
    String ctName;
    Date cgStartDate;
    Date cgEndDate;
    String ctlLevel;
    String ctlContent;
    int cgMaxMbrCount;
    String cgContent;
    String filePath;
}