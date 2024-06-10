package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.dto.ScheduleDTO;
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

@AllArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SeniorRepository seniorRepository;

    public List<ScheduleEntity> getAllSchedules(long userId) {
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException("User not found with id " + userId);
        }
        return scheduleRepository.findByUser_Id(userId);
    }

    public ScheduleEntity getSchedule(long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    public ScheduleEntity addSchedule(long userId, ScheduleDTO scheduleDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        SeniorEntity senior = seniorRepository.findById(scheduleDTO.getSeniorId())
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + scheduleDTO.getSeniorId()));

        ScheduleEntity scheduleEntity = scheduleDTO.toEntity(user, senior);
        return scheduleRepository.save(scheduleEntity);
    }

    public ScheduleEntity updateSchedule(long scheduleId, ScheduleDTO scheduleDTO) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));

        UserEntity user = userRepository.findById(scheduleDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + scheduleDTO.getUserId()));

        SeniorEntity senior = seniorRepository.findById(scheduleDTO.getSeniorId())
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + scheduleDTO.getSeniorId()));

        ScheduleEntity NewSchedule = scheduleDTO.toEntity(user, senior);
        return scheduleRepository.save(NewSchedule);
    }

    public void deleteSchedule(long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
        scheduleRepository.deleteById(scheduleId);
    }
}
