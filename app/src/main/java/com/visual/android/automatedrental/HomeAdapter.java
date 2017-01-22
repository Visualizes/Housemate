package com.visual.android.automatedrental;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class HomeAdapter extends ArrayAdapter<PaymentPlan> {

    private List<PaymentPlan> items;

    public HomeAdapter(Context context, List<PaymentPlan> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_home, parent, false);
        }

        LinearLayout mObject = (LinearLayout)convertView.findViewById(R.id.object);
        TextView mTitle = (TextView)convertView.findViewById(R.id.title);
        TextView mParticipants = (TextView) convertView.findViewById(R.id.participants);

        mTitle.setText(items.get(position).getName());
        mParticipants.setText(items.get(position).getParticipants().size() + " participants");

        mObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PlanDetailActivity.class);
                i.putExtra("PaymentPlan", items.get(position));
                getContext().startActivity(i);
            }
        });
        return convertView;
    }
}
