package com.mikesh.blog.services.implementation;

import com.mikesh.blog.services.FileServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileServices {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File Name
        String name = file.getOriginalFilename();

        // Random name generated file
        String randomId = UUID.randomUUID().toString();
        assert name != null;
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

        // FullPath
        String filePath = path + File.separator + fileName1;

        // Create Folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        // File Copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath = path + File.separator + filename;
        return new FileInputStream(fullPath);
    }
}
