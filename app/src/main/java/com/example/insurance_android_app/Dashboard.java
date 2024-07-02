package com.example.insurance_android_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {
    private User loggedIn;
    private int id;
    private DatabaseHandler db;
    private TextView user, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        db = new DatabaseHandler(this);

        user = (TextView) findViewById(R.id.user);
        balance = (TextView) findViewById(R.id.balance);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        if (id == -1) {
            Toast.makeText(getBaseContext(), "Issue Happened in login.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Dashboard.this, MainActivity.class);
            startActivity(i);
        } else {
            loggedIn = db.getUserById(id);
        }

        setDetails();
    }

    @SuppressLint("SetTextI18n")
    private void setDetails() {
        user.setText(loggedIn.getUsername());
        balance.setText("Balance:" + loggedIn.getBalance());

    }

    @SuppressLint("SetTextI18n")
    private void updateBalance(int amount) {
        db.updateBalanceById(loggedIn.getId(), amount);
        loggedIn = db.getUserById(loggedIn.getId());
        balance.setText("Balance:" + loggedIn.getBalance());
    }

    private void buyInsurance(String name, int amount, int exp) {
        if (loggedIn.getBalance() > amount) {
            int buyId = db.getInsurancesRowCount() + 1;
            Insurance insurance = new Insurance(buyId, name, loggedIn.getId(), exp);
            db.addBuy(insurance);

            updateBalance(loggedIn.getBalance() - amount);
            Toast.makeText(getBaseContext(), "You Bought " + name + " Successfully with Buy ID: " + buyId + "!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Not Enough Money!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButton(View view) {
        if (view.getId() == R.id.logout) {
            Intent intent = new Intent(Dashboard.this, MainActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.add_balance) {
            updateBalance(loggedIn.getBalance() + 200);
        }
        if (view.getId() == R.id.buy_koala) {
            buyInsurance("koala", 100, 365);
        }
        if (view.getId() == R.id.buy_airplane) {
            buyInsurance("airplane", 30, 180);
        }
        if (view.getId() == R.id.buy_fire) {
            buyInsurance("fire", 30, 180);
        }
        if (view.getId() == R.id.buy_medic) {
            buyInsurance("medic", 30, 180);
        }
        if (view.getId() == R.id.buy_money) {
            buyInsurance("money", 30, 180);
        }
        if (view.getId() == R.id.buy_ship) {
            buyInsurance("ship", 30, 180);
        }
        if (view.getId() == R.id.buy_thief) {
            buyInsurance("thief", 30, 180);
        }
    }
}
