package com.annotationtypes.springbootannotation.controller;

import com.annotationtypes.springbootannotation.beans.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/api") // if we want to bind the base url, then in that case
public class BookController {

//    when we are developing the api using spring mvc, then @Controller, @RequestMapping & @ResponseBody should be used
    @RequestMapping("/hello-world") //making this method as handler method by this annotation
//    @ResponseBody // we have to annotate handler method with this annotation & it will return as a JSON to the client
    public String helloWorld(){
        return "Hello World";
    }



//    @RequestMapping("/book")
//    @RequestMapping(value = {"/book", "/java", "/core"},
//        method = RequestMethod.GET,
////            produces = MediaType.APPLICATION_JSON_VALUE
//            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
//            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
////            consumes = MediaType.APPLICATION_XML_VALUE
//    ) // multiple URI's using @RequestMapping
////    @ResponseBody
//    public Book getBook() {
//        Book book = new Book(1, "Core Java", "Learn Java");
//        return book;
//    }


    @GetMapping(value = {"/book", "/java", "/core"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Book getBook() {
        Book book = new Book(1, "Core Java", "Learn Java");
        return book;
    }

//    @RequestMapping(value="/books/create", method=RequestMethod.POST)
//    @PostMapping("/books/create")
    @PostMapping(value = "/books/create",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
//    @ResponseStatus(value= HttpStatus.CREATED) // to add the response status
//    public Book createBook(@RequestBody Book book) { // we need to use one more annotation, RequestBody will convert JSON into java Book  object
    public ResponseEntity<Book> createBook(@RequestBody Book book) { // other way to create Response Status code
        System.out.println(book.getId());
        System.out.println(book.getTitle());
        System.out.println(book.getDescription());
//        return book;
        return new ResponseEntity<>(book, HttpStatus.CREATED); // this is other way to return response and status code, its must with the method signataure used above
    }

//    @RequestMapping(value = "/books/update/{id}", method=RequestMethod.PUT)
    @PutMapping(value = "/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        System.out.println(id);
        System.out.println(updatedBook.getTitle());
        System.out.println(updatedBook.getDescription());
        updatedBook.setId(id);
        return ResponseEntity.ok(updatedBook);
    }

//    @RequestMapping(value = "/books/delete/{id}", method=RequestMethod.DELETE)
    @DeleteMapping(value = "/books/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        System.out.println(id);
        return ResponseEntity.ok("Book deleted successfully: " + id );
    }

    @GetMapping("books/{id}/{title}/{description}")
//    public ResponseEntity<Book> pathVariableDemo(@PathVariable int id, @PathVariable String title, @PathVariable String description) {
    public ResponseEntity<Book> pathVariableDemo(@PathVariable int id, @PathVariable("title") String bookTitle, @PathVariable String description) {
        System.out.println(id);
        Book book = new Book();
        book.setId(id);
        book.setTitle(bookTitle);
        book.setDescription(description);
        return ResponseEntity.ok(book);
    }

//    localhost:8080/api/books/query?id=1&title=Javascript
    @GetMapping("/books/query")
    public ResponseEntity<Book> requestParamDemo(@RequestParam(name="id") int id, @RequestParam String title) {
        System.out.println(id);
        System.out.println(title);

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);

        return ResponseEntity.ok(book);

    }

}
