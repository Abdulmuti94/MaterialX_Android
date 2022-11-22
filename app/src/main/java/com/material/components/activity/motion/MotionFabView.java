package com.material.components.activity.motion;

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
import androidx.transition.TransitionManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialContainerTransform;
import com.material.components.R;
import com.material.components.utils.Tools;

public class MotionFabView extends AppCompatActivity {

    private FloatingActionButton fab_add;
    private View endView;
    private FrameLayout root;
    private boolean slow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_fab_view);
        initToolbar();

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

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showEndView(fab_add);
            }
        }, 1000);
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

    private void showEndView(View startView) {
        // Construct a container transform transition between two views.
        MaterialContainerTransform transition = buildContainerTransform(true);
        transition.setStartView(startView);
        transition.setEndView(endView);

        // Add a single target to stop the container transform from running on both the start
        // and end view.
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
        slow = !slow;
    }

    @NonNull
    private MaterialContainerTransform buildContainerTransform(boolean entering) {
        MaterialContainerTransform transform = new MaterialContainerTransform();
        transform.setTransitionDirection(entering ? MaterialContainerTransform.TRANSITION_DIRECTION_ENTER : MaterialContainerTransform.TRANSITION_DIRECTION_RETURN);
        transform.setElevationShadowEnabled(false);
        transform.setScrimColor(Color.TRANSPARENT);
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
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
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