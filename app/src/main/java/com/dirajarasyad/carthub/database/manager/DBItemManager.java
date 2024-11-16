package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.model.Item;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBItemManager {
    private DBHelper dbHelper;
    private Context context;
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

    public void addItem(String name, String description, Integer price, Integer stock, User user) {
        String id = "ITEM-" + UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_ITEM_ID, id);
        values.put(DBHelper.FIELD_ITEM_NAME, name);
        values.put(DBHelper.FIELD_ITEM_DESCRIPTION, description);
        values.put(DBHelper.FIELD_ITEM_PRICE, price);
        values.put(DBHelper.FIELD_ITEM_STOCK, stock);
        values.put(DBHelper.FIELD_ITEM_USER, user.getId());

        database.insert(DBHelper.TABLE_ITEM, null, values);
        Log.i("DATABASE", "Item Created");
    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM;

        DBUserManager userManager = new DBUserManager(context); // TODO Try not using open and close

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String description = cursor.getString(2);
                    Integer price = cursor.getInt(3);
                    Integer stock = cursor.getInt(4);
                    User seller = userManager.getUserById(cursor.getString(5));

                    itemList.add(new Item(id, name, description, price, stock, seller));
                } while (cursor.moveToNext());
            }
        }

        Log.i("DATABASE", "Fetched Item List");
        return itemList;
    }

    public boolean updateItem(String id, String name, String description, Integer price, Integer stock, User user) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_ITEM_NAME, name);
        values.put(DBHelper.FIELD_ITEM_DESCRIPTION, description);
        values.put(DBHelper.FIELD_ITEM_PRICE, price);
        values.put(DBHelper.FIELD_ITEM_STOCK, stock);
        values.put(DBHelper.FIELD_ITEM_USER, user.getId());

        int updateItem = database.update(DBHelper.TABLE_ITEM, values, DBHelper.FIELD_ITEM_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Item Updated");
        return updateItem > 0; // true -> Success, false -> Fail
    }

    public boolean deleteItem(String id) {
        int deleteItem = database.delete(DBHelper.TABLE_ITEM, DBHelper.FIELD_ITEM_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Item Deleted");
        return deleteItem > 0;
    }

    public Item getItemById(String id) {
        Item item = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_ITEM + " WHERE " + DBHelper.FIELD_ITEM_ID + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});

        DBUserManager userManager = new DBUserManager(context);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String itemId = cursor.getString(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Integer price = cursor.getInt(3);
                Integer stock = cursor.getInt(4);
                User seller = userManager.getUserById(cursor.getString(5));

                item = new Item(itemId, name, description, price, stock, seller);
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched Item by ID");
        return item;
    }
}
