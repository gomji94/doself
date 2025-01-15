package doself.file.service;

import org.springframework.web.multipart.MultipartFile;

import doself.file.domain.Files;

public interface FileService {

	void addFile(MultipartFile file);
	
//	void addFiles(MultipartFile[] files);

	void deleteFile(Files fileDto); 
}
