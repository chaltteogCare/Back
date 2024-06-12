package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.dto.DataUsageDTO;
import com.Chaltteok.DailyCheck.entity.DataUsageEntity;
import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.repository.DataUsageRepository;
import com.Chaltteok.DailyCheck.repository.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DataUsageService {
    private final DataUsageRepository dataUsageRepository;
    private final SeniorRepository seniorRepository;

    public long save(DataUsageDTO dataUsageDTO) {
        SeniorEntity senior = seniorRepository.findById(dataUsageDTO.getSeniorId())
                .orElseThrow(() -> new IllegalArgumentException("노인을 찾을 수 없습니다."));
        DataUsageEntity dataUsageEntity = DataUsageEntity.builder()
                .senior(senior)
                .date(dataUsageDTO.getDate())
                .phoneUsage(dataUsageDTO.getPhoneUsage())
                .waterUsage(dataUsageDTO.getWaterUsage())
                .elecUsage(dataUsageDTO.getElecUsage())
                .status(dataUsageDTO.getStatus())
                .build();
        return dataUsageRepository.save(dataUsageEntity).getId();
    }

    public DataUsageDTO findById(long id) {
        DataUsageEntity dataUsage = dataUsageRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("데이터 사용 기록이 없습니다~"));
        return new DataUsageDTO(dataUsage.getSenior().getId(), dataUsage.getDate(), dataUsage.getPhoneUsage(), dataUsage.getWaterUsage(), dataUsage.getElecUsage(), dataUsage.getStatus());
    }

    public void delete(long id) {
        dataUsageRepository.deleteById(id);
    }
}
