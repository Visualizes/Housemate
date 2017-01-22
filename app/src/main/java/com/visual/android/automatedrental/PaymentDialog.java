package com.visual.android.automatedrental;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class PaymentDialog {

    public void showDialog(final Activity activity, final ListView listView, final List<PaymentItem> paymentItems){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment);

        final EditText mName = (EditText)dialog.findViewById(R.id.name);
        final EditText mDescription = (EditText)dialog.findViewById(R.id.description);
        final EditText mPrice = (EditText)dialog.findViewById(R.id.price);
        Button mDone = (Button)dialog.findViewById(R.id.done);
        Button mCancel = (Button)dialog.findViewById(R.id.cancel);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String description = mDescription.getText().toString();
                String price = mPrice.getText().toString();
                if (!name.equals("") && !price.equals("")){
                    paymentItems.add(new PaymentItem(name, description, price));
                    PaymentAdapter paymentAdapter = new PaymentAdapter(activity, paymentItems);
                    listView.setAdapter(paymentAdapter);
                    dialog.cancel();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();

    }

}
