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
        InventoryItem item = mInventoryList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        holder.getItemImage().setImageResource(item.getImageResId());
        holder.getItemName().setText(item.getName());
        holder.getItemPrice().setText("$"+formatter.format(item.getPrice()));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mInventoryList.size();
    }
}
