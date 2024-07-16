package com.codingshuttle.app.libraryManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private Set<AuthorEntity> authors;

    private LocalDate publishedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getPublishedOn(), that.getPublishedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPublishedOn());
    }
}
