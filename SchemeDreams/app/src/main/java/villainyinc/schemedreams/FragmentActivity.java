package villainyinc.schemedreams;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class FragmentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Checks to see if the a fragment exists, if it doesn't it adds one. If it does, it replaces it. Doesn't work exaclty like I want it to.
        if (mSearchFragment == null) {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                String query = getIntent().getStringExtra(SearchManager.QUERY);
                mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                        this.getApplicationContext()).getInventorySearch(query));
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                mSearchFragment)
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .disallowAddToBackStack()
                        .commit();
            }
            else {
                mSearchFragment = SearchFragment.newInstance(getIntent().getStringArrayListExtra(StoreHomeActivity.SEARCH_QUERY));
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                mSearchFragment)
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .disallowAddToBackStack()
                        .commit();
            }
        }
        else {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                String query = getIntent().getStringExtra(SearchManager.QUERY);
                mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                        this.getApplicationContext()).getInventorySearch(query));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                mSearchFragment)
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .disallowAddToBackStack()
                        .commit();
            }
            else {
                mSearchFragment = SearchFragment.newInstance(getIntent().getStringArrayListExtra(StoreHomeActivity.SEARCH_QUERY));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                mSearchFragment)
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .disallowAddToBackStack()
                        .commit();
            }

        }



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
        getMenuInflater().inflate(R.menu.fragment_activity, menu);

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
        int id = item.getItemId();

        if (id == R.id.nav_lasers) {
            mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                    this.getApplicationContext()).getCategory("100"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            mSearchFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .commit();
        } else if (id == R.id.nav_lairs) {
            mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                    this.getApplicationContext()).getCategory("200"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            mSearchFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .commit();

        } else if (id == R.id.nav_traps) {
            mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                    this.getApplicationContext()).getCategory("300"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            mSearchFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .commit();

        } else if (id == R.id.nav_henchmen) {
            mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                    this.getApplicationContext()).getCategory("400"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            mSearchFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .commit();

        } else if (id == R.id.nav_monologues) {
            mSearchFragment = SearchFragment.newInstance(DBHelper.getInstance(
                    this.getApplicationContext()).getCategory("500"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            mSearchFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .commit();

        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(this, StoreHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
