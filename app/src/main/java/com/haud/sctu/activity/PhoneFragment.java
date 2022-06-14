package com.haud.sctu.activity;

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
import com.google.android.material.tabs.TabLayout;
import com.haud.sctu.helper.PhoneAdapter;
import com.haud.sctu.model.PhoneLog;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.PhoneViewModel;
import com.haud.sctu.R;
import com.haud.sctu.viewmodel.SmsViewModel;

import java.util.ArrayList;
import java.util.List;

public class PhoneFragment extends Fragment {
    private ArrayList<PhoneLog> selectedPhoneLogs = new ArrayList<>();
    private boolean selection_mode = false;

    public PhoneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.phoneRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final PhoneAdapter phoneAdapter = new PhoneAdapter();
        recyclerView.setAdapter(phoneAdapter);

        PhoneViewModel phoneViewModel = new ViewModelProvider(getActivity()).get(PhoneViewModel.class);
        phoneViewModel.getAllPhoneLogs().observe(getViewLifecycleOwner(), new Observer<List<PhoneLog>>() {
            @Override
            public void onChanged(List<PhoneLog> phoneLogs) {
                phoneAdapter.setPhoneLogs(phoneLogs);
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.call_logs_tab);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("SCTU Call Logs");
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Device Call History");
        tabLayout.addTab(firstTab, true);
        tabLayout.addTab(secondTab, false);


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
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                        return true;
                    case R.id.deleteSelected:
                        for (PhoneLog log : selectedPhoneLogs) {
                            phoneViewModel.delete(log);
                        }
                        selectedPhoneLogs.clear();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                        return true;
                    case R.id.searchLogs:
                        AppBarLayout appBarLayout = ((BaseActivity) getActivity()).getAppBarLayout();
                        ((BaseActivity) getActivity()).lockAppBarClosed(appBarLayout);
                        ((BaseActivity) getActivity()).enableToolbarWhenSearchMode();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SearchFragment()).commitNow();
                        return true;
                }
                return false;
            }
        });

        phoneAdapter.setOnItemClickListener(new PhoneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PhoneLog phoneLog) {
                if (selection_mode) {
                    // handle selections
                    if (phoneLog.getIsSelected()) {
                        selectedPhoneLogs.remove(phoneLog);
                    } else {
                        selectedPhoneLogs.add(phoneLog);
                    }
                    ((BaseActivity) getActivity()).setPageTitle(selectedPhoneLogs.size() + " Selected");
                } else {
                    // get existing log for updating
                    Intent intent = new Intent(getContext(), AddEditPhoneActivity.class);
                    intent.putExtra("EXTRA_ID", phoneLog.getId());
                    intent.putExtra("EXTRA_SID", phoneLog.getSid());
                    intent.putExtra("EXTRA_DURATION", phoneLog.getDuration());
                    intent.putExtra("EXTRA_SIM_NUMBER", phoneLog.getSimCardNumber());
                    startActivity(intent);
                }
            }
        });

        phoneAdapter.setOnItemLongClickListener(new PhoneAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(PhoneLog phoneLog) {
                ((BaseActivity) getActivity()).enableToolbarWhenSelectionMode();
                selectedPhoneLogs.add(phoneLog);
                selection_mode = true;
                ((BaseActivity) getActivity()).setPageTitle(selectedPhoneLogs.size() + " Selected");

                ImageView backButton = (ImageView) getActivity().findViewById(R.id.backBtn);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selection_mode = false;
                        for (PhoneLog log : selectedPhoneLogs) {
                            log.setIsSelected(false);
                        }
                        selectedPhoneLogs.clear();
                        phoneAdapter.notifyDataSetChanged();
                        ((BaseActivity) getActivity()).enableToolBarWhenCollapsableEnabled();
                    }
                });
            }
        });

        return view;

    }
}






