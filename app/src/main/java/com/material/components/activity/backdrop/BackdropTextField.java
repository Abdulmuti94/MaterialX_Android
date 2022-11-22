package com.material.components.activity.backdrop;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.material.components.R;
import com.material.components.adapter.AdapterListNews;
import com.material.components.data.DataGenerator;
import com.material.components.model.News;
import com.material.components.utils.BackdropViewAnimation;
import com.material.components.utils.Tools;

import java.util.List;

public class BackdropTextField extends AppCompatActivity {

    private BackdropViewAnimation backdropAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backdrop_text_field);

        initComponent();
    }

    private void initComponent() {
        Tools.setSystemBarColor(this, R.color.deep_purple_700);
        TabLayout tab_layout = findViewById(R.id.tab_layout);

        backdropAnimation = new BackdropViewAnimation(this, findViewById(R.id.backdrop_view), findViewById(R.id.content));
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab_layout.getSelectedTabPosition() == 0 && !backdropAnimation.isBackdropShown()) {
                    backdropAnimation.open();
                } else {
                    backdropAnimation.close();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backdropAnimation.open();
            }
        }, 1000);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<News> items = DataGenerator.getNewsData(this, 10);

        //set data and list adapter
        AdapterListNews mAdapter = new AdapterListNews(this, items, R.layout.item_news_horizontal);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListNews.OnItemClickListener() {
            @Override
            public void onItemClick(View view, News obj, int position) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more_exit, menu);
        Tools.changeMenuIconColor(menu, Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backdropAnimation.isBackdropShown()) {
            backdropAnimation.close();
        } else {
            super.onBackPressed();
        }
    }
}