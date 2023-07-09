package com.finalTest.library.genres.service;

import com.finalTest.library.books.entity.Book;
import com.finalTest.library.genres.entity.Genre;
import com.finalTest.library.genres.entity.GenreRepository;
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
