package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> getListCredentialsByUserId(Integer userId);

    void updateCredential(Credential credential);
}
