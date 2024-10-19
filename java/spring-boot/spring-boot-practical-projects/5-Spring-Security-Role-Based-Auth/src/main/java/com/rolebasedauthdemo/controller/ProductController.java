package com.rolebasedauthdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE')")
    public ResponseEntity<?> saveProduct() {
        String msg = "Admin :: Create Product";
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','SELLER','CUSTOMER') and hasAuthority('READ')")
    public ResponseEntity<?> getProduct() {
        String msg = "Admin, Seller, Customer :: View Product";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyRole('ADMIN','SELLER') and hasAuthority('UPDATE')")
    public ResponseEntity<?> updateProduct() {
        String msg = "Admin, Seller :: Update Product";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('DELETE')")
    public ResponseEntity<?> deleteProduct() {
        String msg = "Admin :: Delete Product";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
