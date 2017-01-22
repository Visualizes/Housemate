package com.visual.android.automatedrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by RamiK on 1/21/2017.
 */
public class FinishPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_plan);

        Bundle bundle = getIntent().getExtras();
        final PaymentPlan paymentPlan = (PaymentPlan)bundle.get("PaymentPlan");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Finish");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = (ListView)findViewById(R.id.listview);
        FinishAdapter finishAdapter = new FinishAdapter(this, paymentPlan.getParticipants());
        listView.setAdapter(finishAdapter);

        final EditText mName = (EditText)findViewById(R.id.name);
        Button mFinish = (Button)findViewById(R.id.finish);
        Button mCancel = (Button)findViewById(R.id.cancel);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString();
                if (!name.equals("")) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    paymentPlan.setName(name);
                    mDatabase.child("users").child(user.getUid()).child("paymentPlans").child(name).setValue(paymentPlan);
                    Intent i = new Intent(FinishPlanActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
