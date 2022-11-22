package com.material.components.activity.card;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.material.components.R;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;

public class CardExpand extends AppCompatActivity {

    private ImageView bt_toggle;
    private View lyt_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_expand);
        initToolbar();

        lyt_more = findViewById(R.id.lyt_more);
        lyt_more.setVisibility(View.GONE);
        bt_toggle = findViewById(R.id.bt_toggle);
        bt_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(bt_toggle);
            }
        });
        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                toggleSection(bt_toggle);
            }
        }, 600);
    }

    private void toggleSection(View view) {
        boolean show = Tools.toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_more, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {

                }
            });
        } else {
            ViewAnimation.collapse(lyt_more);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expand");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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
