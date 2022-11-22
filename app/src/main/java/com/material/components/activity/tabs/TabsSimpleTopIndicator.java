package com.material.components.activity.tabs;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.material.components.R;
import com.material.components.utils.Tools;

public class TabsSimpleTopIndicator extends AppCompatActivity {

    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_simple_top_indicator);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_notes);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_80), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {
        // display images
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.images_1), R.drawable.image_12);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.images_2), R.drawable.image_13);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.images_3), R.drawable.image_14);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.images_4), R.drawable.image_15);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.images_5), R.drawable.image_26);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.images_6), R.drawable.image_30);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_80));
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