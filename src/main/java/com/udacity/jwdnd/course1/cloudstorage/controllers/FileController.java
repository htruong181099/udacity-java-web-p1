package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public FileController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping()
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication auth,
                             Model model) throws IOException {
        User user = this.userService.getUserByUsername(auth.getName());
        if (this.fileService.isFileNameAvailable(fileUpload.getName(), user.getUserId())) {
            try {
                this.fileService.uploadFile(fileUpload, user.getUserId());
                model.addAttribute("fileUploadSuccess", "File successfully uploaded.");
            } catch (Exception e) {
                model.addAttribute("fileError", e.toString());
            }
        } else {
            model.addAttribute("fileError", "Can't upload files with duplicate names.");
        }

        model.addAttribute("fileList", this.fileService.getListFilesByUserId(user.getUserId()));
        model.addAttribute("noteList", this.noteService.getListNotesByUserId(user.getUserId()));
//        model.addAttribute("credentialList", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }

    @GetMapping("/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") Integer fileId,
                                       Authentication auth,
                                       Model model) throws IOException {
        File file = this.fileService.getFileById(fileId);
        String contentType = file.getContentType();
        String fileName = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file.getFileData());
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId,
                             Authentication auth,
                             Model model) throws IOException {
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("fileDeleteSuccess", "File successfully deleted.");
        } catch (Exception e) {
            model.addAttribute("fileError", e.toString());
        }
        User user = this.userService.getUserByUsername(auth.getName());
        model.addAttribute("fileList", this.fileService.getListFilesByUserId(user.getUserId()));
        model.addAttribute("noteList", this.noteService.getListNotesByUserId(user.getUserId()));
//        model.addAttribute("credentialList", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }
}
