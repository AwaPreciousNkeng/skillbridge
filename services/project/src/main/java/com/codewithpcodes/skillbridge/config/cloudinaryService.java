package com.codewithpcodes.skillbridge.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class cloudinaryService {
    private final Cloudinary cloudinary;

    /**
     * Uploads a file (image or video) to Cloudinary.
     * @param file The file to upload (typically from a file upload endpoint).
     * @param folderName The target folder (e.g., "skillbridge/projects").
     * @return A Map containing the API response (including 'url' and 'public_id').
     * @throws IOException If the file stream cannot be read or API call fails.
     */

    public Map uploadFile(MultipartFile file, String folderName) throws IOException {
        // Determine resource type (default to auto for flexibility)
        String resourceType = file.getContentType() != null && file.getContentType().startsWith("video")
                ? "video"
                : "auto";
        Map params = ObjectUtils.asMap(
                "resource_type", resourceType,
                "folder", folderName
        );
        return cloudinary.uploader().upload(file.getBytes(), params);
    }

    /**
     * Deletes a file from Cloudinary using its public ID.
     * @param publicId The unique identifier of the file in Cloudinary.
     * @param mediaType The type of media ("IMAGE" or "VIDEO").
     * @throws IOException If the deletion request fails.
     */
    public Map deleteFile(String publicId, String mediaType) throws IOException {
        // Cloudinary expects lowercase resource type: 'video' or 'image'
        String resourceType = mediaType.equalsIgnoreCase("VIDEO") ? "video" : "image";

        Map params = ObjectUtils.asMap(
                "resource_type", resourceType
        );
        return cloudinary.uploader().destroy(publicId, params);
    }
}
