package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.model.Cart;
import com.dirajarasyad.carthub.model.Item;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBCartManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBCartManager(Context context) {
        this.context = context;
    }

    public DBCartManager open() {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void addCart(User user, Item item, Integer quantity) {
        String id = "CART-" + UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_CART_ID, id);
        values.put(DBHelper.FIELD_CART_USER, user.getId());
        values.put(DBHelper.FIELD_CART_ITEM, item.getId());
        values.put(DBHelper.FIELD_CART_QUANTITY, quantity);

        database.insert(DBHelper.TABLE_CART, null, values);
        Log.i("DATABASE", "Cart Created");
    }

    public List<Cart> getAllCarts() {
        List<Cart> cartList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_CART;

        DBUserManager userManager = new DBUserManager(context); // TODO Try not using open and close
        DBItemManager itemManager = new DBItemManager(context); // TODO Try not using open and close

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    User user = userManager.getUserById(cursor.getString(1));
                    Item item = itemManager.getItemById(cursor.getString(2));
                    Integer quantity = cursor.getInt(3);

                    cartList.add(new Cart(id, user, item, quantity));
                } while (cursor.moveToNext());
            }
        }

        Log.i("DATABASE", "Fetched Cart List");
        return cartList;
    }

    public boolean updateCart(String id, User user, Item item, Integer quantity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_CART_USER, user.getId());
        values.put(DBHelper.FIELD_CART_ITEM, item.getId());
        values.put(DBHelper.FIELD_CART_QUANTITY, quantity);

        int updateCart = database.update(DBHelper.TABLE_CART, values, DBHelper.FIELD_CART_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Cart Updated");
        return updateCart > 0; // true -> Success, false -> Fail
    }

    public boolean deleteCart(String id) {
        int deleteCart = database.delete(DBHelper.TABLE_CART, DBHelper.FIELD_CART_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Cart Deleted");
        return deleteCart > 0;
    }

    public Cart getCartById(String id) {
        Cart cart = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_CART + " WHERE " + DBHelper.FIELD_CART_ID + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});

        DBUserManager userManager = new DBUserManager(context);
        DBItemManager itemManager = new DBItemManager(context);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String cartId = cursor.getString(0);
                User user = userManager.getUserById(cursor.getString(1));
                Item item = itemManager.getItemById(cursor.getString(2));
                Integer quantity = cursor.getInt(3);

                cart = new Cart(cartId, user, item, quantity);
            }
        }

        cursor.close();

        Log.i("DATABASE", "Fetched Cart by ID");
        return cart;
    }
}
