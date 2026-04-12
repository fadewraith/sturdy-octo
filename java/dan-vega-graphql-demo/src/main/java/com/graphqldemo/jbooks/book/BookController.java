package com.graphqldemo.jbooks.book;

import com.graphqldemo.jbooks.author.Author;
import com.graphqldemo.jbooks.author.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

//    @SchemaMapping(typeName = "Query", field = "books") or
//    @QueryMapping
//    public List<Book> books() { // books method name is exactly as defined in schema in Query type
//        return bookRepository.findAll();
//    } or
    @QueryMapping(name = "books")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Optional<Book> book(@Argument Long id) {
        return bookRepository.findById(id);
    }

    @SchemaMapping
    public Author author(Book book) {
        return authorRepository.findById(book.getAuthor().getId()).orElse(null);
    }

    @MutationMapping
    public Book addBook(@Argument BookInput bookInput) {
        Author author = authorRepository.findById(bookInput.authorId()).orElseThrow(() -> new RuntimeException("Author not found"));
        Book book = new Book();
        book.setTitle(bookInput.title());
        book.setAuthor(author);
        return bookRepository.save(book);
    }
}
