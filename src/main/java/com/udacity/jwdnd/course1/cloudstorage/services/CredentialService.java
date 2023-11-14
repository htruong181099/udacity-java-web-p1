package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.CredentialDTO;

import java.util.List;

public interface CredentialService {
    List<Credential> getListCredentialsByUserId(Integer userId);
    Credential getCredentialById(Integer credentialId);
    void uploadCredential(CredentialDTO dto, Integer userId);
    void updateCredential(CredentialDTO dto, Integer userId);
    void deleteCredential(Integer credentialId);
    Credential decodePassword(Integer credentialId, Integer userId);
}
