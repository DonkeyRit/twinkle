package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public record User(
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id,
    
    @Column(name = "login")
    String login,

    @Column(name = "password")
    String password,

    @Column(name = "role")
    boolean role
) 
{
    
}
