package villainyinc.schemedreams;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by joshuagoldberg on 7/26/16.
 */
public class Cart {

    private static Cart mCart = null;
    private static LinkedList<InventoryItem> mShoppingCart = new LinkedList<>();

    private Cart() {
        mShoppingCart = new LinkedList<InventoryItem>();
    }

    public static Cart getInstance() {
        if(mCart == null){
            mCart = new Cart();
        }
        return mCart;
    }

    public LinkedList<InventoryItem> getShoppingCart() {
        return mShoppingCart;
    }

    public void addItemToCart(InventoryItem item, View view){
        if (DBHelper.getInstance(view.getContext()).getItemCount(item) > 0) {
            mShoppingCart.add(item);
        }
        else {
            Snackbar.make(view, "Unfortunately, that item is out of stock.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    public void removeItemFromCart(InventoryItem item) {
        mShoppingCart.remove(item);
    }

    public void emptyCart() {
        for (int i = mShoppingCart.size()-1; i >= 0; i--) {
            mShoppingCart.remove(i);
        }
    }
}
