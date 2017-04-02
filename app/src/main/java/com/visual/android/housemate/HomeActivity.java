package com.visual.android.housemate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 1/21/2017.
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Account user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Housemate");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final ListView listView = (ListView)findViewById(R.id.listview);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView username = (TextView)headerView.findViewById(R.id.username);
        final TextView name = (TextView)headerView.findViewById(R.id.name);

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null){
            final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            Query query =  mDatabaseReference.child("users").child(fUser.getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    username.setText(dataSnapshot.getValue(Account.class).getUsername());
                    name.setText(dataSnapshot.getValue(Account.class).getFirstName() + " " + dataSnapshot.getValue(Account.class).getLastName());

                    Query query2 =  mDatabaseReference.child("users").child(fUser.getUid()).child("paymentPlans");
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<String> names = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                names.add(snapshot.getKey());
                            }
                            HomeAdapter homeAdapter = new HomeAdapter(HomeActivity.this, names);
                            listView.setAdapter(homeAdapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //TODO: Auto-generated method stub
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //TODO: Auto-generated method stub
                }
            });

        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add){
            List<PaymentPlan> paymentPlans = new ArrayList<>();
            Intent i = new Intent(HomeActivity.this, CreateGroupActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("PAYMENTPLANS",(Serializable)paymentPlans);
            i.putExtra("BUNDLE",args);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        if (item.getItemId() == R.id.nav_settings){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, LoginActivityTemp.class);
            startActivity(i);
            finish();
        }

        if (item.getItemId() == R.id.nav_payment){
            Intent i = new Intent(this, PaymentActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
