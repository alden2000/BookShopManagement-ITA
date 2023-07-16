package com.finalTest.BookShop.genres.service;

import com.finalTest.BookShop.genres.entity.Genre;
import com.finalTest.BookShop.genres.entity.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }
}
