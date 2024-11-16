package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.model.Cart;
import com.dirajarasyad.carthub.model.Item;
import com.dirajarasyad.carthub.model.Transaction;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBTransactionManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBTransactionManager(Context context) {
        this.context = context;
    }

    public DBTransactionManager open() {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void addTransaction(String status, User user, Item item, Integer quantity) {
        String id = "TRX-" + UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_TRANSACTION_ID, id);
        values.put(DBHelper.FIELD_TRANSACTION_STATUS, status);
        values.put(DBHelper.FIELD_TRANSACTION_USER, user.getId());
        values.put(DBHelper.FIELD_TRANSACTION_ITEM, item.getId());
        values.put(DBHelper.FIELD_CART_QUANTITY, quantity);

        database.insert(DBHelper.TABLE_TRANSACTION, null, values);
        Log.i("DATABASE", "Transaction Created");
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION;

        DBUserManager userManager = new DBUserManager(context); // TODO Try not using open and close
        DBItemManager itemManager = new DBItemManager(context); // TODO Try not using open and close

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String status = cursor.getString(1);
                    User user = userManager.getUserById(cursor.getString(2));
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);

                    transactionList.add(new Transaction(id, status, user, item, quantity));
                } while (cursor.moveToNext());
            }
        }

        Log.i("DATABASE", "Fetched Transaction List");
        return transactionList;
    }

    public boolean updateTransaction(String id, String status, User user, Item item, Integer quantity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_TRANSACTION_STATUS, status);
        values.put(DBHelper.FIELD_TRANSACTION_USER, user.getId());
        values.put(DBHelper.FIELD_TRANSACTION_ITEM, item.getId());
        values.put(DBHelper.FIELD_TRANSACTION_QUANTITY, quantity);

        int updateTransaction = database.update(DBHelper.TABLE_TRANSACTION, values, DBHelper.FIELD_TRANSACTION_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Transaction Updated");
        return updateTransaction > 0; // true -> Success, false -> Fail
    }

    public boolean deleteTransaction(String id) {
        int deleteTransaction = database.delete(DBHelper.TABLE_TRANSACTION, DBHelper.FIELD_TRANSACTION_ID + " = ?", new String[]{id});

        Log.i("DATABASE", "Transaction Deleted");
        return deleteTransaction > 0;
    }

    public Transaction getTransactionById(String id) {
        Transaction transaction = null;
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION + " WHERE " + DBHelper.FIELD_TRANSACTION_ID + " = ?";

        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});

        DBUserManager userManager = new DBUserManager(context);
        DBItemManager itemManager = new DBItemManager(context);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String transactionId = cursor.getString(0);
                String status = cursor.getString(1);
                User user = userManager.getUserById(cursor.getString(2));
                Item item = itemManager.getItemById(cursor.getString(3));
                Integer quantity = cursor.getInt(4);

                transaction = new Transaction(transactionId, status, user, item, quantity);
            }
        }

        cursor.close();
        Log.i("DATABASE", "Fetched Transaction by ID");
        return transaction;
    }

    public List<Transaction> getSellerTransactionList(String userId) {
        List<Transaction> transactionList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION
                + " JOIN " + DBHelper.TABLE_ITEM + " ON "
                + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_ITEM + " = "
                + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_ID
                + " JOIN " + DBHelper.TABLE_USER + " ON "
                + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_ID + " = "
                + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID;

        DBUserManager userManager = new DBUserManager(context); // TODO Try not using open and close
        DBItemManager itemManager = new DBItemManager(context); // TODO Try not using open and close

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String status = cursor.getString(1);
                    User user = userManager.getUserById(cursor.getString(2));
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);

                    transactionList.add(new Transaction(id, status, user, item, quantity));
                } while (cursor.moveToNext());
            }
        }

        Log.i("DATABASE", "Fetched Transaction by Seller");
        return transactionList;
    }
}