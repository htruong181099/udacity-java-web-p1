package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constant;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@ControllerAdvice
public class ExceptionController {
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public ExceptionController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String fileLimitExceptionHandler(Authentication auth,
                             Model model) {
        User user = userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        model.addAttribute("fileError", "File too large");
        model.addAttribute(Constant.FILE_LIST, fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, noteService.getListNotesByUserId(userId));
        model.addAttribute(Constant.CREDENTIAL_LIST, credentialService.getListCredentialsByUserId(userId));
        return "home";
    }
}
