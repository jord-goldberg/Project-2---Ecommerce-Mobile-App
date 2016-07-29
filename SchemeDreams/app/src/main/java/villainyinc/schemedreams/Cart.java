package villainyinc.schemedreams;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by joshuagoldberg on 7/26/16.
 */
public class Cart {

    private static Cart mCart = null;
    private static ArrayList<InventoryItem> mShoppingCart = new ArrayList<>();

    private Cart() {
        mShoppingCart = new ArrayList<InventoryItem>();
    }

    public static Cart getInstance() {
        if(mCart == null){
            mCart = new Cart();
        }
        return mCart;
    }

    public ArrayList<InventoryItem> getShoppingCart() {
        return mShoppingCart;
    }

    public void addItemToCart(InventoryItem item, View view){

        if (DBHelper.getInstance(view.getContext()).getItemCount(item) > 0) {
            mShoppingCart.add(item);
            DBHelper.getInstance(view.getContext()).removeInventoryItem(item);
        }
        else {
            Snackbar.make(view, "Unfortunately, that item is out of stock.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    public void removeItemFromCart(InventoryItem item, View view) {
        mShoppingCart.remove(item);
        DBHelper.getInstance(view.getContext()).insertInventoryItem(item);
    }

    public void emptyCart() {
        for (int i = mShoppingCart.size()-1; i >= 0; i--) {
            mShoppingCart.remove(i);
        }
    }
}
