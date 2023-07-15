package com.finalTest.library.genres.controller;

import com.finalTest.library.genres.entity.Genre;
import com.finalTest.library.genres.entity.GenreRepository;
import com.finalTest.library.languages.entity.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GenreController {

    @Autowired
    GenreRepository genreRepository;

    @PostMapping("/admin/saveGenre")
    public String saveLanguage(@ModelAttribute(value = "genre") Genre genre){
        genreRepository.save(genre);
        return "redirect:/admin/advanced";
    }
}
