package com.Chaltteok.DailyCheck.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DataUsageDTOResponse {
    private long seniorId;
    private Date date;
    private int phoneUsage;
    private int waterUsage;
    private int elecUsage;
    private String status;

    public DataUsageDTOResponse(long seniorId, Date date, int phoneUsage, int waterUsage, int elecUsage, String status) {
        this.seniorId = seniorId;
        this.date = date;
        this.phoneUsage = phoneUsage;
        this.waterUsage = waterUsage;
        this.elecUsage = elecUsage;
        this.status = status;
    }
}
