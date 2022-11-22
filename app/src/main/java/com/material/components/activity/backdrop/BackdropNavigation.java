package com.material.components.activity.backdrop;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.material.components.R;
import com.material.components.adapter.AdapterListSectioned;
import com.material.components.data.DataGenerator;
import com.material.components.model.People;
import com.material.components.utils.BackdropViewAnimation;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;

import java.util.List;

public class BackdropNavigation extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BackdropViewAnimation backdropAnimation;
    private View[] menus = new View[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backdrop_navigation);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.deep_purple_700);
        Tools.changeOverflowMenuIconColor(toolbar, Color.WHITE);

        backdropAnimation = new BackdropViewAnimation(this, findViewById(R.id.backdrop_view), findViewById(R.id.content));
        backdropAnimation.addStateListener(new BackdropViewAnimation.StateListener() {
            @Override
            public void onOpen(ObjectAnimator animator) {
                toolbar.setNavigationIcon(R.drawable.ic_close);
                toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onClose(ObjectAnimator animator) {
                toolbar.setNavigationIcon(R.drawable.ic_menu);
                toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backdropAnimation.toggle(v);
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
        menus[0] = findViewById(R.id.lyt_unread);
        menus[1] = findViewById(R.id.lyt_inbox);
        menus[2] = findViewById(R.id.lyt_bookmark);
        menus[3] = findViewById(R.id.lyt_social);
        menus[1].setSelected(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<People> items = DataGenerator.getPeopleData(this);
        items.addAll(DataGenerator.getPeopleData(this));
        items.addAll(DataGenerator.getPeopleData(this));

        int sect_count = 0;
        int sect_idx = 0;
        List<String> months = DataGenerator.getStringsMonth(this);
        for (int i = 0; i < items.size() / 6; i++) {
            items.add(sect_count, new People(months.get(sect_idx), true));
            sect_count = sect_count + 5;
            sect_idx++;
        }

        //set data and list adapter
        AdapterListSectioned mAdapter = new AdapterListSectioned(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListSectioned.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int position) {

            }
        });
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

    public void onMenuClick(View view) {
        for (View v : menus) {
            v.setSelected(false);
        }
        view.setSelected(true);
        ViewAnimation.fadeOutIn(recyclerView);
    }
}