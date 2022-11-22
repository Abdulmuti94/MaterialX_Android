package com.material.components.activity.bottomsheet;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.material.components.R;
import com.material.components.utils.Tools;

public class BottomSheetAnimation extends AppCompatActivity {

    private ExtendedFloatingActionButton bt_support;

    private BottomSheetBehavior mBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_animation);
        Tools.setSystemBarColor(this, R.color.blue_600);

        bt_support = findViewById(R.id.bt_support);
        View bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    mBehavior.setDraggable(false);
                    bt_support.hide();
                } else {
                    mBehavior.setDraggable(true);
                    bt_support.show();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        (findViewById(R.id.bt_support)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_support.hide();
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        (findViewById(R.id.bt_expand)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_1), R.drawable.image_5);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_2), R.drawable.image_6);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_3), R.drawable.image_7);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_4), R.drawable.image_8);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_5), R.drawable.image_10);
        Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_6), R.drawable.image_12);


        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                bt_support.hide();
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }, 600);
    }
}