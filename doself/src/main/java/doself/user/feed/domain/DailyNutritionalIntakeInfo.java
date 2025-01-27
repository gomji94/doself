package doself.user.feed.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DailyNutritionalIntakeInfo {
    private String dniiNum;
    private String mbrId;
    private String feedNum;
    private LocalDateTime dniiDate;
    private BigDecimal dniiKcal;
    private BigDecimal dniiCarbo;
    private BigDecimal dniiProtein;
    private BigDecimal dniiFat;
    private BigDecimal dniiSugar;
    private BigDecimal dniiCholesterol;
    private BigDecimal dniiSodium;
    private BigDecimal dniiCalcium;
    private BigDecimal dniiIron;
}