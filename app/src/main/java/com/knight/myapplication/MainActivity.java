package com.knight.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.knight.myapplication.categories.AboutActivity;
import com.knight.myapplication.categories.ApparelsActivity;
import com.knight.myapplication.categories.CartActivity;
import com.knight.myapplication.categories.EducationActivity;
import com.knight.myapplication.categories.ElectronicsActivity;
import com.knight.myapplication.categories.FavouritesActivity;
import com.knight.myapplication.categories.HelpCenterActivity;
import com.knight.myapplication.categories.LocationGrabActivity;
import com.knight.myapplication.categories.MyAccountActivity;
import com.knight.myapplication.categories.NotificationsActivity;
import com.knight.myapplication.categories.OffersActivity;
import com.knight.myapplication.categories.RestaurantsActivity;
import com.knight.myapplication.mFragments.ApparelsFragment;
import com.knight.myapplication.mFragments.EducationFragment;
import com.knight.myapplication.mFragments.ElectronicsFragment;
import com.knight.myapplication.mFragments.MyPagerAdapter;
import com.knight.myapplication.mFragments.OffersFragment;
import com.knight.myapplication.mFragments.RestaurantsFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ViewPager vp;
    TabLayout tabLayout;
    MaterialSearchView searchView;
    ListView search_list;
    ArrayList<String> mSearchData = new ArrayList<>();
    private Firebase mRef;
    ArrayAdapter adapter;
    EditText searchEdittext;
    private ArrayAdapter<String> listAdapter;
    ArrayList<String> listViewAdapterContent = new ArrayList<>();









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Material Search");
        //toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        Firebase.setAndroidContext(this);


        /*Firebase

        mRef = new Firebase("https://fir-listview-c1643.firebaseio.com/mall_list");
       //Search Bar Init


        search_list = (ListView)findViewById(R.id.search_list);
        //Firebase Retrieval

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mSearchData.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mSearchData);
        search_list.setAdapter(adapter);

        searchView = (MaterialSearchView) findViewById(R.id.editText);





        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                search_list = (ListView)findViewById(R.id.search_list);
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mSearchData);
                search_list.setAdapter(adapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()){
                    List<String> lstFound = new ArrayList<String>();

                    for(String item : mSearchData){

                        if (item.contains(newText))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, lstFound);
                    search_list.setAdapter(adapter);
                }
                else {
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mSearchData);
                    search_list.setAdapter(adapter);
                }
                return true;
            }
        });

*/
        searchEdittext=(EditText)findViewById(R.id.editText);
        final ListView searchResult=(ListView)findViewById(R.id.listView);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listViewAdapterContent);
        searchResult.setAdapter(listAdapter);
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.frameLayout);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResult.setVisibility(View.GONE);
            }
        });


        searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.this.listAdapter.getFilter().filter(charSequence);
                searchResult.setVisibility(View.VISIBLE);


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchEdittext.getText().toString().equals(""))
                    searchResult.setVisibility(View.GONE);
            }
        });



        mRef = new Firebase("https://fir-listview-c1643.firebaseio.com/mall_list/");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);

                listViewAdapterContent.add(value);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //String[] listViewAdapterContent = {"School", "House", "Building", "Food", "Sports", "Dress", "Ring"};






//Rest


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        vp = (ViewPager) findViewById(R.id.mViewpager_ID);
        addPages(vp);
        tabLayout = (TabLayout) findViewById(R.id.mTab_ID);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setOnTabSelectedListener(this);

        //Timer Function for slider
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

    }




    private void addPages(ViewPager viewPager){

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());
        pagerAdapter.addFragment(new OffersFragment(),"Offers");
        pagerAdapter.addFragment(new ApparelsFragment(),"Apparels");
        pagerAdapter.addFragment(new EducationFragment(),"Education");
        pagerAdapter.addFragment(new ElectronicsFragment(),"Electronics");
        pagerAdapter.addFragment(new RestaurantsFragment(),"Restaurants");

        //ViewPager
        viewPager.setAdapter(pagerAdapter);


        //TabLayout




    }

    //For Tabbed View on Home Screen

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
// For Slider Auto slide

    public class MyTimerTask extends TimerTask {


        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {

                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.location_grab){

            Intent intent = new Intent(this, LocationGrabActivity.class);
            startActivity(intent);

        }

        if(id == R.id.notification_icon){

            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
        }

        if(id == R.id.cart_icon){

            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {





        getMenuInflater().inflate(R.menu.common_menu, menu);

        //For search Bar



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //@SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edu) {
            Intent intent = new Intent(this, EducationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_electronics) {

            Intent intent = new Intent(this, ElectronicsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_rest) {

            Intent intent = new Intent(this, RestaurantsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cloth) {

            Intent intent = new Intent(this, ApparelsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_offers) {

            Intent intent = new Intent(this, OffersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_wishlist) {

            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_account) {

            Intent intent = new Intent(this, MyAccountActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {

            Intent intent = new Intent(this, HelpCenterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

            //INSERT LOGOUT FUNCTION

        } else if (id == R.id.nav_about) {

            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
