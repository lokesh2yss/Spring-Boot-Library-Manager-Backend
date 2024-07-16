package com.codingshuttle.app.libraryManager.controllers;

import com.codingshuttle.app.libraryManager.entities.AuthorEntity;
import com.codingshuttle.app.libraryManager.entities.BookEntity;
import com.codingshuttle.app.libraryManager.services.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "/authors")
public class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path="/{authorId}")
    public AuthorEntity getAuthorById(@PathVariable Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @GetMapping
    public List<AuthorEntity> getAllAuthors(@RequestParam(defaultValue = "") String name) {
        return authorService.getAllAuthors(name);
    }

    @PostMapping
    public AuthorEntity createNewAuthor(@RequestBody AuthorEntity authorEntity) {
        return authorService.createNewAuthor(authorEntity);
    }

    /*@GetMapping(path="/names")
    public List<AuthorEntity> findAuthorByName(@RequestParam String name) {
        return authorService.findAuthorByName(name);
    }*/

    @PutMapping(path="/{authorId}")
    public AuthorEntity updateAuthorById(@RequestBody AuthorEntity authorEntity, @PathVariable Long authorId) {
        return authorService.updateAuthorById(authorEntity, authorId);
    }

    @DeleteMapping(path="/{authorId}")
    public boolean deleteAuthorById(@PathVariable Long authorId) {
        return authorService.deleteAuthorById(authorId);
    }

    @PutMapping(path = "/{authorId}/books/{bookId}")
    public AuthorEntity assignBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        return authorService.assignBookToAuthor(authorId, bookId);
    }
}
