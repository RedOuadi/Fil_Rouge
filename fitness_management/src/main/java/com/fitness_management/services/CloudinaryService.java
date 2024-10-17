package com.fitness_management.services;

import com.cloudinary.Cloudinary;
import com.fitness_management.exception.FuncErrorException;
import com.fitness_management.models.CloudinaryResponse;
import com.fitness_management.services.interfaces.CloudinaryServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService implements CloudinaryServiceI {

    @Autowired
    private Cloudinary cloudinary;

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    @Override
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName, String resourceType) {
        if (file == null || file.isEmpty()) {
            logger.error("File is null or empty");
            throw new FuncErrorException("File is null or empty");
        }

        try {
            logger.info("Attempting to upload file: {}", fileName);
            Map<String, Object> params = Map.of(
                    "public_id", "nhndev/product/" + fileName,
                    "resource_type", resourceType
            );

            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), params);

            String url = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");
            logger.info("File uploaded successfully. URL: {}, Public ID: {}", url, publicId);

            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (IOException e) {
            logger.error("IO error during file upload: {}", e.getMessage(), e);
            throw new FuncErrorException("IO error during file upload: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during file upload: {}", e.getMessage(), e);
            throw new FuncErrorException("Unexpected error during file upload: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String publicId, String resourceType) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("resource_type", resourceType);

            cloudinary.uploader().destroy(publicId, params);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file from Cloudinary", e);
        }
    }
}
