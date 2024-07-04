package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.SeniorDTO;
import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.service.SeniorService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/senior")
public class SeniorController {

    private final SeniorService seniorService;

    @GetMapping("/{seniorId}")
    public ResponseEntity<SeniorEntity> getSenior(@PathVariable long seniorId) {
        SeniorEntity senior = seniorService.getSenior(seniorId);
        return ResponseEntity.ok(senior);
    }

    @PostMapping
    public ResponseEntity<SeniorEntity> addSenior(@RequestParam long UserId, @RequestBody SeniorDTO seniorDTO) {
        SeniorEntity newSenior = seniorService.addSenior(UserId, seniorDTO);
        return ResponseEntity.ok(newSenior);
    }

    @PatchMapping("/{seniorId}")
    public ResponseEntity<SeniorEntity> updateSenior(@PathVariable long seniorId, @RequestBody SeniorDTO seniorDTO) {
        SeniorEntity updatedSenior = seniorService.updateSenior(seniorId, seniorDTO);
        return ResponseEntity.ok(updatedSenior);
    }

    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Void> deleteSenior(@PathVariable long seniorId) {
        seniorService.deleteSenior(seniorId);
        return ResponseEntity.noContent().build();
    }

    // 이미지 처리

    @PostMapping(value = "/{seniorId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Update Senior Photo", notes = "Upload a photo for a senior")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seniorId", value = "Senior ID", required = true, dataTypeClass = Long.class, paramType = "path"),
            @ApiImplicitParam(name = "file", value = "Photo file", required = true, dataTypeClass = MultipartFile.class, paramType = "form")
    })

    public ResponseEntity<String> updateSeniorPhoto(@PathVariable long seniorId, @RequestParam("file") MultipartFile file) {
        String photoUrl = seniorService.updateSeniorPhoto(seniorId, file);
        return ResponseEntity.ok(photoUrl);
    }

    @GetMapping("/{seniorId}/photo")
    public ResponseEntity<Resource> getSeniorPhoto(@PathVariable long seniorId) {
        byte[] photo = seniorService.getSeniorPhoto(seniorId);
        Resource resource = new ByteArrayResource(photo);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 이미지 타입에 맞게 변경
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"photo.jpg\"")
                .body(resource);
    }

    @DeleteMapping("/{seniorId}/photo")
    public ResponseEntity<Void> deleteSeniorPhoto(@PathVariable long seniorId) {
        seniorService.deleteSeniorPhoto(seniorId);
        return ResponseEntity.noContent().build();
    }
}
