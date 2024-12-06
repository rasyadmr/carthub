package com.dirajarasyad.carthub.database.seeder;

import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;

public class DBItemSeeder {
    private final DBItemManager itemManager;
    private final DBUserManager userManager;
    private final DBCategoryManager categoryManager;
    private final Context context;

    public DBItemSeeder(Context context) {
        this.context = context;
        this.itemManager = new DBItemManager(context);
        this.userManager = new DBUserManager(context);
        this.categoryManager = new DBCategoryManager(context);
        this.seed();
    }

    private void seed() {
        itemManager.open();
        userManager.open();
        categoryManager.open();

        // Electronics
        itemManager.addItem("Smartphone X", "High-end smartphone with 5G capability", 12500000, 50, 5, AppCompatResources.getDrawable(context, R.drawable.electronic_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Electronics"), "Jl. Moh. Jam No.1, Kp. Baru, Kec. Baiturrahman, Kota Banda Aceh, Aceh");
        itemManager.addItem("Laptop Pro", "15-inch laptop for professionals", 15000000, 30, 4, AppCompatResources.getDrawable(context, R.drawable.electronic_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Electronics"), "Jl. Moh. Jam No.1, Kp. Baru, Kec. Baiturrahman, Kota Banda Aceh, Aceh");
        itemManager.addItem("Wireless Headphones", "Bluetooth headphones with noise cancelling", 2500000, 100, 4, AppCompatResources.getDrawable(context, R.drawable.electronic_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Electronics"), "Jl. Moh. Jam No.1, Kp. Baru, Kec. Baiturrahman, Kota Banda Aceh, Aceh");
        itemManager.addItem("Smart TV", "55-inch 4K Smart TV", 8000000, 20, 5, AppCompatResources.getDrawable(context, R.drawable.electronic_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Electronics"), "Jl. Moh. Jam No.1, Kp. Baru, Kec. Baiturrahman, Kota Banda Aceh, Aceh");
        itemManager.addItem("Gaming Console", "Next-gen gaming console", 7500000, 25, 4, AppCompatResources.getDrawable(context, R.drawable.electronic_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Electronics"), "Jl. Moh. Jam No.1, Kp. Baru, Kec. Baiturrahman, Kota Banda Aceh, Aceh");

        // Clothes
        itemManager.addItem("Sport Shoes", "Running shoes size 42", 1500000, 200, 5, AppCompatResources.getDrawable(context, R.drawable.clothe_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Clothes"), "Jl. Pintu Satu Senayan, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270");
        itemManager.addItem("Jeans", "Blue denim jeans", 500000, 300, 3, AppCompatResources.getDrawable(context, R.drawable.clothe_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Clothes"), "Jl. Pintu Satu Senayan, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270");
        itemManager.addItem("T-Shirt", "Cotton casual t-shirt", 200000, 150, 4, AppCompatResources.getDrawable(context, R.drawable.clothe_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Clothes"), "Jl. Pintu Satu Senayan, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270");
        itemManager.addItem("Winter Jacket", "Warm winter jacket", 1000000, 100, 4, AppCompatResources.getDrawable(context, R.drawable.clothe_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Clothes"), "Jl. Pintu Satu Senayan, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270");
        itemManager.addItem("Sport Set", "Complete sportswear set", 800000, 120, 5, AppCompatResources.getDrawable(context, R.drawable.clothe_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Clothes"), "Jl. Pintu Satu Senayan, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270");

        // Foods
        itemManager.addItem("Rice", "Premium rice 5kg", 150000, 500, 4, AppCompatResources.getDrawable(context, R.drawable.food_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Foods"), "Merdeka Square, Jakarta, Jalan Lapangan Monas, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110");
        itemManager.addItem("Instant Noodles", "Pack of 40 instant noodles", 100000, 1000, 4, AppCompatResources.getDrawable(context, R.drawable.food_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Foods"), "Merdeka Square, Jakarta, Jalan Lapangan Monas, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110");
        itemManager.addItem("Cooking Oil", "Vegetable cooking oil 2L", 50000, 300, 1, AppCompatResources.getDrawable(context, R.drawable.food_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Foods"), "Merdeka Square, Jakarta, Jalan Lapangan Monas, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110");
        itemManager.addItem("Snack Pack", "Assorted snacks bundle", 75000, 400, 3, AppCompatResources.getDrawable(context, R.drawable.food_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Foods"), "Merdeka Square, Jakarta, Jalan Lapangan Monas, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110");
        itemManager.addItem("Coffee Beans", "Premium coffee beans 1kg", 200000, 250, 4, AppCompatResources.getDrawable(context, R.drawable.food_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Foods"), "Merdeka Square, Jakarta, Jalan Lapangan Monas, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110");

        // Books
        itemManager.addItem("Novel", "Bestseller novel hardcover", 250000, 1000, 5, AppCompatResources.getDrawable(context, R.drawable.book_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Books"), "Jl. Raya Darmaga Kampus IPB, Babakan, Kec. Dramaga, Kabupaten Bogor, Jawa Barat 16680");
        itemManager.addItem("Study Book", "University textbook", 400000, 800, 1, AppCompatResources.getDrawable(context, R.drawable.book_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Books"), "Jl. Raya Darmaga Kampus IPB, Babakan, Kec. Dramaga, Kabupaten Bogor, Jawa Barat 16680");
        itemManager.addItem("Comic Book", "Popular manga volume", 100000, 200, 2, AppCompatResources.getDrawable(context, R.drawable.book_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Books"), "Jl. Raya Darmaga Kampus IPB, Babakan, Kec. Dramaga, Kabupaten Bogor, Jawa Barat 16680");
        itemManager.addItem("Dictionary", "English-Indonesian dictionary", 350000, 150, 1, AppCompatResources.getDrawable(context, R.drawable.book_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Books"), "Jl. Raya Darmaga Kampus IPB, Babakan, Kec. Dramaga, Kabupaten Bogor, Jawa Barat 16680");
        itemManager.addItem("Magazine", "Monthly lifestyle magazine", 50000, 300, 4, AppCompatResources.getDrawable(context, R.drawable.book_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Books"), "Jl. Raya Darmaga Kampus IPB, Babakan, Kec. Dramaga, Kabupaten Bogor, Jawa Barat 16680");

        // Healths
        itemManager.addItem("Multivitamin", "Daily multivitamin 60 tablets", 180000, 1000, 5, AppCompatResources.getDrawable(context, R.drawable.health_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Healths"), "Jl. Kyai H. Syahdan No.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11480");
        itemManager.addItem("Face Mask", "Surgical face masks 50pcs", 50000, 500, 5, AppCompatResources.getDrawable(context, R.drawable.health_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Healths"), "Jl. Kyai H. Syahdan No.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11480");
        itemManager.addItem("Hand Sanitizer", "500ml hand sanitizer", 40000, 200, 5, AppCompatResources.getDrawable(context, R.drawable.health_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Healths"), "Jl. Kyai H. Syahdan No.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11480");
        itemManager.addItem("First Aid Kit", "Complete emergency kit", 250000, 300, 3, AppCompatResources.getDrawable(context, R.drawable.health_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Healths"), "Jl. Kyai H. Syahdan No.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11480");
        itemManager.addItem("Health Supplements", "Immune booster supplements", 150000, 400, 4, AppCompatResources.getDrawable(context, R.drawable.health_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Healths"), "Jl. Kyai H. Syahdan No.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11480");

        // Sports
        itemManager.addItem("Basketball", "Standard size basketball", 300000, 400, 5, AppCompatResources.getDrawable(context, R.drawable.games_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Sports"), "Jl. Raya Kb. Jeruk No.27, RT.1/RW.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530");
        itemManager.addItem("Badminton Set", "Racket and shuttlecock set", 450000, 100, 5, AppCompatResources.getDrawable(context, R.drawable.games_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Sports"), "Jl. Raya Kb. Jeruk No.27, RT.1/RW.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530");
        itemManager.addItem("Yoga Mat", "Exercise yoga mat", 200000, 300, 3, AppCompatResources.getDrawable(context, R.drawable.games_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Sports"), "Jl. Raya Kb. Jeruk No.27, RT.1/RW.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530");
        itemManager.addItem("Football", "Official size football", 350000, 250, 4, AppCompatResources.getDrawable(context, R.drawable.games_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Sports"), "Jl. Raya Kb. Jeruk No.27, RT.1/RW.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530");
        itemManager.addItem("Dumbbells", "5kg dumbbells pair", 400000, 50, 5, AppCompatResources.getDrawable(context, R.drawable.games_icon), userManager.getUserByUsername("seller"), categoryManager.getCategoryByName("Sports"), "Jl. Raya Kb. Jeruk No.27, RT.1/RW.9, Kemanggisan, Kec. Palmerah, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530");

        itemManager.close();
        userManager.close();
        categoryManager.close();
    }
}
