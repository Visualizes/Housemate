package com.visual.android.housemate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class FinishPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_plan);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final List<PaymentPlan> paymentPlans = (ArrayList<PaymentPlan>) args.getSerializable("PAYMENTPLANS");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Finish");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<Account> participants = new ArrayList<>();
        for (PaymentPlan plan : paymentPlans){
            participants.add(plan.getUser());
        }

        ListView listView = (ListView)findViewById(R.id.listview);
        FinishAdapter finishAdapter = new FinishAdapter(this, participants);
        listView.setAdapter(finishAdapter);

        final EditText mName = (EditText)findViewById(R.id.name);
        Button mFinish = (Button)findViewById(R.id.finish);
        Button mAdd = (Button)findViewById(R.id.addAnother);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString();
                if (!name.equals("")) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    mDatabase.child("users").child(user.getUid()).child("paymentPlans").child(name).setValue(paymentPlans);
                    Intent i = new Intent(FinishPlanActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinishPlanActivity.this, CreateGroupActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("PAYMENTPLANS",(Serializable)paymentPlans);
                i.putExtra("BUNDLE",args);
                startActivity(i);
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
