package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.DataUsageDTO;
import com.Chaltteok.DailyCheck.service.DataUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/data")
public class DataUsageController {
    private final DataUsageService dataUsageService;

    @PostMapping("")
    public ResponseEntity<String> addDataUsage(@RequestBody DataUsageDTO dataUsageDTO) {
        long dataUsageId = dataUsageService.save(dataUsageDTO);
        return ResponseEntity.ok("Data usage record added with ID: " + dataUsageId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataUsageDTO> getDataUsage(@PathVariable long id) {
        DataUsageDTO dataUsageDTO = dataUsageService.findById(id);
        return ResponseEntity.ok(dataUsageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataUsage(@PathVariable long id) {
        dataUsageService.delete(id);
        return ResponseEntity.ok("Data usage record deleted");
    }
}
