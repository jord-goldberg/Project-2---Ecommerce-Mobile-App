package villainyinc.schemedreams;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by joshuagoldberg on 7/28/16.
 */
public class CartRecyclerAdapter extends RecyclerView.Adapter<CartViewHolder> {

    ArrayList<InventoryItem> mInventoryList;

    public CartRecyclerAdapter(ArrayList<InventoryItem> inventoryList) {
        mInventoryList = inventoryList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View parentView = inflater.inflate(R.layout.card_cart, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(parentView);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final InventoryItem item = mInventoryList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        final DBHelper db = DBHelper.getInstance(holder.getDecrease().getContext());
        holder.getItemImage().setImageResource(item.getImageResId());
        holder.getItemCount().setText(Integer.toString(db.getCartCount(item)));
        holder.getItemName().setText(item.getName());
        holder.getItemPrice().setText("$"+formatter.format(item.getPrice() * db.getCartCount(item)));
        holder.getIncrease().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addItemToCart(item, view);
                notifyDataSetChanged();
            }
        });
        holder.getDecrease().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.removeItemFromCart(item);
                notifyDataSetChanged();
                if (db.getCartCount(item) == 0) {
                    mInventoryList.remove(item);
                    notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mInventoryList.size();
    }
}
