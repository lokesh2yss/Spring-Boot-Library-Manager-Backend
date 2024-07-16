package com.codingshuttle.app.libraryManager.controllers;

import com.codingshuttle.app.libraryManager.entities.AuthorEntity;
import com.codingshuttle.app.libraryManager.entities.BookEntity;
import com.codingshuttle.app.libraryManager.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/{bookId}")
    public BookEntity getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping
    public List<BookEntity> getAllBooks(@RequestParam Optional<String> title, @RequestParam Optional<String> dateAfter, @RequestParam Optional<Long> authorId) {
        if(title.isPresent()) {
            return bookService.getAllBooks(title.get());
        }
        else if(dateAfter.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.getDefault());
            LocalDate date = LocalDate.parse(dateAfter.orElse("1970-01-01"), formatter);
            return bookService.getBookPublishedAfter(date);
        }
        else if(authorId.isPresent()) {
            return bookService.getBooksByAuthor(authorId.orElse(null));
        }
        else {
            return bookService.getAllBooks("");
        }
    }

    /*@GetMapping
    public List<BookEntity> getBookByTitle(@RequestParam Optional<String> title) {
        return bookService.getBookByTitle(title.orElse(""));
    }*/

    /*@GetMapping
    public List<BookEntity> getBooksPublishedAfter(@RequestParam Optional<String> dateAfter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.getDefault());
        LocalDate date = LocalDate.parse(dateAfter.orElse("1970-01-01"), formatter);
        return bookService.getBookPublishedAfter(date);
    }*/

    /*@GetMapping
    public List<BookEntity> getBooksByAuthor(@RequestParam Optional<Long> authorId) {
        return bookService.getBooksByAuthor(authorId.orElse(null));
    }*/

    @PostMapping
    public BookEntity createNewBook(@RequestBody BookEntity bookEntity) {
        return bookService.createNewBook(bookEntity);
    }

    @PutMapping(path = "/{bookId}")
    public BookEntity updateBookById(@RequestBody BookEntity bookEntity, @PathVariable Long bookId) {
        return bookService.updateBookById(bookEntity, bookId);
    }
    @DeleteMapping(path="/{bookId}")
    public boolean deleteBookById(@PathVariable Long bookId) {
        return bookService.deleteBookById(bookId);
    }

}
