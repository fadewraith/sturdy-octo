package com.excelappdemo.service;

import com.excelappdemo.model.ProductDetails;

import java.util.List;

public interface ProductDetailsService {

    public Boolean saveProduct(List<ProductDetails> productDetails);

    List<ProductDetails> getAllProducts();
}
