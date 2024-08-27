package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.dto.DataUsageDTORequest;
import com.Chaltteok.DailyCheck.dto.DataUsageDTOResponse;
import com.Chaltteok.DailyCheck.entity.DataUsageEntity;
import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.repository.DataUsageRepository;
import com.Chaltteok.DailyCheck.repository.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DataUsageService {
    private final DataUsageRepository dataUsageRepository;
    private final SeniorRepository seniorRepository;

    public long save(DataUsageDTORequest request) {
        SeniorEntity senior = seniorRepository.findById(request.getSeniorId())
                .orElseThrow(() -> new IllegalArgumentException("노인을 찾을 수 없습니다."));
        DataUsageEntity dataUsageEntity = DataUsageEntity.builder()
                .senior(senior)
                .date(request.getDate())
                .phoneUsage(request.getPhoneUsage())
                .waterUsage(request.getWaterUsage())
                .elecUsage(request.getElecUsage())
                .status(request.getStatus())
                .build();
        return dataUsageRepository.save(dataUsageEntity).getId();
    }

    public DataUsageDTOResponse findById(long id) {
        DataUsageEntity dataUsage = dataUsageRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 데이터 기록이 없습니다~"));
        return new DataUsageDTOResponse(dataUsage.getSenior().getId(), dataUsage.getDate(), dataUsage.getPhoneUsage(), dataUsage.getWaterUsage(), dataUsage.getElecUsage(), dataUsage.getStatus());
    }

    public List<DataUsageDTOResponse> findBySeniorId(long SeniorId) {
        List<DataUsageEntity> dataUsages = dataUsageRepository.findBySeniorId(SeniorId);
        return dataUsages.stream()
                .map(dataUsage -> new DataUsageDTOResponse(
                        dataUsage.getSenior().getId(),
                        dataUsage.getDate(),
                        dataUsage.getPhoneUsage(),
                        dataUsage.getWaterUsage(),
                        dataUsage.getElecUsage(),
                        dataUsage.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public void update(Long id, DataUsageDTORequest request) {
        DataUsageEntity dataUsage = dataUsageRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 데이터 기록이 없습니다~"));
        if (request.getSeniorId() != 0) {
            SeniorEntity senior = seniorRepository.findById(request.getSeniorId())
                    .orElseThrow(() -> new IllegalArgumentException("노인을 찾을 수 없습니다."));
            dataUsage.setSenior(senior);
        }
        if(request.getDate()!=null){
            dataUsage.setDate(request.getDate());
        }
        if(request.getPhoneUsage()!=0){
            dataUsage.setPhoneUsage(request.getPhoneUsage());
        }
        if(request.getWaterUsage()!=0){
            dataUsage.setWaterUsage(request.getWaterUsage());
        }
        if(request.getElecUsage()!=0){
            dataUsage.setElecUsage(request.getElecUsage());
        }
        if(request.getStatus()!=null){
            dataUsage.setStatus(request.getStatus());
        }
        dataUsageRepository.save(dataUsage);
    }

    public void delete(long id) {
        dataUsageRepository.deleteById(id);
    }
}
