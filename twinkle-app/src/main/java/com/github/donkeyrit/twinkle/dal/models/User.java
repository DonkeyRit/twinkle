package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    @Column(name = "login")
    private String login;

    public String getLogin() 
    {
        return login;
    }

    public void setLogin(String login) 
    {
        this.login = login;
    }

    @Column(name = "password")
    private String password;

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    @Column(name = "role")
    private boolean role;

    public boolean isRole() 
    {
        return role;
    }

    public void setRole(boolean role) 
    {
        this.role = role;
    }
}
