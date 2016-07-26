package villainyinc.schemedreams;

/**
 * Created by joshuagoldberg on 7/25/16.
 */
public class InventoryItem {

    private String mName, mDescription, mCategory;
    private int mImageResId;
    private double mPrice;
    private boolean mOnSale;

    public InventoryItem() {}

    public InventoryItem(String name, String description, String category, double price, int imageResId) {
        mName = name;
        mDescription = description;
        mCategory = category;
        mPrice = price;
        mImageResId = imageResId;
        mOnSale = false;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
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
