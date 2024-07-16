package com.codingshuttle.app.libraryManager.repositories;

import com.codingshuttle.app.libraryManager.entities.AuthorEntity;
import com.codingshuttle.app.libraryManager.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);

    List<BookEntity> findByPublishedOnAfter(LocalDate date);
}
