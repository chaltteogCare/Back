package com.Chaltteok.DailyCheck.service;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.Chaltteok.DailyCheck.dto.SeniorDTO;
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

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@AllArgsConstructor
@Service
public class SeniorService {

    private final SeniorRepository seniorRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public SeniorEntity getSenior(long seniorId) {
        return seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));
    }

    public SeniorEntity addSenior(long userId, SeniorDTO seniorDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        SeniorEntity seniorEntity = seniorDTO.toEntity(user);
        return seniorRepository.save(seniorEntity);
    }

    public SeniorEntity updateSenior(long seniorId, SeniorDTO seniorDTO) {
        SeniorEntity senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));

        UserEntity user = userRepository.findById(seniorDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + seniorDTO.getUserId()));

        senior.setUser(user);
        senior.setName(seniorDTO.getName());
        senior.setAge(seniorDTO.getAge());
        senior.setAddress(seniorDTO.getAddress());
        senior.setTelephoneNumber(seniorDTO.getTelephoneNumber());
        senior.setNotes(seniorDTO.getNotes());
        // senior.setPhotoUrl(seniorDTO.getPhotoUrl());
        return seniorRepository.save(senior);
    }

    public void deleteSenior(long seniorId) {
        SeniorEntity senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResourceNotFoundException("Senior not found with id " + seniorId));
        seniorRepository.deleteById(seniorId);
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
            SeniorEntity senior = getSenior(seniorId);
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
