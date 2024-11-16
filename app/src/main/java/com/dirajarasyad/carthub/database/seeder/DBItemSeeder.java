package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;

public class DBItemSeeder {
    DBItemManager itemManager;
    DBUserManager userManager;

    public DBItemSeeder(Context context) {
        this.itemManager = new DBItemManager(context);
        this.userManager = new DBUserManager(context);
        this.seed();
    }

    private void seed() {
        itemManager.open();
        userManager.open();
        itemManager.addItem("Bakso Bakar", "Bakso dibakar selama 10 menit", 2000, 100, userManager.getUserByUsername("seller"));
        itemManager.addItem("Tahu Balado", "Tahu goreng diberi saus balado", 10000, 50, userManager.getUserByUsername("seller"));
        itemManager.addItem("Pizza Nugget", "Pizza yang diberi topping nugget", 20000, 20, userManager.getUserByUsername("seller"));
        itemManager.addItem("Es Teh Manis", "Teh manis yang diberi es", 5000, 70, userManager.getUserByUsername("seller"));
        itemManager.close();
        userManager.close();
    }
}
