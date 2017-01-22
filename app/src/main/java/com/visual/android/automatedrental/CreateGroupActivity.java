package com.visual.android.automatedrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class CreateGroupActivity extends AppCompatActivity {

    private List<Account> accounts;
    private PaymentPlan paymentPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<PaymentItem> paymentItems = (ArrayList<PaymentItem>) args.getSerializable("ARRAYLIST");

        paymentPlan = new PaymentPlan(paymentItems, new ArrayList<Account>());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Group");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        accounts = new ArrayList<>();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query query =  mDatabaseReference.child("users").orderByChild("username");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    accounts.add(snapshot.getValue(Account.class));
                }
                ListView mListSearch = (ListView)findViewById(R.id.listview);
                SearchAdapter searchAdapter = new SearchAdapter(CreateGroupActivity.this, accounts, paymentPlan);
                mListSearch.setAdapter(searchAdapter);

                EditText mSearch = (EditText)findViewById(R.id.search);
                SearchEngine searchEngine = new SearchEngine(accounts, mListSearch, CreateGroupActivity.this, paymentPlan);
                mSearch.addTextChangedListener(searchEngine);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: Auto-generated stub
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
            Intent i = new Intent(this, FinishPlanActivity.class);
            i.putExtra("PaymentPlan", paymentPlan);
            startActivity(i);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
