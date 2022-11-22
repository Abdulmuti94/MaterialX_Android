package com.material.components.activity.backdrop;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.material.components.R;
import com.material.components.utils.BackdropViewAnimation;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;

public class BackdropSteppers extends AppCompatActivity {

    private BackdropViewAnimation backdropAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backdrop_steppers);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Tools.setSystemBarColor(this, R.color.deep_purple_700);
        ImageView action_menu =  findViewById(R.id.action_menu);

        backdropAnimation = new BackdropViewAnimation(this, findViewById(R.id.backdrop_view), findViewById(R.id.content));
        backdropAnimation.addStateListener(new BackdropViewAnimation.StateListener() {
            @Override
            public void onOpen(ObjectAnimator animator) {
                action_menu.setImageResource(R.drawable.ic_close);
            }

            @Override
            public void onClose(ObjectAnimator animator) {
                action_menu.setImageResource(R.drawable.ic_menu);
            }
        });
        action_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backdropAnimation.toggle();
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backdropAnimation.toggle();
            }
        }, 1000);
    }

    private void initComponent() {

    }

    public void onMenuClick(View view) {

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