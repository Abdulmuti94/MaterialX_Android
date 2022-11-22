package com.material.components.activity.banner;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.material.components.R;
import com.material.components.adapter.AdapterListNews;
import com.material.components.data.DataGenerator;
import com.material.components.model.News;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;

import java.util.List;

public class BannerWarning extends AppCompatActivity {

    private View parent_view;
    private View banner;

    private RecyclerView recyclerView;
    private AdapterListNews mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_warning);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Warning");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.orange_700);
    }

    private void initComponent() {
        banner = findViewById(R.id.banner);
        banner.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<News> items = DataGenerator.getNewsData(this, 10);

        //set data and list adapter
        mAdapter = new AdapterListNews(this, items, R.layout.item_news_card);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListNews.OnItemClickListener() {
            @Override
            public void onItemClick(View view, News obj, int position) {
                if (banner.getVisibility() == View.GONE) {
                    ViewAnimation.expand(banner);
                } else {
                    ViewAnimation.collapse(banner);
                }
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimation.expand(banner);
            }
        }, 1500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite_setting, menu);
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

    public void onBannerActionClicked(View view) {
        ViewAnimation.collapse(banner);
    }
}