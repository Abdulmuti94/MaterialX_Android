package com.material.components.activity.motion;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.material.components.R;
import com.material.components.fragment.FragmentMotionSearchBar;
import com.material.components.utils.Tools;

public class MotionSearchBarExpand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_search_bar_expand);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, new FragmentMotionSearchBarExpand())
                .commit();

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    public static class FragmentMotionSearchBarExpand extends Fragment {

        private CardView searchBar;

        public FragmentMotionSearchBarExpand() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_motion_search_bar_expand, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            searchBar = view.findViewById(R.id.search_bar);
            ViewCompat.setTransitionName(searchBar, "simple_fragment_transition");

            (view.findViewById(R.id.bt_search)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchBarClicked();
                }
            });
            (view.findViewById(R.id.lyt_content)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchBarClicked();
                }
            });
            (view.findViewById(R.id.bt_mic)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchBarClicked();
                }
            });

            new Handler(getActivity().getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    onSearchBarClicked();
                }
            }, 1000);
        }

        private void onSearchBarClicked() {
            FragmentMotionSearchBar simpleFragmentB = FragmentMotionSearchBar.newInstance();
            getFragmentManager()
                    .beginTransaction()
                    .addSharedElement(searchBar, ViewCompat.getTransitionName(searchBar))
                    .addToBackStack(null)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();
        }

    }

}