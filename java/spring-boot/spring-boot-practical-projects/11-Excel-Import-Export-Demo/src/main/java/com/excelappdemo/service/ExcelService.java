package com.excelappdemo.service;

import com.excelappdemo.model.ProductDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    public List<ProductDetails> importExcel(MultipartFile file) throws IOException;

    public void generateExcel(List<ProductDetails> products, HttpServletResponse response);

}
