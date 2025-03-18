package net.google.journalApp.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	@Autowired
	private Cloudinary cloudinary;

	public String uploadFile(MultipartFile file) throws IOException {
		// Upload file as video explicitly
		String contentType = file.getContentType();
		String resourceType = "auto"; // Auto-detect image/video

		if (contentType != null && contentType.startsWith("image/")) {
			resourceType = "image";
		} else if (contentType != null && contentType.startsWith("video/")) {
			resourceType = "video";
		}

		Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
				ObjectUtils.asMap("resource_type", resourceType));

		return uploadResult.getOrDefault("secure_url", "").toString();

	}

}
