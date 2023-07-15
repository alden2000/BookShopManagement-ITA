package com.finalTest.library.books.controller;

import com.finalTest.library.books.entity.Book;
import com.finalTest.library.books.entity.BookRepository;
import com.finalTest.library.books.service.BookService;
import com.finalTest.library.genres.entity.Genre;
import com.finalTest.library.genres.service.GenreService;
import com.finalTest.library.languages.entity.Language;
import com.finalTest.library.languages.service.LanguageService;
import com.finalTest.library.states.entity.State;
import com.finalTest.library.states.entity.StateRepository;
import com.finalTest.library.states.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    BookService bookService;
    @Autowired
    GenreService genreService;
    @Autowired
    LanguageService languageService;
    @Autowired
    StateService stateService;

    @GetMapping("/admin/addRemoveBook")
    public String addRemoveBook(Model model, Model genreModel){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("book", books);
        List<Genre> genreList = genreService.getAllGenres();
        model.addAttribute("genre", genreList);
        return "addRemoveBook";
    }

    @GetMapping("/admin/addBook")
        public String addBook(Model model, Model languageModel, Model genreModel, Model stateModel) {
        Book book = new Book();
        List<Language> languages = languageService.getAllLanguages();
        book.setLanguageList(languages);
        languageModel.addAttribute("languageOptions", languages);
        List<Genre> genres = genreService.getAllGenres();
        book.setGenreList(genres);
        genreModel.addAttribute("genreOptions", genres);
        List<State> states = stateService.getAllStates();
        stateModel.addAttribute("stateOptions", states);
        model.addAttribute("book", book);
        return "addBook";
    }

    @PostMapping("/admin/saveBook")
    public String saveBook(@ModelAttribute("book") Book book){
        bookService.saveBook(book);
        return "redirect:/admin/addRemoveBook";
    }

    @GetMapping("/admin/updateBook")
    public String updateBook(@ModelAttribute(value = "stateId") Long stateId,
                             @ModelAttribute(value = "bookPrimaryTitle") String bookPrimaryTitle,
                             @ModelAttribute(value = "bookTitle") String bookTitle,
                             @ModelAttribute(value = "bookAuthor") String bookAuthor,
                             @ModelAttribute(value = "bookPrice") BigDecimal bookPrice,
                             @ModelAttribute(value = "bookInStock") Integer bookInStock,
                             @ModelAttribute(value = "bookState") String bookState,
                             @ModelAttribute(value = "bookId") Long bookId) {
        Book book = bookRepository.getReferenceById(bookId);
        State state = stateRepository.getReferenceById(stateId);
        String[] arrayOfString = bookState.split("i");
        state.setName(arrayOfString[0]);
        state.setId((long) Integer.parseInt(arrayOfString[1]));
        book.setAuthor(bookAuthor);
        book.setTitle(bookTitle);
        book.setPrice(bookPrice);
        book.setPrimaryTitle(bookPrimaryTitle);
        book.setInStock(bookInStock);
        book.setState(state);

        bookRepository.save(book);
        return "redirect:/admin/addRemoveBook";
    }
    @GetMapping("/books")
    public String loadBooks(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int pageSize, Model model) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<Book> books = bookRepository.findAll(pageable);
        model.addAttribute("book", books);
        int totalPages = books.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers= IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //System.out.println(books.get(books.size()-1).getImgUrl());
        return "books";
    }

    @GetMapping("/updateInStock")
    public String updateInStock(@ModelAttribute(value = "InStock") Integer inStock,
                              @ModelAttribute(value = "bookId") Long bookId){
        Book book = bookRepository.getReferenceById(bookId);
        book.setInStock(inStock-1);
        if (book.getInStock() != -1) {
            bookRepository.save(book);
        }
        return "redirect:/books";
    }
    @GetMapping("/admin/advanced")
    public String advanced(Model modelLanguage, Model modenGenre, Model modelState){
        Language language = new Language();
        Genre genre = new Genre();
        State state = new State();
        modelLanguage.addAttribute("language", language);
        modenGenre.addAttribute("genre", genre);
        modelState.addAttribute("state", state);
        return "advanced";
    }
}
