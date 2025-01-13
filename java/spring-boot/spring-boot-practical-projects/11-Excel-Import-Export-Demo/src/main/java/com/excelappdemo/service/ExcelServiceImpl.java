package com.excelappdemo.service;

import com.excelappdemo.model.ProductDetails;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelServiceImpl implements ExcelService {

    private String[] header = { "Category", "Product Name", "Quantity", "Price", "Total Price" };

    @Override
    public List<ProductDetails> importExcel(MultipartFile file) throws IOException {
        List<ProductDetails> productDetailsList = new ArrayList<>();

        if(file.isEmpty()) {
            throw new IllegalArgumentException("Please import file");
        }

        if(!file.isEmpty() && !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xlsx")) {
            throw new IllegalArgumentException("Invalid excel file");
        }

        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        like sheet 1 (index = 0), sheet 2 = (i = 1), etc...
        XSSFSheet sheet = workbook.getSheetAt(0);

        for(int i = 1; i <= sheet.getPhysicalNumberOfRows(); ++i) {
            XSSFRow row = sheet.getRow(i);
//            if row is empty, ignore it
            if(row == null) continue;
//            cell also starts with 0 index
            String category = row.getCell(0).getStringCellValue();
            String productName = row.getCell(1).getStringCellValue();
            Integer quantity = (int) row.getCell(2).getNumericCellValue();
            double price = row.getCell(3).getNumericCellValue();
            double totalPrice = row.getCell(4).getNumericCellValue();

            ProductDetails product = new ProductDetails();
            product.setCategory(category);
            product.setProductName(productName);
            product.setQuantity(quantity);
            product.setPrice(price);
            product.setTotalPrice(totalPrice);

            productDetailsList.add(product);

        }
        workbook.close();
        return productDetailsList;
    }

    @Override
    public void generateExcel(List<ProductDetails> products, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            XSSFSheet sheet = workbook.createSheet("Product Details");

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(12);
            headerStyle.setFont(font);

            XSSFCellStyle dataStyle = workbook.createCellStyle();
            XSSFFont cellFont = workbook.createFont();
            cellFont.setBold(false);
            cellFont.setFontHeight(10);
            dataStyle.setFont(cellFont);

            int rowCount = 0;
//        header creation
            XSSFRow headerRow = sheet.createRow(0);
            createCell(sheet, headerRow, header, headerStyle);

//        data creation
            for(ProductDetails product : products) {
                rowCount++;
                XSSFRow row = sheet.createRow(rowCount);
                Object[] cellValue = { product.getCategory(), product.getProductName(), product.getQuantity(), product.getPrice(), product.getTotalPrice() };
                createCell(sheet, row, cellValue, dataStyle);
            }
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();  // Close the outputStream
                    }
                    if (workbook != null) {
                        workbook.close();  // Close the workbook
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    private void createCell(XSSFSheet sheet, XSSFRow row, Object[] data, XSSFCellStyle style) {
        for(int i = 0; i < data.length; ++i) {
            sheet.autoSizeColumn(i);
//            create cell
            XSSFCell cell = row.createCell(i);
            Object dataValue = data[i];
            if(dataValue instanceof String) {
                cell.setCellValue((String) dataValue);
            } else if(dataValue instanceof Integer) {
                cell.setCellValue((Integer) dataValue);
            } else if(dataValue instanceof Long) {
                cell.setCellValue((Long) dataValue);
            } else if(dataValue instanceof Boolean) {
                cell.setCellValue((Boolean) dataValue);
            } else {
                cell.setCellValue(cell != null ? dataValue.toString() : "");
            }
            cell.setCellStyle(style);
        }
    }
}
