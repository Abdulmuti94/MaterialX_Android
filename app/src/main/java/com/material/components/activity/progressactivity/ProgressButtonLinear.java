package com.material.components.activity.progressactivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.material.components.R;
import com.material.components.utils.Tools;

public class ProgressButtonLinear extends AppCompatActivity {

    private ProgressBar progress_bar;
    private Handler mHandler;
    private Button button_action;
    private Runnable runnable;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_button_linear);
        initToolbar();
        mHandler = new Handler(this.getMainLooper());
        progress_bar = findViewById(R.id.progress_bar);
        button_action = findViewById(R.id.button_action);

        button_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runProgressDeterminateCircular();
            }
        });


        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                runProgressDeterminateCircular();
            }
        }, 500);

        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_1), R.drawable.image_11);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_2), R.drawable.image_30);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_3), R.drawable.image_20);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_4), R.drawable.image_18);
    }

    private void runProgressDeterminateCircular() {
        runnable = new Runnable() {
            public void run() {
                running = true;
                int progress = progress_bar.getProgress() + 1;
                button_action.setText("DOWNLOAD " + progress + "%");
                button_action.setClickable(false);
                progress_bar.setProgress(progress);
                progress_bar.setVisibility(View.VISIBLE);
                if (progress > 100) {
                    progress_bar.setProgress(0);
                    progress_bar.setVisibility(View.INVISIBLE);
                    running = false;
                    button_action.setText("DOWNLOAD");
                    button_action.setClickable(true);
                } else {
                    mHandler.postDelayed(this, 20);
                }
            }
        };
        mHandler.post(runnable);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHandler == null || runnable == null) return;
        mHandler.removeCallbacks(runnable);
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
