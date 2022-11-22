package com.material.components.activity.button;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.material.components.R;
import com.material.components.adapter.AdapterListSectioned;
import com.material.components.data.DataGenerator;
import com.material.components.model.People;
import com.material.components.utils.Tools;

import java.util.List;

public class FabAnimation extends AppCompatActivity {

    private View parent_view;
    private boolean rotate = false;
    private ExtendedFloatingActionButton extendedFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_fab_animation);

        // parent layout must coordinator layout
        parent_view = findViewById(R.id.coordinator_lyt);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fab Animation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

        extendedFab = findViewById(R.id.fab);
        extendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(extendedFab.isExtended()){
                    extendedFab.shrink();
                } else {
                    extendedFab.extend();
                }
            }
        });

        initComponent();

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(extendedFab.isExtended()){
                    extendedFab.shrink();
                } else {
                    extendedFab.extend();
                }
            }
        }, 1000);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        extendedFab.shrink();
    }

    private void initComponent() {
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
                Toast.makeText(getApplicationContext(), "Item " + obj.name + " clicked", Toast.LENGTH_LONG).show();
            }
        });

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
