package com.excelappdemo.controller;

import com.excelappdemo.model.ProductDetails;
import com.excelappdemo.service.ExcelService;
import com.excelappdemo.service.ProductDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private ProductDetailsService productDetailsService;

    @PostMapping("/import-excel")
    public ResponseEntity<?> importExcel(@RequestParam MultipartFile file) {
        try {
            List<ProductDetails> importExcel = excelService.importExcel(file);
            Boolean saveProduct = productDetailsService.saveProduct(importExcel);
            if (saveProduct) {
                return new ResponseEntity<>("upload success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("upload failed. please try again...", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/generate-excel")
    public void generateExcel(HttpServletResponse response) {
        try {
            List<ProductDetails> products = productDetailsService.getAllProducts();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=product_details.xlsx");
            excelService.generateExcel(products, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
