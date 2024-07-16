package com.codingshuttle.app.libraryManager.services;

import com.codingshuttle.app.libraryManager.entities.AuthorEntity;
import com.codingshuttle.app.libraryManager.entities.BookEntity;
import com.codingshuttle.app.libraryManager.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.libraryManager.repositories.AuthorRepository;
import com.codingshuttle.app.libraryManager.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    public BookEntity getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
    public List<BookEntity> getAllBooks(String title) {
        if(title.isEmpty()) {
            return bookRepository.findAll();
        }
        else {
            return bookRepository.findByTitle(title);
        }
    }

    public List<BookEntity> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<BookEntity> getBookPublishedAfter(LocalDate date) {
        return bookRepository.findByPublishedOnAfter(date);
    }

    public List<BookEntity> getBooksByAuthor(Long authorId) {
        if(authorId == null) return new ArrayList<>();
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElse(null);
        if(authorEntity == null) throw new ResourceNotFoundException("Author not present with id="+authorId);
        return new ArrayList<>(authorEntity.getBooks());
    }

    public BookEntity createNewBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    public BookEntity updateBookById(BookEntity bookEntity, Long bookId) {
        existsById(bookId);
        bookEntity.setId(bookId);
        return bookRepository.save(bookEntity);

    }
    private void existsById(Long bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if(!exists) throw new ResourceNotFoundException("Book not found with id="+bookId);
    }

    public boolean deleteBookById(Long bookId) {
        existsById(bookId);
        bookRepository.deleteById(bookId);
        return true;
    }
}
