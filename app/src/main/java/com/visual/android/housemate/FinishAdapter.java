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
public class FinishAdapter extends ArrayAdapter<Account> {

    private List<Account> items;

    public FinishAdapter(Context context, List<Account> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_searchresult, parent, false);
        }

        final TextView name = (TextView)convertView.findViewById(R.id.name);
        final TextView username = (TextView) convertView.findViewById(R.id.username);
        name.setText(items.get(position).getFirstName() + " " + items.get(position).getLastName());
        username.setText("@" + items.get(position).getUsername());

        return convertView;
    }
}
