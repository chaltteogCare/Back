package com.Chaltteok.DailyCheck.dto;

import lombok.Data;

import java.util.List;

@Data
public class PredictionDTO {
    private List<List<Double>> prediction;
}
