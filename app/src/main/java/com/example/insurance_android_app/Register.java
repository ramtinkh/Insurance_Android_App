package com.example.insurance_android_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends AppCompatActivity {

    private EditText username, password, email;
    private DatabaseHandler db;
    TelephonyManager telephonyManager;
    private String deviceId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new DatabaseHandler(this);
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        username = (EditText) findViewById(R.id.username_field);
        password = (EditText) findViewById(R.id.password_field);
        email = (EditText) findViewById(R.id.email_field);

        perm();
    }

    void perm() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 432);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 432: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    read();
                } else {
                    //tt --> don't have permission
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @SuppressLint("HardwareIds")
    void read() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);;
    }

    private void makeToast(String fName, String pass, String emailAddress) {
        String result = "Hello " + fName + ". You have register with email address: " + emailAddress + ".";
        Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();

        int userId = db.getRowCount() + 1;
        User user = new User(userId, fName, pass, emailAddress, deviceId);
        db.addUser(user);
    }

    public void onClickButton(View view) {
        if (view.getId() == R.id.register_button) {
            makeToast(username.getText().toString(), password.getText().toString(), email.getText().toString());
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
