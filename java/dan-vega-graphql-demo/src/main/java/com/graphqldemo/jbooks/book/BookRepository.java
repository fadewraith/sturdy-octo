package com.graphqldemo.jbooks.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Collection<?> findAllByTitleContainsIgnoreCase(String text);

    List<Book> findByAuthorIdIn(List<Long> authorIds);

}
