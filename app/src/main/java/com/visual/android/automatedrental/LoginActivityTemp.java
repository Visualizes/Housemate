package com.visual.android.automatedrental;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
public class LoginActivityTemp extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp4);

        mAuth = FirebaseAuth.getInstance();

        final EditText mUsername = (EditText)findViewById(R.id.username);
        final EditText mPassword = (EditText)findViewById(R.id.password);
        Button mSignIn = (Button)findViewById(R.id.signIn);
        TextView mSignUp = (TextView)findViewById(R.id.signUp);
        TextView mHelp = (TextView)findViewById(R.id.help);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(LoginActivityTemp.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivityTemp.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mUsername.getText().toString().equals("") &&
                        !mPassword.getText().toString().equals("")) {
                    String username = mUsername.getText().toString();
                    String password = mPassword.getText().toString();

                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivityTemp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w("TAG", "signInWithEmail:failed", task.getException());
                                    }
                                    else {
                                        Intent i = new Intent(LoginActivityTemp.this, HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
