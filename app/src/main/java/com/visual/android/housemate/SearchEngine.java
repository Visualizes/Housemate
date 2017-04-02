package com.visual.android.housemate;

import android.content.Context;
import android.text.Editable;
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
    private CharSequence updatedText;

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
        List<Integer> availableCursorSpots = mSearch.getAvailableCursorSpots();

        System.out.println(start);

        for (int i = 0; i < availableCursorSpots.size() - 1; i++){
            if (start == availableCursorSpots.get(i)){
                String text = s.toString();
                s = text.substring(0, start) + text.substring(start + 1) + text.substring(start, start + 1);
                System.out.println("text: " + text);
                System.out.println("1: " + text.substring(0, start));
                System.out.println("2: " + text.substring(start + 1));
                System.out.println("3: " + text.substring(start, start + 1));
                System.out.println("updated text: " + s);
                updatedText = s;
                moveLetter = true;
                mSearch.setSelection(s.length(), s.length());
                break;
            }
        }

        if (availableCursorSpots.size() > 0) {
            s = s.toString().substring(availableCursorSpots.get(availableCursorSpots.size() - 1));
        }
        /*
        SpannableStringBuilder spannableString = (SpannableStringBuilder) s;
        String x = Html.toHtml(spannableString);
        System.out.println(x);
        String index1 = "<span style=\"color:#FF0000;\">";
        String index2 = "</span>";
        if (x.contains(index1)) {
            String result1 = x.substring(0, x.indexOf(index1));
            String result2 = x.substring(x.indexOf(index2)+index2.length());
            String result = result1 + result2;
            System.out.println(result);
            String ad = "<p dir=\"ltr\"></p>";
            /*if (!result.trim().equals(ad)){
                System.out.println("xxx");
                s = result;
                System.out.println(s);
            } else {
                s = "";
            }
        }
*/
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

        SearchAdapter searchAdapter = new SearchAdapter(context, updatedUsers, paymentPlans);
        searchAdapter.updateIsSelected(false);
        listView.setAdapter(searchAdapter);

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (moveLetter) {
            mSearch.removeTextChangedListener(this);
            System.out.println(updatedText);
            s.replace(0, s.length(), updatedText);
            mSearch.addTextChangedListener(this);
            moveLetter = false;
        }
    }

    public List<Account> getUpdatedSearchList(){
        return updatedUsers;
    }

}
