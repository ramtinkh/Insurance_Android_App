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
