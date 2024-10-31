package com.example.jpa_2023_c16_e1_spring_data_jpa.repositories;

import com.example.jpa_2023_c16_e1_spring_data_jpa.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

//    its recommended to use @Query instead of relying on name of the method

//    @Query("SELECT a FROM Author a WHERE a.id = :id")
//    Author findAuthorById(Integer id); // select a from author a where a.id = :id

    @Query("SELECT a FROM Author a WHERE a.id = :id")
    Optional<Author> findAuthorById(Integer id); // select a from author a where a.id = :id

    @Query("SELECT a FROM Author a")
    List<Author> findAllAuthors();
}
