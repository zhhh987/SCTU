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
import com.haud.sctu.helper.SmsAdapter;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.SmsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SmsFragment extends Fragment {
    private ArrayList<SmsLog> selectedSmsLogs = new ArrayList<>();
    private boolean selection_mode = false;
    final SmsAdapter smsAdapter = new SmsAdapter();

    public SmsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.smsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(smsAdapter);

        SmsViewModel smsViewModel = new ViewModelProvider(getActivity()).get(SmsViewModel.class);
        smsViewModel.getAllSmsLogs().observe(getViewLifecycleOwner(), new Observer<List<SmsLog>>() {
            @Override
            public void onChanged(List<SmsLog> smsLogs) {
                smsAdapter.setSmsLogs(smsLogs);

            }
        });

        AppBarLayout appBarLayout = ((BaseActivity) getActivity()).getAppBarLayout();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.main_menu);
        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
        toolbar.getMenu().findItem(R.id.uploadAllCalls).setVisible(false);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        Intent intent = new Intent(getActivity(),SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.uploadAllSms:
                        return true;
                    case R.id.uploadSelected:
                        return true;
                    case R.id.deleteSelected:
                        for (SmsLog log : selectedSmsLogs) {
                            String selectedOa = log.getOa();
                            smsViewModel.deleteAllSmsLogsByOa(selectedOa);
                        }
                        selectedSmsLogs.clear();
                        smsAdapter.allSmsOa.clear();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                        selection_mode = false;
                        smsAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.searchLogs:
                        ((BaseActivity) getActivity()).lockAppBarClosed(appBarLayout);
                        ((BaseActivity) getActivity()).enableToolbarWhenSearchMode();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SearchFragment()).commitNow();
                        return true;
                }
                return false;
            }
        });

        smsAdapter.setOnItemClickListener(new SmsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SmsLog smsLog) {
                if (selection_mode) {
                    // handle selections
                    if (smsLog.isSelected()) {
                        selectedSmsLogs.remove(smsLog);
                    } else {
                        selectedSmsLogs.add(smsLog);
                    }
                    ((BaseActivity) getActivity()).setPageTitle(selectedSmsLogs.size() + " Selected");
                } else {
                    ((MainActivity) getActivity()).selectedSmsOa = smsLog.getOa();
                    ((BaseActivity) getActivity()).lockAppBarClosed(appBarLayout);
                    ((BaseActivity) getActivity()).enableToolbarWhenOaNoneSelectionMode();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SmsByOaFragment()).commitNow();
                }
            }
        });

        smsAdapter.setOnItemLongClickListener(new SmsAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(SmsLog smsLog) {
                ((BaseActivity) getActivity()).enableToolbarWhenSelectionMode();
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
                        smsAdapter.allSmsOa.clear();
                        smsAdapter.notifyDataSetChanged();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                    }
                });
            }
        });

        return view;
    }

}
