package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.ScheduleDTO;
import com.Chaltteok.DailyCheck.entity.ScheduleEntity;
import com.Chaltteok.DailyCheck.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleEntity>> getAllSchedules(@RequestParam int userId) {
        List<ScheduleEntity> schedules = scheduleService.getAllSchedules(userId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleEntity> getSchedule(@PathVariable int scheduleId) {
        ScheduleEntity schedule = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping()
    public ResponseEntity<ScheduleEntity> addSchedule(@RequestParam int userId, @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleEntity newSchedule = scheduleService.addSchedule(userId,scheduleDTO);
        return ResponseEntity.ok(newSchedule);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleEntity> updateSchedule(@PathVariable int scheduleId, @RequestBody ScheduleEntity schedule) {
        ScheduleEntity updatedSchedule = scheduleService.updateSchedule(scheduleId,schedule);
        return ResponseEntity.ok(updatedSchedule);
        }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
