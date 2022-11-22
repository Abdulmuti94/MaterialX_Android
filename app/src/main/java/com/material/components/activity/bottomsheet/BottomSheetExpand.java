package com.material.components.activity.bottomsheet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.material.components.R;
import com.material.components.utils.Tools;

public class BottomSheetExpand extends AppCompatActivity {

    private View bottom_sheet;
    private View lyt_sheet_header, lyt_sheet_header_white;
    private BottomSheetBehavior mBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_expand);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        lyt_sheet_header = findViewById(R.id.lyt_sheet_header);
        lyt_sheet_header_white = findViewById(R.id.lyt_sheet_header_white);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    mBehavior.setDraggable(false);
                    lyt_sheet_header.setVisibility(View.GONE);
                    lyt_sheet_header_white.setVisibility(View.VISIBLE);
                } else {
                    mBehavior.setDraggable(true);
                    lyt_sheet_header.setVisibility(View.VISIBLE);
                    lyt_sheet_header_white.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

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

        Toast.makeText(this, "Swipe Up details panel", Toast.LENGTH_SHORT).show();
    }
}