package com.fitness_management.services.interfaces;

import com.fitness_management.models.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;



public interface CloudinaryServiceI {
    CloudinaryResponse uploadFile(MultipartFile file, String fileName, String resourceType);
    void deleteFile(String publicId, String resourceType);
}

