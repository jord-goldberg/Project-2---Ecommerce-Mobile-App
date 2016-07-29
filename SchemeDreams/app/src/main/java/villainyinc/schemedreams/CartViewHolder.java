package villainyinc.schemedreams;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by joshuagoldberg on 7/28/16.
 */
public class CartViewHolder extends RecyclerView.ViewHolder {

    private ImageView mItemImage, mIncrease, mDecrease;
    private TextView mItemPrice, mItemName, mItemCount;

    public CartViewHolder(View itemView) {
        super(itemView);

        mItemImage = (ImageView) itemView.findViewById(R.id.cart_card_image);
        mIncrease = (ImageView) itemView.findViewById(R.id.cart_increase);
        mDecrease = (ImageView) itemView.findViewById(R.id.cart_decrease);
        mItemName = (TextView) itemView.findViewById(R.id.cart_card_name);
        mItemPrice = (TextView) itemView.findViewById(R.id.cart_card_price);
        mItemCount = (TextView) itemView.findViewById(R.id.cart_count);


    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        itemView.setOnClickListener(onClickListener);
    }

    public ImageView getItemImage() {
        return mItemImage;
    }

    public TextView getItemName() {
        return mItemName;
    }

    public TextView getItemPrice() {
        return mItemPrice;
    }

    public ImageView getDecrease() {
        return mDecrease;
    }

    public ImageView getIncrease() {
        return mIncrease;
    }

    public TextView getItemCount() {
        return mItemCount;
    }
}
