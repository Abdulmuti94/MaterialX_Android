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

public class ProgressButton extends AppCompatActivity {

    private ProgressBar progress_indeterminate_circular;
    private Handler mHandler;
    private Button button_action;
    private Runnable runnable;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_button);
        initToolbar();
        mHandler = new Handler(this.getMainLooper());
        progress_indeterminate_circular = findViewById(R.id.progress_indeterminate_circular);
        button_action = findViewById(R.id.button_action);

        button_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runProgressDeterminateCircular();
            }
        });
        runProgressDeterminateCircular();

        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_1), R.drawable.image_11);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_2), R.drawable.image_30);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_3), R.drawable.image_20);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_4), R.drawable.image_18);
    }

    private void runProgressDeterminateCircular() {
        runnable = new Runnable() {
            public void run() {
                running = true;
                button_action.setText("");
                button_action.setClickable(false);
                int progress = progress_indeterminate_circular.getProgress() + 1;
                progress_indeterminate_circular.setProgress(progress);
                if (progress > 100) {
                    progress_indeterminate_circular.setProgress(0);
                    running = false;
                    button_action.setText("DOWNLOAD");
                    button_action.setClickable(true);
                } else {
                    mHandler.postDelayed(this, 5);
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
