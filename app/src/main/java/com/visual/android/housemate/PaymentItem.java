package com.visual.android.housemate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class PaymentItem implements Serializable {

    private String name;
    private String description;
    private String price;
    private List<Account> users;

    public PaymentItem(){

    }

    public PaymentItem(List<Account> users){
        this.users = users;
    }

    public PaymentItem(String name, String description, String price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public PaymentItem(String name, String description, String price, List<Account> users){
        this.name = name;
        this.description = description;
        this.price = price;
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public List<Account> getUsers() {
        return users;
    }
}
