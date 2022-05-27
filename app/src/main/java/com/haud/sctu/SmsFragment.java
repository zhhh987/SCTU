package com.haud.sctu;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SmsFragment extends Fragment{
    private SmsViewModel smsViewModel;
    private ArrayList<SmsLog> selectedSmsLogs = new ArrayList<>();
    private boolean selection_mode = false;

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

        final SmsAdapter smsAdapter = new SmsAdapter();
        recyclerView.setAdapter(smsAdapter);

        smsViewModel = new ViewModelProvider(this).get(SmsViewModel.class);
        smsViewModel.getAllSmsLogs().observe(getViewLifecycleOwner(), new Observer<List<SmsLog>>() {
            @Override
            public void onChanged(List<SmsLog> smsLogs) {
                smsAdapter.setSmsLogs(smsLogs);
            }
        });

        TextView selectedCount = (TextView) getActivity().findViewById(R.id.selected_logs_count);

        TextView logsCountText = (TextView) getActivity().findViewById(R.id.total_logs_count);
        logsCountText.setText(String.valueOf(smsAdapter.getItemCount()));

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
                        Intent intent = new Intent(getActivity(),SettingsActivity.class);
                        startActivity(intent);

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
                    if (smsLog.getIsSelected()) {
                        selectedSmsLogs.remove(smsLog);
                    } else {
                        selectedSmsLogs.add(smsLog);
                    }
                    selectedCount.setText(selectedSmsLogs.size() + " Selected");
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
                selection_mode = true;
                selectedSmsLogs.add(smsLog);
                selectedCount.setText(selectedSmsLogs.size() + " Selected");

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
                                for (SmsLog log : selectedSmsLogs) {
                                    smsViewModel.delete(log);
                                }
                                if (selectedSmsLogs.size() == 1) {
                                    Toast.makeText(getContext(), "1 SMS log deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), selectedSmsLogs.size() + " SMS logs deleted", Toast.LENGTH_SHORT).show();
                                }
                                exitSelectionMode(toolbar, selectedCount);
                                selectedSmsLogs.clear();
                                return true;
                            case R.id.upload_icon:
                                exitSelectionMode(toolbar, selectedCount);
                                selectedSmsLogs.clear();
                                return true;
                        }
                        return false;
                    }
                });

                // back button to exit selection mode w/o performing any actions
                toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exitSelectionMode(toolbar, selectedCount);
                        for (SmsLog log : selectedSmsLogs) {
                            log.setIsSelected(false);
                        }
                        selectedSmsLogs.clear();
                        Toast.makeText(getContext(), "All selection cleared", Toast.LENGTH_SHORT).show();
                        smsAdapter.notifyDataSetChanged();
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
                smsViewModel.delete(smsAdapter.getSmsLogAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "SMS log deleted", Toast.LENGTH_SHORT).show();

                if (selection_mode) {
                    exitSelectionMode(toolbar, selectedCount);
                    selectedSmsLogs.clear();
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
            selectedSmsLogs.clear();
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
