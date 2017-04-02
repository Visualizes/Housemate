package com.visual.android.housemate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class PaymentPlan implements Serializable{

    String name;
    List<PaymentItem> paymentItems;
    Account user;

    public PaymentPlan(){

    }

    public PaymentPlan(Account user, List<PaymentItem> paymentItems){
        this.user = user;
        this.paymentItems = paymentItems;
    }

    public Account getUser() {
        return user;
    }

    public List<PaymentItem> getPaymentItems() {
        return paymentItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
