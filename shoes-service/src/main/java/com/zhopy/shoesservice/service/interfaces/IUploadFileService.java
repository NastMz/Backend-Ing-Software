package com.zhopy.shoesservice.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IUploadFileService {

    String getImageName(MultipartFile file) throws IOException;

    byte[] getImageBytes(MultipartFile file) throws IOException;


}
