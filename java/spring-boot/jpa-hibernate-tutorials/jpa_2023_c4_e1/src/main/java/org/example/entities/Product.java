package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.example.entities.keys.ProductKey;

@Entity
@IdClass(ProductKey.class)
public class Product {
//    custom Id demo
//    using ProductKey, so name should be same as in the ProductKey class and we can use any num of cols for key and use @Id notation to define the part of the key

    @Id
    private String code;
    @Id
    private long number;
    private String color;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
