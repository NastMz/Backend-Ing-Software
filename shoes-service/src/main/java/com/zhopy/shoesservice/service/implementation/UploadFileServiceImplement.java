package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.service.interfaces.IUploadFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Transactional
@Qualifier("UploadService")
public class UploadFileServiceImplement implements IUploadFileService {

    @Override
    public String getImageName(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    @Override
    public byte[] getImageBytes(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            InputStream in = file.getInputStream();
            return IOUtils.toByteArray(in);
        }
        String route = "shoes-service//images//";
        File f = new File(route + "default.jpg");
        InputStream in = new FileInputStream(f);
        return IOUtils.toByteArray(in);
    }
}
