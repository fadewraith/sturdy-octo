package com.excelappdemo.service;

import com.excelappdemo.model.ProductDetails;
import com.excelappdemo.repository.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Override
    public Boolean saveProduct(List<ProductDetails> productDetails) {
        List<ProductDetails> saveAllDetails = productDetailsRepository.saveAll(productDetails);
        if(!CollectionUtils.isEmpty(saveAllDetails)) {
            return true;
        }
        return false;
    }

    @Override
    public List<ProductDetails> getAllProducts() {
        List<ProductDetails> detailsList = productDetailsRepository.findAll();
        if(!detailsList.isEmpty()) {
            return detailsList;
        }
        return Collections.emptyList();
    }
}
