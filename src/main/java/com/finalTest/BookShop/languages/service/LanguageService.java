package com.finalTest.BookShop.languages.service;

import com.finalTest.BookShop.languages.entity.Language;
import com.finalTest.BookShop.languages.entity.LanguageRepository;
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
