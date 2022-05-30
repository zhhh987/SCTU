package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.android.material.tabs.TabLayout;
import com.haud.sctu.helper.PhoneAdapter;
import com.haud.sctu.model.PhoneLog;
import com.haud.sctu.viewmodel.PhoneViewModel;
import com.haud.sctu.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneFragment extends Fragment{
    private PhoneViewModel phoneViewModel;
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

        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
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
        tabLayout.addTab(firstTab,true);
        tabLayout.addTab(secondTab,false);

        TextView selectedCount = (TextView) getActivity().findViewById(R.id.selected_logs_count);

        TextView logsCountText = (TextView) getActivity().findViewById(R.id.total_logs_count);
        logsCountText.setText(String.valueOf(phoneAdapter.getItemCount()));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.getMenu().findItem(R.id.delete_icon).setVisible(false);
        toolbar.getMenu().findItem(R.id.upload_icon).setVisible(false);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // non-selection mode menu
                switch (item.getItemId()) {
                    case R.id.upload_all_SMS:
                        exitSelectionMode(toolbar, selectedCount);
                        return true;
                    case R.id.settings:
                        exitSelectionMode(toolbar, selectedCount);
                        Intent intent = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(intent);
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
                    selectedCount.setText(selectedPhoneLogs.size() + " Selected");
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
                selectedPhoneLogs.add(phoneLog);
                selection_mode = true;
                selectedCount.setText(selectedPhoneLogs.size() + " Selected");

                toolbar.getMenu().findItem(R.id.delete_icon).setVisible(true);
                toolbar.getMenu().findItem(R.id.upload_icon).setVisible(true);
                toolbar.getMenu().findItem(R.id.settings).setVisible(false);
                toolbar.getMenu().findItem(R.id.upload_all_SMS).setVisible(false);
                toolbar.getMenu().findItem(R.id.search_icon).setVisible(false);

                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // selection mode menu
                        switch (item.getItemId()) {
                            case R.id.delete_icon:
                                for (PhoneLog log : selectedPhoneLogs) {
                                    phoneViewModel.delete(log);
                                }
                                if (selectedPhoneLogs.size() == 1) {
                                    Toast.makeText(getContext(), "1 phone log deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), selectedPhoneLogs.size() + " phone logs deleted", Toast.LENGTH_SHORT).show();
                                }
                                exitSelectionMode(toolbar, selectedCount);
                                selectedPhoneLogs.clear();
                                return true;
                            case R.id.upload_icon:
                                exitSelectionMode(toolbar, selectedCount);
                                selectedPhoneLogs.clear();
                                return true;
                        }
                        return false;
                    }
                });

                // cancel button to exit selection mode w/o performing any actions
                toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exitSelectionMode(toolbar, selectedCount);
                        for (PhoneLog log : selectedPhoneLogs) {
                            log.setIsSelected(false);
                        }
                        selectedPhoneLogs.clear();
                        Toast.makeText(getContext(), "All selection cleared", Toast.LENGTH_SHORT).show();
                        phoneAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                phoneViewModel.delete(phoneAdapter.getPhoneLogAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Phone log deleted", Toast.LENGTH_SHORT).show();

                if (selection_mode) {
                    exitSelectionMode(toolbar, selectedCount);
                    selectedPhoneLogs.clear();
                }
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    // exit selection mode if bottom nav or add button is pressed
    @Override
    public void onPause() {
        if (selection_mode) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            TextView selectedCount = (TextView) getActivity().findViewById(R.id.selected_logs_count);
            exitSelectionMode(toolbar, selectedCount);
            selectedPhoneLogs.clear();
        }
        super.onPause();
    }

    private void exitSelectionMode(Toolbar toolbar, TextView selectedCount) {
        selectedCount.setText(null);
        toolbar.setNavigationIcon(null);
        toolbar.getMenu().findItem(R.id.delete_icon).setVisible(false);
        toolbar.getMenu().findItem(R.id.upload_icon).setVisible(false);
        toolbar.getMenu().findItem(R.id.search_icon).setVisible(true);
        selection_mode = false;
    }
}






