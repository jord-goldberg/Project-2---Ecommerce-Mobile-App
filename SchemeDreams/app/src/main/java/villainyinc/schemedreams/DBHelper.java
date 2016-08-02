package villainyinc.schemedreams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by joshuagoldberg on 7/26/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "schemeDreamDB";
    public static final int DATABASE_VERSION = 1;

    public static final String INVENTORY_TABLE_NAME = "inventoryTBL";
    public static final String COL_ITEM_SKU = "itemSKU";
    public static final String COL_ITEM_NAME = "itemName";
    public static final String COL_ITEM_DESCRIPTION = "itemDescription";
    public static final String COL_ITEM_PRICE = "itemPrice";
    public static final String COL_ITEM_IMAGE = "itemImage";
    public static final String COL_ITEM_SALE = "itemOnSale";
    public static final String COL_ITEM_COUNT = "itemCount";

    public static final String CART_TABLE_NAME = "cartTBL";
    public static final String COL_CART_ID = "_id";
    public static final String COL_CART_SKU = "cartSKUs";
    public static final String COL_CART_COUNT = "cartCount";


    private static DBHelper sInstance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + INVENTORY_TABLE_NAME + " (" +
                COL_ITEM_SKU + " TEXT PRIMARY KEY, " +
                COL_ITEM_NAME + " TEXT, " +
                COL_ITEM_DESCRIPTION + " TEXT, " +
                COL_ITEM_PRICE + " REAL, " +
                COL_ITEM_IMAGE + " INTEGER, " +
                COL_ITEM_SALE + " INTEGER, " +
                COL_ITEM_COUNT + " INTEGER)");

        db.execSQL("CREATE TABLE " + CART_TABLE_NAME + " (" +
                COL_CART_ID + " INTEGER PRIMARY KEY, " +
                COL_CART_SKU + " TEXT, " +
                COL_CART_COUNT + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

// Add an inventory Item to the database
    public void insertInventoryItem(InventoryItem item) {

    // first check to see if the associated SKU already exists in the table
        SQLiteDatabase db = getReadableDatabase();
        String sku = item.getSku();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_COUNT},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);

    // if it does, get the current count of that item, add 1 and update the count column
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndex(COL_ITEM_COUNT));
            ContentValues values = new ContentValues();
            values.put(COL_ITEM_COUNT, count+1);
            SQLiteDatabase db2 = getWritableDatabase();
            db2.update(INVENTORY_TABLE_NAME, values, COL_ITEM_SKU + " = ?", new String[]{sku});
            db2.close();
        }

    // if it doesn't, add the values associated with the item to the table
        else {
            SQLiteDatabase db2 = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_ITEM_SKU, item.getSku());
            values.put(COL_ITEM_NAME, item.getName());
            values.put(COL_ITEM_DESCRIPTION, item.getDescription());
            values.put(COL_ITEM_PRICE, item.getPrice());
            values.put(COL_ITEM_IMAGE, item.getImageResId());
            values.put(COL_ITEM_COUNT, 1);

        // SQLite can't store boolean values. If the item is on sale, it's stored as a 1. If not, a 0.
            int onSale;
            if (item.isOnSale()) {
                onSale = 1;
            }
            else {
                onSale = 0;
            }
            values.put(COL_ITEM_SALE, onSale);
            db2.insertOrThrow(INVENTORY_TABLE_NAME, null, values);
            db2.close();
        }
        cursor.close();
    }

    public void removeInventoryItem(InventoryItem item) {

        SQLiteDatabase db = getReadableDatabase();
        String sku = item.getSku();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_COUNT},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getInt(cursor.getColumnIndex(COL_ITEM_COUNT));
        ContentValues values = new ContentValues();
        values.put(COL_ITEM_COUNT, count-1);
        SQLiteDatabase db2 = getWritableDatabase();
        db2.update(INVENTORY_TABLE_NAME, values, COL_ITEM_SKU + " = ?", new String[]{sku});
        db2.close();
    }

    public void addItemToCart(InventoryItem item, View view) {

     // Check to see if the item is in the working inventory
        if (sInstance.getItemCount(item) > 0) {

            // check to see if the associated SKU already exists in the cart
            SQLiteDatabase db = getReadableDatabase();
            String sku = item.getSku();
            Cursor cursor = db.query(CART_TABLE_NAME, new String[]{COL_CART_COUNT},
                    COL_CART_SKU + " = ? ", new String[]{sku}, null, null, null);

            // if it does, get the current count of that item, add 1 and update the count column
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(cursor.getColumnIndex(COL_CART_COUNT));
                ContentValues values = new ContentValues();
                values.put(COL_CART_COUNT, count+1);
                SQLiteDatabase db2 = getWritableDatabase();
                db2.update(CART_TABLE_NAME, values, COL_CART_SKU + " = + ?", new String[]{sku});
                db2.close();
            }

            // if it doesn't, add the values associated with the item to the table
            else {
                SQLiteDatabase db2 = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COL_CART_SKU, item.getSku());
                values.put(COL_CART_COUNT, 1);
                db2.insertOrThrow(CART_TABLE_NAME, null, values);
                db2.close();
            }
            cursor.close();

            // remove item from working inventory
            sInstance.removeInventoryItem(item);
        }

    // if the item is not in the working inventory, let the customer know it's out of stock
        else {
            Snackbar.make(view, "Unfortunately, that item is out of stock.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    public void removeItemFromCart(InventoryItem item) {

        SQLiteDatabase db = getReadableDatabase();
        String sku = item.getSku();
        Cursor cursor = db.query(CART_TABLE_NAME, new String[]{COL_CART_COUNT},
                COL_CART_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getInt(cursor.getColumnIndex(COL_CART_COUNT));
        ContentValues values = new ContentValues();
        values.put(COL_CART_COUNT, count-1);
        SQLiteDatabase db2 = getWritableDatabase();
        db2.update(CART_TABLE_NAME, values, COL_CART_SKU + " = + ?", new String[]{sku});
        db2.close();

    // add the item back to working inventory
        sInstance.insertInventoryItem(item);

    }

    public String getNameFromDB(String sku) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_NAME},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));
        cursor.close();
        return name;
    }

    public String getDescriptionFromDB(String sku) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_DESCRIPTION},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        String description = cursor.getString(cursor.getColumnIndex(COL_ITEM_DESCRIPTION));
        cursor.close();
        return description;
    }

    public int getImageFromDB(String sku) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_IMAGE},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        int image = cursor.getInt(cursor.getColumnIndex(COL_ITEM_IMAGE));
        cursor.close();
        return image;
    }

    public double getPriceFromDB(String sku) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_PRICE},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        double price = cursor.getDouble(cursor.getColumnIndex(COL_ITEM_PRICE));
        cursor.close();
        return price;
    }

    public boolean isOnSaleFromDB(String sku) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SALE},
                COL_ITEM_SKU + " = ? ", new String[]{sku}, null, null, null);
        cursor.moveToFirst();
        switch (cursor.getInt(cursor.getColumnIndex(COL_ITEM_SALE))) {
            default:
            case 0:
                cursor.close();
                return false;
            case 1:
                cursor.close();
                return true;
        }
    }

// Uses a Sku and the previous 5 methods to return an object
    public InventoryItem getItemFromSku (String sku) {
        String name = getNameFromDB(sku);
        String description = getDescriptionFromDB(sku);
        int imageResId = getImageFromDB(sku);
        double price = getPriceFromDB(sku);
        boolean onSale = isOnSaleFromDB(sku);

        return new InventoryItem(name, description, sku, price, imageResId, onSale);
    }

    public ArrayList<InventoryItem> getItemListFromSkuList (ArrayList<String> skuList) {
        ArrayList<InventoryItem> inventoryList = new ArrayList<>();
        for (String sku : skuList) {
            inventoryList.add(sInstance.getItemFromSku(sku));
        }
        return inventoryList;
    }

// Get a list containing the Skus of all InventoryItem objects
    public ArrayList<String> getInventory() {

    // Get a cursor containing all Skus & create a list to return the results
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SKU},
                COL_ITEM_COUNT + " > 0", null, null, null, null);
        ArrayList<String> inventorySkus = new ArrayList<>();

    // Run through the cursor adding a Sku to the list at every position
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                inventorySkus.add(cursor.getString(cursor.getColumnIndex(COL_ITEM_SKU)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return inventorySkus;
    }

// Just like the above method but this takes in a string and searches names and descriptions for matches
    public ArrayList<String> getInventorySearch(String search) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SKU},
                COL_ITEM_NAME + " LIKE ? OR " + COL_ITEM_DESCRIPTION + " LIKE ? ",
                new String[]{"%"+search+"%", "%"+search+"%"}, null, null, null);
        ArrayList<String> inventorySkus = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                inventorySkus.add(cursor.getString(cursor.getColumnIndex(COL_ITEM_SKU)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return inventorySkus;
    }

// Just like the above method but this will show only one category (First 3 digits of Sku represents category)
    public ArrayList<String> getCategory(String first3IntsOfSku) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SKU},
                COL_ITEM_SKU + " LIKE ? ", new String[]{first3IntsOfSku+"%"},
                null, null, null);
        ArrayList<String> inventorySkus = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                inventorySkus.add(cursor.getString(cursor.getColumnIndex(COL_ITEM_SKU)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return inventorySkus;
    }

// Just like the above method but this will show only one product line (Last 3 digits of Sku represents line)
    public ArrayList<String> getProductLine(String last3IntsOfSku) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SKU},
                COL_ITEM_SKU + " LIKE ? ", new String[]{"%"+last3IntsOfSku},
                null, null, null);
        ArrayList<String> inventorySkus = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                inventorySkus.add(cursor.getString(cursor.getColumnIndex(COL_ITEM_SKU)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return inventorySkus;
    }

    public ArrayList<String> getCartSkus() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CART_TABLE_NAME, new String[]{COL_CART_SKU},
                COL_CART_COUNT + " > 0 ", null, null, null, null);
        ArrayList<String> cartSkus = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                cartSkus.add(cursor.getString(cursor.getColumnIndex(COL_CART_SKU)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return cartSkus;
    }

// Returns an int that represents the number of that item in stock
    public int getItemCount(InventoryItem item) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_COUNT},
                COL_ITEM_SKU + " = ? ", new String[]{item.getSku()}, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(COL_ITEM_COUNT));
    }

    public int getCartCount(InventoryItem item) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CART_TABLE_NAME, new String[]{COL_CART_COUNT},
                COL_CART_SKU + " = ? ", new String[]{item.getSku()}, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(COL_CART_COUNT));
    }

    public void checkOut() {
        ContentValues values = new ContentValues();
        values.put(COL_CART_COUNT, 0);
        SQLiteDatabase db = getWritableDatabase();
        db.update(CART_TABLE_NAME, values, null, null);
        db.close();
    }

    public void populateDB() {
        insertInventoryItem(new InventoryItem("Laser Pointer", "Purrfect for playing with cats!", "100100", 14.99, R.drawable.laser_pointer, false));
        insertInventoryItem(new InventoryItem("Pew Pew Pistol", "Pew Pew! For the space cadet villain!", "100200", 349.99, R.drawable.laser_gun, true));
        insertInventoryItem(new InventoryItem("Blaster", "These devices tend to miss. Perfect for the standard henchman!", "100300", 834.95, R.drawable.laser_rifle, false));
        insertInventoryItem(new InventoryItem("Doomsday Device", "Standard issue for the supervillain on the go!", "100400", 21149.99, R.drawable.laser_doomsday, true));
        insertInventoryItem(new InventoryItem("Star Smasher", "Please don't sue me.", "100500", 2350000.99, R.drawable.laser_deathstar, true));

        insertInventoryItem(new InventoryItem("Mountain Hideout", "A cozy mountain chateau for the villain on the go.", "200300", 249999.99, R.drawable.lair_mountain, false));
        insertInventoryItem(new InventoryItem("Island Getaway", "A romantic island escape for you and your princess prisoner", "200200", 175999.99, R.drawable.lair_island, true));
        insertInventoryItem(new InventoryItem("Office Space", "Be a boss that deserves the hate you're sure to get.", "200100", 99999.99, R.drawable.lair_office, false));
        insertInventoryItem(new InventoryItem("Space Office", "Your standard villain HQ... but in space!", "200500", 725999.99, R.drawable.lair_space, true));
        insertInventoryItem(new InventoryItem("Castle Fortress", "Being chased by an army of heroes? Take shelter behind these walls.", "200400", 249999.99, R.drawable.lair_castle, true));

        insertInventoryItem(new InventoryItem("Mouse Trap", "Perfect defense against Mighty Mouse.", "300100", 3.49, R.drawable.trap_mouse, false));
        insertInventoryItem(new InventoryItem("ACME Anvil", "Have a coyote chasing you down? Pick up a few of these!", "300200", 189.95, R.drawable.trap_anvil, false));
        insertInventoryItem(new InventoryItem("Flame Jet", "Broil your enemies or a Filet Mignon!", "300300", 209.95, R.drawable.trap_fire, true));
        insertInventoryItem(new InventoryItem("Spike Pit", "Careful not to accidentally fall into this one.", "300400", 409.95, R.drawable.trap_spikes, true));
        insertInventoryItem(new InventoryItem("Acid Bath", "Exfoliate x1000", "300500", 1029.95, R.drawable.trap_acid, true));

        insertInventoryItem(new InventoryItem("Help Desk Henchman", "Have you tried turning it off and on again?", "400100", 64999.99, R.drawable.henchman_computer, false));
        insertInventoryItem(new InventoryItem("Mutant Marauder", "Oog smash good!", "400300", 19999.99, R.drawable.henchman_mutant, true));
        insertInventoryItem(new InventoryItem("Gangster Cronie", "Eh, sleep with the fishes, see?", "400200", 89999.99, R.drawable.henchman_gangster, true));
        insertInventoryItem(new InventoryItem("Infantry Grunt", "They used to fight for freedom; now they fight for you!", "400500", 99999.99, R.drawable.henchman_soldier, false));
        insertInventoryItem(new InventoryItem("K9 Doggo", "A big ol' pupper", "400400", 24999.99, R.drawable.henchman_dog, true));

        insertInventoryItem(new InventoryItem("Flashcards", "Need to practice your Monologuing discreetly? Pick these up!", "500200", 1009.95, R.drawable.monologue_flashcard, false));
        insertInventoryItem(new InventoryItem("Pen & Paper", "For the villain who hates to change", "500100", 999.95, R.drawable.monologue_paper, false));
        insertInventoryItem(new InventoryItem("Audio Book", "Are you an auditory learner? Pick up your monologue on tape!", "500300", 1019.95, R.drawable.monologue_audio, false));
        insertInventoryItem(new InventoryItem("Typewritten", "Facing hipster heroes? They'll love your typewritten monologue!", "500400", 1049.95, R.drawable.monologue_typewriter, false));
        insertInventoryItem(new InventoryItem("Kindle/eBook", "For the modern schemer", "500500", 1149.95, R.drawable.monologue_kindle, false));
    }
}