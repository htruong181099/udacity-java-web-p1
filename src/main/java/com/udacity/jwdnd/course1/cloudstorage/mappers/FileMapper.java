package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES " +
            "(#{file.fileName}, #{file.contentType}, #{file.fileSize}, #{file.userId}, #{file.fileData})")
    void insert(@Param("file") File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> findFilesByUserId(@Param("userId") Integer userId);
    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File findFileById(@Param("fileId") Integer fileId);
    @Select("SELECT * FROM FILES WHERE filename = #{fileName} and userid = #{userId}")
    File findFileByFilename(String fileName, Integer userId);

    @Delete("Delete from FILES where fileid = #{fileId}")
    void delete(Integer fileId);
}
