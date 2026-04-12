package com.graphqldemo.jbooks.author;

import com.graphqldemo.jbooks.book.Book;
import com.graphqldemo.jbooks.book.BookController;
import com.graphqldemo.jbooks.book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public AuthorController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @SchemaMapping
    public List<Book> booksWithDelay(Author author) throws InterruptedException {
        // this could be a call to some microservice would retrieve books by authorId
        log.info("Retrieving books for author " + author.getName());
        Thread.sleep((1000));
        return new ArrayList<>();
    }

    // sees that 'books' is the field name on 'Author'
    // and will automatically passing the parent of 'books' field, i.e. 'Author'
//    @SchemaMapping
//    public List<Book> books(Author author) throws InterruptedException {
//        log.info("books for author: {}", author);
//        Thread.sleep(1000);
//        return new ArrayList<>();
//    }

    @BatchMapping
    public List<List<Book>> books(List<Author> authors) {
        log.info("Getting books for {}", authors.size());
        List<Long> authorIds = authors.stream().map(Author::getId).toList();

        List<Book> books = bookRepository.findByAuthorIdIn(authorIds);

        Map<Long, List<Book>> booksByAuthorId = books.stream().collect(Collectors.groupingBy(book -> book.getAuthor().getId()));

        return authors.stream().map(author -> booksByAuthorId.getOrDefault(author.getId(), Collections.emptyList())).toList();
    }

}
