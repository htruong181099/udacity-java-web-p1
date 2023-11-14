package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> findCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    Credential findByCredentialId(Integer credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    void delete(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key},password=#{password} WHERE credentialId = #{credentialId}")
    void update(Credential credential);


}
