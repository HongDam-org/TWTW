package com.twtw.backend.domain.image.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.twtw.backend.domain.image.dto.ImageResponse;
import com.twtw.backend.domain.image.exception.InvalidFileTypeException;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.global.properties.StorageProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String IMAGE_FILE_TYPE = "image";
    private final AuthService authService;
    private final Storage storage;
    private final StorageProperties storageProperties;

    public ImageResponse uploadImage(final MultipartFile image) throws IOException {
        final Member member = authService.getMemberByJwt();
        final String contentType = image.getContentType();
        validate(contentType);

        final String uuid = UUID.randomUUID().toString();
        upload(image, contentType, uuid);

        final String imagePath = getImageUrl(uuid);
        member.updateProfileImage(imagePath);

        return new ImageResponse(imagePath);
    }

    private String getImageUrl(final String uuid) {
        return storageProperties.getStorageUrl() + uuid;
    }

    private void upload(final MultipartFile image, final String contentType, final String uuid)
            throws IOException {
        storage.create(
                BlobInfo.newBuilder(storageProperties.getBucket(), uuid)
                        .setContentType(contentType)
                        .build(),
                image.getInputStream());
    }

    private void validate(final String contentType) {
        if (contentType == null || !contentType.startsWith(IMAGE_FILE_TYPE)) {
            throw new InvalidFileTypeException();
        }
    }
}
