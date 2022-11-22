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
import androidx.cardview.widget.CardView;

import com.material.components.R;
import com.material.components.utils.Tools;

public class ProgressButtonDone extends AppCompatActivity {

    enum ButtonState {
        NORMAL, LOADING, DONE
    }

    private ProgressBar progress_indeterminate_circular;
    private Handler mHandler;
    private View button_action;
    private TextView tv_status, tv_download;
    private ImageView icon_download;
    private CardView card_view;
    private Runnable runnable;
    private ButtonState buttonState = ButtonState.LOADING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_button_done);
        initToolbar();

        mHandler = new Handler(this.getMainLooper());

        progress_indeterminate_circular = findViewById(R.id.progress_indeterminate_circular);
        button_action = findViewById(R.id.button_action);
        tv_status = findViewById(R.id.tv_status);
        tv_download = findViewById(R.id.tv_download);
        icon_download = findViewById(R.id.icon_download);
        card_view = findViewById(R.id.card_view);

        button_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonState == ButtonState.DONE) {
                    onResetClicked(v);
                } else {
                    runProgressDeterminateCircular();
                }
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

    public void onResetClicked(View view) {
        button_action.setClickable(true);
        buttonState = buttonState.NORMAL;
        progress_indeterminate_circular.setProgress(0);
        progress_indeterminate_circular.setVisibility(View.INVISIBLE);
        icon_download.setImageResource(R.drawable.ic_arrow_circle_down);
        icon_download.setVisibility(View.VISIBLE);
        tv_status.setText("");
        tv_download.setText("DOWNLOAD");
        card_view.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void runProgressDeterminateCircular() {
        runnable = new Runnable() {
            public void run() {
                buttonState = buttonState.LOADING;
                int progress = progress_indeterminate_circular.getProgress() + 1;
                progress_indeterminate_circular.setProgress(progress);
                tv_status.setText(progress + " %");
                tv_download.setText("DOWNLOADING...");
                button_action.setClickable(false);
                progress_indeterminate_circular.setVisibility(View.VISIBLE);
                icon_download.setVisibility(View.INVISIBLE);
                card_view.setCardBackgroundColor(getResources().getColor(R.color.amber_700));
                if (progress > 100) {
                    buttonState = buttonState.DONE;
                    progress_indeterminate_circular.setProgress(0);
                    tv_status.setText("DONE");
                    tv_download.setText("DOWNLOADED");
                    button_action.setClickable(true);
                    progress_indeterminate_circular.setVisibility(View.INVISIBLE);
                    icon_download.setVisibility(View.VISIBLE);
                    icon_download.setImageResource(R.drawable.ic_download_done);
                    card_view.setCardBackgroundColor(getResources().getColor(R.color.light_green_500));
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
        getMenuInflater().inflate(R.menu.menu_refresh_setting, menu);
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
        } else if (item.getItemId() == R.id.action_refresh) {
            onResetClicked(null);
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
