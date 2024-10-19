package org.example.entities;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;

@Entity
@SecondaryTable(
        name = "user_details",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
) // used in legacy codes, not a good practice to do this, SecondaryTable
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(table = "user_details") // part of demo @SecondaryTable
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
