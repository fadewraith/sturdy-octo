package com.productmanagement.dto;

//import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDTO {

    private Integer id;

//    @NotBlank // after writing this, need to use @Valid k/w at the endpoint, look save-product api
    private String name;

//    @NotEmpty
//    @Size(min = 3, max = 50, message = "description min and max size 3 - 50")
    private String description;

//    @NotNull(message = "Price must be provided")
//    @Pattern(regexp = "^[0-9]*$", message = "invalid price")
    @NotNull
    @Min(value = 0, message = "The price must be a positive number")
    private Double price;

    private Integer quantity;

//    @Email(message = "invalid email")
//    private String email;

}
