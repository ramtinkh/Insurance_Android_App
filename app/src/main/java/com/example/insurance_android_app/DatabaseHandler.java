package com.example.insurance_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Insurance";
    private static final String TABLE_USER = "User";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DEVICE_ID = "deviceId";
    private static final String KEY_BALANCE = "balance";


    private static final String TABLE_BUY = "Buy";
    private static final String KEY_ID_BUY = "id";
    private static final String KEY_NAME_BUY = "name";
    private static final String KEY_USERID_BUY = "userId";
    private static final String KEY_EXP_BUY = "expiry";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_DEVICE_ID + " TEXT,"
                + KEY_BALANCE + " INTEGER," + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_BUY_TABLE = "CREATE TABLE " + TABLE_BUY + "("
                + KEY_ID_BUY + " INTEGER PRIMARY KEY," + KEY_NAME_BUY + " TEXT,"
                + KEY_USERID_BUY + " INTEGER," + KEY_EXP_BUY + " TEXT" + ")";
        db.execSQL(CREATE_BUY_TABLE);
    }

    public List<Insurance> getAllInsurances() {
        List<Insurance> insurancesList = new ArrayList<Insurance>();

        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "select * from " + TABLE_BUY;
        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor.moveToFirst()) {
            do {
                Insurance insurance = new Insurance(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3));
                insurancesList.add(insurance);

            } while (cursor.moveToNext());
        }
        cursor.close();

        db.close();
        return insurancesList;
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "select * from " + TABLE_USER;
        Cursor cursor = db.rawQuery(strSQL, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(5),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)));
                usersList.add(user);

            } while (cursor.moveToNext());

        }
        cursor.close();

        db.close();
        return usersList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUY);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, String.join(",", user.getUsername()));
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_DEVICE_ID, user.getDeviceId());
        values.put(KEY_BALANCE, user.getBalance());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addBuy(Insurance insurance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_BUY, String.join(",", insurance.getName()));
        values.put(KEY_USERID_BUY, insurance.getUserId());
        values.put(KEY_EXP_BUY, insurance.getExpiryDate().toString());
        db.insert(TABLE_BUY, null, values);
        db.close();
    }

    public void updateUserById(int id, String username, String password, String email, String deviceId, int balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_EMAIL, email);
        values.put(KEY_DEVICE_ID, deviceId);
        values.put(KEY_BALANCE, balance);

        db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateBalanceById(int id, int balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BALANCE, balance);

        db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getUsersRowCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getInsurancesRowCount() {
        String countQuery = "SELECT * FROM " + TABLE_BUY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void resetDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUY);
        createDB();
    }

    public void createDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_DEVICE_ID + " TEXT,"
                + KEY_BALANCE + " INTEGER," + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_BUY_TABLE = "CREATE TABLE " + TABLE_BUY + "("
                + KEY_ID_BUY + " INTEGER PRIMARY KEY," + KEY_NAME_BUY + " TEXT,"
                + KEY_USERID_BUY + " INTEGER," + KEY_EXP_BUY + " TEXT" + ")";
        db.execSQL(CREATE_BUY_TABLE);
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_BALANCE, KEY_EMAIL, KEY_DEVICE_ID},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            int userId = cursor.getInt(0);
            String username = cursor.getString(1);

            String password = cursor.getString(2);
            String deviceId = cursor.getString(5);
            int balance = Integer.parseInt(cursor.getString(3));
            String email = cursor.getString(4);

            cursor.close();
            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setDeviceId(deviceId);
            user.setBalance(balance);

            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    public User getUserByUsername(String usernameInput) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_BALANCE, KEY_EMAIL, KEY_DEVICE_ID},
                KEY_USERNAME + "=?", new String[]{usernameInput}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            int userId = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String deviceId = cursor.getString(5);
            int balance = Integer.parseInt(cursor.getString(3));
            String email = cursor.getString(4);

            cursor.close();
            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setDeviceId(deviceId);
            user.setBalance(balance);

            return user;
        } else {
            cursor.close();
            return null;
        }
    }
}

