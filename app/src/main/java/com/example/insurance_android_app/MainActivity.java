package com.example.insurance_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        username = (EditText) findViewById(R.id.username_field);
        password = (EditText) findViewById(R.id.password_field);
    }

    public void onClickButton(View view) {
        if (view.getId() == R.id.login_button) {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            User user1 = db.getUserByUsername(user);
            if (user1 == null) {
                Toast.makeText(getBaseContext(), "User not exists.", Toast.LENGTH_SHORT).show();
            } else if (Objects.equals(user1.getPassword(), pass) && user.equals("admin")) {
                Toast.makeText(getBaseContext(), "Welcome Back Admin.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                intent.putExtra("id", user1.getId());
                startActivity(intent);
            } else if (Objects.equals(user1.getPassword(), pass)) {
                Toast.makeText(getBaseContext(), "Congrats! You were perfectly logged in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                intent.putExtra("id", user1.getId());
                startActivity(intent);
            } else { Toast.makeText(getBaseContext(), "Sorry you're wrong.", Toast.LENGTH_LONG).show();}
        } else if (view.getId() == R.id.register_button) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        } else if (view.getId() == R.id.reset_database) {
            db.resetDb();
            Toast.makeText(getBaseContext(), "DB Reset!", Toast.LENGTH_SHORT).show();
        }
    }
}