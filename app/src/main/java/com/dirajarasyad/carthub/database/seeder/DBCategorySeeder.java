package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import com.dirajarasyad.carthub.database.manager.DBCategoryManager;

public class DBCategorySeeder {
    DBCategoryManager categoryManager;

    public DBCategorySeeder(Context context) {
        this.categoryManager = new DBCategoryManager(context);
        this.seed();
    }

    private void seed() {
        categoryManager.open();
        categoryManager.addCategory("Electronics");
        categoryManager.addCategory("Clothes");
        categoryManager.addCategory("Foods");
        categoryManager.addCategory("Healths");
        categoryManager.addCategory("Sports");
        categoryManager.addCategory("Books");
        categoryManager.close();
    }
}
