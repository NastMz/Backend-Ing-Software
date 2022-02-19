package com.zhopy.shoesservice.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IUploadFileService {
    String folder = "images//";

    String saveImage(MultipartFile file) throws IOException;

    void deleteImage(String name);
}