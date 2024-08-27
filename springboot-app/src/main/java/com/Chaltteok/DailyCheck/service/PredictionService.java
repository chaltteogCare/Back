package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.dto.DataUsageDTORequest;
import com.Chaltteok.DailyCheck.dto.DataUsageDTOResponse;
import com.Chaltteok.DailyCheck.dto.PredictionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class PredictionService {
    @Autowired
    private RestTemplate restTemplate;

    public PredictionDTO predictRisk(DataUsageDTORequest request) {
        String url = "http://localhost:5000/predict"; //TODO: 나중에 수정 필요?
        ResponseEntity<PredictionDTO> response = restTemplate.postForEntity(url, request, PredictionDTO.class);
        return response.getBody();
    }
}
