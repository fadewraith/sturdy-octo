package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

//    mappedBy is always on the oppo side of the relationship owner
//    while join column is always on the owner of the relationship
//    to identify who is the owner of the relationship, where we have the foreign key
//    in case of one2many - its always on the side with many

//    demo for unidirectional
//    @OneToMany // is always on the opp side of the ownership
//    @JoinColumn(name = "post_id") // need to use, in case the ownership is @ oppo side
//    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST) // in case of bidirectional, always use mappedBy
//    all the values in the comments will be applied PERSIST
//    cascading means applying all the operations on the child
    private List<Comment> comments; // Collection/List/Set

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}