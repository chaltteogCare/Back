package com.Chaltteok.DailyCheck.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataUsageDTO {
    private long seniorId;
    private Date date;
    private int phoneUsage;
    private int waterUsage;
    private int elecUsage;
    private String status;
}
