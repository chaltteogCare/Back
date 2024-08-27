package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.ScheduleDTORequest;
import com.Chaltteok.DailyCheck.dto.ScheduleDTOResponse;
import com.Chaltteok.DailyCheck.dto.SeniorDTOResponse;
import com.Chaltteok.DailyCheck.entity.ScheduleEntity;
import com.Chaltteok.DailyCheck.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTOResponse>> getAllSchedules(@RequestParam long userId) {
        List<ScheduleDTOResponse> schedules = scheduleService.getAllSchedules(userId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTOResponse> getSchedule(@PathVariable long scheduleId) {
        ScheduleDTOResponse schedule = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping()
    public ResponseEntity<ScheduleDTOResponse> addSchedule(@RequestParam long userId, @RequestBody ScheduleDTORequest scheduleDTORequest) {
        ScheduleDTOResponse newSchedule = scheduleService.addSchedule(userId, scheduleDTORequest);
        return ResponseEntity.ok(newSchedule);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTOResponse> updateSchedule(@PathVariable long scheduleId, @RequestBody ScheduleDTORequest scheduleDTORequest) {
        ScheduleDTOResponse updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleDTORequest);
        return ResponseEntity.ok(updatedSchedule);
        }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }


}
