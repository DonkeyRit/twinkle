package com.github.donkeyrit.twinkle.dal.models;

import java.io.Serializable;

import com.github.donkeyrit.twinkle.dal.interfaces.BaseDbModel;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseDbModel implements Serializable
{
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private boolean role;

    public User()
    {

    }

    public User(String login, String password, boolean role)
    {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }


    public String getLogin() 
    {
        return login;
    }

    public void setLogin(String login) 
    {
        this.login = login;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public boolean isRole() 
    {
        return role;
    }

    public void setRole(boolean role) 
    {
        this.role = role;
    }
}