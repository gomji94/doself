package doself.file.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Files {
	private String fileIdx;
	private String fileOriginalName;
	private String fileNewName;
	private String filePath;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fileRegDate;
}
