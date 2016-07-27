package villainyinc.schemedreams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public static final String ORDERS_TABLE_NAME = "ordersTBL";
    public static final String COL_ORDER_ID = "_id";
    public static final String COL_ORDER_SKUS = "orderSKUs";


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

        db.execSQL("CREATE TABLE " + ORDERS_TABLE_NAME + " (" +
                COL_ORDER_ID + " INTEGER PRIMARY KEY, " +
                COL_ORDER_SKUS + " TEXT)");
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
            int count = cursor.getColumnIndex(COL_ITEM_COUNT);
            ContentValues values = new ContentValues();
            values.put(COL_ITEM_COUNT, count+1);
            SQLiteDatabase db2 = getWritableDatabase();
            db2.update(INVENTORY_TABLE_NAME, values, COL_ITEM_SKU + " = + ?", new String[]{sku});
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

// Add purchase order with list of purchased SKUs to the database
    public void insertOrder(LinkedList<InventoryItem> cart) {

    // Store purchased SKUs in string separated by commas
        String orderSkus = "";
        for (int i = 0; i < cart.size(); i++) {
            orderSkus += cart.get(i).getSku() + ",";
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_SKUS, orderSkus);
        db.insertOrThrow(ORDERS_TABLE_NAME, null, values);
        db.close();

    // Empty the cart list after storing the info in the database
        Cart.getInstance().emptyCart();
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

// Get a list containing the Skus of all InventoryItem objects
    public ArrayList<String> getInventory() {

    // Get a cursor containing all Skus & create a list to return the results
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SKU},
                null, null, null, null, null);
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

    public ArrayList<String> getCategoryOnSale(String first3IntsOfSku) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_SKU},
                COL_ITEM_SKU + " LIKE ? AND " + COL_ITEM_SALE + " = ?", new String[]{first3IntsOfSku+"%", "1"},
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

// Returns an int that represents the number of that item in stock
    public int getItemCount(InventoryItem item) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(INVENTORY_TABLE_NAME, new String[]{COL_ITEM_COUNT},
                COL_ITEM_SKU + " = ? ", new String[]{item.getSku()}, null, null, null);
        return cursor.getInt(cursor.getColumnIndex(COL_ITEM_COUNT));
    }
}