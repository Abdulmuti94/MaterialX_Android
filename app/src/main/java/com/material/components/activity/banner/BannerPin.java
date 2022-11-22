package com.material.components.activity.banner;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.material.components.R;
import com.material.components.adapter.AdapterGridAlbums;
import com.material.components.data.DataGenerator;
import com.material.components.model.Image;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;
import com.material.components.widget.SpacingItemDecoration;

import java.util.List;

public class BannerPin extends AppCompatActivity {

    private View parent_view;
    private View banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pin);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Banner Pin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {
        banner = findViewById(R.id.banner);
        banner.setVisibility(View.GONE);

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimation.expand(banner);
            }
        }, 1500);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 4), true));
        recyclerView.setHasFixedSize(true);

        List<Image> items = DataGenerator.getImageDate(this);

        //set data and list adapter
        AdapterGridAlbums mAdapter = new AdapterGridAlbums(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridAlbums.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Image obj, int position) {
                if (banner.getVisibility() == View.GONE) {
                    ViewAnimation.expand(banner);
                } else {
                    ViewAnimation.collapse(banner);
                }
            }
        });

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
        } else if (item.getItemId() == R.id.action_search) {
            if (banner.getVisibility() == View.GONE) {
                ViewAnimation.expand(banner);
            } else {
                ViewAnimation.collapse(banner);
            }
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBannerActionClicked(View view) {
        ViewAnimation.collapse(banner);
    }
}