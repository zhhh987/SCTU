package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.haud.sctu.R;
import com.haud.sctu.helper.SmsByOaAdapter;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.SmsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SmsByOaFragment extends Fragment {
    private ArrayList<SmsLog> selectedSmsLogs = new ArrayList<>();
    private boolean selection_mode = false;
    final SmsByOaAdapter smsByOaAdapter = new SmsByOaAdapter();

    public SmsByOaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms_by_oa, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.smsByOARecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(smsByOaAdapter);

        String selectedOa = ((MainActivity) getActivity()).selectedSmsOa;
        ((BaseActivity) getActivity()).setPageTitle(selectedOa);
        SmsViewModel smsViewModel = new ViewModelProvider(getActivity()).get(SmsViewModel.class);
        smsViewModel.getAllSmsByOa(selectedOa).observe(getViewLifecycleOwner(), new Observer<List<SmsLog>>() {
            @Override
            public void onChanged(List<SmsLog> smsLogs) {
                smsByOaAdapter.setSmsLogs(smsLogs);
            }
        });

        AppBarLayout appBarLayout = ((BaseActivity) getActivity()).getAppBarLayout();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.main_menu);
        ((BaseActivity) getActivity()).enableToolbarWhenOaNoneSelectionMode();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.uploadSelected:
                        return true;
                    case R.id.deleteSelected:
                        for (SmsLog log : selectedSmsLogs) {
                            smsViewModel.delete(log);
                        }
                        selectedSmsLogs.clear();
                        ((BaseActivity) getActivity()).enableToolbarWhenOaNoneSelectionMode();
                        selection_mode = false;
                        return true;
                }
                return false;
            }
        });


        ImageView backButton = getActivity().findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selection_mode) {
                    for (SmsLog log : selectedSmsLogs) {
                        log.setSelected(false);
                    }
                    selectedSmsLogs.clear();
                    smsByOaAdapter.notifyDataSetChanged();
                    ((BaseActivity) getActivity()).enableToolbarWhenOaNoneSelectionMode();
                    ((BaseActivity) getActivity()).setPageTitle(selectedOa);
                    selection_mode = false;
                } else {
                    ((BaseActivity) getActivity()).unlockAppBar(appBarLayout);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SmsFragment()).commitNow();
                }

            }
        });

        smsByOaAdapter.setOnOaItemClickListener(new SmsByOaAdapter.OnOaItemClickListener() {
            @Override
            public void onOaItemClick(SmsLog smsLog) {
                if (selection_mode) {
                    // handle selections
                    if (smsLog.isSelected()) {
                        selectedSmsLogs.remove(smsLog);
                    } else {
                        selectedSmsLogs.add(smsLog);
                    }
                    ((BaseActivity) getActivity()).setPageTitle(selectedSmsLogs.size() + " Selected");
                }
            }
        });

        smsByOaAdapter.setOnOaItemLongClickListener(new SmsByOaAdapter.OnOaItemLongClickListener() {
            @Override
            public void onOaItemLongClick(SmsLog smsLog) {
                ((BaseActivity) getActivity()).enableToolbarWhenOaSelectionMode();
                selection_mode = true;
                selectedSmsLogs.add(smsLog);
                ((BaseActivity) getActivity()).setPageTitle(selectedSmsLogs.size() + " Selected");

                ImageView backButton = (ImageView) getActivity().findViewById(R.id.backBtn);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selection_mode = false;
                        for (SmsLog log : selectedSmsLogs) {
                            log.setSelected(false);
                        }
                        selectedSmsLogs.clear();
                        smsByOaAdapter.notifyDataSetChanged();
                        ((BaseActivity) getActivity()).enableToolbarWhenOaNoneSelectionMode();
                        ((BaseActivity) getActivity()).setPageTitle(selectedOa);
                    }
                });


            }
        });

        return view;
    }
}
