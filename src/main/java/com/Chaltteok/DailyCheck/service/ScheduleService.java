package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.dto.ScheduleDTORequest;
import com.Chaltteok.DailyCheck.dto.ScheduleDTOResponse;
import com.Chaltteok.DailyCheck.entity.ScheduleEntity;
import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.exception.ResourceNotFoundException;
import com.Chaltteok.DailyCheck.exception.UserNotFoundException;
import com.Chaltteok.DailyCheck.repository.ScheduleRepository;
import com.Chaltteok.DailyCheck.repository.SeniorRepository;
import com.Chaltteok.DailyCheck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;

    public List<ScheduleDTOResponse> getAllSchedules(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id " + userId);
        }
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findByUser_Id(userId);
        return scheduleEntities.stream().map(ScheduleDTOResponse::fromEntity).collect(Collectors.toList());
    }

    public ScheduleDTOResponse getSchedule(long scheduleId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
        return ScheduleDTOResponse.fromEntity(scheduleEntity);
    }

    public ScheduleDTOResponse addSchedule(long userId, ScheduleDTORequest scheduleDTORequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        SeniorEntity senior = seniorRepository.findById(scheduleDTORequest.getSeniorId())
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + scheduleDTORequest.getSeniorId()));

        ScheduleEntity scheduleEntity = scheduleDTORequest.toEntity(user, senior);
        ScheduleEntity savedSchedule = scheduleRepository.save(scheduleEntity);
        return ScheduleDTOResponse.fromEntity(savedSchedule);
    }

    public ScheduleDTOResponse updateSchedule(long scheduleId, ScheduleDTORequest scheduleDTORequest) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));

        SeniorEntity senior = seniorRepository.findById(scheduleDTORequest.getSeniorId())
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + scheduleDTORequest.getSeniorId()));

        schedule.setSenior(senior);
        schedule.setDate(scheduleDTORequest.getDate());
        schedule.setScheduleTime(scheduleDTORequest.getScheduleTime());

        ScheduleEntity updatedSchedule = scheduleRepository.save(schedule);
        return ScheduleDTOResponse.fromEntity(updatedSchedule);
    }

    public void deleteSchedule(long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
        scheduleRepository.deleteById(scheduleId);
    }
}
