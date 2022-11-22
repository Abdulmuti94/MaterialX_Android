package com.material.components.room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.material.components.R;
import com.material.components.adapter.MnAdapter;
import com.material.components.adapter.MnAdapter.Item;
import com.material.components.adapter.MnSearchAdapter;
import com.material.components.data.MenuGenerator;
import com.material.components.room.table.FavoriteEntity;
import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class ActivityFavorites extends AppCompatActivity {

    public static void navigate(Activity activity) {
        Intent i = new Intent(activity, ActivityFavorites.class);
        activity.startActivity(i);
    }

    public View parent_view;
    private RecyclerView recyclerView;
    private DAO dao;
    private MnSearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        dao = AppDatabase.getDb(this).getDAO();

        initToolbar();
        iniComponent();
    }

    private void initToolbar() {
        ActionBar actionBar;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Favorites");
        Tools.setSystemBarColor(this, android.R.color.black);
    }

    private void iniComponent() {
        parent_view = findViewById(android.R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<FavoriteEntity> listEntity = dao.getFavorites();
        List<Item> items = new ArrayList<>();
        for (FavoriteEntity en : listEntity) {
            items.add(en.getOriginal());
        }
        searchAdapter = new MnSearchAdapter(this, items);
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new MnSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Item obj, int position) {
                Item new_obj = MenuGenerator.getItemById(obj.Id_str);
                if(new_obj != null && new_obj.Act != null){
                    startActivity(new Intent(ActivityFavorites.this, new_obj.Act));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed when open page", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}