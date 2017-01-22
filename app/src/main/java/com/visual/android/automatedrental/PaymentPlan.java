package com.visual.android.automatedrental;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class PaymentPlan implements Serializable{

    String name;
    List<PaymentItem> paymentItems;
    List<Account> participants;

    public PaymentPlan(){

    }

    public PaymentPlan(List<PaymentItem> paymentItems, List<Account> participants){
        this.participants = participants;
        this.paymentItems = paymentItems;
    }

    public List<Account> getParticipants() {
        return participants;
    }

    public List<PaymentItem> getPaymentItems() {
        return paymentItems;
    }

    public void setParticipants(List<Account> participants) {
        this.participants = participants;
    }

    public void setPaymentItems(List<PaymentItem> paymentItems) {
        this.paymentItems = paymentItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
