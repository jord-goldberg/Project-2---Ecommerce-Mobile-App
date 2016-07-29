package villainyinc.schemedreams;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    CardView mCheckOut;
    TextView mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView cartRecycler = (RecyclerView) findViewById(R.id.cart_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        CartRecyclerAdapter adapter = new CartRecyclerAdapter(Cart.getInstance().getShoppingCart());
        cartRecycler.setLayoutManager(layoutManager);
        cartRecycler.setAdapter(adapter);

        double total = 0;
        for (InventoryItem item : Cart.getInstance().getShoppingCart()) {
            total += item.getPrice();
        }

        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        mTotal = (TextView) findViewById(R.id.total_tv);
        mTotal.setText("Total: $" + formatter.format(total));
    }
}
