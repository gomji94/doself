package doself.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.file.domain.Files;

@Mapper
public interface FilesMapper {

	int deleteFileByIdx(String fileIdx);
	Files getFileInfoByIdx(String fileIdx);
	List<Files> getFileList();
	int addfile(Files fileDto);
	int addfiles(List<Files> fileDto);
}
