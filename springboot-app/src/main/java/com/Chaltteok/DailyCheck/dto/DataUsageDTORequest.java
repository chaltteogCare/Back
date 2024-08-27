package com.Chaltteok.DailyCheck.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
public class DataUsageDTORequest {
    private long seniorId;
    private Date date;
    @JsonProperty("Phone Usage")
    private int phoneUsage;
    @JsonProperty("Water Usage")
    private int waterUsage;
    @JsonProperty("Electricity Usage")
    private int elecUsage;
    private String status;
}
