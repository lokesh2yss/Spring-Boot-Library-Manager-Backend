package com.codingshuttle.app.libraryManager.services;

import com.codingshuttle.app.libraryManager.entities.AuthorEntity;
import com.codingshuttle.app.libraryManager.entities.BookEntity;
import com.codingshuttle.app.libraryManager.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.libraryManager.repositories.AuthorRepository;
import com.codingshuttle.app.libraryManager.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public AuthorEntity getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    public List<AuthorEntity> getAllAuthors(String name) {
        if(name.isEmpty()) {
            return authorRepository.findAll();
        }
        else {
            return authorRepository.findByName(name);
        }
    }

    public AuthorEntity createNewAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    public List<AuthorEntity> findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    public AuthorEntity updateAuthorById(AuthorEntity authorEntity, Long authorId) {
        existsById(authorId);
        authorEntity.setId(authorId);
        return authorRepository.save(authorEntity);
    }
    private void existsById(Long authorId) {
        boolean exists = authorRepository.existsById(authorId);
        if(!exists) throw new ResourceNotFoundException("Book not found with id="+authorId);
    }

    public boolean deleteAuthorById(Long authorId) {
        existsById(authorId);
        authorRepository.deleteById(authorId);
        return true;
    }

    public AuthorEntity assignBookToAuthor(Long authorId, Long bookId) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
        Optional<BookEntity> bookEntity = bookRepository.findById(bookId);

        return authorEntity.flatMap(author ->
                bookEntity.map(book -> {
                    author.getBooks().add(book);
                    return authorRepository.save(author);

                })).orElse(null);
    }
}
