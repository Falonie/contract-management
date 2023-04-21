package com.contractManagement.service.impl;

import com.contractManagement.entity.FileDB;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.FileDBRepo;
import com.contractManagement.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final FileDBRepo fileDBRepo;

    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileDBRepo.save(FileDB);
    }

    @Override
    public FileDB getFile(String id) {
        return fileDBRepo.findById(id).orElseThrow(() -> new ResultException(ResultEnum.CONTRACT_NOT_EXIST));
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepo.findAll().stream();
    }
}
