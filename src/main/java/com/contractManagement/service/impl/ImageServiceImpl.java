package com.contractManagement.service.impl;

import com.contractManagement.entity.Image;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.ImageRepo;
import com.contractManagement.service.ImageService;
import com.contractManagement.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;

    @Override
    public Image save(Image image) {
        return imageRepo.save(image);
    }

    @Override
    public Image findByName(String name) {
        return imageRepo.findByName(name).orElseThrow(() -> new ResultException(ResultEnum.CONTRACT_IMAGE_NOT_EXIST));
    }
    private Image buildDecompressImage(Image image){
        return Image.builder()
                .name(image.getName())
                .type(image.getType())
                .image(ImageUtility.decompressImage(image.getImage()))
                .build();
    }
}
