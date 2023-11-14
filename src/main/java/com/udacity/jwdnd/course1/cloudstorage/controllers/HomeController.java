package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constant;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;
    private final UserService userService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService, UserService userService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        model.addAttribute(Constant.FILE_LIST, fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, noteService.getListNotesByUserId(userId));
        model.addAttribute(Constant.CREDENTIAL_LIST, credentialService.getListCredentialsByUserId(userId));
        return "home";
    }
}
