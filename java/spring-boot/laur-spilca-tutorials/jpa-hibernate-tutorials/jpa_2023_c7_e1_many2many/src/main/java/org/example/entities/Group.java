package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`groups`") // used backticks, coz groups is areserved k/w in mysql
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE } )
    @JoinTable( // this is @ownership side & Group is the owner, whereas mappedBy is @ opposite
            name = "users_groups",
//            joinColumns refers to the current entity
            joinColumns = @JoinColumn(name = "group_id"),
//            refers to the User
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
