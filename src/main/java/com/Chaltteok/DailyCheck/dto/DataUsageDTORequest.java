package com.Chaltteok.DailyCheck.dto;

import lombok.*;

import java.util.Date;

@Data
public class DataUsageDTORequest {
    private long seniorId;
    private Date date;
    private int phoneUsage;
    private int waterUsage;
    private int elecUsage;
    private String status;
}
