package com.visual.android.housemate;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
    private List<PaymentPlan> paymentPlans;
    private SearchEditText mSearch;
    private boolean moveLetter = false;
    private String updatedText;

    public SearchEngine(List<Account> users, ListView listView, Context context, List<PaymentPlan> paymentPlans, SearchEditText mSearch){
        this.users = users;
        this.listView = listView;
        this.context = context;
        updatedUsers = users;
        this.paymentPlans = paymentPlans;
        this.mSearch = mSearch;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //TODO: Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updatedUsers = new ArrayList<>();

        for (int i = 0; i < mSearch.getAvailableCursorSpots().size(); i++){
            if (i > 0 && count == 0 && start + 1 == mSearch.getAvailableCursorSpots().get(i) && mSearch.getAvailableCursorSpots().size() > 1){
                if (mSearch.getSelectionStart() == mSearch.getSelectionEnd() && mSearch.getSelectionEnd() + 1 == mSearch.getAvailableCursorSpots().get(i)) {
                    mSearch.getAccounts().remove(i-1);
                    mSearch.updateObjectsAndUpdateText();
                    mSearch.setSelection(mSearch.getAvailableCursorSpots().get(i-1), mSearch.getAvailableCursorSpots().get(i-1));
                    break;
                }
            }
            if (count == 1 && start == mSearch.getAvailableCursorSpots().get(i) && i != mSearch.getAvailableCursorSpots().size() - 1){
                String text = s.toString();
                s = text.substring(0, start) + text.substring(start + 1) + text.substring(start, start + 1);
                updatedText = text.substring(start, start + 1);
                moveLetter = true;
                break;
            }
        }

        if (mSearch.getAvailableCursorSpots().size() > 0) {
            s = s.toString().substring(mSearch.getAvailableCursorSpots().get(mSearch.getAvailableCursorSpots().size() - 1));
        }

        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().length() >= s.length() && s.length() > 0){
                if (users.get(i).getUsername().toLowerCase().substring(0, s.length()).equals(String.valueOf(s).toLowerCase())) {
                    updatedUsers.add(users.get(i));
                }
            }
            //if there is nothing in the edittext, display the entire arraylist
            if (s.length() == 0){
                updatedUsers = users;
            }
        }

        SearchAdapter searchAdapter = new SearchAdapter(context, updatedUsers, paymentPlans);
        searchAdapter.updateIsSelected(false);
        listView.setAdapter(searchAdapter);

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (moveLetter) {
            moveLetter = false;
            mSearch.addToText(updatedText);
        }
    }

    public List<Account> getUpdatedSearchList(){
        return updatedUsers;
    }

}
