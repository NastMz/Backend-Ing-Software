package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.service.interfaces.IUploadFileService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadFileServiceImplement implements IUploadFileService {

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    @Override
    public void deleteImage(String name) {
        String route = "images//";
        File file = new File(route + name);
        file.delete();
    }
}
