package com.material.components.activity.bottomsheet;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.material.components.R;
import com.material.components.adapter.AdapterGridMusicCardAlbums;
import com.material.components.data.DataGenerator;
import com.material.components.model.MusicAlbum;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;
import com.material.components.widget.SpacingItemDecoration;

import java.util.List;

public class BottomSheetPlayer extends AppCompatActivity {

    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterGridMusicCardAlbums mAdapter;
    private View bottom_sheet;
    private BottomSheetBehavior mBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_player);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Album");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {
        parent_view = findViewById(R.id.parent_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 4), true));
        recyclerView.setHasFixedSize(true);

        List<MusicAlbum> items = DataGenerator.getMusicAlbum(this);

        //set data and list adapter
        mAdapter = new AdapterGridMusicCardAlbums(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridMusicCardAlbums.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MusicAlbum obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    ViewAnimation.rotate(findViewById(R.id.bt_expand), true);
                } else {
                    ViewAnimation.rotate(findViewById(R.id.bt_expand), false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        (findViewById(R.id.bt_expand)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ViewAnimation.rotate(v, true);
                } else {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    ViewAnimation.rotate(v, false);
                }
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                ViewAnimation.rotate(findViewById(R.id.bt_expand), false);
            }
        }, 1000);

        Toast.makeText(this, "Swipe Up player indicator", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}