package com.contractManagement.controller;

import com.contractManagement.common.UploadResponse;
import com.contractManagement.service.impl.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fileStore")
@RequiredArgsConstructor
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("fullName") String fullName,
            @RequestParam("dateOfBirth") String dateOfBirth
    ) {
        String fileName = fileStorageService.storeFile(file);

        UploadResponse uploadResponse = new UploadResponse(fileName, fullName, dateOfBirth);

        return ResponseEntity.ok().body(uploadResponse);
    }


}
