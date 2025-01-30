package com.example.cdoback.service;

import com.example.cdoback.entity.AppUserEntity;
import com.example.cdoback.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<AppUserEntity> getAppUsers() {
        return appUserRepository.findAll();
    }

    public AppUserEntity getAppUser(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    public AppUserEntity getAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUserEntity addAppUser(AppUserEntity appUserEntity) {
        appUserEntity.setPassword(bCryptPasswordEncoder.encode(appUserEntity.getPassword()));
        return appUserRepository.save(appUserEntity);
    }

    public AppUserEntity updateAppUser(AppUserEntity appUserEntity, Long id) {
        appUserEntity.setId(id);
        return appUserRepository.save(appUserEntity);
    }

    public void deleteAppUser(Long id) {
        appUserRepository.deleteById(id);
    }

}
