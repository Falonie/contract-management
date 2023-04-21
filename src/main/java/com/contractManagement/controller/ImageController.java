package com.contractManagement.controller;

import com.contractManagement.common.Result;
import com.contractManagement.entity.Image;
import com.contractManagement.repository.ImageRepo;
import com.contractManagement.service.ImageService;
import com.contractManagement.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
//@CrossOrigin(origins = "http://localhost:8082") open for specific port
@CrossOrigin() // open for all ports
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepo imageRepo;
    private final ImageService imageService;

    @PostMapping("/upload/image")
    public ResponseEntity<Result<String>> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

/*        imageRepo.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());*/

/*        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));*/
        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build();
        imageService.save(image);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success("Image uploaded successfully: " +
                file.getOriginalFilename()));
    }

    @GetMapping("/get/image/info/{name}")
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {

/*        final Optional<Image> dbImage = imageRepo.findByName(name);

        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();*/
        Image image = imageService.findByName(name);
        return buildDecompressImage(image);
    }

    @GetMapping("/get/image/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

/*        final Optional<Image> dbImage = imageRepo.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtility.decompressImage(dbImage.get().getImage()));*/

        Image image = imageService.findByName(name);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtility.decompressImage(image.getImage()));
    }

    private Image buildDecompressImage(Image image) {
        return Image.builder()
                .name(image.getName())
                .type(image.getType())
                .image(ImageUtility.decompressImage(image.getImage()))
                .build();
    }
}