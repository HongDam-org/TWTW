package com.twtw.backend.domain.image.controller;

import com.twtw.backend.domain.image.dto.ImageResponse;
import com.twtw.backend.domain.image.service.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(final MultipartFile image) throws IOException {
        return ResponseEntity.ok(imageService.uploadImage(image));
    }
}
