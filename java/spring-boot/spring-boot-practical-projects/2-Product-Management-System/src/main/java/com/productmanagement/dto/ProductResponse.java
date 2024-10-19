package com.productmanagement.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

//    for pagination
    private List<ProductDTO> products;
    private long totalElements;
    private int totalPages;
    private Boolean isFirst;
    private Boolean isLast;
    private int pageNo;
    private int pageSize;
}
