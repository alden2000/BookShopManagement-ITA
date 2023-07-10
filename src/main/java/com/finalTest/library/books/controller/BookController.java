package com.finalTest.library.books.controller;

import com.finalTest.library.books.entity.Book;
import com.finalTest.library.books.entity.BookRepository;
import com.finalTest.library.books.service.BookSercice;
import com.finalTest.library.genres.entity.Genre;
import com.finalTest.library.genres.service.GenreService;
import com.finalTest.library.languages.entity.Language;
import com.finalTest.library.languages.entity.LanguageRepository;
import com.finalTest.library.languages.service.LanguageService;
import com.finalTest.library.states.entity.State;
import com.finalTest.library.states.service.StateService;
import com.finalTest.library.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookSercice bookSercice;
    @Autowired
    GenreService genreService;
    @Autowired
    LanguageService languageService;
    @Autowired
    StateService stateService;

    @GetMapping("/addRemoveBook")
    public String addRemoveBook(Model model){
        List<Book> books = bookSercice.getAllBooks();
        model.addAttribute("book", books);
        return "addRemoveBook";
    }

    @GetMapping("/addBook")
        public String addBook(Model model, Model languageModel, Model genreModel, Model stateModel) {
        Book book = new Book();
        List<Language> languages = languageService.getAllLanguages();
        book.setLanguageList(languages);
        languageModel.addAttribute("languageOptions", languages);
        List<Genre> genres = genreService.getAllGenres();
        genreModel.addAttribute("genreOptions", genres);
        List<State> states = stateService.getAllStates();
        stateModel.addAttribute("stateOptions", states);
        model.addAttribute("book", book);
        return "addBook";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book){
        bookSercice.saveBook(book);
        return "redirect:/";
    }

//    @GetMapping("/removeBook")
//    public String removeBook(Model model){
//        List<Book> books = bookSercice.getAllBooks();
//        model.addAttribute("book", books);
//        return "removeBook";
//    }

    @GetMapping("/deleteBook")
    public String deletePerson(@RequestParam(value = "id") Book id) {
        bookRepository.delete(id);
        return "redirect:/removeBook";
    }
}
