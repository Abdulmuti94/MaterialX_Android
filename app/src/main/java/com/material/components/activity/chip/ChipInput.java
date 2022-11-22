package com.material.components.activity.chip;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.material.components.R;
import com.material.components.adapter.AdapterPhotoInfo;
import com.material.components.data.DataGenerator;
import com.material.components.model.Image;
import com.material.components.utils.Tools;

import java.util.List;

public class ChipInput extends AppCompatActivity {

    private ChipGroup chip_group;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_input);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photo Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Image> items = DataGenerator.getPhotoInfo(this);
        AdapterPhotoInfo _adapter = new AdapterPhotoInfo(this, items);
        recyclerView.setAdapter(_adapter);
        _adapter.setOnItemClickListener(new AdapterPhotoInfo.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Image obj, int position) {
                addChip(obj);
            }
        });
        chip_group = findViewById(R.id.chip_group);
        input = findViewById(R.id.input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addChip(items.get(items.size() - 1));
    }

    private void addChip(Image obj) {
        Chip chip = new Chip(this, null, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry));
        chip.setText(obj.name);
        chip.setClickable(true);
        chip.setCheckable(false);
        chip.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.spacing_xxxlarge)));
        chip.setTextColor(getResources().getColor(R.color.grey_60));
        chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.grey_10)));
        chip.setChipIconResource(obj.image);
        chip.setChipIconTint(ColorStateList.valueOf(getResources().getColor(R.color.grey_60)));
        chip.setIconStartPaddingResource(R.dimen.spacing_medium);
        chip.setCloseIconTint(ColorStateList.valueOf(getResources().getColor(R.color.grey_40)));
        chip.setCloseIconEndPaddingResource(R.dimen.spacing_medium);

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chip_group.removeView(chip);
            }
        });

        chip_group.addView(chip, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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
