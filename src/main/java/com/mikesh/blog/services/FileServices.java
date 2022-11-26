package com.mikesh.blog.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileServices {

    String uploadImage(String path, MultipartFile file) throws IOException ;
    InputStream getResource(String path, String filename) throws FileNotFoundException;
}
