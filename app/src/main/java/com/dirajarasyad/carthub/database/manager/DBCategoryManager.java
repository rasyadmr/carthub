package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.model.Category;
import com.dirajarasyad.carthub.utilities.Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBCategoryManager {
    private DBHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DBCategoryManager(Context context) {
        this.context = context;
    }

    public DBCategoryManager open() {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void addCategory(String name, Drawable image) {
        String id = "CATE-" + UUID.randomUUID().toString();
        Image img = new Image(image);
        byte[] imageBytes = img.getByteArray();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_CATEGORY_ID, id);
        values.put(DBHelper.FIELD_CATEGORY_NAME, name);
        values.put(DBHelper.FIELD_CATEGORY_IMAGE, imageBytes);

        database.insert(DBHelper.TABLE_CATEGORY, null, values);
        Log.i("DATABASE", "Category Created");
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_CATEGORY;

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    Drawable image = new Image(cursor.getBlob(2), this.context).getImage();

                    categoryList.add(new Category(id, name, image));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched Category List");
        return categoryList;
    }

    public boolean updateCategory(String id, String name, Drawable image) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_CATEGORY_NAME, name);
        values.put(DBHelper.FIELD_CATEGORY_IMAGE, new Image(image).getByteArray());

        int updateCategory = database.update(DBHelper.TABLE_CATEGORY, values, DBHelper.FIELD_CATEGORY_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Category Updated");
        return updateCategory > 0; // true -> Success, false -> Fail
    }

    public boolean deleteCategory(String id) {
        int deleteCategory = database.delete(DBHelper.TABLE_CATEGORY, DBHelper.FIELD_CATEGORY_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Category Deleted");
        return deleteCategory > 0;
    }

    public Category getCategoryById(String id) {
        Category category = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_CATEGORY + " WHERE " + DBHelper.FIELD_CATEGORY_ID + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                Drawable image = new Image(cursor.getBlob(2), this.context).getImage();

                category = new Category(id, name, image);
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched Category by ID");
        return category;
    }

    public Category getCategoryByName(String name) {
        Category category = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_CATEGORY + " WHERE " + DBHelper.FIELD_CATEGORY_NAME + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{name});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String id = cursor.getString(0);
                Drawable image = new Image(cursor.getBlob(2), this.context).getImage();

                category = new Category(id, name, image);
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched Category by Name");
        return category;
    }
}
