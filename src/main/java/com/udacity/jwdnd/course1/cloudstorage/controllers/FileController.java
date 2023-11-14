package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constant;
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
        User user = userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        if (fileUpload.isEmpty()) {
            model.addAttribute("fileError", "Invalid file");
        } else if(this.fileService.isFileNameAvailable(fileUpload.getOriginalFilename(), userId)){
            try {
                this.fileService.uploadFile(fileUpload, userId);
                model.addAttribute("fileUploadSuccess", "File successfully uploaded.");
            } catch (Exception e) {
                model.addAttribute("fileError", e.toString());
            }
        } else {
            model.addAttribute("fileError", "Can't upload files with duplicate names.");
        }

        model.addAttribute(Constant.FILE_LIST, fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, noteService.getListNotesByUserId(userId));
        model.addAttribute(Constant.CREDENTIAL_LIST, credentialService.getListCredentialsByUserId(userId));
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
        User user = userService.getUserByUsername(auth.getName());
        Integer userId = user.getUserId();
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("fileDeleteSuccess", "File successfully deleted.");
        } catch (Exception e) {
            model.addAttribute("fileError", e.toString());
        }

        model.addAttribute(Constant.FILE_LIST, fileService.getListFilesByUserId(userId));
        model.addAttribute(Constant.NOTE_LIST, noteService.getListNotesByUserId(userId));
        model.addAttribute(Constant.CREDENTIAL_LIST, credentialService.getListCredentialsByUserId(userId));
        return "home";
    }
}
