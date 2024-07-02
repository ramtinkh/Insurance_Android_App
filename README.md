# Insurance_Android_App

## گزارش اپلیکیشن اندروید بیمه

این گزارش برای کد یک اپلیکیشن اندروید بیمه تهیه شده است. در ادامه به توضیح بخش‌های مختلف کد پرداخته می‌شود.

### فایل `MainActivity.java`

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

### فایل `activity_main.xml`

این فایل XML مربوط به رابط کاربری صفحه اصلی اپلیکیشن اندروید بیمه است.

#### ساختار کلی

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:background="@drawable/blue_to_black">
```

این `RelativeLayout` به عنوان ریشه تمام viewهای موجود در این صفحه عمل می‌کند. از `tools:context` برای تعیین کلاس فعالیت مرتبط با این رابط کاربری استفاده شده است و `android:background` برای تنظیم تصویر پس‌زمینه اعمال شده است.

#### RelativeLayout مرکزی

```xml
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true">
```

این `RelativeLayout` در مرکز صفحه قرار دارد و شامل عناصری مانند `ImageView`، `TextView` و `EditText` می‌باشد.

#### ImageView

```xml
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="190dp"
            android:layout_height="69dp"
            android:src="@drawable/umbrella_logo"
            android:layout_marginBottom="5dp"
            android:contentDescription="@string/umbrella"
            android:layout_centerHorizontal="true"/>
```

این عنصر یک تصویر لوگو با آی‌دی `imageView` است که در مرکز افقی قرار دارد.

#### TextView

```xml
        <TextView
            android:id="@+id/title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/we_protect_you_like_an_umbrella"
            android:textColor="@color/white"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_marginBottom="20dp"
            android:layout_below="@+id/imageView"
            android:layout_centerHorizontal="true"/>
```

این عنصر متن با آی‌دی `title` در زیر `ImageView` قرار دارد و شامل پیامی است که به صورت برجسته نمایش داده می‌شود.

#### تگ‌های EditText

```xml
        <EditText
            android:id="@+id/username_field"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:textAlignment="center"
            android:hint="@string/username_hint"
            android:textColorHint="@color/white"
            android:textColor="@color/white"
            android:inputType="text"
            android:autofillHints="username"
            android:layout_below="@id/title"/>

        <EditText
            android:id="@+id/password_field"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:textAlignment="center"
            android:hint="@string/password_hint"
            android:textColorHint="@color/white"
            android:textColor="@color/white"
            android:inputType="textPassword"
            android:autofillHints="password"
            android:layout_below="@id/username_field"/>
```

این دو `EditText` برای ورود نام کاربری و رمز عبور کاربران استفاده می‌شوند. هر دو به صورت مرکزی تراز شده‌اند و به ترتیب در زیر عنوان و فیلد نام کاربری قرار دارند.

#### دکمه‌ها

```xml
        <RelativeLayout
            android:id="@+id/buttons_layout"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_below="@id/password_field">

            <Button
                android:id="@+id/register_button"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/register_button"
                android:layout_marginTop="5dp"
                android:backgroundTint="@color/black"
                android:onClick="onClickButton"/>

            <Button
                android:id="@+id/login_button"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/login_button"
                android:layout_marginTop="5dp"
                android:backgroundTint="@color/black"
                android:layout_toRightOf="@+id/register_button"
                android:layout_alignBaseline="@id/register_button"
                android:onClick="onClickButton"/>
        </RelativeLayout>
```

این دکمه‌ها برای ثبت نام و ورود کاربران طراحی شده‌اند و در یک `RelativeLayout` دیگر قرار دارند که به صورت افقی در مرکز صفحه تراز شده است. دکمه ثبت نام در سمت چپ و دکمه ورود در سمت راست قرار دارند.

#### دکمه ریست دیتابیس

```xml
    <Button
        android:id="@+id/reset_database"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Reset Database"
        android:layout_marginTop="40dp"
        android:layout_centerHorizontal="true"
        android:backgroundTint="@color/black"
        android:onClick="onClickButton"/>
```

این دکمه برای ریست دیتابیس طراحی شده و در پایین‌ترین بخش صفحه قرار دارد.

![image](https://github.com/ramtinkh/Insurance_Android_App/assets/62210678/79330ba6-d694-4c3b-b7e5-1481a48aabb0)

### فایل `Dashboard.java`

#### پکیج‌ها و ایمپورت‌ها

```java
package com.example.insurance_android_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
```

در این بخش، پکیج‌ها و کلاس‌های مورد نیاز برای توسعه اپلیکیشن ایمپورت شده‌اند. این پکیج‌ها شامل کتابخانه‌های مربوط به اکتیویتی‌ها، رابط کاربری و مدیریت دیتابیس می‌باشند.

#### تعریف کلاس `Dashboard`

```java
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
```

در این بخش، کلاس `Dashboard` تعریف شده که از `AppCompatActivity` ارث‌بری می‌کند. در متد `onCreate`، اکتیویتی ایجاد شده و رابط کاربری `dashboard` ست می‌شود. سپس، شیء `DatabaseHandler` برای مدیریت دیتابیس و ویجت‌های `TextView` برای نمایش نام کاربری و موجودی حساب تعریف و مقداردهی می‌شوند.

#### متد `setDetails`

```java
    @SuppressLint("SetTextI18n")
    private void setDetails() {
        user.setText(loggedIn.getUsername());
        balance.setText("Balance:" + loggedIn.getBalance());
    }
```

در این بخش، متد `setDetails` برای نمایش جزئیات کاربر وارد شده تعریف شده است. این متد نام کاربری و موجودی حساب کاربر را در ویجت‌های مربوطه نمایش می‌دهد.

#### متد `updateBalance`

```java
    @SuppressLint("SetTextI18n")
    private void updateBalance(int amount) {
        db.updateBalanceById(loggedIn.getId(), amount);
        loggedIn = db.getUserById(loggedIn.getId());
        balance.setText("Balance:" + loggedIn.getBalance());
    }
```

در این بخش، متد `updateBalance` برای به‌روزرسانی موجودی حساب کاربر تعریف شده است. این متد موجودی حساب کاربر را در دیتابیس به‌روزرسانی کرده و مقدار جدید را در ویجت مربوطه نمایش می‌دهد.

#### متد `buyInsurance`

```java
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
```

در این بخش، متد `buyInsurance` برای خرید بیمه تعریف شده است. این متد بررسی می‌کند که آیا کاربر موجودی کافی برای خرید بیمه دارد یا خیر و در صورت وجود، اطلاعات بیمه خریداری شده را به دیتابیس اضافه می‌کند و موجودی حساب کاربر را به‌روزرسانی می‌کند.

#### متد `onClickButton`

```java
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
```

در این بخش، متد `onClickButton` برای مدیریت رویدادهای کلیک تعریف شده است. این متد با توجه به `ID` ویوی کلیک شده، عملیات متفاوتی را انجام می‌دهد:

- **دکمه خروج (`logout`)**: انتقال به صفحه اصلی.
- **دکمه افزودن موجودی (`add_balance`)**: افزودن 200 واحد به موجودی حساب کاربر.
- **دکمه‌های خرید بیمه**: خرید انواع بیمه با نام‌ها و مقدارهای مختلف.

### فایل `dashboard.xml`

#### تعریف XML و Layout کلی

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:tools="http://schemas.android.com/tools"
    android:background="@drawable/white_to_blue"
    tools:context=".Dashboard">
```

در این بخش، `RelativeLayout` به عنوان ریشه‌ی رابط کاربری انتخاب شده است. همچنین، `xmlns` های مورد نیاز تعریف شده و پس‌زمینه به `white_to_blue` تنظیم شده است.

#### بخش اطلاعات کاربر

```xml
    <RelativeLayout
        android:id="@+id/user_info"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/person_logo"
            android:layout_width="80dp"
            android:layout_height="69dp"
            android:src="@drawable/person_blue"
            android:layout_alignParentStart="true"/>

        <TextView
            android:id="@+id/user"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Ramtin"
            android:textColor="@color/royalblue"
            android:textSize="30sp"
            android:layout_alignBaseline="@+id/person_logo"
            android:layout_toEndOf="@id/person_logo"
            android:layout_centerVertical="true"/>

        <TextView
            android:id="@+id/balance"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Balance:"
            android:textColor="@color/royalblue"
            android:textSize="20sp"
            android:layout_alignParentEnd="true"
            android:layout_centerVertical="true"
            android:layout_marginEnd="20dp"/>
    </RelativeLayout>
```

در این بخش، اطلاعات کاربر شامل یک تصویر و دو `TextView` برای نمایش نام کاربری و موجودی حساب کاربر نمایش داده می‌شود.

#### گزینه‌های بیمه (ScrollView)

```xml
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/user_info">

        <RelativeLayout
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:gravity="center">
```

این بخش شامل یک `ScrollView` است که تمامی گزینه‌های بیمه را در خود جای داده و قابل اسکرول است.

#### دکمه افزودن موجودی

```xml
            <Button
                android:id="@+id/add_balance"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Add Balance"
                android:backgroundTint="@color/blueviolet"
                android:layout_centerHorizontal="true"
                android:layout_marginBottom="10dp"
                android:onClick="onClickButton"/>
```

این دکمه به کاربر امکان افزودن موجودی را می‌دهد.

#### بیمه کوآلا

```xml
            <RelativeLayout
                android:id="@+id/koala_layout"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@id/add_balance"
                android:layout_margin="20dp">

                <ImageView
                    android:id="@+id/koala"
                    android:layout_width="120dp"
                    android:layout_height="120dp"
                    android:src="@drawable/koala"
                    android:layout_alignParentStart="true"
                    android:layout_marginRight="10dp"/>

                <TextView
                    android:id="@+id/koala_title"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="Koala Insurance For Full Services."
                    android:layout_toRightOf="@+id/koala"/>

                <Button
                    android:id="@+id/buy_koala"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Buy Koala"
                    android:layout_below="@id/koala_title"
                    android:layout_alignStart="@id/koala_title"
                    android:backgroundTint="@color/royalblue"
                    android:onClick="onClickButton"
                    android:layout_marginStart="60dp"/>

                <TextView
                    android:id="@+id/koala_price"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="100$"
                    android:layout_marginStart="10dp"
                    android:layout_alignBaseline="@id/buy_koala"
                    android:layout_toRightOf="@+id/buy_koala"/>
            </RelativeLayout>
```

در این بخش، اطلاعات مربوط به بیمه کوآلا شامل تصویر، عنوان و دکمه خرید نمایش داده می‌شود.

#### Layout های دیگر بیمه

بخش‌های دیگر مشابه با `koala_layout` طراحی شده‌اند و شامل بیمه‌های مختلف مانند `money`, `fire`, `medic`, `airplane`, `ship`, و `thief` می‌باشند. هر کدام شامل تصویر، عنوان، دکمه خرید و قیمت مخصوص به خود می‌باشد.

#### دکمه خروج از حساب کاربری

```xml
            <Button
                android:id="@+id/logout"
                android:layout_below="@id/thief_layout"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Logout"
                android:backgroundTint="@color/black"
                android:layout_centerHorizontal="true"
                android:onClick="onClickButton"
                android:layout_marginBottom="30dp"/>
```

این دکمه به کاربر امکان خروج از حساب کاربری را می‌دهد.

### `فایل splash.java`

#### پکیج‌ها و ایمپورت‌ها

```java
package com.example.insurance_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
```

در این بخش، پکیج‌ها و کلاس‌های مورد نیاز برای توسعه صفحه ابتدایی (splash screen) اپلیکیشن ایمپورت شده‌اند. این پکیج‌ها شامل کتابخانه‌های مربوط به اکتیویتی‌ها و مدیریت زمان‌بندی عملیات‌ها می‌باشند.

#### تعریف کلاس `splash`

```java
public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
```

در این بخش، کلاس `splash` تعریف شده که از `AppCompatActivity` ارث‌بری می‌کند. در متد `onCreate`، اکتیویتی ایجاد شده و رابط کاربری `splash_activity` ست می‌شود.

#### زمان‌بندی انتقال به MainActivity

```java
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
```

در این بخش، یک `Handler` برای زمان‌بندی انتقال به فعالیت اصلی (MainActivity) ایجاد شده است. `postDelayed` یک `Runnable` را پس از یک تاخیر 2 ثانیه‌ای (2000 میلی‌ثانیه) اجرا می‌کند. این `Runnable` شامل کد زیر است:
- **ایجاد Intent**: یک `Intent` جدید برای انتقال از `splash` به `MainActivity` ایجاد می‌شود.
- **شروع فعالیت**: فعالیت `MainActivity` شروع می‌شود.
- **پایان فعالیت جاری**: فعالیت `splash` خاتمه می‌یابد.

### فایل `splash_activity.xml`

#### عناصر XML و ویژگی‌ها

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".splash"
    android:background="@drawable/splash">

</androidx.constraintlayout.widget.ConstraintLayout>
```
فایل `splash_activity.xml` یک رابط کاربری ساده برای صفحه ابتدایی اپلیکیشن اندروید فراهم می‌کند. در اینجا از `ConstraintLayout` استفاده شده است. این فایل شامل یک تصویر پس‌زمینه با نام `splash` است که به عنوان پس‌زمینه صفحه ابتدایی تنظیم شده است. طراحی این فایل ساده است و به سرعت اجرا می‌شود.


### فایل `DatabaseHandler.java`

این کد شامل کلاس `DatabaseHandler` می‌باشد که وظیفه مدیریت عملیات مربوط به پایگاه داده SQLite را بر عهده دارد.

#### پکیج‌ها و ایمپورت‌ها

```java
package com.example.insurance_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
```

در این بخش، پکیج‌ها و کلاس‌های مورد نیاز برای مدیریت پایگاه داده SQLite وارد شده‌اند. این پکیج‌ها شامل کلاس‌هایی برای عملیات خواندن و نوشتن در دیتابیس و مدیریت جداول دیتابیس می‌باشند.

#### تعریف کلاس `DatabaseHandler`

```java
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Insurance";
    private static final String TABLE_USER = "User";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DEVICE_ID = "deviceId";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
```

کلاس `DatabaseHandler` از کلاس `SQLiteOpenHelper` ارث‌بری می‌کند. این کلاس به عنوان مدیریت کننده دیتابیس عمل می‌کند و شامل متغیرهای ثابت برای نسخه دیتابیس، نام دیتابیس و جداول و ستون‌های آن می‌باشد.

#### متد `onCreate`

```java
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_DEVICE_ID + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }
```

در این متد، جدول `User` با ستون‌های مشخص شده ایجاد می‌شود. این متد تنها یک بار و در زمان ایجاد دیتابیس فراخوانی می‌شود.

#### متد `onUpgrade`

```java
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
```

در این متد، در صورت تغییر نسخه دیتابیس، جدول `User` حذف شده و دوباره ایجاد می‌شود. این عمل برای بروزرسانی ساختار دیتابیس استفاده می‌شود.

#### متد `addUser`

```java
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_DEVICE_ID, user.getDeviceId());
        db.insert(TABLE_USER, null, values);
        db.close();
    }
```

این متد برای افزودن یک کاربر جدید به جدول `User` استفاده می‌شود. ابتدا دیتابیس در حالت قابل نوشتن باز شده و مقادیر کاربر جدید به صورت `ContentValues` ست می‌شود. سپس این مقادیر در جدول `User` درج می‌شود و دیتابیس بسته می‌شود.

#### متد `updateUserById`

```java
    public void updateUserById(int id, String username, String password, String email, String deviceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_EMAIL, email);
        values.put(KEY_DEVICE_ID, deviceId);

        db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
```

این متد برای بروزرسانی اطلاعات یک کاربر بر اساس `ID` استفاده می‌شود. مقادیر جدید کاربر به صورت `ContentValues` ست شده و در جدول `User` بروز می‌شوند.

#### متد `getRowCount`

```java
    public int getRowCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
```

این متد تعداد رکوردهای موجود در جدول `User` را برمی‌گرداند. با اجرای یک کوئری شمارش، تعداد رکوردها از طریق `Cursor` بدست می‌آید و نتیجه برگردانده می‌شود.

#### متد `resetDb`

```java
    public void resetDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        createDB();
    }
```

این متد برای ریست کردن دیتابیس استفاده می‌شود. در واقع جدول `User` حذف شده و دوباره ایجاد می‌شود.

#### متد `createDB`

```java
    public void createDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_DEVICE_ID + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }
```

این متد برای ایجاد جدول `User` در دیتابیس استفاده می‌شود.

#### متد `getUserById`

```java
    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL, KEY_DEVICE_ID},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            int userId = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String email = cursor.getString(3);
            String deviceId = cursor.getString(4);

            cursor.close();
            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setDeviceId(deviceId);

            return user;
        } else {
            cursor.close();
            return null;
        }
    }
```

این متد برای دریافت اطلاعات یک کاربر بر اساس `ID` استفاده می‌شود. مقادیر کاربر از جدول `User` استخراج شده و به صورت یک شیء `User` برگردانده می‌شود.

#### متد `getUserByUsername`

```java
    public User getUserByUsername(String usernameInput) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL, KEY_DEVICE_ID},
                KEY_USERNAME + "=?", new String[]{usernameInput}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            int userId = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String email = cursor.getString(3);
            String deviceId = cursor.getString(4);

            cursor.close();
            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setDeviceId(deviceId);

            return user;
        } else {
            cursor.close();
            return null;
        }
    }
}
```

این متد برای دریافت اطلاعات یک کاربر بر اساس نام کاربری (`username`) استفاده می‌شود. مقادیر کاربر از جدول `User` استخراج شده و به صورت یک شیء `User` برگردانده می‌شود.

### فایل `User.java`

این کد به تحلیل کلاس `User` پرداخته است که به عنوان مدل داده‌ای کاربران در اپلیکیشن اندروید بیمه استفاده می‌شود.

#### پکیج‌ و تعریف کلاس

```java
package com.example.insurance_android_app;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String deviceId;

    public User() {}

    public User(int id, String username, String password, String email, String deviceId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.deviceId = deviceId;
    }
```

این کلاس شامل تعریف فیلدهای خصوصی (private) برای ذخیره اطلاعات کاربر مانند شناسه، نام کاربری، رمز عبور، ایمیل و شناسه دستگاه است. همچنین دارای دو constructor می‌باشد: یکی بدون پارامتر و دیگری با پارامترهای لازم برای مقداردهی فیلدها.

#### متدهای getter و setter

```java
    public int getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
```

این بخش شامل متدهای getter و setter برای هر یک از فیلدهای خصوصی است. این متدها به encapsulation داده‌ها کمک می‌کنند و امکان دسترسی و تغییر امن اطلاعات کاربر را فراهم می‌سازند.

### فایل `Register.java` 

این قسمت به کد کلاس `Register` پرداخته است که وظیفه ثبت نام کاربران جدید در اپلیکیشن اندروید بیمه را بر عهده دارد.

#### پکیج‌ها و ایمپورت‌ها

```java
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
```

در این بخش، پکیج‌ها و کلاس‌های مورد نیاز برای فعالیت‌های اندروید، دسترسی به مجوزها، مدیریت داده‌های کاربر و دریافت شناسه دستگاه وارد شده‌اند.

#### تعریف کلاس Register

```java
public class Register extends AppCompatActivity {

    private EditText username, password, email;
    private DatabaseHandler db;
    TelephonyManager telephonyManager;
    private String deviceId;
```

کلاس `Register` از `AppCompatActivity` ارث‌بری می‌کند و فیلدهای خصوصی برای ذخیره اطلاعات کاربر و دسترسی به پایگاه داده تعریف می‌کند.

#### متد `onCreate`

```java
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
```

در این متد، نمای ثبت نام بارگذاری می‌شود و اشیاء `DatabaseHandler` و `TelephonyManager` مقداردهی اولیه می‌شوند. همچنین، فیلدهای ورودی برای نام کاربری، رمز عبور و ایمیل تنظیم می‌شوند. در نهایت، متد `perm` برای درخواست مجوز خواندن شناسه دستگاه فراخوانی می‌شود.

#### متد `perm`

```java
    void perm() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 432);
    }
```

این متد برای درخواست مجوز خواندن وضعیت تلفن (شناسه دستگاه) از کاربر استفاده می‌شود.

#### متد `onRequestPermissionsResult`

```java
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 432: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    read();
                } else {
                    // don't have permission
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```

این متد برای پردازش نتیجه درخواست مجوز استفاده می‌شود. اگر مجوز داده شود، متد `read` فراخوانی می‌شود تا شناسه دستگاه خوانده شود.

#### متد `read`

```java
    @SuppressLint("HardwareIds")
    void read() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }
```

این متد برای خواندن شناسه دستگاه (در اینجا از `ANDROID_ID` استفاده شده) استفاده می‌شود. اگر مجوز لازم وجود نداشته باشد، متد بدون انجام کاری بازمی‌گردد.

#### متد `makeToast`

```java
    private void makeToast(String fName, String pass, String emailAddress) {
        String result = "Hello " + fName + ". You have registered with email address: " + emailAddress + ".";
        Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();

        int userId = db.getRowCount() + 1;
        User user = new User(userId, fName, pass, emailAddress, deviceId);
        db.addUser(user);
    }
```

این متد پیامی با اطلاعات کاربر جدید ایجاد می‌کند و آن را به صورت Toast نمایش می‌دهد. سپس، شناسه جدید کاربر محاسبه شده و کاربر جدید به پایگاه داده افزوده می‌شود.

#### متد `onClickButton`

```java
    public void onClickButton(View view) {
        if (view.getId() == R.id.register_button) {
            makeToast(username.getText().toString(), password.getText().toString(), email.getText().toString());
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
```

این متد برای پردازش کلیک بر روی دکمه ثبت نام استفاده می‌شود. اطلاعات کاربر خوانده شده و متد `makeToast` فراخوانی می‌شود. سپس، یک Intent برای بازگشت به صفحه اصلی (MainActivity) ایجاد و شروع می‌شود.

### فایل `register.xml`

این فایل مربوط به رابط کاربری صفحه ثبت نام در اپلیکیشن اندروید بیمه است.

#### ساختار کلی

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".Register"
    android:background="@drawable/blue_to_black">
```

این `RelativeLayout` به عنوان ریشه تمام viewهای موجود در این صفحه عمل می‌کند و از `android:background` برای تنظیم تصویر پس‌زمینه استفاده شده است.

#### RelativeLayout مرکزی

```xml
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true">
```

این `RelativeLayout` در مرکز صفحه قرار دارد و شامل عناصری مانند لوگو، عنوان، فیلدهای ورود اطلاعات و دکمه ثبت نام است.

#### ImageView برای نمایش لوگو

```xml
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="190dp"
            android:layout_height="69dp"
            android:src="@drawable/umbrella_logo"
            android:layout_marginBottom="5dp"
            android:contentDescription="@string/coffee"
            android:layout_centerHorizontal="true"/>
```

این `ImageView` برای نمایش لوگوی اپلیکیشن استفاده می‌شود و به‌طور مرکزی تراز شده است.

#### TextView برای عنوان

```xml
        <TextView
            android:textColor="@color/white"
            android:id="@+id/title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/register_to_get_cover"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_marginBottom="20dp"
            android:layout_below="@+id/imageView"
            android:layout_centerHorizontal="true"/>
```

این `TextView` عنوان صفحه ثبت نام را نمایش می‌دهد و در زیر لوگو قرار دارد.

#### RelativeLayout برای فیلد ورود نام کاربری

```xml
        <RelativeLayout
            android:id="@+id/username_layout"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_below="@+id/title">
            
            <TextView
                android:id="@+id/username_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@color/white"
                android:text="@string/username_header"/>
            
            <EditText
                android:id="@+id/username_field"
                android:layout_width="300dp"
                android:layout_height="50dp"
                android:autofillHints="username"
                android:hint="@string/username_hint"
                android:textColorHint="@color/white"
                android:textColor="@color/white"
                android:inputType="text"
                android:textAlignment="center"
                android:layout_centerHorizontal="true"
                android:layout_alignBaseline="@id/username_title"
                android:layout_toRightOf="@id/username_title"/>
            
        </RelativeLayout>
```

این `RelativeLayout` شامل یک `TextView` برای نمایش عنوان "نام کاربری" و یک `EditText` برای وارد کردن نام کاربری است. 

#### RelativeLayout برای فیلد ورود کلمه عبور

```xml
        <RelativeLayout
            android:id="@+id/password_layout"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_below="@id/username_layout"
            android:layout_marginTop="15dp">
            
            <TextView
                android:id="@+id/password_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@color/white"
                android:text="@string/password_header"/>
            
            <EditText
                android:id="@+id/password_field"
                android:layout_width="300dp"
                android:layout_height="50dp"
                android:autofillHints="password"
                android:textColorHint="@color/white"
                android:hint="@string/password_hint"
                android:inputType="textPassword"
                android:textColor="@color/white"
                android:textAlignment="center"
                android:layout_centerHorizontal="true"
                android:layout_alignBaseline="@id/password_title"
                android:layout_toEndOf="@id/password_title"/>
            
        </RelativeLayout>
```

این `RelativeLayout` شامل یک `TextView` برای نمایش عنوان "کلمه عبور" و یک `EditText` برای وارد کردن کلمه عبور است.

#### RelativeLayout برای فیلد ورود ایمیل

```xml
        <RelativeLayout
            android:id="@+id/email_layout"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_below="@id/password_layout"
            android:layout_marginTop="15dp">
            
            <TextView
                android:id="@+id/email_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@color/white"
                android:text="@string/email_header"/>
            
            <EditText
                android:id="@+id/email_field"
                android:layout_width="300dp"
                android:layout_height="50dp"
                android:textAlignment="center"
                android:textColorHint="@color/white"
                android:hint="@string/email_hint"
                android:inputType="textEmailAddress"
                android:textColor="@color/white"
                android:autofillHints="email"
                android:layout_toEndOf="@id/email_title"
                android:layout_alignBaseline="@+id/email_title"/>
            
        </RelativeLayout>
```

این `RelativeLayout` شامل یک `TextView` برای نمایش عنوان "ایمیل" و یک `EditText` برای وارد کردن ایمیل است.

#### RelativeLayout برای دکمه ثبت نام

```xml
        <RelativeLayout
            android:id="@+id/button_layout"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_below="@id/email_layout">
            
            <Button
                android:id="@+id/register_button"
                android:layout_width="200dp"
                android:layout_height="wrap_content"
                android:text="@string/register_button"
                android:layout_marginTop="5dp"
                android:backgroundTint="@color/black"
                android:onClick="onClickButton"/>
            
        </RelativeLayout>
```

این `RelativeLayout` شامل یک دکمه برای ثبت نام کاربران است که در زیر فیلد ایمیل قرار دارد و با رنگ مشکی تنظیم شده است.

![image](https://github.com/ramtinkh/Insurance_Android_App/assets/62210678/d4d329be-19c2-4917-a375-e5a7da57561b)


### فایل `OTP.java`

این گزارش به بررسی کد کلاس `OTP` پرداخته است که برای تایید شماره تلفن کاربران با استفاده از کد OTP در اپلیکیشن اندروید بیمه استفاده می‌شود.

#### ‌پکیج‌ها و ‌ایمپورت‌ها

```java
package com.example.insurance_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
```

در این بخش، ‌پکیج‌ها و کلاس‌های مورد نیاز برای اکتیویتی‌های اندروید، مدیریت تایید شماره تلفن و کار با Firebase وارد شده‌اند.

#### تعریف کلاس OTP

```java
public class OTP extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText edtPhone, edtOTP;

    private Button verifyOTPBtn, generateOTPBtn;

    private String verificationId;
```

کلاس `OTP` از `AppCompatActivity` ارث‌بری می‌کند و فیلدهای خصوصی برای مدیریت تایید شماره تلفن و دسترسی به Firebase تعریف می‌کند.

#### متد `onCreate`

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        mAuth = FirebaseAuth.getInstance();

        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(OTP.this, "Someone is logged in!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(OTP.this, "No one is here.", Toast.LENGTH_SHORT).show();
        }

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(OTP.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = "+98" + edtPhone.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });

        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(OTP.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
    }
```

در این متد، view ی تایید OTP بارگذاری می‌شود و اشیاء `FirebaseAuth` مقداردهی اولیه می‌شوند. همچنین، فیلدهای ورودی برای شماره تلفن و کد OTP تنظیم می‌شوند. دکمه‌های تایید و تولید کد OTP نیز با استفاده از `OnClickListener` تنظیم می‌شوند.

#### متد `signInWithCredential`

```java
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(OTP.this, Dashboard.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
```

این متد برای ورود کاربر با استفاده از اعتبارنامه تلفن استفاده می‌شود. در صورت موفقیت، کاربر به صفحه داشبورد هدایت می‌شود.

#### متد `sendVerificationCode`

```java
    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(OTP.this, "OTP Sent!", Toast.LENGTH_SHORT).show();
    }
```

این متد برای ارسال کد تایید به شماره تلفن وارد شده توسط کاربر استفاده می‌شود.

#### متغیر `mCallBack`

```java
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Toast.makeText(OTP.this, "OTP Sent!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                edtOTP.setText(code);

                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
```

این متغیر به عنوان callback برای مدیریت حالات مختلف تایید شماره تلفن استفاده می‌شود. در صورت ارسال موفق کد، کد تایید دریافت شده و یا خطا رخ می‌دهد، پیام‌های مناسب به کاربر نمایش داده می‌شود.

#### متد `verifyCode`

```java
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithCredential(credential);
    }
}
```

این متد برای تایید کد وارد شده توسط کاربر استفاده می‌شود. در صورت صحیح بودن کد، کاربر وارد حساب کاربری خود می‌شود.

![image](https://github.com/ramtinkh/Insurance_Android_App/assets/62210678/157b0973-298f-4061-9bf5-d9545c90dccb)
![image](https://github.com/ramtinkh/Insurance_Android_App/assets/62210678/bc26d2e6-386c-4b25-a341-d5888215a133)

###فایل `otp.xml`

این فایل XML مربوط به رابط کاربری صفحه OTP (رمز یک‌بار مصرف) در اپلیکیشن اندروید بیمه است. 

#### ساختار کلی

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/black_to_blue">
```

این `RelativeLayout` به عنوان ریشه تمام viewهای موجود در این صفحه عمل می‌کند و از `android:background` برای تنظیم تصویر پس‌زمینه استفاده شده است.

#### RelativeLayout مرکزی

```xml
    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true">
```

این `RelativeLayout` در مرکز عمودی صفحه قرار دارد و شامل عناصر ورودی و دکمه‌ها است.

#### EditText برای شماره تلفن

```xml
        <EditText
            android:id="@+id/idEdtPhoneNumber"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_margin="10dp"
            android:hint="@string/_9121111111"
            android:gravity="center"
            android:textColor="@color/white"
            android:textColorHint="@color/white"
            android:importantForAutofill="no"
            android:inputType="textEmailAddress"
            tools:ignore="TextFields" />
```

این `EditText` با آی‌دی `idEdtPhoneNumber` برای وارد کردن شماره تلفن کاربران استفاده می‌شود. به‌طور مرکزی تراز شده و با حاشیه‌ای برابر از اطراف قرار گرفته است. 

#### دکمه برای دریافت OTP

```xml
        <Button
            android:id="@+id/idBtnGetOtp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@id/idEdtPhoneNumber"
            android:layout_margin="10dp"
            android:backgroundTint="@color/black"
            android:text="@string/get_otp"
            android:textAllCaps="false" />
```

این دکمه با آی‌دی `idBtnGetOtp` برای ارسال درخواست OTP به شماره تلفن وارد شده توسط کاربر استفاده می‌شود و در زیر `EditText` شماره تلفن قرار دارد.

#### EditText برای وارد کردن OTP

```xml
        <EditText
            android:id="@+id/idEdtOtp"
            android:layout_centerHorizontal="true"
            android:layout_centerVertical="true"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@id/idBtnGetOtp"
            android:layout_margin="10dp"
            android:textColor="@color/white"
            android:textColorHint="@color/white"
            android:hint="@string/enter_otp"
            android:gravity="center"
            android:importantForAutofill="no"
            android:inputType="textEmailAddress" />
```

این `EditText` با آی‌دی `idEdtOtp` برای وارد کردن کد OTP توسط کاربر استفاده می‌شود و در زیر دکمه دریافت OTP قرار دارد. این عنصر نیز به‌طور مرکزی تراز شده است.

#### دکمه برای تأیید OTP

```xml
        <Button
            android:id="@+id/idBtnVerify"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@id/idEdtOtp"
            android:layout_margin="10dp"
            android:backgroundTint="@color/black"
            android:text="@string/verify_otp"
            android:textAllCaps="false" />
```

این دکمه با آی‌دی `idBtnVerify` برای تأیید کد OTP وارد شده توسط کاربر استفاده می‌شود و در زیر `EditText` وارد کردن OTP قرار دارد.

![image](https://github.com/ramtinkh/Insurance_Android_App/assets/62210678/41e55f1c-c847-4cff-b93f-1083e596830b)




