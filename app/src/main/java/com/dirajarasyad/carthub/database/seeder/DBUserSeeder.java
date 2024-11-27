package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBUserManager;

public class DBUserSeeder {
    private final DBUserManager userManager;
    private final Context context;

    public DBUserSeeder(Context context) {
        this.userManager = new DBUserManager(context);
        this.context = context;
        this.seed();
    }

    private void seed() {
        userManager.open();

        // Admin
        userManager.addUser("rasyadmr", "admin123", "rasyadmr@carthub.com", "081234567890", "Rawa Belong", AppCompatResources.getDrawable(context, R.drawable.baseline_person_24));
        userManager.addUser("diraja", "admin123", "diraja@carthub.com", "080987654321", "Tanah Abang", AppCompatResources.getDrawable(context, R.drawable.baseline_person_24));

        // Seller
        userManager.addUser("seller", "seller123", "seller@carthub.com", "081029384756", "Kebon Jeruk", AppCompatResources.getDrawable(context, R.drawable.baseline_person_24));

        // Guest
        userManager.addUser("guest", "guest123", "guest@carthub.com", "085647382910", "Syahdan", AppCompatResources.getDrawable(context, R.drawable.baseline_person_24));
        userManager.close();
    }
}
