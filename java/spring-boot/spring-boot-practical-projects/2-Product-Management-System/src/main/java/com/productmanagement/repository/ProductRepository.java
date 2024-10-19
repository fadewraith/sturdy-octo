package com.productmanagement.repository;

import com.productmanagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    // to do, writing a query to do natural sorting for the below data -
    /**
     * 742 Evergreen Terrace Springfield Illinois 62704 United States
     * 22B Baker Street Marylebone London NW1 6XE United Kingdom
     * 1600 Pennsylvania Avenue NW Washington DC 20500 United States
     * 1 Infinite Loop Cupertino California 95014 United States
     * 50 Avenue des Champs-Élysées Paris 75008 France
     * */

}
