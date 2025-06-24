package com.booklnad.bookland.DB.entity;

import com.booklnad.bookland.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Purchase> purchases;

    public User(String login, String password, String name, Role role, String icon) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
        this.icon = icon;
    }

    public User(String login, String password, String name, Role role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
