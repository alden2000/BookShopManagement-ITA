package com.finalTest.BookShop.languages.controller;

import com.finalTest.BookShop.languages.entity.Language;
import com.finalTest.BookShop.languages.entity.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LanguageController {
    @Autowired
    LanguageRepository languageRepository;

    @PostMapping("/admin/saveLanguage")
    public String saveLanguage(@ModelAttribute(value = "language") Language language){
        languageRepository.save(language);
        return "redirect:/admin/advanced";
    }
}
