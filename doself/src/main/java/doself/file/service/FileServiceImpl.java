package doself.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.file.domain.Files;
import doself.file.mapper.FilesMapper;
import doself.file.util.FilesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{
	
	private final FilesUtils filesUtils;
	private final FilesMapper filesMapper;
	
	@Override
	public void deleteFile(Files files) {
		String path = files.getFilePath();
		Boolean isDelete = filesUtils.deleteFileByPath(path);
		if(isDelete) filesMapper.deleteFileByIdx(files.getFileIdx());   // 값이 true로 넘어온다면 delete 쿼리문 실행
	}
	
	@Override
	public void addFile(MultipartFile file) {
		Files fileInfo = filesUtils.uploadFile(file);
		if(fileInfo != null) filesMapper.addfile(fileInfo);
	}
	
//	@Override
//	public void addFiles(MultipartFile[] files) {
//		List<Files> fileList = filesUtils.uploadFiles(files);
//		if(!fileList.isEmpty()) filesMapper.addfiles(fileList);
//	}
}
