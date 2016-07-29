package villainyinc.schemedreams;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
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
        final InventoryItem item = mInventoryList.get(position);
        final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        holder.getItemImage().setImageResource(item.getImageResId());
        holder.getItemPrice().setText("$"+formatter.format(item.getPrice()));
        holder.getItemName().setText(item.getName());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View view2 = inflater.inflate(R.layout.detail_dialog, null);
                builder.setView(view2);

                ImageView image = (ImageView) view2.findViewById(R.id.detail_image);
                TextView name = (TextView) view2.findViewById(R.id.detail_name);
                TextView price = (TextView) view2.findViewById(R.id.detail_price);
                TextView description = (TextView) view2.findViewById(R.id.detail_description);

                name.setText(item.getName());
                image.setImageResource(item.getImageResId());
                price.setText("$"+formatter.format(item.getPrice()));
                description.setText(item.getDescription());

                builder.setPositiveButton("Add to Cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cart.getInstance().addItemToCart(item, view);
                    }
                });
                builder.setNegativeButton("Continue Shopping", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInventoryList.size();
    }
}
