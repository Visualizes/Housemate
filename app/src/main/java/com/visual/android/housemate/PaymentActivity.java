package com.visual.android.housemate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.android.*;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;


/**
 * Created by RamiK on 1/21/2017.
 */
public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final EditText mCreditCardNumber = (EditText)findViewById(R.id.creditCardNumber);
        final EditText mExpMonth = (EditText)findViewById(R.id.expMonth);
        final EditText mExpYear = (EditText)findViewById(R.id.expYear);
        final EditText mCVC = (EditText)findViewById(R.id.cvc);

        Button mAddCard = (Button)findViewById(R.id.addCard);
        mAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String creditCardNumber = mCreditCardNumber.getText().toString();
                Integer expMonth = Integer.valueOf(mExpMonth.getText().toString());
                Integer expYear = Integer.valueOf(mExpYear.getText().toString());
                String cvc = mCVC.getText().toString();

                if (!creditCardNumber.equals("") &&
                        expMonth != null &&
                        expYear != null &&
                        !cvc.equals("")){
                    final Card card = new Card(creditCardNumber, expMonth, expYear, cvc);
                    if (card.validateCard()) {
                        new Stripe().createToken(
                                card,
                                "pk_test_NSxTAdkVShntkW1ROzg2BJJg",
                                new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        mDatabase.child("users").child(user.getUid()).child("tokens")
                                                .child(card.getLast4())
                                                .setValue(token);
                                    }
                                    public void onError(Exception error) {
                                        // Show localized error message
                                        Toast.makeText(PaymentActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    }
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
