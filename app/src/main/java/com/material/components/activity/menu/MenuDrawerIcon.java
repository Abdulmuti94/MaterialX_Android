package com.material.components.activity.menu;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.navigation.NavigationView;
import com.material.components.R;
import com.material.components.utils.Tools;

public class MenuDrawerIcon extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;
    private NavigationView nav_view;
    private CoordinatorLayout main_content;
    private boolean isHide = true;
    private int animation_duration = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_icon);

        initToolbar();
        initNavigationMenu();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Icon Admin");
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void initNavigationMenu() {
        nav_view = findViewById(R.id.nav_view);
        nav_view.post(new Runnable() {
            @Override
            public void run() {
                nav_view.setTranslationX(-nav_view.getWidth());
            }
        });

        main_content = findViewById(R.id.main_content);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide) {
                    showMenu(nav_view, main_content);
                } else {
                    hideMenu(nav_view, main_content);
                }
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showMenu(nav_view, main_content);
            }
        }, 1000);
    }

    public ObjectAnimator hideMenu(View menu_view, View content_view) {
        isHide = true;
        // menu animation
        ObjectAnimator animation = ObjectAnimator.ofFloat(menu_view, "translationX", -menu_view.getWidth());
        animation.setDuration(300);
        animation.start();

        // content animation
        ObjectAnimator animationContent = ObjectAnimator.ofFloat(content_view, "translationX", 0);
        animationContent.setDuration(300);
        animationContent.start();

        return animation;
    }

    public ObjectAnimator showMenu(View menu_view, View content_view) {
        isHide = false;
        ObjectAnimator animation = ObjectAnimator.ofFloat(menu_view, "translationX", 0);
        animation.setDuration(animation_duration);
        animation.start();

        // content animation
        ObjectAnimator animationContent = ObjectAnimator.ofFloat(content_view, "translationX", menu_view.getWidth());
        animationContent.setDuration(animation_duration);
        animationContent.start();

        return animation;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
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

    @Override
    public void onBackPressed() {
        if(!isHide){
            hideMenu(nav_view, main_content);
        } else {
            super.onBackPressed();
        }
    }

    public void onMenuClick(View view) {
        hideMenu(nav_view, main_content);
    }
}