package com.haud.sctu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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
                            smsViewModel.delete(log);
                        }
                        selectedSmsLogs.clear();

                        return true;
                    case R.id.searchLogs:
                        AppBarLayout appBarLayout = ((BaseActivity) getActivity()).getAppBarLayout();
                        ((BaseActivity) getActivity()).lockAppBarClosed(appBarLayout);
                        ((BaseActivity) getActivity()).enableToolbarWhenSearchMode();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SearchFragment()).commitNow();
                        return true;
                }
                ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                selection_mode = false;
                return false;
            }
        });

        smsAdapter.setOnItemClickListener(new SmsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SmsLog smsLog) {
                if (selection_mode) {
                    // handle selections
                    if (smsLog.getIsSelected()) {
                        selectedSmsLogs.remove(smsLog);
                    } else {
                        selectedSmsLogs.add(smsLog);
                    }
                    ((BaseActivity) getActivity()).setPageTitle(selectedSmsLogs.size() + " Selected");
                } else {
                    // get existing log for updating
                    Intent intent = new Intent(getContext(), AddEditSmsActivity.class);
                    intent.putExtra("EXTRA_ID", smsLog.getId());
                    intent.putExtra("EXTRA_SID", smsLog.getSid());
                    intent.putExtra("EXTRA_CONTENT", smsLog.getContent());
                    intent.putExtra("EXTRA_DATE", smsLog.getDate());
                    startActivity(intent);
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
                            log.setIsSelected(false);
                        }
                        selectedSmsLogs.clear();
                        smsAdapter.notifyDataSetChanged();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                    }
                });
            }
        });

        return view;
    }

}
