package net.google.journalApp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.CloudinaryService;

@RestController
@RequestMapping("v1/file-upload")
public class FileUploadController {

	@Autowired
	private CloudinaryService cloudinaryService;

	@PostMapping("/upload")
	public ServiceResponse uploadFile(@RequestParam("file") MultipartFile file) { 
		try {
			return ServiceResponse.asSuccess(cloudinaryService.uploadFile(file));
		} catch (IOException e) {
			return ServiceResponse.asSuccess("File upload failed"); 
		}
	}
}
