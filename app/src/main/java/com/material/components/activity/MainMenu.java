package com.material.components.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.material.components.BuildConfig;
import com.material.components.R;
import com.material.components.adapter.ExpandableRecyclerAdapter;
import com.material.components.adapter.MnAdapter;
import com.material.components.adapter.MnAdapter.Item;
import com.material.components.adapter.MnSearchAdapter;
import com.material.components.data.MenuGenerator;
import com.material.components.data.SharedPref;
import com.material.components.fcm.ActivityNotifications;
import com.material.components.model.MnType;
import com.material.components.room.ActivityFavorites;
import com.material.components.room.AppDatabase;
import com.material.components.room.DAO;
import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private RecyclerView recycler, search_recycler;
    private MnAdapter adapter;
    private MnSearchAdapter searchAdapter;
    private SharedPref sharedPref;
    private ActionBar actionBar;
    private AppBarLayout appbar_layout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private View navigation_header;

    private View notif_badge;
    private int notification_count = -1;
    private DAO dao;
    List<Item> search_items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = new SharedPref(this);

        dao = AppDatabase.getDb(this).getDAO();

        initToolbar();
        initComponentMenu();
        initDrawerMenu();

        initAds();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Tools.setSystemBarColorInt(this, Color.parseColor("#0A0A0A"));
    }

    private void initComponentMenu() {
        appbar_layout = findViewById(R.id.appbar_layout);

        List<Item> items = MenuGenerator.getItems();
        search_items.clear();
        for (Item i : items) {
            if (i.ItemType == MnType.SUB.getValue()) search_items.add(i);
        }
        recycler = findViewById(R.id.main_recycler);
        adapter = new MnAdapter(this, items, new MnAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Item item) {
                onMenuItemSelected(item);
            }
        });

        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //up
                    animateSearchBar(true);
                } else { // down
                    animateSearchBar(false);
                }
            }
        });

        search_recycler = findViewById(R.id.search_recycler);
        search_recycler.setLayoutManager(new LinearLayoutManager(this));
        search_recycler.setNestedScrollingEnabled(false);
        searchAdapter = new MnSearchAdapter(this, search_items);
        search_recycler.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new MnSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Item obj, int position) {
                onMenuItemSelected(obj);
            }
        });

        search_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //up
                    animateSearchBar(true);
                } else { // down
                    animateSearchBar(false);
                }
            }
        });

        if (sharedPref.isFirstLaunch()) {
            showDialogAbout();
        }
    }

    boolean isSearchBarHide = false;

    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? -(2 * appbar_layout.getHeight()) : 0;
        appbar_layout.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    private void onMenuItemSelected(Item item) {
        if (sharedPref.getClickSwitch()) {
            if (sharedPref.actionClickOffer()) {
                showDialogOffer();
                sharedPref.setClickSwitch(false);
                return;
            }
        } else {
            if (sharedPref.actionClickInters()) {
                boolean istShown = showInterstitial();
                sharedPref.setClickSwitch(true);
                if (istShown) return;
            }
        }

        if (item.Act != null) {
            startActivity(new Intent(this, item.Act));
            return;
        }

        switch (item.Id) {
            case 1:
                showDialogAbout();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        Tools.changeMenuIconColor(menu, Color.WHITE);
        Tools.changeOverflowMenuIconColor(toolbar, Color.WHITE);

        final MenuItem menu_notif = menu.findItem(R.id.action_notifications);
        View actionView = MenuItemCompat.getActionView(menu_notif);
        notif_badge = actionView.findViewById(R.id.notif_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menu_notif);
            }
        });

        MenuItem action_search = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(action_search, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                recycler.setVisibility(View.VISIBLE);
                search_recycler.setVisibility(View.GONE);
                menu_notif.setVisible(true);
                initToolbar();
                initDrawerMenu();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                recycler.setVisibility(View.GONE);
                search_recycler.setVisibility(View.VISIBLE);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setHomeButtonEnabled(false);
                menu_notif.setVisible(false);
                return true;
            }
        });

        return true;
    }

    private void setupBadge() {
        if (notif_badge == null) return;
        if (notification_count == 0) {
            notif_badge.setVisibility(View.GONE);
        } else {
            notif_badge.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.action_notifications) {

        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawerMenu() {
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                updateCounter(nav_view);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                int item_id = item.getItemId();
                if (item_id == R.id.action_portfolio) {
                    Tools.openInAppBrowser(MainMenu.this, "http://portfolio.dream-space.web.id/", false);
                } else if (item_id == R.id.action_notifications) {
                    ActivityNotifications.navigate(MainMenu.this);
                } else if (item_id == R.id.action_favorite) {
                    ActivityFavorites.navigate(MainMenu.this);
                } else if (item_id == R.id.action_rate) {
                    Tools.rateAction(MainMenu.this);
                } else if (item_id == R.id.action_about) {
                    showDialogAbout();
                }
                return true;
            }
        });

        // navigation header
        navigation_header = nav_view.getHeaderView(0);

        TextView tv_new_version = (TextView) navigation_header.findViewById(R.id.tv_new_version);
        ImageButton bt_update = (ImageButton) navigation_header.findViewById(R.id.bt_update);
        tv_new_version.setVisibility(View.GONE);
        bt_update.setVisibility(View.GONE);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.rateAction(MainMenu.this);
            }
        });
    }

    private void updateCounter(NavigationView nav) {
        Menu m = nav.getMenu();
        View drw_notif_badge = (m.findItem(R.id.action_notifications).getActionView().findViewById(R.id.notif_badge));
        if (notification_count == 0) {
            drw_notif_badge.setVisibility(View.GONE);
        } else {
            drw_notif_badge.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int new_notif_count = dao.getNotificationUnreadCount();
        if (new_notif_count != notification_count) {
            notification_count = new_notif_count;
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    private void showDialogAbout() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.tv_version)).setText("Version " + BuildConfig.VERSION_NAME);

        ((View) dialog.findViewById(R.id.bt_getcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://codecanyon.net/user/dream_space/portfolio"));
                startActivity(i);
            }
        });

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_rate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.rateAction(MainMenu.this);
            }
        });

        ((Button) dialog.findViewById(R.id.bt_portfolio)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.openInAppBrowser(MainMenu.this, "http://portfolio.dream-space.web.id/", false);
            }
        });

        sharedPref.setFirstLaunch(false);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showDialogOffer() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_offer);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((View) dialog.findViewById(R.id.bt_getcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://codecanyon.net/user/dream_space/portfolio"));
                startActivity(i);
            }
        });

        sharedPref.setFirstLaunch(false);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    private void initAds() {

    }

    private void showBanner() {

    }

    private void prepareInterstitial() {

    }

    /* show ads */
    public boolean showInterstitial() {
        return true;
    }


}
