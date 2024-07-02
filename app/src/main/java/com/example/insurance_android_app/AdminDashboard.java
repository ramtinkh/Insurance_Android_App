package com.example.insurance_android_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AdminDashboard extends AppCompatActivity {
    private DatabaseHandler db;
    TextView tv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        tv = (TextView) findViewById(R.id.my_tv);

        db = new DatabaseHandler(this);
    }

    void show_all_users(DatabaseHandler db) {
        List<User> all_contacts = db.getAllUsers();
        tv.append("All Users: " + "\n");
        for (User c : all_contacts) {
            tv.append(c.getId() + " | " + c.getUsername() + " | " + c.getBalance() + "\n");
        }
        tv.append("\n");
    }

    void show_all_insurances(DatabaseHandler db) {
        List<Insurance> allInsurances = db.getAllInsurances();
        tv.append("All Insurances: " + "\n");
        for (Insurance c : allInsurances) {
            tv.append(c.getId() + " | " + c.getName() + " | for user " + c.getUserId() + "\n");
            System.out.println("User: " + c.getUserId());
        }
        tv.append("\n");
    }

    public void onClickButton(View view) {
        if (view.getId() == R.id.show_insurances) {
            tv.setText("");
            show_all_insurances(db);
        }
        if (view.getId() == R.id.show_users) {
            tv.setText("");
            show_all_users(db);
        }
        if (view.getId() == R.id.logout) {
            Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
