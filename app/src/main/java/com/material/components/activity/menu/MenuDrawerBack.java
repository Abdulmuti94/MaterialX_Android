package com.material.components.activity.menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.utils.Tools;

public class MenuDrawerBack extends AppCompatActivity {


    private ActionBar actionBar;
    private Toolbar toolbar;
    private CardView content_view;
    private LinearLayout nav_view;
    private boolean isHide = true;
    private int width_content = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_back);

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
        actionBar.setTitle("Work Space");
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
                    showMenu(content_view);
                } else {
                    hideMenu(content_view);
                }
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showMenu(content_view);
            }
        }, 1000);
    }

    public ObjectAnimator hideMenu(View view) {
        isHide = true;
        view.animate()
                .scaleX(1f).scaleY(1f)
                .translationX(0f)
                .setDuration(300)
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

    public ObjectAnimator showMenu(View view) {
        isHide = false;
        view.animate()
                .scaleX(0.9f).scaleY(0.9f)
                .translationX(view.getWidth()/2f)
                .setDuration(300)
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