package com.material.components.activity.button;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialContainerTransform;
import com.material.components.R;
import com.material.components.adapter.AdapterPeopleLeft;
import com.material.components.data.DataGenerator;
import com.material.components.model.People;
import com.material.components.utils.Tools;

public class FabExpand extends AppCompatActivity {

    private FloatingActionButton fab_add;
    private View endView;
    private View back_drop;
    private FrameLayout root;
    private boolean slow = false;

    private RecyclerView recyclerView;
    private AdapterPeopleLeft adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_expand);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_900);
    }


    private void initComponent() {
        back_drop = findViewById(R.id.back_drop);
        root = findViewById(R.id.root_view);
        fab_add = findViewById(R.id.fab_add);
        endView = findViewById(R.id.contact_card);

        ViewCompat.setTransitionName(fab_add, String.valueOf(fab_add.getId()));
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndView(v);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set data and list adapter
        adapter = new AdapterPeopleLeft(this, DataGenerator.getPeopleData(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterPeopleLeft.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int pos) {

            }
        });

        back_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recyclerViewExpand = findViewById(R.id.recyclerViewExpand);
        recyclerViewExpand.setLayoutManager(new LinearLayoutManager(this));
        //set data and list adapter
        AdapterPeopleLeft adapterExpand = new AdapterPeopleLeft(this, adapter.getItems().subList(0, 3));
        recyclerViewExpand.setAdapter(adapterExpand);
        adapterExpand.setOnItemClickListener(new AdapterPeopleLeft.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int pos) {

            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showEndView(fab_add);
            }
        }, 1000);
    }

    private void showEndView(View startView) {
        // Construct a container transform transition between two views.
        MaterialContainerTransform transition = buildContainerTransform(true);
        transition.setStartView(startView);
        transition.setEndView(endView);
        transition.addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                super.onTransitionEnd(transition);
                back_drop.setVisibility(View.VISIBLE);
            }
        });

        // Add a single target to stop the container transform from running on both the start and end view.
        transition.addTarget(endView);

        // Trigger the container transform transition.
        TransitionManager.beginDelayedTransition(root, transition);
        startView.setVisibility(View.INVISIBLE);
        endView.setVisibility(View.VISIBLE);
    }

    private void showStartView(View endView) {
        View startView = fab_add;

        // Construct a container transform transition between two views.
        MaterialContainerTransform transition = buildContainerTransform(false);
        transition.setStartView(endView);
        transition.setEndView(startView);

        // Add a single target to stop the container transform from running on both the start
        transition.addTarget(startView);

        // Trigger the container transform transition.
        TransitionManager.beginDelayedTransition(root, transition);
        startView.setVisibility(View.VISIBLE);
        endView.setVisibility(View.INVISIBLE);
        back_drop.setVisibility(View.GONE);
        slow = !slow;
    }

    @NonNull
    private MaterialContainerTransform buildContainerTransform(boolean entering) {
        MaterialContainerTransform transform = new MaterialContainerTransform();
        transform.setTransitionDirection(entering ? MaterialContainerTransform.TRANSITION_DIRECTION_ENTER : MaterialContainerTransform.TRANSITION_DIRECTION_RETURN);
        transform.setDrawingViewId(root.getId());
        transform.setDuration(slow ? 1500 : 500);
        return transform;
    }

    @Override
    public void onBackPressed() {
        if (endView != null && endView.getVisibility() == View.VISIBLE) {
            showStartView(endView);
        } else {
            super.onBackPressed();
        }
    }

    public void onViewItemClicked(View v) {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        Tools.changeMenuIconColor(menu, Color.WHITE);
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