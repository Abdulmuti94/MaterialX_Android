package com.material.components.activity.backdrop;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.material.components.R;
import com.material.components.utils.BackdropViewAnimation;
import com.material.components.utils.Tools;

public class BackdropBasic extends AppCompatActivity {

    private BackdropViewAnimation backdropAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backdrop_basic);

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_100_), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.yellow_700);
        Tools.setSystemBarLight(this);

        backdropAnimation = new BackdropViewAnimation(
                this, findViewById(R.id.backdrop_view), findViewById(R.id.product_grid),
                R.drawable.ic_menu, R.drawable.ic_close, R.color.grey_100_
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backdropAnimation.toggle(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_100_));
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