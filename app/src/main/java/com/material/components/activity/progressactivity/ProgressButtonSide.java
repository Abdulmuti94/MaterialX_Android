package com.material.components.activity.progressactivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.material.components.R;
import com.material.components.utils.Tools;

public class ProgressButtonSide extends AppCompatActivity {

    private ProgressBar progress_indeterminate_circular;
    private Handler mHandler;
    private View button_action;
    private TextView tv_status;
    private ImageView icon_download;
    private Runnable runnable;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_button_side);
        initToolbar();

        mHandler = new Handler(this.getMainLooper());

        progress_indeterminate_circular = findViewById(R.id.progress_indeterminate_circular);
        button_action = findViewById(R.id.button_action);
        tv_status = findViewById(R.id.tv_status);
        icon_download = findViewById(R.id.icon_download);

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

        Tools.displayImageOriginal(this, findViewById(R.id.image_1), R.drawable.image_11);
        Tools.displayImageOriginal(this, findViewById(R.id.image_2), R.drawable.image_30);
        Tools.displayImageOriginal(this, findViewById(R.id.image_3), R.drawable.image_20);
        Tools.displayImageOriginal(this, findViewById(R.id.image_4), R.drawable.image_18);
    }

    private void runProgressDeterminateCircular() {
        runnable = new Runnable() {
            public void run() {
                running = true;
                int progress = progress_indeterminate_circular.getProgress() + 1;
                progress_indeterminate_circular.setProgress(progress);
                //tv_status.setText(progress + " %");
                button_action.setClickable(false);
                progress_indeterminate_circular.setVisibility(View.VISIBLE);
                icon_download.setVisibility(View.INVISIBLE);
                if (progress > 100) {
                    progress_indeterminate_circular.setProgress(0);
                    running = false;
                    //tv_status.setText("DOWNLOAD");
                    button_action.setClickable(true);
                    progress_indeterminate_circular.setVisibility(View.INVISIBLE);
                    icon_download.setVisibility(View.VISIBLE);
                } else {
                    mHandler.postDelayed(this, 10);
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
