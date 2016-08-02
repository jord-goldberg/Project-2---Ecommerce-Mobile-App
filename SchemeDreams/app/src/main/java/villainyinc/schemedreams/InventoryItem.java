package villainyinc.schemedreams;

/**
 * Created by joshuagoldberg on 7/25/16.
 */
public class InventoryItem {

    // The sku should be a 6 digit number stored in a string.
    // The first 3 digits denote category. The last 3 digits denote the number in that category
    private String mSku;
    private String mName, mDescription;
    private int mImageResId;
    private double mPrice;
    private boolean mOnSale;

    public InventoryItem() {}

    public InventoryItem(String name, String description, String sku, double price, int imageResId, boolean onSale) {
        mName = name;
        mDescription = description;
        mSku = sku;
        mPrice = price;
        mImageResId = imageResId;
        mOnSale = onSale;
    }

    public String getSku() {
        return mSku;
    }

    public void setSku(String sku) {
        this.mSku = sku;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public void setImageResId(int imageResId) {
        this.mImageResId = imageResId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public boolean isOnSale() {
        return mOnSale;
    }

    public void setOnSale(boolean onSale) {
        this.mOnSale = onSale;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }
}
