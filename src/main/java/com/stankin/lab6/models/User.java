package com.stankin.lab6.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName () {
        return this.userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public User () {}

    public User (String userName) {
        this.userName = userName;
    }
}
