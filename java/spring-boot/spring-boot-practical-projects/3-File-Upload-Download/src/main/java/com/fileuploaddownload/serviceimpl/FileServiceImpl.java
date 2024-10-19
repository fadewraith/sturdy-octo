package com.fileuploaddownload.serviceimpl;

import com.fileuploaddownload.model.ProductWithImageData;
import com.fileuploaddownload.repository.ProductWithImageDataRepository;
import com.fileuploaddownload.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private ProductWithImageDataRepository productWithImageDataRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public Boolean uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); // get the file name
        File saveFile = new File(uploadPath);

        if(!saveFile.exists()) {
            saveFile.mkdir();
        }
        String storePath = uploadPath.concat(fileName);
        long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
        if(upload != 0) {
            return true;
        }
        return false;
    }

    @Override
    public byte[] downloadFile(String file) throws Exception {
        String fullPath = uploadPath.concat(file); // file/file_name.extension
        try {
            FileInputStream fileInputStream = new FileInputStream(fullPath);
            return StreamUtils.copyToByteArray(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean saveProduct(ProductWithImageData product) {
        ProductWithImageData save = productWithImageDataRepository.save(product);
        if(!ObjectUtils.isEmpty(save)) {
            return true;
        }

        return false;
    }

    @Override
    public String uploadFileWithData(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); // get the file name
        File saveFile = new File(uploadPath);

//        use to make the uploaded img name unique
        String str = UUID.randomUUID().toString();
//        get the file name without extension
        String removedExtension = FilenameUtils.removeExtension(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        fileName = removedExtension + "_" + str + "." + extension;


        if(!saveFile.exists()) {
            saveFile.mkdir();
        }
        String storePath = uploadPath.concat(fileName);
        long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
        if(upload != 0) {
            return fileName;
        }
        return null;
    }
}
