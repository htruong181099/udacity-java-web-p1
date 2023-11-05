package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    Note createNote(NoteDTO dto);
    List<Note> getListNotesByUserId(int userId);
    Note getNoteById(Integer noteId);
    void updateNote(Note note);
    void deleteNote(Integer noteId);
}
