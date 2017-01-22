package com.visual.android.automatedrental;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by RamiK on 1/22/2017.
 */
public class PlanDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        Bundle extras = getIntent().getExtras();
        PaymentPlan paymentPlan = (PaymentPlan)extras.get("PaymentPlan");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(paymentPlan.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = (ListView)findViewById(R.id.listview);
        PlanDetailAdapter planDetailAdapter = new PlanDetailAdapter(this, paymentPlan);
        listView.setAdapter(planDetailAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
