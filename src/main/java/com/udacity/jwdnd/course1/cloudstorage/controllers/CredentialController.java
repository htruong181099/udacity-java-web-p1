package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constant;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public CredentialController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping("/upload")
    public String uploadCredential(Authentication auth, Model model){
        User user = this.userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        model.addAttribute(Constant.FILE_LIST, this.fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, this.noteService.getListNotesByUserId(userId));
//        model.addAttribute(Constant.CREDENTIAL_LIST, this.credentialService.getListCredentialsByUserId(userId));
        return "home";
    }

}
