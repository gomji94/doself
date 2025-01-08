package doself.common.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	
	String getPrimaryKey(String separator, String tableName, String primaryKeyName);
	
}
