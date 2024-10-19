package com.fileuploaddownload.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileuploaddownload.model.ProductWithImageData;
import com.fileuploaddownload.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j // log4j
@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
//        testing in api, choose form data, key = 'multipartFile' same as param name, upload image
        try {
            Boolean uploadedFile = fileService.uploadFile(file);
            if(uploadedFile) {
                return new ResponseEntity<>("file uploaded successfully", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("file not uploaded", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String file) {
//        url - /download?file=img.jpg
        try {
            byte[] downloadFile = fileService.downloadFile(file);
            String contentType = getContentType(file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
//            file is of string type, so it is telling the length of the character
//            headers.setContentLength(file.length());
//            "attachment", "inline"
            headers.setContentDispositionFormData("attachment", file);

            return ResponseEntity.ok().headers(headers).body(downloadFile);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getContentType(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);

        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
            case "txt":
                return "text/plan";
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpg";
            default:
                return "application/octet-stream";
        }
    }

    @PostMapping("/upload-data")
    public ResponseEntity<?> uploadFileWithData(@RequestParam String product, @RequestParam MultipartFile file) {
//        testing in api, choose form data, key = 'multipartFile' same as param name, upload image and product obj with product k/w as in the param
//        testing of image will be using form-data with key and value, in post
        log.info("Product: {}", product);
        log.info("File: {}", file);

        List<String> objectList = Arrays.asList("jpeg", "png", "jpg");
        if(file.isEmpty()) {
            return new ResponseEntity<>("upload the file", HttpStatus.BAD_REQUEST);
        } else {
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            boolean contains = objectList.contains(extension);
            if(!contains) {
                return new ResponseEntity<>("please upload jpg/png/jpg formats only", HttpStatus.BAD_REQUEST);
            }
        }

        try {
            String fileName = fileService.uploadFileWithData(file);
            if(StringUtils.hasText(fileName)) {
                ObjectMapper objectMapper = new ObjectMapper();
//                product will be in stream format
                ProductWithImageData productWithImageDataObj = objectMapper.readValue(product, ProductWithImageData.class);

                productWithImageDataObj.setImageName(fileName);

                Boolean saveProduct = fileService.saveProduct(productWithImageDataObj);

                if(saveProduct) {
                    return new ResponseEntity<>("product & image uploaded successfully", HttpStatus.CREATED);
                }
                return new ResponseEntity<>("file uploaded but data not saved", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
