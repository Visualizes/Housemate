package com.visual.android.automatedrental;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class SearchEngine implements TextWatcher {

    private List<Account> users;
    private Context context;
    private ListView listView;
    public List<Account> updatedUsers;
    private PaymentPlan paymentPlan;

    public SearchEngine(List<Account> users, ListView listView, Context context, PaymentPlan paymentPlan){
        this.users = users;
        this.listView = listView;
        this.context = context;
        updatedUsers = users;
        this.paymentPlan = paymentPlan;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        updatedUsers = new ArrayList<>();

        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().length() > s.length() && s.length() > 0){
                if (users.get(i).getUsername().toLowerCase().substring(0, s.length()).equals(String.valueOf(s).toLowerCase())) {
                    updatedUsers.add(users.get(i));
                }
            }
            //if there is nothing in the edittext, display the entire arraylist
            if (s.length() == 0){
                updatedUsers = users;
            }
        }

        SearchAdapter searchAdapter = new SearchAdapter(context, updatedUsers, paymentPlan);
        searchAdapter.updateIsSelected(false);
        listView.setAdapter(searchAdapter);

    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public List<Account> getUpdatedSearchList(){
        return updatedUsers;
    }

}
