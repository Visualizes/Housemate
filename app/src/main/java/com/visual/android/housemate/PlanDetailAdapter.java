package com.visual.android.housemate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 1/22/2017.
 */
public class PlanDetailAdapter extends ArrayAdapter<PaymentPlan> {

    List<PaymentPlan> paymentPlans;

    public PlanDetailAdapter(Context context, List<PaymentPlan> paymentPlans) {
        super(context, 0, paymentPlans);
        this.paymentPlans = paymentPlans;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_plan_detail, parent, false);
        }

        TextView mName = (TextView)convertView.findViewById(R.id.name);
        TextView mUsername = (TextView)convertView.findViewById(R.id.username);
        TextView mTotalMoney = (TextView)convertView.findViewById(R.id.money);

        mName.setText(paymentPlans.get(position).getUser().getFirstName() + " " + paymentPlans.get(position).getUser().getLastName());
        mUsername.setText(paymentPlans.get(position).getUser().getUsername());

        int price = 0;
        for (int i = 0; i < paymentPlans.get(position).getPaymentItems().size(); i++){
            price += Integer.valueOf(paymentPlans.get(position).getPaymentItems().get(i).getPrice());
        }
        mTotalMoney.setText("$" + price);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PaymentItemsPreviewActivity.class);
                i.putExtra("PaymentPlan", paymentPlans.get(position));
                getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
