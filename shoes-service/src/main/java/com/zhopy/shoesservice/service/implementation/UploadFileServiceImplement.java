package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.service.interfaces.IUploadFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadFileServiceImplement implements IUploadFileService {

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    @Override
    public void deleteImage(String name) {
        String route = "shoes-service//images//";
        File file = new File(route + name);
        file.delete();
    }

    @Override
    public byte[] getImage(String name) throws IOException {
        String route = "shoes-service//images//";
        File file = new File(route + name);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }
}
