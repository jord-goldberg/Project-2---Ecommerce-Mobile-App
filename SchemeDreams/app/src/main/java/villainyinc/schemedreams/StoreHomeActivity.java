package villainyinc.schemedreams;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class StoreHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SEARCH_QUERY = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DBHelper db = DBHelper.getInstance(this);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra(SEARCH_QUERY, db.getInventorySearch(query));
            startActivity(intent);
        }

// checks to see if the database is empty. If so, it fills it.
        if (db.getInventory().isEmpty()) {
            db.populateDB();
            db.populateDB();
            db.populateDB();
            db.populateDB();
            db.populateDB();
        }

// Instantiates the viewflipper from content_store_home and sets animations for switching views
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

// Sets up the 2 recyclerViews
        RecyclerView categoryRecycler1 = (RecyclerView) findViewById(R.id.homeRecycler_1);
        RecyclerView categoryRecycler3 = (RecyclerView) findViewById(R.id.homeRecycler_3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        categoryRecycler1.setLayoutManager(layoutManager);
        categoryRecycler3.setLayoutManager(layoutManager3);
        QuickCardRecyclerAdapter cardRecyclerAdapter1 = new QuickCardRecyclerAdapter(db.getItemListFromSkuList(db.getProductLine("100")));
        QuickCardRecyclerAdapter cardRecyclerAdapter3 = new QuickCardRecyclerAdapter(db.getItemListFromSkuList(db.getProductLine("500")));
        categoryRecycler1.setAdapter(cardRecyclerAdapter1);
        categoryRecycler3.setAdapter(cardRecyclerAdapter3);

// Rotates the text on the first advertisement
        TextView bookLeft = (TextView) findViewById(R.id.book_left);
        TextView bookRight = (TextView) findViewById(R.id.book_right);
        RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.rotate_text);
        bookLeft.setAnimation(rotate);
        bookRight.setAnimation(rotate);

        CardView advert1 = (CardView) findViewById(R.id.advertisement_1);
        advert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreHomeActivity.this, FragmentActivity.class);
                intent.putStringArrayListExtra(SEARCH_QUERY, db.getCategory("500"));
                startActivity(intent);
            }
        });

        CardView advert2 = (CardView) findViewById(R.id.advertisement_2);
        advert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreHomeActivity.this, FragmentActivity.class);
                intent.putStringArrayListExtra(SEARCH_QUERY, db.getInventory());
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_home, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        ComponentName componentName = new ComponentName(this, FragmentActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DBHelper db = DBHelper.getInstance(this.getApplicationContext());
        int id = item.getItemId();

        if (id == R.id.nav_lasers) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra(SEARCH_QUERY, db.getCategory("100"));
            startActivity(intent);
        } else if (id == R.id.nav_lairs) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra(SEARCH_QUERY, db.getCategory("200"));
            startActivity(intent);
        } else if (id == R.id.nav_traps) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra(SEARCH_QUERY, db.getCategory("300"));
            startActivity(intent);
        } else if (id == R.id.nav_henchmen) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra(SEARCH_QUERY, db.getCategory("400"));
            startActivity(intent);
        } else if (id == R.id.nav_monologues) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra(SEARCH_QUERY, db.getCategory("500"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
