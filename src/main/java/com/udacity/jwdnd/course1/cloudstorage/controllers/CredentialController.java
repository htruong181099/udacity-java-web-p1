package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constant;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public String uploadCredential(@ModelAttribute CredentialDTO dto, Authentication auth, Model model) {
        User user = userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();

        if (dto.getCredentialId() == null) {
            credentialService.uploadCredential(dto, userId);
            model.addAttribute("credentialUploadSuccess", "Credential successfully deleted.");
        } else {
            credentialService.updateCredential(dto, userId);
            model.addAttribute("credentialEditSuccess", "Credential successfully deleted.");
        }

        model.addAttribute(Constant.FILE_LIST, fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, noteService.getListNotesByUserId(userId));
        model.addAttribute(Constant.CREDENTIAL_LIST, credentialService.getListCredentialsByUserId(userId));
        return "home";
    }

    @GetMapping(value = "/password")
    @ResponseBody
    public Map<String, String> decodePassword(@RequestParam Integer credentialId, Authentication auth) {
        User user = userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        Credential credential = credentialService.decodePassword(credentialId, userId);
        Map<String, String> response = new HashMap<>();
        response.put("decryptedPassword", credential.getPassword());
        return response;
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId,
                                   Authentication auth,
                                   Model model) {
        User user = userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        try {
            credentialService.deleteCredential(credentialId);
            model.addAttribute("credentialDeleteSuccess", "Credential successfully deleted.");
        } catch (Exception e) {
            model.addAttribute("credentialError", e.toString());
        }

        model.addAttribute(Constant.FILE_LIST, fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, noteService.getListNotesByUserId(userId));
        model.addAttribute(Constant.CREDENTIAL_LIST, credentialService.getListCredentialsByUserId(userId));
        return "home";
    }

}
