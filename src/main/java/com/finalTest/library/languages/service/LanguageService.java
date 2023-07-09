package com.finalTest.library.languages.service;

import com.finalTest.library.languages.entity.Language;
import com.finalTest.library.languages.entity.LanguageRepository;
import com.finalTest.library.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    public List<Language> getAllLanguages(){
        return languageRepository.findAll();
    }
}
