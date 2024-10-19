package com.fileuploaddownload.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_with_image_data")
public class ProductWithImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private String imageName;
}
