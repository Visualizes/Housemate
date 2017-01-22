package com.visual.android.automatedrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class CreatePaymentPlanActivity extends AppCompatActivity {

    private List<PaymentItem> paymentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment_plan);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Payment Plan");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ListView listView = (ListView)findViewById(R.id.listview);
        paymentItems = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentDialog paymentDialog = new PaymentDialog();
                paymentDialog.showDialog(CreatePaymentPlanActivity.this, listView, paymentItems);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        if (menuItem.getItemId() == R.id.checkmark){
            Intent i = new Intent(this, CreateGroupActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)paymentItems);
            i.putExtra("BUNDLE",args);
            startActivity(i);
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
