package com.visual.android.housemate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<PaymentPlan> paymentPlans = (ArrayList<PaymentPlan>) args.getSerializable("PAYMENTPLANS");

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

                SearchEditText mSearch = (SearchEditText) findViewById(R.id.search);
                ListView mListSearch = (ListView)findViewById(R.id.listview);

                SearchEngine searchEngine = new SearchEngine(accounts, mListSearch, CreateGroupActivity.this, paymentPlans, mSearch);
                SearchAdapter searchAdapter = new SearchAdapter(CreateGroupActivity.this, accounts, paymentPlans, mSearch);

                mListSearch.setAdapter(searchAdapter);
                mSearch.addTextChangedListener(searchEngine);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: Auto-generated stub
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
