package com.contractManagement.service;

import com.contractManagement.entity.Image;

public interface ImageService {
    Image save(Image image);
    Image findByName(String name);
}
