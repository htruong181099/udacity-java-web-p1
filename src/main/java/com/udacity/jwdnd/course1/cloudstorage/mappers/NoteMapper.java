package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> findNotesByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Optional<Note> findNoteById(@Param("noteId") Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES " +
            "(#{noteTitle},#{noteDescription},#{userId})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription}, userid = #{note.userId} " +
            "WHERE noteid = #{note.noteId}")
    void update(@Param("note") Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void delete(@Param("noteId") Integer noteId);

}
