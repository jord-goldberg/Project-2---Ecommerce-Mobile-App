package villainyinc.schemedreams;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by joshuagoldberg on 7/25/16.
 */
public class QuickCardRecyclerAdapter extends RecyclerView.Adapter<QuickCardViewHolder> {

    ArrayList<InventoryItem> mInventoryList;

    public QuickCardRecyclerAdapter(ArrayList<InventoryItem> inventoryList) {
        mInventoryList = inventoryList;
    }

    @Override
    public QuickCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View parentView = inflater.inflate(R.layout.quick_card, parent, false);
        QuickCardViewHolder quickCardViewHolder = new QuickCardViewHolder(parentView);
        return quickCardViewHolder;
    }

    @Override
    public void onBindViewHolder(QuickCardViewHolder holder, int position) {
        InventoryItem item = mInventoryList.get(position);
        holder.getItemImage().setImageResource(item.getImageResId());
        holder.getItemPrice().setText(Double.toString(item.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
