package doself.file.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import doself.file.domain.Files;

@Component
public class FilesUtils {
	
	// 고정된 정적인 값(어플리케이션 프로퍼티에 저장된 값을 value 어노테이션으로 호출 가능
	@Value("${file.path}")
	private String fileRealPath;

	public Files uploadFile(MultipartFile multipartFile) {
		
		Files fileInfo = storeFile(multipartFile);
		
		return fileInfo;
	}

	public List<Files> uploadFiles(MultipartFile[] multipartFiles) {
		List<Files> fileList = new ArrayList<Files>();
		Files fileInfo;
		// 올린 파일의 갯수만큼 업로드
		for(MultipartFile multipartFile : multipartFiles) {
			fileInfo = storeFile(multipartFile);
			if(fileInfo != null) fileList.add(fileInfo);
		}
		return fileList;
	}
	
	
	private Files storeFile(MultipartFile multipartFile) {
		// 파일이 비어있으면 null로 반환
		if(multipartFile.isEmpty()) return null;
		
		// 현재 날짜 구하기(Asia/Seoul)
		LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
		// 날짜 패턴(디렉토리) - 디렉토리명에 할당하기 위함
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		// 콘텐츠타입 분류(디렉토리)
		String contentType = multipartFile.getContentType();
		
		if(contentType != null && contentType.indexOf("image") > -1) {
			contentType = "/image";   // 이미지라면 image로 타입을 바꿈
		}else {
			contentType = "/file";    // 이외에는 file로 받은
		}
		
		String dbFilePath = fileRealPath + "/attachment/" + now.format(formatter) + contentType;
		String path = Paths.get(dbFilePath).toString();
		
		// 파일 명이 겹치지 않게 파일명 설정
    	String newFileName = "";
    	/*
    	 * 나노타임 설정
    	String[] fileNameSplit = multipartFile.getOriginalFilename().split("\\.");

    	for(int i=0; i<fileNameSplit.length; i++) {
    		if(i == (fileNameSplit.length-1)) {
    			fileNameSplit[i] = "." + fileNameSplit[i];
    		}else {			    			
    			fileNameSplit[i] = fileNameSplit[i].replaceAll("\\s", "") + Long.toString(System.nanoTime());
    		}
    		resultFileName += fileNameSplit[i];
    	}
		*/
    	// 유효 아이디값 설정
    	String[] fileNameSplit = multipartFile.getOriginalFilename().split("\\.");
    	newFileName = UUID.randomUUID().toString()+ "." + fileNameSplit[1];
    	
    	createFolder(path);
    	
    	byte[] bytes;   // byte로 바꿈    	
    	Path uploadPath = Paths.get(path + "/" + newFileName);
    	
    	dbFilePath += ("/" + newFileName);
    	
    	Files fileInfo = null;
    	
		try {
			
			bytes = multipartFile.getBytes();   // 이진코드로 bytes에 담아 변환
			
			// 파일업로드 
			java.nio.file.Files.write(uploadPath, bytes);     // 이진코드명 변경
			
			DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyMMdd");
			
			String fileIdx = "file_"+ now.format(fileFormatter)+Long.toString(System.nanoTime());
			fileInfo = Files.builder()
							.fileOriginalName(multipartFile.getOriginalFilename())
							.fileNewName(newFileName)
							.filePath(dbFilePath.replaceAll(fileRealPath, ""))   // 변수명 세팅했던 값에 replace
							.build();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return fileInfo;
	}
	
	
	private void createFolder(String path){
 		// 파일을 조작할 수 있는 자바 클래스
		File isFile = new File(path);		
 		if(!isFile.isDirectory()){
 			isFile.mkdirs();			
 		}
 		if(!isFile.canWrite()){
 			isFile.setWritable(true);   // 쓰는 권한 부여
 		}
 		if(!isFile.canRead()){
 			isFile.setReadable(true);   // 읽는 권한 부여
 		}
 	}

	public boolean deleteFileByPath(String path) {
		// DB path와 realpath를 합쳐서 객체 생성
		path = fileRealPath + path;
		boolean isDelete = false;
		File file = new File(path);
		
		Path deletePath = Paths.get(file.getAbsolutePath());
		
		try {
			java.nio.file.Files.deleteIfExists(deletePath);   // 지워졌다면
			isDelete = true;   // delete true
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isDelete;
	}
}





