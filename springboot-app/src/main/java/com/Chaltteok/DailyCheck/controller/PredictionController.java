package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.DataUsageDTORequest;
import com.Chaltteok.DailyCheck.dto.DataUsageDTOResponse;
import com.Chaltteok.DailyCheck.dto.PredictionDTO;
import com.Chaltteok.DailyCheck.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PredictionController {
    @Autowired
    private PredictionService predictionService;

    @PostMapping("/predict")
    public PredictionDTO getPrediction(@RequestBody DataUsageDTORequest request) {
        return predictionService.predictRisk(request);
    }
}
