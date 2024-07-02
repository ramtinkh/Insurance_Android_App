package com.example.insurance_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_DEVICE_ID + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, String.join(",", user.getUsername()));
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_DEVICE_ID, user.getDeviceId());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

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

    public int getRowCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void resetDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        createDB();
    }

    public void createDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_DEVICE_ID + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

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

