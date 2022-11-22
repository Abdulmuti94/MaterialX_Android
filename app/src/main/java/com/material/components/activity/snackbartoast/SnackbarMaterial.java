package com.material.components.activity.snackbartoast;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.material.components.R;
import com.material.components.utils.Tools;

public class SnackbarMaterial extends AppCompatActivity {

    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar_material);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Snackbar Material");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {

        ((AppCompatButton) findViewById(R.id.snackbar_simple)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Simple Snackbar", Snackbar.LENGTH_SHORT).show();
            }
        });

        ((AppCompatButton) findViewById(R.id.snackbar_with_action)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBarWithAction();
            }
        });

        ((AppCompatButton) findViewById(R.id.snackbar_with_action_indefinite)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBarWithActionIndefinite();
            }
        });
    }

    private void snackBarWithAction() {
        Snackbar snackbar = Snackbar.make(parent_view, "Snackbar With Action", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(parent_view, "UNDO CLICKED!", Snackbar.LENGTH_SHORT).show();
                    }
                });
        snackbar.show();
    }

    private void snackBarWithActionIndefinite() {
        Snackbar snackbar = Snackbar.make(parent_view, "Snackbar With Action INDEFINITE", Snackbar.LENGTH_INDEFINITE)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(parent_view, "UNDO CLICKED!", Snackbar.LENGTH_SHORT).show();
                    }
                });
        snackbar.show();
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
