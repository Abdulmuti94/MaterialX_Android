package com.material.components.activity.motion;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.material.components.R;
import com.material.components.utils.Tools;


public class MotionPageBasicDetails extends AppCompatActivity {

    private ImageView mHeaderImageView;
    private TextView mHeaderTitle;
    private long duration = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        duration = getIntent().getLongExtra("EXTRA_DURATION", 400L);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
            // Set up shared element transition
            findViewById(android.R.id.content).setTransitionName("EXTRA_VIEW");
            setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
            getWindow().setSharedElementEnterTransition(buildContainerTransform(true));
            getWindow().setSharedElementReturnTransition(buildContainerTransform(false));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_page_basic_details);

        int extra_image = getIntent().getIntExtra("EXTRA_IMAGE", 0);
        String extra_name = getIntent().getStringExtra("EXTRA_NAME");

        mHeaderImageView = findViewById(R.id.image);
        mHeaderTitle = findViewById(R.id.name);

        mHeaderTitle.setText(extra_name);
        Tools.displayImageOriginal(this, mHeaderImageView, extra_image);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // animation transition
            ViewCompat.setTransitionName(findViewById(R.id.parent_view), "EXTRA_VIEW");
        }

        initToolbar();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private MaterialContainerTransform buildContainerTransform(boolean entering) {
        MaterialContainerTransform transform = new MaterialContainerTransform();
        transform.setTransitionDirection(entering ? MaterialContainerTransform.TRANSITION_DIRECTION_ENTER : MaterialContainerTransform.TRANSITION_DIRECTION_RETURN);
        transform.setAllContainerColors(MaterialColors.getColor(findViewById(android.R.id.content), R.attr.colorSurface));
        transform.addTarget(android.R.id.content);
        transform.setDuration(duration);
        return transform;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
