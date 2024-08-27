package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.DataUsageDTORequest;
import com.Chaltteok.DailyCheck.dto.DataUsageDTOResponse;
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
    public ResponseEntity<String> addDataUsage(@RequestBody DataUsageDTORequest request) {
        long dataUsageId = dataUsageService.save(request);
        return ResponseEntity.ok("Data usage record added with ID: " + dataUsageId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataUsageDTOResponse> getDataUsage(@PathVariable long id) {
        DataUsageDTOResponse dataUsageDTOResponse = dataUsageService.findById(id);
        return ResponseEntity.ok(dataUsageDTOResponse);
    }

    @GetMapping("/senior/{seniorId}")
    public ResponseEntity<List<DataUsageDTOResponse>> getDataUsageBySeniorId(@PathVariable long seniorId) {
        List<DataUsageDTOResponse> dataUsages = dataUsageService.findBySeniorId(seniorId);
        return ResponseEntity.ok(dataUsages);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateDataUsage(@PathVariable long id, @RequestBody DataUsageDTORequest request) {
        dataUsageService.update(id, request);
        return ResponseEntity.ok("Data usage record updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataUsage(@PathVariable long id) {
        dataUsageService.delete(id);
        return ResponseEntity.ok("Data usage record deleted");
    }
}
