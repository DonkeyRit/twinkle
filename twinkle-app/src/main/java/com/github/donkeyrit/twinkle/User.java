package com.github.donkeyrit.twinkle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dima
 */
public class User {
    private String login; 
    private String password; 
    private boolean role; 

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public User(String login, String password, boolean role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    
    
}
