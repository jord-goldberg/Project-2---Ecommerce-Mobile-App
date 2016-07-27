package villainyinc.schemedreams;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class StoreHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DBHelper db = DBHelper.getInstance(this);

        if (db.getInventory().isEmpty()) {
            db.insertInventoryItem(new InventoryItem("Laser Pointer", "", "100100", 14.99, R.drawable.laser_pointer, false));
            db.insertInventoryItem(new InventoryItem("Laser Pistol", "", "100200", 349.99, R.drawable.laser_gun, true));
            db.insertInventoryItem(new InventoryItem("Laser Rifle", "", "100300", 834.95, R.drawable.laser_rifle, false));
            db.insertInventoryItem(new InventoryItem("Doomsday Laser", "", "100400", 21149, R.drawable.laser_doomsday, true));
            db.insertInventoryItem(new InventoryItem("Anti-Planet Laser", "", "100500", 2350000, R.drawable.laser_deathstar, true));

            db.insertInventoryItem(new InventoryItem("Mountain Lair", "", "200300", 250000, R.drawable.lair_mountain, false));
            db.insertInventoryItem(new InventoryItem("Island Lair", "", "200200", 175000, R.drawable.lair_island, true));
            db.insertInventoryItem(new InventoryItem("Office Lair", "", "200100", 90000, R.drawable.lair_office, false));
            db.insertInventoryItem(new InventoryItem("Space Lair", "", "200500", 725000, R.drawable.lair_space, true));
            db.insertInventoryItem(new InventoryItem("Castle Lair", "", "200400", 250000, R.drawable.lair_castle, true));

            db.insertInventoryItem(new InventoryItem("Mouse Trap", "", "300100", 3.49, R.drawable.trap_mouse, false));
            db.insertInventoryItem(new InventoryItem("Anvil Trap", "", "300200", 189.99, R.drawable.trap_anvil, false));
            db.insertInventoryItem(new InventoryItem("Fire Trap", "", "300300", 209.99, R.drawable.trap_fire, true));
            db.insertInventoryItem(new InventoryItem("Spike Trap", "", "300400", 409.49, R.drawable.trap_spikes, true));
            db.insertInventoryItem(new InventoryItem("Acid Trap", "", "300500", 1029.95, R.drawable.trap_acid, true));

            db.insertInventoryItem(new InventoryItem("IT Henchman", "", "400100", 65000, R.drawable.henchman_computer, false));
            db.insertInventoryItem(new InventoryItem("Mutant Henchman", "", "400300", 20000, R.drawable.henchman_mutant, true));
            db.insertInventoryItem(new InventoryItem("Gangster Henchman", "", "400200", 90000, R.drawable.henchman_gangster, true));
            db.insertInventoryItem(new InventoryItem("Soldier Henchman", "", "400400", 100000, R.drawable.henchman_soldier, false));
            db.insertInventoryItem(new InventoryItem("K9 Henchman", "", "400500", 25000, R.drawable.henchman_dog, true));

            db.insertInventoryItem(new InventoryItem("Flashcard Monologue", "", "500100", 1010, R.drawable.monologue_flashcard, false));
            db.insertInventoryItem(new InventoryItem("Paper Monologue", "", "500200", 1000, R.drawable.monologue_paper, false));
            db.insertInventoryItem(new InventoryItem("Audio Monologue", "", "500300", 1015, R.drawable.monologue_audio, false));
            db.insertInventoryItem(new InventoryItem("Typewriter Monologue", "", "500400", 1050, R.drawable.monologue_typewriter, false));
            db.insertInventoryItem(new InventoryItem("Kindle Monologue", "", "500500", 1150, R.drawable.monologue_kindle, false));
        }

//Instantiates the viewflipper from content_store_home and sets animations for switching views
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

//Instantiates RecyclerView and sets a horizontal layout managaer
        RecyclerView categoryRecycler1 = (RecyclerView) findViewById(R.id.homeRecycler_1);
        RecyclerView categoryRecycler2 = (RecyclerView) findViewById(R.id.homeRecycler_2);
        RecyclerView categoryRecycler3 = (RecyclerView) findViewById(R.id.homeRecycler_3);
        RecyclerView categoryRecycler4 = (RecyclerView) findViewById(R.id.homeRecycler_4);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);


        categoryRecycler1.setLayoutManager(layoutManager);
        categoryRecycler2.setLayoutManager(layoutManager2);
        categoryRecycler3.setLayoutManager(layoutManager3);
        categoryRecycler4.setLayoutManager(layoutManager4);

//        QuickCardRecyclerAdapter cardRecyclerAdapter1 = new QuickCardRecyclerAdapter(db.getCategoryOnSale("100"));
//        QuickCardRecyclerAdapter cardRecyclerAdapter2 = new QuickCardRecyclerAdapter(db.getCategoryOnSale("200"));
//        QuickCardRecyclerAdapter cardRecyclerAdapter3 = new QuickCardRecyclerAdapter(db.getCategoryOnSale("300"));
//        QuickCardRecyclerAdapter cardRecyclerAdapter4 = new QuickCardRecyclerAdapter(db.getCategoryOnSale("400"));
//
//        categoryRecycler1.setAdapter(cardRecyclerAdapter1);
//        categoryRecycler2.setAdapter(cardRecyclerAdapter2);
//        categoryRecycler3.setAdapter(cardRecyclerAdapter3);
//        categoryRecycler4.setAdapter(cardRecyclerAdapter4);

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            intent.putStringArrayListExtra("category", db.getCategory("100"));
            startActivity(intent);
        } else if (id == R.id.nav_lairs) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra("category", db.getCategory("200"));
            startActivity(intent);
        } else if (id == R.id.nav_traps) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra("category", db.getCategory("300"));
            startActivity(intent);
        } else if (id == R.id.nav_henchmen) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra("category", db.getCategory("400"));
            startActivity(intent);
        } else if (id == R.id.nav_monologues) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putStringArrayListExtra("category", db.getCategory("500"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
