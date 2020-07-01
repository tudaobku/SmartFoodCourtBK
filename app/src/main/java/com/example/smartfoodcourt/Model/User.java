package com.example.smartfoodcourt.Model;

public class User {
    private String email;
    private String password;
    private String phone;

    public User(){}

    public User(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getPhone(){return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
