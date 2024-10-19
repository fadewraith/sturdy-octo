package com.productmanagement.controller;

import com.productmanagement.dto.ProductDTO;
import com.productmanagement.dto.ProductResponse;
import com.productmanagement.service.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
//    requestbody, pathvariable, requestparam & with alias
//    use dto, inplace of entity, to avoid exposure of entity

    @Autowired
    private ProductService productService;

    @PostMapping("/save-product")
//    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductDTO productDTO) { // in built validator
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) {

        try {
            //        custom validation
            validateProductDto(productDTO);

            Boolean saveProduct = productService.saveProduct(productDTO);
            if(!saveProduct) {
                return new ResponseEntity<>("product not saved", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("saved success", HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {

        List<ProductDTO> allProducts = null;

        try {
            allProducts = productService.getAllProducts();

            if(CollectionUtils.isEmpty(allProducts)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") Integer id) {

        ProductDTO productDTO = null;

        try {
            productDTO = productService.getProductById(id);

            if(ObjectUtils.isEmpty(productDTO)) {
                return new ResponseEntity<>("product not found for id: " + id, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {

        Boolean deleteProduct = null;

        try {
            deleteProduct = productService.deleteProduct(id);

            if(!deleteProduct) {
                return new ResponseEntity<>("product not deleted", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("product deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/products-page")
    public ResponseEntity<?> getProductsPagination(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
    ) {

        ProductResponse productsWithPagination = null;
//        String name = null;
//        name.toUpperCase();
        try {

            productsWithPagination = productService.getProductsWithPagination(pageNo, pageSize, sortBy, sortDir);

            if(ObjectUtils.isEmpty(productsWithPagination)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productsWithPagination, HttpStatus.OK);
    }

    private void validateProductDto(ProductDTO productDTO) throws BadRequestException {

        if(productDTO.getName() == null) {
            throw new BadRequestException("Name field is null");
        }

        if(productDTO.getName().isEmpty()) {
            throw new BadRequestException("Name field is empty");
        }

        if(productDTO.getDescription() == null) {
            throw new BadRequestException("description is null");
        }

        if(productDTO.getDescription().isEmpty()) {
            throw new BadRequestException("description is empty");
        }

        if(productDTO.getDescription().length() <= 3 || productDTO.getDescription().length() >= 50) {
            throw new BadRequestException("description must be including 3 - 50 chars");
        }

    }


}
