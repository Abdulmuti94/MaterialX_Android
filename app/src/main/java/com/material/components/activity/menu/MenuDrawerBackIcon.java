package com.material.components.activity.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.material.components.R;
import com.material.components.utils.Tools;

public class MenuDrawerBackIcon extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;
    private CardView content_view;
    private LinearLayout nav_view;
    private boolean isHide = true;
    private int width_content = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_back_icon);

        initToolbar();
        initNavigationMenu();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.blue_700), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Black Drawer");
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void initNavigationMenu() {
        content_view = findViewById(R.id.main_content);
        nav_view = findViewById(R.id.nav_view);
        content_view.post(new Runnable() {
            @Override
            public void run() {
                content_view.setTranslationX(0);
                content_view.setRadius(Tools.dpToPx(getApplicationContext(), 0));
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide) {
                    showMenu(nav_view, content_view);
                } else {
                    hideMenu(content_view);
                }
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showMenu(nav_view, content_view);
            }
        }, 1000);
    }

    public ObjectAnimator hideMenu(View contentView_) {
        isHide = true;
        contentView_.animate()
                .scaleX(1f).scaleY(1f)
                .translationX(0f)
                .setDuration(300)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        content_view.setRadius(Tools.dpToPx(getApplicationContext(), 0));
                    }
                })
                .start();
        return null;
    }

    public ObjectAnimator showMenu(View navView_, View contentView_) {
        isHide = false;
        float scaleFactor = 0.9f;
        float transX = (contentView_.getWidth() * (1 - scaleFactor)) / 2;
        contentView_.animate()
                .scaleY(scaleFactor).scaleX(scaleFactor)
                .translationX(navView_.getWidth() - transX)
                .setDuration(300)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        content_view.setRadius(Tools.dpToPx(getApplicationContext(), 10));
                    }
                })
                .start();
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.blue_700));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!isHide){
            hideMenu(content_view);
        } else {
            super.onBackPressed();
        }
    }

    public void onMenuClick(View view) {
        hideMenu(content_view);
    }
}