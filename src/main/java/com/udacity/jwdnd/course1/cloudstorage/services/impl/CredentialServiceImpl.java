package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {
    public final Logger logger = LoggerFactory.getLogger(CredentialServiceImpl.class);

    @Override
    public List<Credential> getListCredentialsByUserId(Integer userId) {
        return null;
    }

    @Override
    public void updateCredential(Credential credential) {

    }
}
