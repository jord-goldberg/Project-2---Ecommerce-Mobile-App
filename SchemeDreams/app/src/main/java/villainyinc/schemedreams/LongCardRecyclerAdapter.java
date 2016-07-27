package villainyinc.schemedreams;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by joshuagoldberg on 7/27/16.
 */
public class LongCardRecyclerAdapter extends RecyclerView.Adapter<LongCardViewHolder> {

    ArrayList<InventoryItem> mInventoryList;

    public LongCardRecyclerAdapter(ArrayList<InventoryItem> inventoryList) {
        mInventoryList = inventoryList;
    }

    @Override
    public LongCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View parentView = inflater.inflate(R.layout.card_long, parent, false);
        LongCardViewHolder longCardViewHolder = new LongCardViewHolder(parentView);
        return longCardViewHolder;
    }

    @Override
    public void onBindViewHolder(LongCardViewHolder holder, int position) {
        InventoryItem item = mInventoryList.get(position);
        holder.getItemImage().setImageResource(item.getImageResId());
        holder.getItemPrice().setText("$"+Double.toString(item.getPrice()));
        holder.getItemName().setText(item.getName());
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
