package com.visual.android.housemate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class SearchAdapter extends ArrayAdapter<Account> {

    private List<Account> items;
    private static boolean isSelected = false;
    private TextView groupResult;
    private List<PaymentPlan> paymentPlans;
    private static List<Account> selectedAccounts;
    private static Singleton singleton;


    public SearchAdapter(Context context, List<Account> items, List<PaymentPlan> paymentPlans) {
        super(context, 0, items);
        this.items = items;
        this.paymentPlans = paymentPlans;
    }

    public SearchAdapter(Context context, List<Account> items, List<PaymentPlan> paymentPlans, SearchEditText mSearch) {
        super(context, 0, items);
        this.items = items;
        this.paymentPlans = paymentPlans;
        selectedAccounts = new ArrayList<>();
        singleton = new Singleton();
        singleton.setSearch(mSearch);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_searchresult, parent, false);
        }

        LinearLayout searchResult = (LinearLayout)convertView.findViewById(R.id.searchResult);
        final TextView name = (TextView)convertView.findViewById(R.id.name);
        final TextView username = (TextView) convertView.findViewById(R.id.username);
        name.setText(items.get(position).getFirstName() + " " + items.get(position).getLastName());
        username.setText("@" + items.get(position).getUsername());
        convertView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        if (selectedAccounts.contains(items.get(position))){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }

        searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedAccounts.contains(items.get(position))){
                    selectedAccounts.add(items.get(position));
                    System.out.println("size: " + selectedAccounts.size());
                    String searchText = singleton.mSearch.getText().toString();
                    String addition = "<font color='purple'>" +items.get(position).getFirstName() + " " + items.get(position).getLastName() + ", " + "</font>";
                    //singleton.mSearch.setText(Html.fromHtml(searchText + addition));
                    singleton.mSearch.updateSelectedAccounts(selectedAccounts);
                    singleton.mSearch.updateObjects();
                } else {
                    System.out.println("REMOVED");
                    selectedAccounts.remove(items.get(position));
                    singleton.mSearch.updateSelectedAccounts(selectedAccounts);
                    singleton.mSearch.updateObjects();
                }

                /*
                Intent i = new Intent(getContext(), CreatePaymentPlanActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("PAYMENTPLANS",(Serializable)paymentPlans);
                i.putExtra("BUNDLE",args);
                i.putExtra("ACCOUNT", items.get(position));
                getContext().startActivity(i);*/
            }
        });
        return convertView;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    public void updateIsSelected(boolean update){
        isSelected = update;
    }

}
