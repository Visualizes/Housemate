package com.visual.android.automatedrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by RamiK on 1/21/2017.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        final EditText mFirstName = (EditText)findViewById(R.id.firstNamae);
        final EditText mLastName = (EditText)findViewById(R.id.lastName);
        final EditText mEmail = (EditText)findViewById(R.id.email);
        final EditText mPassword = (EditText)findViewById(R.id.password);
        final EditText mUsername = (EditText)findViewById(R.id.username);
        Button mNext = (Button) findViewById(R.id.nextButton);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mFirstName.getText().toString().equals("") &&
                        !mLastName.getText().toString().equals("") &&
                        !mEmail.getText().toString().equals("") &&
                        !mPassword.getText().toString().equals("") &&
                        !mUsername.getText().toString().equals("")) {

                    final String email = mEmail.getText().toString();
                    final String password = mPassword.getText().toString();
                    final String username = mUsername.getText().toString();
                    final String firstName = mFirstName.getText().toString();
                    final String lastName = mLastName.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(CreateAccountActivity.this, "didnt work fam",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        createNewUser(firstName, lastName, username, task.getResult().getUser());
                                                        Intent i = new Intent(CreateAccountActivity.this, HomeActivity.class);
                                                        startActivity(i);
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
    }

    private void createNewUser(String firstName, String lastName, String username, FirebaseUser userFromRegistration) {
        String email = userFromRegistration.getEmail();
        String userId = userFromRegistration.getUid();

        Account user = new Account(firstName, lastName, username, email);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).setValue(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
