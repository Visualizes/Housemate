package com.visual.android.housemate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/22/2017.
 */
public class PlanDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("NAME");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final List<PaymentPlan> paymentPlans = new ArrayList<>();

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null) {
            final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            Query query = mDatabaseReference.child("users").child(fUser.getUid()).child("paymentPlans").child(name);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        paymentPlans.add(snapshot.getValue(PaymentPlan.class));
                    }

                    ListView listView = (ListView)findViewById(R.id.listview);
                    PlanDetailAdapter planDetailAdapter = new PlanDetailAdapter(PlanDetailActivity.this, paymentPlans);
                    listView.setAdapter(planDetailAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //TODO: Auto-generated method stub
                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
