package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;
import com.dirajarasyad.carthub.model.Category;

public class DBCategorySeeder {
    private final DBCategoryManager categoryManager;
    private Context context;

    public DBCategorySeeder(Context context) {
        this.categoryManager = new DBCategoryManager(context);
        this.context = context;
        this.seed();
    }

    private void seed() {
        categoryManager.open();
        categoryManager.addCategory("Electronics", AppCompatResources.getDrawable(context, R.drawable.baseline_home_24));
        categoryManager.addCategory("Clothes", AppCompatResources.getDrawable(context, R.drawable.carthub_logo_only));
        categoryManager.addCategory("Foods", AppCompatResources.getDrawable(context, R.drawable.carthub_logo_only));
        categoryManager.addCategory("Healths", AppCompatResources.getDrawable(context, R.drawable.carthub_logo_only));
        categoryManager.addCategory("Sports", AppCompatResources.getDrawable(context, R.drawable.carthub_logo_only));
        categoryManager.addCategory("Books", AppCompatResources.getDrawable(context, R.drawable.carthub_logo_only));
        categoryManager.close();
    }
}
