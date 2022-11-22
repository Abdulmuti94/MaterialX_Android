package com.material.components.fragment;


import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.material.components.R;
import com.material.components.adapter.AdapterSuggestionSearch;

public class FragmentMotionSearchBar extends Fragment {

    public FragmentMotionSearchBar() {
        // Required empty public constructor
    }

    public static FragmentMotionSearchBar newInstance() {
        return new FragmentMotionSearchBar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motion_search_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (view.findViewById(R.id.bt_menu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final CardView imageView = view.findViewById(R.id.fragment_b_image);
        ViewCompat.setTransitionName(imageView, "simple_fragment_transition");

        RecyclerView recyclerSuggestion = view.findViewById(R.id.recyclerSuggestion);

        recyclerSuggestion.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSuggestion.setHasFixedSize(true);

        //set data and list adapter suggestion
        AdapterSuggestionSearch mAdapterSuggestion = new AdapterSuggestionSearch(getContext());
        mAdapterSuggestion.addSearchHistory("Orci odio blandit risus");
        mAdapterSuggestion.addSearchHistory("Vivamus laoreet");
        mAdapterSuggestion.addSearchHistory("Aliquam ac elit porttitor");
        mAdapterSuggestion.addSearchHistory("Proin libero sem");
        mAdapterSuggestion.addSearchHistory("Quisque imperdiet nunc");
        mAdapterSuggestion.addSearchHistory("Laoreet aliquam");
        recyclerSuggestion.setAdapter(mAdapterSuggestion);

    }
}