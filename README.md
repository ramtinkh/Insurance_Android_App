# Insurance_Android_App

## گزارش اپلیکیشن اندروید بیمه

این گزارش برای کد یک اپلیکیشن اندروید بیمه تهیه شده است. در ادامه به توضیح بخش‌های مختلف کد پرداخته می‌شود.

### فایل 'MainActivity.java'

#### پکیج‌ها و ایمپورت‌ها

```java
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
```

در این بخش، ‌پکیج‌ها و کلاس‌های مورد نیاز برای توسعه اپلیکیشن ایمپورت شده‌اند. این پکیج‌ها شامل کتابخانه‌های مربوط به اکتیویتی‌ها، رابط کاربری و مدیریت دیتابیس می‌باشند.

#### تعریف کلاس `MainActivity`

```java
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
```

در این بخش، کلاس `MainActivity` تعریف شده که از `AppCompatActivity` ارث‌بری می‌کند. در متد `onCreate`، اکتیویتی ایجاد شده و رابط کاربری `activity_main` ست می‌شود. سپس، شیء `DatabaseHandler` برای مدیریت دیتابیس و ویجت‌های `EditText` برای دریافت نام کاربری و رمز عبور تعریف و مقداردهی می‌شوند.

#### متد `onClickButton`

```java
    public void onClickButton(View view) {
        if (view.getId() == R.id.login_button) {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            User user1 = db.getUserByUsername(user);
            if (user1 == null) {
                Toast.makeText(getBaseContext(), "User not exists.", Toast.LENGTH_SHORT).show();
            } else if (Objects.equals(user1.getPassword(), pass)) {
                Toast.makeText(getBaseContext(), "Congrats! You were perfectly logged in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
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
```

در این بخش، متد `onClickButton` برای مدیریت رویدادهای کلیک تعریف شده است. این متد با توجه به `ID` ویوی کلیک شده، عملیات متفاوتی را انجام می‌دهد:

- **دکمه ورود (`login_button`)**: نام کاربری و رمز عبور وارد شده توسط کاربر دریافت شده و بررسی می‌شود که آیا کاربر در دیتابیس موجود است یا خیر. در صورت مطابقت رمز عبور، پیغام موفقیت و انتقال به صفحه داشبورد نمایش داده می‌شود.
- **دکمه ثبت نام (`register_button`)**: انتقال به صفحه ثبت نام انجام می‌شود.
- **دکمه ریست دیتابیس (`reset_database`)**: دیتابیس ریست شده و پیغام مربوطه نمایش داده می‌شود.
