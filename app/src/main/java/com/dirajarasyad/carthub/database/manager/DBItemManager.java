package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.manager.ImageManager;
import com.dirajarasyad.carthub.model.Category;
import com.dirajarasyad.carthub.model.Item;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBItemManager {
    private DBHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DBItemManager(Context context) {
        this.context = context;
    }

    public DBItemManager open() {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void addItem(String name, String description, Integer price, Integer stock, Integer rating, Drawable image, User user, Category category) {
        String id = "ITEM-" + UUID.randomUUID().toString();
        ImageManager imageManager = new ImageManager(image);

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_ITEM_ID, id);
        values.put(DBHelper.FIELD_ITEM_NAME, name);
        values.put(DBHelper.FIELD_ITEM_DESCRIPTION, description);
        values.put(DBHelper.FIELD_ITEM_PRICE, price);
        values.put(DBHelper.FIELD_ITEM_STOCK, stock);
        values.put(DBHelper.FIELD_ITEM_RATING, rating);
        values.put(DBHelper.FIELD_ITEM_IMAGE, imageManager.getByteArray());
        values.put(DBHelper.FIELD_ITEM_USER, user.getId());
        values.put(DBHelper.FIELD_ITEM_CATEGORY, category.getId());

        database.insert(DBHelper.TABLE_ITEM, null, values);
        Log.i("DATABASE", "Item Created");
    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM;

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBCategoryManager categoryManager = new DBCategoryManager(context);
        categoryManager.open();

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String description = cursor.getString(2);
                    Integer price = cursor.getInt(3);
                    Integer stock = cursor.getInt(4);
                    Integer rating = cursor.getInt(5);
                    Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                    User user = userManager.getUserById(cursor.getString(7));
                    Category category = categoryManager.getCategoryById(cursor.getString(8));

                    itemList.add(new Item(id, name, description, price, stock, rating, image, user, category));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();

        Log.i("DATABASE", "Fetched Item List");
        return itemList;
    }

    public boolean updateItem(String id, String name, String description, Integer price, Integer stock, Integer rating, Drawable image, User user, Category category) {
        ImageManager imageManager = new ImageManager(image);

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_ITEM_NAME, name);
        values.put(DBHelper.FIELD_ITEM_DESCRIPTION, description);
        values.put(DBHelper.FIELD_ITEM_PRICE, price);
        values.put(DBHelper.FIELD_ITEM_STOCK, stock);
        values.put(DBHelper.FIELD_ITEM_RATING, rating);
        values.put(DBHelper.FIELD_ITEM_IMAGE, imageManager.getByteArray());
        values.put(DBHelper.FIELD_ITEM_USER, user.getId());
        values.put(DBHelper.FIELD_ITEM_CATEGORY, category.getId());

        int updateItem = database.update(DBHelper.TABLE_ITEM, values, DBHelper.FIELD_ITEM_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Item Updated");
        return updateItem > 0;
    }

    public boolean deleteItem(Item item) {
        String itemId = item.getId();
        int deleteItem = database.delete(DBHelper.TABLE_ITEM, DBHelper.FIELD_ITEM_ID + " = ?", new String[]{itemId});

        Log.i("DATABASE", "Item Deleted");
        return deleteItem > 0;
    }

    public Item getItemById(String id) {
        Item item = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM + " WHERE " + DBHelper.FIELD_ITEM_ID + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBCategoryManager categoryManager = new DBCategoryManager(context);
        categoryManager.open();

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String itemId = cursor.getString(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Integer price = cursor.getInt(3);
                Integer stock = cursor.getInt(4);
                Integer rating = cursor.getInt(5);
                Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                User user = userManager.getUserById(cursor.getString(7));
                Category category = categoryManager.getCategoryById(cursor.getString(8));

                item = new Item(itemId, name, description, price, stock, rating, image, user, category);
            }
        }

        cursor.close();
        userManager.close();

        Log.i("DATABASE", "Fetched Item by ID");
        return item;
    }

    public List<Item> getTop(Integer top, Boolean ascending) {
        List<Item> itemList = new ArrayList<>();
        String ORDER = ascending? "ASC" : "DESC";
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM
                + " ORDER BY " + DBHelper.FIELD_ITEM_RATING + " " + ORDER
                + " LIMIT ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBCategoryManager categoryManager = new DBCategoryManager(context);
        categoryManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{top.toString()});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String description = cursor.getString(2);
                    Integer price = cursor.getInt(3);
                    Integer stock = cursor.getInt(4);
                    Integer rating = cursor.getInt(5);
                    Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                    User user = userManager.getUserById(cursor.getString(7));
                    Category category = categoryManager.getCategoryById(cursor.getString(8));

                    itemList.add(new Item(id, name, description, price, stock, rating, image, user, category));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();

        Log.i("DATABASE", "Fetched Top Item List by Rating");
        return itemList;
    }

    public List<Item> getTopSeller(Integer top, Boolean ascending, User user) {
        List<Item> itemList = new ArrayList<>();
        String userId = user.getId();

        String ORDER = ascending? "ASC" : "DESC";
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM
                + " JOIN " + DBHelper.TABLE_USER + " ON "
                + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID + " = "
                + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_USER
                + " WHERE " + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID + " = ?"
                + " ORDER BY " + DBHelper.FIELD_ITEM_RATING + " " + ORDER
                + " LIMIT ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBCategoryManager categoryManager = new DBCategoryManager(context);
        categoryManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{userId, top.toString()});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String description = cursor.getString(2);
                    Integer price = cursor.getInt(3);
                    Integer stock = cursor.getInt(4);
                    Integer rating = cursor.getInt(5);
                    Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                    Category category = categoryManager.getCategoryById(cursor.getString(8));

                    itemList.add(new Item(id, name, description, price, stock, rating, image, user, category));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();

        Log.i("DATABASE", "Fetched Top Item List by Rating");
        return itemList;
    }

    public List<Item> getAllItemsByCategory(Category category) {
        List<Item> itemList = new ArrayList<>();
        String categoryId = category.getId();

        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM
                + " WHERE " + DBHelper.FIELD_ITEM_CATEGORY + " =?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBCategoryManager categoryManager = new DBCategoryManager(context);
        categoryManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{categoryId});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String description = cursor.getString(2);
                    Integer price = cursor.getInt(3);
                    Integer stock = cursor.getInt(4);
                    Integer rating = cursor.getInt(5);
                    Drawable image = new ImageManager(cursor.getBlob(6), this.context).getImage();
                    User user = userManager.getUserById(cursor.getString(7));

                    itemList.add(new Item(id, name, description, price, stock, rating, image, user, category));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();

        Log.i("DATABASE", "Fetched Item List by Category");
        return itemList;
    }
}
