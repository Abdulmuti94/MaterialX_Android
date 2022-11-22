package com.material.components.activity.checkradio;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.material.components.R;
import com.material.components.model.CheckBoxState;
import com.material.components.utils.Tools;

public class CheckboxBasic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_basic);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkbox Basic");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        CheckBox checkbox_basic_ind = findViewById(R.id.checkbox_basic_ind);
        checkbox_basic_ind.setTag(CheckBoxState.INDETERMINATE);
        checkbox_basic_ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_basic_ind.getTag().equals(CheckBoxState.INDETERMINATE)) {
                    checkbox_basic_ind.setButtonDrawable(R.drawable.ic_check_box);
                    checkbox_basic_ind.setTag(CheckBoxState.CHECKED);
                } else if (checkbox_basic_ind.getTag().equals(CheckBoxState.CHECKED)) {
                    checkbox_basic_ind.setButtonDrawable(R.drawable.ic_check_box_outline);
                    checkbox_basic_ind.setTag(CheckBoxState.UNCHECKED);
                } else if (checkbox_basic_ind.getTag().equals(CheckBoxState.UNCHECKED)) {
                    checkbox_basic_ind.setButtonDrawable(R.drawable.ic_indeterminate_check_box);
                    checkbox_basic_ind.setTag(CheckBoxState.INDETERMINATE);
                }
            }
        });

        MaterialCheckBox checkbox_material_ind = findViewById(R.id.checkbox_material_ind);
        checkbox_material_ind.setTag(CheckBoxState.INDETERMINATE);
        checkbox_material_ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_material_ind.getTag().equals(CheckBoxState.INDETERMINATE)) {
                    checkbox_material_ind.setButtonDrawable(R.drawable.ic_check_box);
                    checkbox_material_ind.setTag(CheckBoxState.CHECKED);
                } else if (checkbox_material_ind.getTag().equals(CheckBoxState.CHECKED)) {
                    checkbox_material_ind.setButtonDrawable(R.drawable.ic_check_box_outline);
                    checkbox_material_ind.setTag(CheckBoxState.UNCHECKED);
                } else if (checkbox_material_ind.getTag().equals(CheckBoxState.UNCHECKED)) {
                    checkbox_material_ind.setButtonDrawable(R.drawable.ic_indeterminate_check_box);
                    checkbox_material_ind.setTag(CheckBoxState.INDETERMINATE);
                }
            }
        });

        AppCompatCheckBox checkbox_compat_ind = findViewById(R.id.checkbox_compat_ind);
        checkbox_compat_ind.setTag(CheckBoxState.INDETERMINATE);
        checkbox_compat_ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_compat_ind.getTag().equals(CheckBoxState.INDETERMINATE)) {
                    checkbox_compat_ind.setButtonDrawable(R.drawable.ic_check_box);
                    checkbox_compat_ind.setTag(CheckBoxState.CHECKED);
                } else if (checkbox_compat_ind.getTag().equals(CheckBoxState.CHECKED)) {
                    checkbox_compat_ind.setButtonDrawable(R.drawable.ic_check_box_outline);
                    checkbox_compat_ind.setTag(CheckBoxState.UNCHECKED);
                } else if (checkbox_compat_ind.getTag().equals(CheckBoxState.UNCHECKED)) {
                    checkbox_compat_ind.setButtonDrawable(R.drawable.ic_indeterminate_check_box);
                    checkbox_compat_ind.setTag(CheckBoxState.INDETERMINATE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
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
