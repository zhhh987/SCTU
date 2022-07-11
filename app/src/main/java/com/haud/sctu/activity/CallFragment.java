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
import com.google.android.material.tabs.TabLayout;
import com.haud.sctu.helper.CallAdapter;
import com.haud.sctu.model.CallLog;
import com.haud.sctu.viewmodel.CallViewModel;
import com.haud.sctu.R;

import java.util.ArrayList;
import java.util.List;

public class CallFragment extends Fragment {
    private ArrayList<CallLog> selectedCallLogs = new ArrayList<>();
    private boolean selection_mode = false;
    final CallAdapter callAdapter = new CallAdapter();

    public CallFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.callRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callAdapter);

        CallViewModel callViewModel = new ViewModelProvider(getActivity()).get(CallViewModel.class);
        callViewModel.getAllCallLogs().observe(getViewLifecycleOwner(), new Observer<List<CallLog>>() {
            @Override
            public void onChanged(List<CallLog> callLogs) {
                callAdapter.setCallLogs(callLogs);
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.call_logs_tab);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("SCTU Call Logs");
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Device Call History");
        tabLayout.addTab(firstTab, true);
        tabLayout.addTab(secondTab, false);

        AppBarLayout appBarLayout = ((BaseActivity) getActivity()).getAppBarLayout();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.main_menu);
        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
        toolbar.getMenu().findItem(R.id.uploadAllSms).setVisible(false);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        Intent intent = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.uploadAllSms:
                        return true;
                    case R.id.uploadAllCalls:
                        return true;
                    case R.id.uploadSelected:
                        return true;
                    case R.id.deleteSelected:
                        for (CallLog log : selectedCallLogs) {
                            callViewModel.delete(log);
                        }
                        selectedCallLogs.clear();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
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

        callAdapter.setOnItemClickListener(new CallAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CallLog callLog) {
                if (selection_mode) {
                    // handle selections
                    if (callLog.isSelected()) {
                        selectedCallLogs.remove(callLog);
                    } else {
                        selectedCallLogs.add(callLog);
                    }
                    ((BaseActivity) getActivity()).setPageTitle(selectedCallLogs.size() + " Selected");
                }
            }
        });

        callAdapter.setOnItemLongClickListener(new CallAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(CallLog callLog) {
                ((BaseActivity) getActivity()).enableToolbarWhenSelectionMode();
                selectedCallLogs.add(callLog);
                selection_mode = true;
                ((BaseActivity) getActivity()).setPageTitle(selectedCallLogs.size() + " Selected");

                ImageView backButton = (ImageView) getActivity().findViewById(R.id.backBtn);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selection_mode = false;
                        for (CallLog log : selectedCallLogs) {
                            log.setSelected(false);
                        }
                        selectedCallLogs.clear();
                        callAdapter.notifyDataSetChanged();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                    }
                });
            }
        });

        return view;

    }
}






