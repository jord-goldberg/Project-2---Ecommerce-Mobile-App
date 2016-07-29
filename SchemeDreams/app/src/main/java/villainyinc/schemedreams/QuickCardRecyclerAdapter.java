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
        final InventoryItem item = mInventoryList.get(position);
        final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        holder.getItemImage().setImageResource(item.getImageResId());
        holder.getItemPrice().setText("$"+formatter.format(item.getPrice()));
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
