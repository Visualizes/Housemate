package com.visual.android.housemate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class PaymentAdapter extends ArrayAdapter<PaymentItem> {

    List<PaymentItem> items;

    public PaymentAdapter(Context context, List<PaymentItem> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_payment, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);
        TextView price = (TextView)convertView.findViewById(R.id.money);

        name.setText(items.get(position).getName());
        description.setText(items.get(position).getDescription());
        price.setText("$" + items.get(position).getPrice());

        return convertView;
    }

}
