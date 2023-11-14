package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteMapper noteMapper;
    public final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    public NoteServiceImpl(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    @Override
    public Note createNote(NoteDTO dto) {
        Note note = new Note(
                dto.getNoteTitle(),
                dto.getNoteDescription(),
                dto.getUserId()
        );
        noteMapper.insert(note);
        return note;
    }

    @Override
    public List<Note> getListNotesByUserId(int userId) {
        return noteMapper.findNotesByUserId(userId);
    }

    @Override
    public Note getNoteById(Integer noteId) {
        return noteMapper.findNoteById(noteId).orElseThrow(()->new RuntimeException("note not found"));
    }

    @Override
    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    @Override
    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
