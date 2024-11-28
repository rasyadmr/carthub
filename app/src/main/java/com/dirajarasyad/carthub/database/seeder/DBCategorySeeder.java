package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;

public class DBCategorySeeder {
    private final DBCategoryManager categoryManager;
    private final Context context;

    public DBCategorySeeder(Context context) {
        this.categoryManager = new DBCategoryManager(context);
        this.context = context;
        this.seed();
    }

    private void seed() {
        categoryManager.open();
        categoryManager.addCategory("Electronics", AppCompatResources.getDrawable(context, R.drawable.electronic_icon));
        categoryManager.addCategory("Clothes", AppCompatResources.getDrawable(context, R.drawable.clothe_icon));
        categoryManager.addCategory("Foods", AppCompatResources.getDrawable(context, R.drawable.food_icon));
        categoryManager.addCategory("Healths", AppCompatResources.getDrawable(context, R.drawable.health_icon));
        categoryManager.addCategory("Sports", AppCompatResources.getDrawable(context, R.drawable.sport_icon));
        categoryManager.addCategory("Books", AppCompatResources.getDrawable(context, R.drawable.book_icon));
        categoryManager.addCategory("Furniture", AppCompatResources.getDrawable(context, R.drawable.furniture_icon));
        categoryManager.addCategory("Toys & Games", AppCompatResources.getDrawable(context, R.drawable.games_icon));
        categoryManager.close();
    }
}
