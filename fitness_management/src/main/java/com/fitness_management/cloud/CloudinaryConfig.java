package com.fitness_management.cloud;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class CloudinaryConfig {


    @Bean
    Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dbqa47fjt");
        config.put("api_key", "264549763441685");
        config.put("api_secret", "i7URJs7EoofhhJ3u8puYvcmpL4s");
        return new Cloudinary(config);
    }
}

