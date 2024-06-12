package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.DataUsageDTO;
import com.Chaltteok.DailyCheck.service.DataUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/senior/{seniorId}")
    public ResponseEntity<List<DataUsageDTO>> getDataUsageBySeniorId(@PathVariable long seniorId) {
        List<DataUsageDTO> dataUsages = dataUsageService.findBySeniorId(seniorId);
        return ResponseEntity.ok(dataUsages);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateDataUsage(@PathVariable long id, @RequestBody DataUsageDTO dataUsageDTO) {
        dataUsageService.update(id, dataUsageDTO);
        return ResponseEntity.ok("Data usage record updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataUsage(@PathVariable long id) {
        dataUsageService.delete(id);
        return ResponseEntity.ok("Data usage record deleted");
    }
}
