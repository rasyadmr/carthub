package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.manager.DatetimeManager;
import com.dirajarasyad.carthub.manager.ImageManager;
import com.dirajarasyad.carthub.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBUserManager {
    private DBHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DBUserManager(Context context) {
        this.context = context;
    }

    public DBUserManager open() {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void addUser(String username, String password, String email, String phone, String address, Drawable image, User.Role role) {
        String id = "USER-" + UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_USER_ID, id);
        values.put(DBHelper.FIELD_USER_USERNAME, username);
        values.put(DBHelper.FIELD_USER_PASSWORD, password);
        values.put(DBHelper.FIELD_USER_EMAIL, email);
        values.put(DBHelper.FIELD_USER_PHONE, phone);
        values.put(DBHelper.FIELD_USER_ADDRESS, address);
        values.put(DBHelper.FIELD_USER_IMAGE, new ImageManager(image).getByteArray());
        values.put(DBHelper.FIELD_USER_ROLE, role.value());
        values.put(DBHelper.FIELD_USER_CREATED_AT, new DatetimeManager(LocalDateTime.now()).getDatetime());
        values.put(DBHelper.FIELD_USER_UPDATED_AT, new DatetimeManager(LocalDateTime.now()).getDatetime());

        database.insert(DBHelper.TABLE_USER, null, values);
        Log.i("DATABASE", "User Created");
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_USER;

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String username = cursor.getString(1);
                    String password = cursor.getString(2);
                    String email = cursor.getString(3);
                    String phone = cursor.getString(4);
                    String address = cursor.getString(5);
                    Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                    User.Role role = User.Role.fromString(cursor.getString(7));
                    LocalDateTime created = new DatetimeManager(cursor.getString(8)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(9)).getLDT();

                    userList.add(new User(id, username, password, email, phone, address, image, role, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched User List");
        return userList;
    }

    public boolean updateUser(String id, String username, String password, String email, String phone, String address, Drawable image, User.Role role, LocalDateTime createdAt) {
        ImageManager imageManager = new ImageManager(image);

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_USER_USERNAME, username);
        values.put(DBHelper.FIELD_USER_PASSWORD, password);
        values.put(DBHelper.FIELD_USER_EMAIL, email);
        values.put(DBHelper.FIELD_USER_PHONE, phone);
        values.put(DBHelper.FIELD_USER_ADDRESS, address);
        values.put(DBHelper.FIELD_USER_IMAGE, imageManager.getByteArray());
        values.put(DBHelper.FIELD_USER_ROLE, role.value());
        values.put(DBHelper.FIELD_USER_CREATED_AT, new DatetimeManager(createdAt).getDatetime());
        values.put(DBHelper.FIELD_USER_UPDATED_AT, new DatetimeManager(LocalDateTime.now()).getDatetime());

        int updateUser = database.update(DBHelper.TABLE_USER, values, DBHelper.FIELD_USER_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "User Updated");
        return updateUser > 0; // true -> Success, false -> Fail
    }

    public boolean deleteUser(User user) {
        String userId = user.getId();
        int deleteUser = database.delete(DBHelper.TABLE_USER, DBHelper.FIELD_USER_ID + " = ?", new String[]{userId});

        Log.i("DATABASE", "User Deleted");
        return deleteUser > 0;
    }

    public User getUserById(String id) {
        User user = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.FIELD_USER_ID + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String email = cursor.getString(3);
                String phone = cursor.getString(4);
                String address = cursor.getString(5);
                Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                User.Role role = User.Role.fromString(cursor.getString(7));
                LocalDateTime created = new DatetimeManager(cursor.getString(8)).getLDT();
                LocalDateTime updated = new DatetimeManager(cursor.getString(9)).getLDT();

                user = new User(id, username, password, email, phone, address, image, role, created, updated);
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched User by ID");
        return user;
    }

    public User getUserByUsername(String username) {
        User user = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.FIELD_USER_USERNAME + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{username});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String id = cursor.getString(0);
                String password = cursor.getString(2);
                String email = cursor.getString(3);
                String phone = cursor.getString(4);
                String address = cursor.getString(5);
                Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                User.Role role = User.Role.fromString(cursor.getString(7));
                LocalDateTime created = new DatetimeManager(cursor.getString(8)).getLDT();
                LocalDateTime updated = new DatetimeManager(cursor.getString(9)).getLDT();

                user = new User(id, username, password, email, phone, address, image, role, created, updated);
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched User by Username");
        return user;
    }

    public List<User> getAllUsersByRole(User.Role role) {
        List<User> userList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_USER
                + " WHERE " + DBHelper.FIELD_USER_ROLE + " =?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{role.value()});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String username = cursor.getString(1);
                    String password = cursor.getString(2);
                    String email = cursor.getString(3);
                    String phone = cursor.getString(4);
                    String address = cursor.getString(5);
                    Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                    LocalDateTime created = new DatetimeManager(cursor.getString(8)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(9)).getLDT();

                    userList.add(new User(id, username, password, email, phone, address, image, role, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched User List by Role");
        return userList;
    }
}
