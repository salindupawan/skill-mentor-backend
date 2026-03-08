package com.salindupawan.skill_mentor_backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SupabaseConfig {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    @Value("${file.upload.max-size:5242880}") // Default 5MB in bytes
    private long maxFileSize;

    @Value("${file.upload.allowed-types:image/png,image/jpeg,image/jpg,image/gif,image/webp}")
    private String[] allowedFileTypes;

    /**
     * Get the storage API URL
     */
    public String getStorageUrl() {
        return supabaseUrl + "/storage/v1";
    }

    /**
     * Get the public URL for a file
     */
    public String getPublicUrl(String filePath) {
        return String.format("%s/storage/v1/object/public/%s/%s",
                supabaseUrl, bucketName, filePath);
    }

    /**
     * Get the upload URL for the bucket
     */
    public String getUploadUrl() {
        return String.format("%s/object/%s", getStorageUrl(), bucketName);
    }

    /**
     * Validate file type
     */
    public boolean isAllowedFileType(String contentType) {
        if (contentType == null) {
            return false;
        }
        for (String allowedType : allowedFileTypes) {
            if (contentType.toLowerCase().contains(allowedType.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
