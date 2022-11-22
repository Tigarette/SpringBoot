package com.hnieacm.media.controller.photo;

import com.hnieacm.media.Service.photo.UploadPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UploadPhotoController {

    @Autowired
    private UploadPhotoService uploadPhotoService;

    @PostMapping("/user/UploadImage/")
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file){

        return uploadPhotoService.uploadPhoto(file);
    }
}
