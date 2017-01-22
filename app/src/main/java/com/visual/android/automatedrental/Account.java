package com.visual.android.automatedrental;

import java.io.Serializable;

/**
 * Created by RamiK on 1/21/2017.
 */
public class Account implements Serializable{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;

    public Account(){

    }

    public Account(String email, String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Account(String email, String firstName, String lastName, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public Account(String email, String firstName, String lastName, String phone, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

}
