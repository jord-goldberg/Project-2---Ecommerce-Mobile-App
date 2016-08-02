package villainyinc.schemedreams;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    CardView mCheckOut;
    RecyclerView mCartRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final DBHelper db = DBHelper.getInstance(this);

        mCartRecycler = (RecyclerView) findViewById(R.id.cart_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        final CartRecyclerAdapter adapter = new CartRecyclerAdapter(db.getItemListFromSkuList(db.getCartSkus()));
        mCartRecycler.setLayoutManager(layoutManager);
        mCartRecycler.setAdapter(adapter);

        mCheckOut = (CardView) findViewById(R.id.checkOut);
        mCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!adapter.mInventoryList.isEmpty()) {
                    double total = 0;
                    for (InventoryItem item : db.getItemListFromSkuList(db.getCartSkus())) {
                        total += (item.getPrice() * db.getCartCount(item));
                    }
                    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Confirm Checkout");
                    builder.setMessage("Your total is $" + formatter.format(total) +
                            "\n\nAre you sure you'd like to check out?\n");

                    builder.setPositiveButton("Send me my stuff!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBHelper.getInstance(view.getContext()).checkOut();
                            adapter.mInventoryList.clear();
                            adapter.notifyDataSetChanged();
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
            }
        });

    }
}
