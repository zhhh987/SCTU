package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.haud.sctu.R;
import com.haud.sctu.helper.SearchAdapter;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.SmsViewModel;

import java.util.List;

public class SearchFragment extends Fragment {
    final SearchAdapter searchAdapter = new SearchAdapter();
    String searchInput = null;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        RecyclerView smsRecyclerView = view.findViewById(R.id.smsSearchResultsRecyclerView);
        //RecyclerView callRecyclerView = view.findViewById(R.id.callSearchResultsRecyclerView);

        smsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        smsRecyclerView.setHasFixedSize(true);
        smsRecyclerView.setAdapter(searchAdapter);

        EditText searchEditText = ((BaseActivity) getActivity()).getSearch();
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                searchInput = editable.toString();
            }
        });

        SmsViewModel smsViewModel = new ViewModelProvider(getActivity()).get(SmsViewModel.class);
        smsViewModel.getSmsSearchResults(searchInput).observe(getViewLifecycleOwner(), new Observer<List<SmsLog>>() {
            @Override
            public void onChanged(List<SmsLog> smsLogs) {
                searchAdapter.setSmsLogs(smsLogs);
            }
        });



        /*
        TextView smsSearchTextView = view.findViewById(R.id.smsSearchResultsTextView);
        if (((MainActivity) getActivity()).searchInput.length() == 0) {
            RelativeLayout smsSearchResults = view.findViewById(R.id.smsSearchResultsFrame);
            smsSearchResults.setVisibility(View.GONE);
            RelativeLayout callSearchResults = view.findViewById(R.id.callSearchResultsFrame);
            callSearchResults.setVisibility(View.GONE);
        }

         */



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
