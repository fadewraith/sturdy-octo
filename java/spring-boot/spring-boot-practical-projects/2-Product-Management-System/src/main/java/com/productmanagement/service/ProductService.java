package com.productmanagement.service;

import com.productmanagement.dto.ProductDTO;
import com.productmanagement.dto.ProductResponse;
import com.productmanagement.model.Product;

import java.util.List;

public interface ProductService {

    Boolean saveProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Integer id);

    Boolean deleteProduct(Integer id);

    ProductResponse getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir);
}
