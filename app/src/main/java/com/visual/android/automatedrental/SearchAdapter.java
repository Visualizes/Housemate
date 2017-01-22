package com.visual.android.automatedrental;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private PaymentPlan paymentPlan;

    public SearchAdapter(Context context, List<Account> items, PaymentPlan paymentPlan) {
        super(context, 0, items);
        this.items = items;
        this.paymentPlan = paymentPlan;
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

        searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPlan.getParticipants().add(items.get(position));
                items.remove(position);
                notifyDataSetChanged();
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
