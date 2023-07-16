package com.finalTest.BookShop.books.controller;

import com.finalTest.BookShop.books.entity.Book;
import com.finalTest.BookShop.books.entity.BookRepository;
import com.finalTest.BookShop.books.service.BookService;
import com.finalTest.BookShop.genres.entity.Genre;
import com.finalTest.BookShop.genres.service.GenreService;
import com.finalTest.BookShop.languages.entity.Language;
import com.finalTest.BookShop.languages.service.LanguageService;
import com.finalTest.BookShop.purchases.entity.Purchase;
import com.finalTest.BookShop.purchases.entity.PurchaseRepository;
import com.finalTest.BookShop.states.entity.State;
import com.finalTest.BookShop.states.entity.StateRepository;
import com.finalTest.BookShop.states.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Autowired
    PurchaseRepository purchaseRepository;

    @GetMapping("/admin/addRemoveBook")
    public String addRemoveBook(Model model){
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
    public String loadBooks(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, Model model) {
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
        //List<Book> bookList = bookService.getAllBooks();
        Book book = bookRepository.getReferenceById(bookId);
        book.setInStock(inStock-1);
        if (book.getInStock() != -1) {
            Purchase purchase= new Purchase();
            purchase.setBook(bookId);
            purchase.setDate(LocalDate.now());
            purchaseRepository.save(purchase);
            bookRepository.save(book);
        }
        return "redirect:/books";
    }
    @GetMapping("/admin/advanced")
    public String advanced(Model modelLanguage, Model modelGenre, Model modelState){
        Language language = new Language();
        Genre genre = new Genre();
        State state = new State();
        modelLanguage.addAttribute("language", language);
        modelGenre.addAttribute("genre", genre);
        modelState.addAttribute("state", state);
        return "advanced";
    }
}
