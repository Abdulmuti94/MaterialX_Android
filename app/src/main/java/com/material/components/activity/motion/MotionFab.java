package com.material.components.activity.motion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.material.components.R;
import com.material.components.data.DataGenerator;
import com.material.components.model.Image;
import com.material.components.utils.Tools;

import java.util.List;

public class MotionFab extends AppCompatActivity {

    private FloatingActionButton fab;
    private boolean slow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_fab);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Motion FAB");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.teal_700);
    }

    private void initComponent() {
        List<Image> items = DataGenerator.getImageDate(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFABClicked(fab, items.get(DataGenerator.randInt(items.size() - 1)));
            }
        });
    }

    private void onFABClicked(View sharedElement, Image obj) {
        Intent intent = new Intent(this, MotionPageBasicDetails.class);
        intent.putExtra("EXTRA_IMAGE", obj.image);
        intent.putExtra("EXTRA_NAME", obj.name);
        intent.putExtra("EXTRA_DURATION", slow ? 1200L : 400L);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(this, slow ? "Slow" : "Fast", Toast.LENGTH_SHORT).show();
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, sharedElement, "EXTRA_VIEW");
            startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, "EXTRA_VIEW");
            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        }
        slow = !slow;
    }
}