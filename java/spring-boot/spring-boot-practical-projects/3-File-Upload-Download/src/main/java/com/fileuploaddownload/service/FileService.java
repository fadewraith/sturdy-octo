package com.fileuploaddownload.service;

import com.fileuploaddownload.model.ProductWithImageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    public Boolean uploadFile(MultipartFile file) throws IOException;

    public byte[] downloadFile(String fileName) throws Exception;

    public Boolean saveProduct(ProductWithImageData product);

    public String uploadFileWithData(MultipartFile file) throws IOException;
}
