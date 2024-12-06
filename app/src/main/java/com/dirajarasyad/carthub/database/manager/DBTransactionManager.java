package com.dirajarasyad.carthub.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dirajarasyad.carthub.database.helper.DBHelper;
import com.dirajarasyad.carthub.manager.DatetimeManager;
import com.dirajarasyad.carthub.model.Item;
import com.dirajarasyad.carthub.model.Transaction;
import com.dirajarasyad.carthub.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBTransactionManager {
    private DBHelper dbHelper;
    private final Context context;
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

    public void addTransaction(Transaction.Status status, User user, Item item, Integer quantity) {
        String id = "TRX-" + UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_TRANSACTION_ID, id);
        values.put(DBHelper.FIELD_TRANSACTION_STATUS, status.value());
        values.put(DBHelper.FIELD_TRANSACTION_USER, user.getId());
        values.put(DBHelper.FIELD_TRANSACTION_ITEM, item.getId());
        values.put(DBHelper.FIELD_TRANSACTION_QUANTITY, quantity);
        values.put(DBHelper.FIELD_TRANSACTION_CREATED_AT, new DatetimeManager(LocalDateTime.now()).getDatetime());
        values.put(DBHelper.FIELD_TRANSACTION_UPDATED_AT, new DatetimeManager(LocalDateTime.now()).getDatetime());

        database.insert(DBHelper.TABLE_TRANSACTION, null, values);
        Log.i("DATABASE", "Transaction Created");
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION;

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Transaction.Status status = Transaction.Status.fromString(cursor.getString(1));
                    User user = userManager.getUserById(cursor.getString(2));
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);
                    LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                    transactionList.add(new Transaction(id, status, user, item, quantity, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction List");
        return transactionList;
    }

    public boolean updateTransaction(String id, Transaction.Status status, User user, Item item, Integer quantity, LocalDateTime createdAt) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FIELD_TRANSACTION_STATUS, status.value());
        values.put(DBHelper.FIELD_TRANSACTION_USER, user.getId());
        values.put(DBHelper.FIELD_TRANSACTION_ITEM, item.getId());
        values.put(DBHelper.FIELD_TRANSACTION_QUANTITY, quantity);
        values.put(DBHelper.FIELD_TRANSACTION_CREATED_AT, new DatetimeManager(createdAt).getDatetime());
        values.put(DBHelper.FIELD_TRANSACTION_UPDATED_AT, new DatetimeManager(LocalDateTime.now()).getDatetime());

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
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String transactionId = cursor.getString(0);
                Transaction.Status status = Transaction.Status.fromString(cursor.getString(1));
                User user = userManager.getUserById(cursor.getString(2));
                Item item = itemManager.getItemById(cursor.getString(3));
                Integer quantity = cursor.getInt(4);
                LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                transaction = new Transaction(transactionId, status, user, item, quantity, created, updated);
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction by ID");
        return transaction;
    }

    public List<Transaction> getUserTransactionList(User user) {
        List<Transaction> transactionList = new ArrayList<>();
        String userId = user.getId();

        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION
                + " JOIN " + DBHelper.TABLE_USER + " ON "
                + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_USER + " = "
                + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID
                + " WHERE " + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID
                + " = ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{userId});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Transaction.Status status = Transaction.Status.fromString(cursor.getString(1));
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);
                    LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                    transactionList.add(new Transaction(id, status, user, item, quantity, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction by User");
        return transactionList;
    }

    public List<Transaction> getSellerTransactionList(User user) {
        List<Transaction> transactionList = new ArrayList<>();
        String userId = user.getId();

        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION
                + " JOIN " + DBHelper.TABLE_ITEM + " ON "
                + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_ITEM + " = "
                + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_ID
                + " JOIN " + DBHelper.TABLE_USER + " ON "
                + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_ID + " = "
                + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID
                + " WHERE " + DBHelper.TABLE_USER + "." + DBHelper.FIELD_USER_ID + " = ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{userId});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Transaction.Status status = Transaction.Status.fromString(cursor.getString(1));
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);
                    LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                    transactionList.add(new Transaction(id, status, user, item, quantity, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction by User (Seller)");
        return transactionList;
    }

    public List<Transaction> getAllTransactionsByItem(Item item) {
        List<Transaction> transactionList = new ArrayList<>();
        String itemId = item.getId();
        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION
                + " WHERE " + DBHelper.FIELD_TRANSACTION_ITEM + " = ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{itemId});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Transaction.Status status = Transaction.Status.fromString(cursor.getString(1));
                    User user = userManager.getUserById(cursor.getString(2));
                    Integer quantity = cursor.getInt(4);
                    LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                    transactionList.add(new Transaction(id, status, user, item, quantity, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction List by Item");
        return transactionList;
    }

    public List<Transaction> getUserAndStatusTransactionList(User user, Transaction.Status status) {
        List<Transaction> transactionList = new ArrayList<>();
        String userId = user.getId();
        String statusValue = status.value();

        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION
                + " WHERE " + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_USER + " = ? "
                + " AND " + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_STATUS + " = ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{userId, statusValue});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);
                    LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                    transactionList.add(new Transaction(id, status, user, item, quantity, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction List by Item");
        return transactionList;
    }

    public List<Transaction> getSellerAndStatusTransactionList(User user, Transaction.Status status) {
        List<Transaction> transactionList = new ArrayList<>();
        String userId = user.getId();
        String statusValue = status.value();

        String rawQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION
                + " JOIN " + DBHelper.TABLE_ITEM + " ON "
                + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_ITEM + " = " + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_ID
                + " WHERE " + DBHelper.TABLE_ITEM + "." + DBHelper.FIELD_ITEM_USER + " = ? "
                + " AND " + DBHelper.TABLE_TRANSACTION + "." + DBHelper.FIELD_TRANSACTION_STATUS + " = ?";

        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        DBItemManager itemManager = new DBItemManager(context);
        itemManager.open();

        Cursor cursor = database.rawQuery(rawQuery, new String[]{userId, statusValue});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Item item = itemManager.getItemById(cursor.getString(3));
                    Integer quantity = cursor.getInt(4);
                    LocalDateTime created = new DatetimeManager(cursor.getString(5)).getLDT();
                    LocalDateTime updated = new DatetimeManager(cursor.getString(6)).getLDT();

                    transactionList.add(new Transaction(id, status, user, item, quantity, created, updated));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        userManager.close();
        itemManager.close();

        Log.i("DATABASE", "Fetched Transaction List by Item");
        return transactionList;
    }
}
