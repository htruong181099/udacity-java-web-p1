package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {
    public final Logger logger = LoggerFactory.getLogger(CredentialServiceImpl.class);

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialServiceImpl(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    @Override
    public List<Credential> getListCredentialsByUserId(Integer userId) {
        return credentialMapper.findCredentialsByUserId(userId);
    }

    public Credential getCredentialById(Integer credentialId){
        return credentialMapper.findByCredentialId(credentialId);
    }

    @Override
    public void uploadCredential(CredentialDTO dto, Integer userId) {
        String encodedKey = genEncodedKey();
        String encodedPassword = encryptionService.encryptValue(dto.getPassword(), encodedKey);
        Credential credential = new Credential(null,
                dto.getUrl(), dto.getUsername(), encodedKey, encodedPassword, userId);
        credentialMapper.insert(credential);
    }

    @Override
    public void updateCredential(CredentialDTO dto, Integer userId) {
        String encodedKey = genEncodedKey();
        String encodedPassword = encryptionService.encryptValue(dto.getPassword(), encodedKey);
        Credential credential = new Credential(dto.getCredentialId(),
                dto.getUrl(), dto.getUsername(), encodedKey, encodedPassword, userId);
        credentialMapper.update(credential);
    }

    @Override
    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    @Override
    public Credential decodePassword(Integer credentialId, Integer userId) {
        Credential credential = credentialMapper.findByCredentialId(credentialId);
        if(!credential.getUserId().equals(userId)){
            throw new RuntimeException("Invalid userId");
        }
        String encryptedPassword = credential.getPassword();
        String encodedKey = credential.getKey();
        EncryptionService encryptionService = new EncryptionService();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        credential.setPassword(decryptedPassword);
        return credential;
    }

    protected String genEncodedKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
