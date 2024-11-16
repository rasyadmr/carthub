package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import com.dirajarasyad.carthub.database.manager.DBUserManager;

public class DBUserSeeder {
    DBUserManager userManager;

    public DBUserSeeder(Context context) {
        this.userManager = new DBUserManager(context);
        this.seed();
    }

    private void seed() {
        userManager.open();
        userManager.addUser("rasyadmr", "admin123", "rasyadmr@carthub.com", "081234567890", "Rawa Belong");
        userManager.addUser("diraja", "admin123", "diraja@carthub.com", "080987654321", "Tanah Abang");
        userManager.addUser("seller", "seller123", "seller@carthub.com", "081029384756", "Kebon Jeruk");
        userManager.addUser("guest", "guest123", "guest@carthub.com", "085647382910", "Syahdan");
        userManager.close();
    }
}
