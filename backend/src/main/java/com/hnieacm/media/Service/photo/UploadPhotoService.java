package com.hnieacm.media.Service.photo;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadPhotoService {
    Map<String, String> uploadPhoto(MultipartFile file);
}
