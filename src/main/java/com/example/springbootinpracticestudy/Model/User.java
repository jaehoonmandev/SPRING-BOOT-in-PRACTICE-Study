package com.example.springbootinpracticestudy.Model;

import com.example.springbootinpracticestudy.Validation.Password;

public class User {
    private String userName;

    @Password
    private String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
