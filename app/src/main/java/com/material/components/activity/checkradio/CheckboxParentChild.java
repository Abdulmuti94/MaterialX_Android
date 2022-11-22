package com.material.components.activity.checkradio;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.material.components.R;
import com.material.components.model.CheckBoxState;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;

public class CheckboxParentChild extends AppCompatActivity {

    private MaterialCheckBox checkbox_add;
    private View lyt_sub;
    private MaterialCheckBox[] additional;
    private int[] additional_id = new int[]{
            R.id.checkbox_1, R.id.checkbox_2, R.id.checkbox_3,
            R.id.checkbox_4, R.id.checkbox_5, R.id.checkbox_6
    };
    private ImageButton bt_toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_parent_child);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meal Option");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        lyt_sub = findViewById(R.id.lyt_sub);
        bt_toggle = findViewById(R.id.bt_toggle);
        bt_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionText(bt_toggle);
            }
        });

        additional = new MaterialCheckBox[additional_id.length];
        for (int i = 0; i < additional_id.length; i++) {
            additional[i] = findViewById(additional_id[i]);
            additional[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateParentCheckbox();
                }
            });
        }

        checkbox_add = findViewById(R.id.checkbox_add);
        checkbox_add.setTag(CheckBoxState.INDETERMINATE);
        checkbox_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_add.getTag().equals(CheckBoxState.CHECKED) || checkbox_add.getTag().equals(CheckBoxState.INDETERMINATE)) {
                    checkbox_add.setButtonDrawable(R.drawable.ic_check_box_outline);
                    checkbox_add.setTag(CheckBoxState.UNCHECKED);
                    checkUncheckCheckBox(false);
                } else if (checkbox_add.getTag().equals(CheckBoxState.UNCHECKED)) {
                    checkbox_add.setButtonDrawable(R.drawable.ic_check_box);
                    checkbox_add.setTag(CheckBoxState.CHECKED);
                    checkUncheckCheckBox(true);
                }
            }
        });
    }

    private void updateParentCheckbox() {
        CheckBoxState stat = checkedStatus();
        if (stat.equals(CheckBoxState.CHECKED)) {
            checkbox_add.setButtonDrawable(R.drawable.ic_check_box);
            checkbox_add.setTag(CheckBoxState.CHECKED);
        } else if (stat.equals(CheckBoxState.UNCHECKED)) {
            checkbox_add.setButtonDrawable(R.drawable.ic_check_box_outline);
            checkbox_add.setTag(CheckBoxState.UNCHECKED);
        } else {
            checkbox_add.setButtonDrawable(R.drawable.ic_indeterminate_check_box);
            checkbox_add.setTag(CheckBoxState.INDETERMINATE);
        }
    }

    private void checkUncheckCheckBox(boolean isChecked) {
        for (MaterialCheckBox cb : additional) {
            cb.setChecked(isChecked);
        }
    }

    private CheckBoxState checkedStatus() {
        int count = 0;
        for (MaterialCheckBox cb : additional) {
            if (cb.isChecked()) count++;
        }
        if (count == 0) {
            return CheckBoxState.UNCHECKED;
        } else if (count == additional.length) {
            return CheckBoxState.CHECKED;
        } else {
            return CheckBoxState.INDETERMINATE;
        }
    }
    private void toggleSectionText(View view) {
        boolean show = Tools.toggleArrow(view);
        if (!show) {
            ViewAnimation.expand(lyt_sub, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {

                }
            });
        } else {
            ViewAnimation.collapse(lyt_sub);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
