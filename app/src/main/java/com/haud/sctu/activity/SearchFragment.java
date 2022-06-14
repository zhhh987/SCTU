package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.haud.sctu.R;

public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ImageView backButton = getActivity().findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBarLayout appBarLayout = ((BaseActivity) getActivity()).getAppBarLayout();
                ((BaseActivity) getActivity()).unlockAppBar(appBarLayout);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SmsFragment()).commitNow();
            }
        });

        return view;
    }
}
