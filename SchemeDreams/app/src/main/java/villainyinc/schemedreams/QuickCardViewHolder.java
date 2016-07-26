package villainyinc.schemedreams;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by joshuagoldberg on 7/25/16.
 */
public class QuickCardViewHolder extends RecyclerView.ViewHolder {

    private ImageView mItemImage;
    private TextView mItemPrice;

    public QuickCardViewHolder(View itemView) {
        super(itemView);

        mItemImage = (ImageView) itemView.findViewById(R.id.quick_card_image);
        mItemPrice = (TextView) itemView.findViewById(R.id.quick_card_price);
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        itemView.setOnClickListener(onClickListener);
    }

    public ImageView getItemImage() {
        return mItemImage;
    }

    public TextView getItemPrice() {
        return mItemPrice;
    }
}
