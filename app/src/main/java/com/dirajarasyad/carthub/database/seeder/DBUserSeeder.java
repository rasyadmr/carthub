package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

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
        userManager.addUser("rasyadmr", "admin123", "rasyadmr@carthub.com", "081234567890",
                "Lorem ipsum dolor sit amet consectetur adipisicing elit. Illum maiores impedit delectus harum nesciunt dolorum, eum iure possimus saepe nemo sit nam quibusdam dignissimos nobis ad corporis? Doloribus omnis suscipit asperiores, ut dicta inventore eius laudantium amet rerum expedita quo aut quasi ab. Iure, voluptatum! Voluptatum quasi amet vel obcaecati quod incidunt illum consequuntur deleniti consectetur quisquam magnam, aut debitis repellendus tempora necessitatibus perspiciatis exercitationem ut doloribus corporis magni numquam. Labore, nemo? Consectetur voluptas temporibus sapiente consequatur id architecto sit non eaque possimus omnis porro expedita, esse delectus in quaerat. Quisquam, consequatur explicabo eius nulla saepe iure ratione veritatis? Repellat!", AppCompatResources.getDrawable(context, R.drawable.baseline_person_100), User.Role.ADMIN);
        userManager.addUser("diraja", "admin123", "diraja@carthub.com", "080987654321", "Tanah Abang", AppCompatResources.getDrawable(context, R.drawable.baseline_person_100), User.Role.ADMIN);

        // Seller
        userManager.addUser("seller", "seller123", "seller@carthub.com", "081029384756", "Kebon Jeruk", AppCompatResources.getDrawable(context, R.drawable.baseline_person_100), User.Role.SELLER);

        // Requested
        userManager.addUser("request", "request123", "request@carthub.com", "082944728140", "Jalan K. H. Syahdan", AppCompatResources.getDrawable(context, R.drawable.baseline_person_100), User.Role.REQUESTED);

        // Normal
        userManager.addUser("guest", "guest123", "guest@carthub.com", "085647382910", "Syahdan", AppCompatResources.getDrawable(context, R.drawable.baseline_person_100), User.Role.NORMAL);
        userManager.close();
    }
}
