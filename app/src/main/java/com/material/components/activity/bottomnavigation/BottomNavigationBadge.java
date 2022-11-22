package com.material.components.activity.bottomnavigation;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.material.components.R;
import com.material.components.utils.Tools;

public class BottomNavigationBadge extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView navigation;
    private View search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_badge);

        initComponent();
    }

    private void initComponent() {
        search_bar = (View) findViewById(R.id.search_bar);
        mTextMessage = (TextView) findViewById(R.id.search_text);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                BadgeDrawable badgeFav = navigation.getOrCreateBadge(R.id.navigation_favorites);
                badgeFav.setVisible(true);
                badgeFav.setVerticalOffset(Tools.dpToPx(BottomNavigationBadge.this, 3));
                badgeFav.setBackgroundColor(getResources().getColor(R.color.red_500));
            }
        }, 1000);

        BadgeDrawable badgeBook = navigation.getOrCreateBadge(R.id.navigation_books);
        badgeBook.setVisible(true);
        badgeBook.setVerticalOffset(Tools.dpToPx(this, 3));
        badgeBook.setNumber(88);
        badgeBook.setBackgroundColor(getResources().getColor(R.color.red_500));

        navigation.setSelectedItemId(R.id.navigation_music);

        ((ImageButton) findViewById(R.id.bt_menu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

}
