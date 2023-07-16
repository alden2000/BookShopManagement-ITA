package com.finalTest.BookShop.genres.controller;

import com.finalTest.BookShop.genres.entity.Genre;
import com.finalTest.BookShop.genres.entity.GenreRepository;
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
