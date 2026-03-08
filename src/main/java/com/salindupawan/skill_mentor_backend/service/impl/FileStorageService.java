package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.config.SupabaseConfig;
import com.salindupawan.skill_mentor_backend.exception.SkillMentorException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    private final SupabaseConfig supabaseConfig;
    private final OkHttpClient client = new OkHttpClient();

    public String uploadFile(MultipartFile file) {

        validateFile(file);

        try {
            String filePath = generateFileName(file);
            String uploadUrl = String.format("%s/%s",
                    supabaseConfig.getUploadUrl(),filePath
                    );

            RequestBody requestBody = RequestBody.create(
                    file.getBytes(),
                    MediaType.parse(file.getContentType())
            );

            Request request = new Request.Builder()
                    .url(uploadUrl)
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + supabaseConfig.getSupabaseKey())
                    .addHeader("apikey", supabaseConfig.getSupabaseKey())
                    .addHeader("Content-Type", file.getContentType())
                    .addHeader("x-upsert", "true") // Overwrite if exists
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    log.error("Supabase upload failed: {} - {}", response.code(), errorBody);
                    throw new ValidationException("Failed to upload file to storage: " + errorBody);
                }

                String publicUrl = supabaseConfig.getPublicUrl(filePath);
                log.info("File uploaded successfully: {}", publicUrl);
                return publicUrl;
            }
        } catch (IOException e) {
            log.error("Error uploading file to Supabase", e);
            throw new SkillMentorException("Failed to upload file: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        try {
            // Extract file path from URL
            String filePath = extractFilePathFromUrl(fileUrl);

            String deleteUrl = String.format("%s/object/%s/%s",
                    supabaseConfig.getStorageUrl(),
                    supabaseConfig.getBucketName(),
                    filePath);

            Request request = new Request.Builder()
                    .url(deleteUrl)
                    .delete()
                    .addHeader("Authorization", "Bearer " + supabaseConfig.getSupabaseKey())
                    .addHeader("apikey", supabaseConfig.getSupabaseKey())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.warn("Failed to delete file from Supabase: {}", response.message());
                } else {
                    log.info("Successfully deleted file: {}", filePath);
                }
            }
        } catch (Exception e) {
            log.error("Error deleting file from Supabase", e);
            // Don't throw exception - deletion failure shouldn't stop the main operation
        }
    }

    /**
     * Generate unique file name
     */
    private String generateFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension.toLowerCase();
    }

    /**
     * Check if file extension is allowed
     */
    private boolean isAllowedExtension(String extension) {
        return extension.matches("^(png|jpg|jpeg|gif|webp)$");
    }

    /**
     * Extract file path from full Supabase URL
     */
    private String extractFilePathFromUrl(String fileUrl) {
        // URL format: https://xxx.supabase.co/storage/v1/object/public/bucket-name/path/to/file.png
        String publicPath = "/storage/v1/object/public/" + supabaseConfig.getBucketName() + "/";

        if (fileUrl.contains(publicPath)) {
            return fileUrl.substring(fileUrl.indexOf(publicPath) + publicPath.length());
        }

        // If URL doesn't match expected format, return as-is
        return fileUrl;
    }

    /**
     * Get public URL for a file path
     */
    public String getPublicUrl(String filePath) {
        return supabaseConfig.getPublicUrl(filePath);
    }

    /**
     * Validate uploaded file
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("File cannot be empty");
        }

        // Check file size
        if (file.getSize() > supabaseConfig.getMaxFileSize()) {
            throw new ValidationException(
                    String.format("File size exceeds maximum allowed size of %d bytes",
                            supabaseConfig.getMaxFileSize())
            );
        }

        // Check file type
        String contentType = file.getContentType();
        if (!supabaseConfig.isAllowedFileType(contentType)) {
            throw new ValidationException(
                    "Invalid file type. Allowed types: " +
                            String.join(", ", supabaseConfig.getAllowedFileTypes())
            );
        }

        // Check file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new ValidationException("File name cannot be empty");
        }

        String extension = FilenameUtils.getExtension(originalFilename).toLowerCase();
        if (!isAllowedExtension(extension)) {
            throw new ValidationException(
                    "Invalid file extension. Allowed: png, jpg, jpeg, gif, webp"
            );
        }
    }
}
