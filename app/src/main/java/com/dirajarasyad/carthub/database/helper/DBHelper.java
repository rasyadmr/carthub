package com.dirajarasyad.carthub.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "carthub";

    // TABLE USER
    public static final String TABLE_USER = "users";
    public static final String FIELD_USER_ID = "id";
    public static final String FIELD_USER_USERNAME = "username";
    public static final String FIELD_USER_PASSWORD = "password";
    public static final String FIELD_USER_EMAIL = "email";
    public static final String FIELD_USER_PHONE = "phonenumber";
    public static final String FIELD_USER_ADDRESS = "address";
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + FIELD_USER_ID + " TEXT PRIMARY KEY,"
            + FIELD_USER_USERNAME + " TEXT NOT NULL UNIQUE,"
            + FIELD_USER_PASSWORD + " TEXT NOT NULL,"
            + FIELD_USER_EMAIL + " TEXT NOT NULL UNIQUE,"
            + FIELD_USER_PHONE + " TEXT NOT NULL,"
            + FIELD_USER_ADDRESS + " TEXT NOT NULL)";

    // TABLE CATEGORY
    public static final String TABLE_CATEGORY = "categories";
    public static final String FIELD_CATEGORY_ID = "id";
    public static final String FIELD_CATEGORY_NAME = "name";
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
            + FIELD_CATEGORY_ID + " TEXT PRIMARY KEY,"
            + FIELD_CATEGORY_NAME + " TEXT NOT NULL UNIQUE)";

    // TABLE ITEM
    public static final String TABLE_ITEM = "items";
    public static final String FIELD_ITEM_ID = "id";
    public static final String FIELD_ITEM_NAME = "name";
    public static final String FIELD_ITEM_DESCRIPTION = "description";
    public static final String FIELD_ITEM_PRICE = "price";
    public static final String FIELD_ITEM_STOCK = "stock";
    public static final String FIELD_ITEM_USER = "userId";
    public static final String FIELD_ITEM_CATEGORY = "categoryId";
    public static final String CREATE_TABLE_ITEM = "CREATE TABLE " + TABLE_ITEM + "("
            + FIELD_ITEM_ID + " TEXT PRIMARY KEY,"
            + FIELD_ITEM_NAME + " TEXT NOT NULL,"
            + FIELD_ITEM_DESCRIPTION + " TEXT,"
            + FIELD_ITEM_PRICE + " INTEGER NOT NULL,"
            + FIELD_ITEM_STOCK + " INTEGER NOT NULL,"
            + FIELD_ITEM_USER + " TEXT NOT NULL,"
            + FIELD_ITEM_CATEGORY + " TEXT NOT NULL,"
            + "FOREIGN KEY (" + FIELD_ITEM_USER + ") REFERENCES " + TABLE_USER + "(" + FIELD_USER_ID + "),"
            + "FOREIGN KEY (" + FIELD_ITEM_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + FIELD_CATEGORY_ID + "))";

    // TABLE CART
    public static final String TABLE_CART = "carts";
    public static final String FIELD_CART_ID = "id";
    public static final String FIELD_CART_USER = "userId";
    public static final String FIELD_CART_ITEM = "item";
    public static final String FIELD_CART_QUANTITY = "quantity";
    public static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + "("
            + FIELD_CART_ID + " TEXT PRIMARY KEY,"
            + FIELD_CART_USER + " TEXT NOT NULL,"
            + FIELD_CART_ITEM + " TEXT NOT NULL,"
            + FIELD_CART_QUANTITY + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + FIELD_CART_USER + ") REFERENCES " + TABLE_USER + "(" + FIELD_USER_ID + "),"
            + "FOREIGN KEY (" + FIELD_CART_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + FIELD_ITEM_ID + "))";

    // TABLE TRANSACTION
    public static final String TABLE_TRANSACTION = "transactions";
    public static final String FIELD_TRANSACTION_ID = "id";
    public static final String FIELD_TRANSACTION_STATUS = "status";
    public static final String FIELD_TRANSACTION_USER = "userId";
    public static final String FIELD_TRANSACTION_ITEM = "item";
    public static final String FIELD_TRANSACTION_QUANTITY = "quantity";
    public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + "("
            + FIELD_TRANSACTION_ID + " TEXT PRIMARY KEY,"
            + FIELD_TRANSACTION_STATUS + " TEXT NOT NULL,"
            + FIELD_TRANSACTION_USER + " TEXT NOT NULL,"
            + FIELD_TRANSACTION_ITEM + " TEXT NOT NULL,"
            + FIELD_TRANSACTION_QUANTITY + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + FIELD_TRANSACTION_USER + ") REFERENCES " + TABLE_USER + "(" + FIELD_USER_ID + "),"
            + "FOREIGN KEY (" + FIELD_TRANSACTION_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + FIELD_ITEM_ID + "))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        Log.i("DATABASE", "Created User Table");
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        Log.i("DATABASE", "Created Category Table");
        sqLiteDatabase.execSQL(CREATE_TABLE_ITEM);
        Log.i("DATABASE", "Created Item Table");
        sqLiteDatabase.execSQL(CREATE_TABLE_CART);
        Log.i("DATABASE", "Created Cart Table");
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);
        Log.i("DATABASE", "Created Transaction Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        Log.i("DATABASE", "Deleted User Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        Log.i("DATABASE", "Deleted Category Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        Log.i("DATABASE", "Deleted Item Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        Log.i("DATABASE", "Deleted Cart Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        Log.i("DATABASE", "Deleted Transaction Table");
        this.onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        Log.i("DATABASE", "Deleted User Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        Log.i("DATABASE", "Deleted Category Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        Log.i("DATABASE", "Deleted Item Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        Log.i("DATABASE", "Deleted Cart Table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        Log.i("DATABASE", "Deleted Transaction Table");
        this.onCreate(sqLiteDatabase);
    }
}
