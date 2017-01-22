package com.visual.android.automatedrental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 1/22/2017.
 */
public class PlanDetailAdapter extends ArrayAdapter<Account> {

    PaymentPlan paymentPlan;
    List<Account> items;

    public PlanDetailAdapter(Context context, PaymentPlan paymentPlan) {
        super(context, 0, paymentPlan.getParticipants());
        this.items = paymentPlan.getParticipants();
        this.paymentPlan = paymentPlan;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_plan_detail, parent, false);
        }

        TextView mName = (TextView)convertView.findViewById(R.id.name);
        TextView mUsername = (TextView)convertView.findViewById(R.id.username);
        TextView mTotalMoney = (TextView)convertView.findViewById(R.id.money);

        mName.setText(items.get(position).getFirstName() + items.get(position).getLastName());
        mUsername.setText(items.get(position).getUsername());

        return convertView;
    }
}
