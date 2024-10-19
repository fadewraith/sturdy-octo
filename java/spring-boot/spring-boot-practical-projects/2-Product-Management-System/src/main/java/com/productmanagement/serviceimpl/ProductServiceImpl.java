package com.productmanagement.serviceimpl;

import com.productmanagement.dto.ProductDTO;
import com.productmanagement.dto.ProductResponse;
import com.productmanagement.model.Product;
import com.productmanagement.repository.ProductRepository;
import com.productmanagement.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveProduct(ProductDTO productDTO) {
//        Product product = new Product();
//        product.setId(productDTO.getId());
//        product.setName(productDTO.getName());
//        product.setPrice(productDTO.getPrice());
//        product.setDescription(productDTO.getDescription());
//        product.setQuantity(productDTO.getQuantity());

//        way 2 - by using maven dependency model mapper to reduce the code, variable names should be same
        Product product = mapper.map(productDTO, Product.class); // (source, destination)

        Product save = productRepository.save(product);
//        if(ObjectUtils.isEmpty(save)) {
//            return false;
//        }
//        return true;
//        return ObjectUtils.isEmpty(save) ? false : true;
        return !ObjectUtils.isEmpty(save);

    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = products.stream().map(product -> mapper.map(product, ProductDTO.class)).collect(Collectors.toList());
        return productDTOList;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Optional<Product> findProduct = productRepository.findById(id);
        if(findProduct.isPresent()) {
            Product product = findProduct.get();
            ProductDTO productDTO = mapper.map(product, ProductDTO.class);
            return productDTO;
        }
        return null;
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Optional<Product> findProduct = productRepository.findById(id);
        if(findProduct.isPresent()) {
            Product product = findProduct.get();
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public ProductResponse getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {

//        Sort sortAsc = Sort.by(sortBy).ascending();
//        Sort sortDesc = Sort.by(sortBy).descending();

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);

        List<Product> products = page.getContent();
        List<ProductDTO> productDTOS = products.stream().map(p -> mapper.map(p, ProductDTO.class)).toList();


//        List<ProductDTO> productDTOS = new ArrayList<>(products.stream().map(p -> mapper.map(p, ProductDTO.class)).toList());
//        if("name".equalsIgnoreCase(sortBy) || "description".equalsIgnoreCase(sortBy)) {
//            Comparator<ProductDTO> comparator = customComparator(sortBy, sortDir);
////            Collections.sort(productDTOS, comparator);
//            productDTOS.sort(comparator);
//            System.out.println("productDTOS -> " + productDTOS);
//        }

//        long pageNumber = page.getNumber();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        boolean isFirst = page.isFirst();
        boolean isLast = page.isLast();

        // method 1 -
//        ProductResponse productResponse = new ProductResponse();
//        productResponse.setProductDTOList(productDTOList);
//        productResponse.setTotalElements(totalElements);

//        method 2 -
        ProductResponse productResponse = ProductResponse.builder()
                .products(productDTOS)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .isFirst(isFirst)
                .isLast(isLast)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();

        return productResponse;
    }

    private static Comparator<ProductDTO> customComparator(String sortBy, String sortDir) {
        if("name".equalsIgnoreCase(sortBy)) {
            return (p1, p2) -> {
                int num1 = extractNumber(p1.getName());
                int num2 = extractNumber(p2.getName());

                // If no numeric part is found, fall back to lexicographical sorting
                if (num1 == -1 || num2 == -1) {
                    int result = p1.getName().compareToIgnoreCase(p2.getName());
                    return "asc".equalsIgnoreCase(sortDir) ? result : -result;
                }

                // Compare by numeric part
                int result = Integer.compare(num1, num2);
                return "asc".equalsIgnoreCase(sortDir) ? result : -result;
            };
        } else if("description".equalsIgnoreCase(sortBy)) {
            return (p1, p2) -> {
                int num1 = extractNumber(p1.getName());
                int num2 = extractNumber(p2.getName());

                // If no numeric part is found, fall back to lexicographical sorting
                if (num1 == -1 || num2 == -1) {
                    int result = p1.getName().compareToIgnoreCase(p2.getName());
                    return "asc".equalsIgnoreCase(sortDir) ? result : -result;
                }

                // Compare by numeric part
                int result = Integer.compare(num1, num2);
                return "asc".equalsIgnoreCase(sortDir) ? result : -result;
            };
        } else {
            throw new IllegalArgumentException("Invalid sortBy value: " + sortBy);
        }
    }

    // Helper method to extract the first numeric part from the string
    private static int extractNumber(String name) {
        // Use regex to extract the first sequence of digits
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(name);

        // If a number is found, return it
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        // If no number is found, return -1 (to fall back to lexicographical sorting)
        return -1;
    }



}
