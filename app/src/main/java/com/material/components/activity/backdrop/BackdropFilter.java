package com.material.components.activity.backdrop;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.material.components.R;
import com.material.components.adapter.AdapterListSectioned;
import com.material.components.data.DataGenerator;
import com.material.components.model.People;
import com.material.components.utils.BackdropViewAnimation;
import com.material.components.utils.Tools;

import java.util.List;

public class BackdropFilter extends AppCompatActivity {
    private BackdropViewAnimation backdropAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backdrop_filter);

        initComponent();
    }

    private void initComponent() {
        Tools.setSystemBarColor(this, R.color.deep_purple_700);
        ImageView action_menu = findViewById(R.id.action_menu);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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

        backdropAnimation = new BackdropViewAnimation(this, findViewById(R.id.backdrop_view), findViewById(R.id.content));
        backdropAnimation.addStateListener(new BackdropViewAnimation.StateListener() {
            @Override
            public void onOpen(ObjectAnimator animator) {
                action_menu.setImageResource(R.drawable.ic_close);
            }

            @Override
            public void onClose(ObjectAnimator animator) {
                action_menu.setImageResource(R.drawable.ic_tune);
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
        }, 600);
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