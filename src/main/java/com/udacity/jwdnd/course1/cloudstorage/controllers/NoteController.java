package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constant;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.impl.UserServiceImpl;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public NoteController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    public final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @PostMapping()
    public String addNote(@ModelAttribute Note note,
                          Model model,
                          Authentication auth
    ){
        User user = this.userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        if (note.getNoteId() == null){
            try {
                this.noteService.createNote(new NoteDTO(note.getNoteTitle(),note.getNoteDescription(),userId));
                model.addAttribute("noteUploadSuccess", "Note is created");
            } catch (Exception e){
                logger.error(e.getMessage(),e);
                model.addAttribute("noteError", "Error creating note");
            }
        } else {
            try {
                this.noteService.updateNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), userId));
                model.addAttribute("noteEditSuccess", "Note is updated");
            } catch (Exception e){
                logger.error(e.getMessage(),e);
                model.addAttribute("noteError", "Error updating note");
            }
        }
        model.addAttribute(Constant.FILE_LIST, this.fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, this.noteService.getListNotesByUserId(userId));
//        model.addAttribute("credentials", this.credentialService.getListCredentialsByUserId(userId));
        return "home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId,
                             Authentication auth,
                             Model model){
        User user = this.userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        try {
            noteService.deleteNote(noteId);
            model.addAttribute("noteDeleteSuccess", "Note successfully deleted.");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            model.addAttribute("noteError", "Error deleting note");
        }
        model.addAttribute(Constant.FILE_LIST, this.fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, this.noteService.getListNotesByUserId(userId));
//        model.addAttribute("credentials", this.credentialService.getListCredentialsByUserId(user.getUserid()));
        return "home";
    }
}
