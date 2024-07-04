package com.Chaltteok.DailyCheck.service;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.Chaltteok.DailyCheck.dto.SeniorDTORequest;
import com.Chaltteok.DailyCheck.dto.SeniorDTOResponse;
import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.exception.ResourceNotFoundException;
import com.Chaltteok.DailyCheck.exception.UserNotFoundException;
import com.Chaltteok.DailyCheck.repository.SeniorRepository;
import com.Chaltteok.DailyCheck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class SeniorService {

    private final SeniorRepository seniorRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public SeniorDTOResponse getSenior(long seniorId) {
        SeniorEntity seniorEntity = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));
        return SeniorDTOResponse.fromEntity(seniorEntity);
    }

    public SeniorDTOResponse addSenior(long userId, SeniorDTORequest seniorDTORequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        SeniorEntity seniorEntity = seniorDTORequest.toEntity(user);
        SeniorEntity savedSenior = seniorRepository.save(seniorEntity);
        return SeniorDTOResponse.fromEntity(savedSenior);
    }

    public SeniorDTOResponse updateSenior(long seniorId, SeniorDTORequest seniorDTORequest) {
        SeniorEntity senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));

        senior.setName(seniorDTORequest.getName());
        senior.setAge(seniorDTORequest.getAge());
        senior.setAddress(seniorDTORequest.getAddress());
        senior.setTelephoneNumber(seniorDTORequest.getTelephoneNumber());
        senior.setNotes(seniorDTORequest.getNotes());

        SeniorEntity updatedSenior = seniorRepository.save(senior);
        return SeniorDTOResponse.fromEntity(updatedSenior);
    }

    public void deleteSenior(long seniorId) {
        SeniorEntity senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));
        seniorRepository.deleteById(seniorId);
    }

    public SeniorEntity findSeniorEntityById(long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));
    }

    public String updateSeniorPhoto(long seniorId, MultipartFile file) {
        SeniorEntity senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));

        String photoUrl = fileUploadService.storeFile(file);
        senior.setPhotoUrl(photoUrl);
        seniorRepository.save(senior);

        return photoUrl;
    }

    public byte[] getSeniorPhoto(long seniorId) {
        try {
            SeniorEntity senior = this.findSeniorEntityById(seniorId);
            String photoUrl = senior.getPhotoUrl();

            String decodedPath = URLDecoder.decode(photoUrl, StandardCharsets.UTF_8.name());

            String filePath = decodedPath.replaceFirst("^file:/+", "");
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }

            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                throw new NoSuchFileException("File does not exist: " + filePath);
            }

            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read photo file", e);
        }
    }


    public void deleteSeniorPhoto(long seniorId) {
        SeniorEntity senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));

        String photoUrl = senior.getPhotoUrl();
        if (photoUrl != null) {
            try {

                String filePath = URLDecoder.decode(photoUrl, StandardCharsets.UTF_8.name());
                filePath = filePath.replaceFirst("^file:/+", "");
                if (filePath.startsWith("/")) {
                    filePath = filePath.substring(1);
                }

                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);

                senior.setPhotoUrl(null);
                seniorRepository.save(senior);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete photo file", e);
            }
        }
    }
}
