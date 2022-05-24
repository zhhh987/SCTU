package com.haud.sctu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.getMenu().findItem(R.id.delete_icon).setVisible(false);
        toolbar.getMenu().findItem(R.id.upload_icon).setVisible(false);
        toolbar.getMenu().findItem(R.id.delete_all_sms_logs).setVisible(false);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // non-selection mode menu
                switch (item.getItemId()) {
                    case R.id.delete_all_phone_logs:
                        phoneViewModel.deleteAllPhoneLogs();
                        Toast.makeText(getContext(), "All phone logs deleted", Toast.LENGTH_SHORT).show();
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
                    collapsingToolbarLayout.setTitle(selectedPhoneLogs.size() + " Selected");
                } else {
                    // get existing log for updating
                    Intent intent = new Intent(getContext(), AddEditPhoneActivity.class);
                    intent.putExtra("EXTRA_ID", phoneLog.getId());
                    intent.putExtra("EXTRA_DATE", phoneLog.getDate());
                    intent.putExtra("EXTRA_TIME", phoneLog.getTime());
                    intent.putExtra("EXTRA_INFO", phoneLog.getInfo());
                    startActivity(intent);
                }
            }
        });

        phoneAdapter.setOnItemLongClickListener(new PhoneAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(PhoneLog phoneLog) {
                selectedPhoneLogs.add(phoneLog);
                selection_mode = true;
                collapsingToolbarLayout.setTitle(selectedPhoneLogs.size() + " Selected");

                toolbar.getMenu().findItem(R.id.delete_icon).setVisible(true);
                toolbar.getMenu().findItem(R.id.upload_icon).setVisible(true);
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // selection mode menu
                        switch (item.getItemId()) {
                            case R.id.delete_all_phone_logs:
                                phoneViewModel.deleteAllPhoneLogs();
                                Toast.makeText(getContext(), "All phone logs deleted", Toast.LENGTH_SHORT).show();
                                exitSelectionMode(collapsingToolbarLayout,toolbar);
                                selectedPhoneLogs.clear();
                                return true;
                            case R.id.delete_icon:
                                for (PhoneLog log : selectedPhoneLogs) {
                                    phoneViewModel.delete(log);
                                }
                                if (selectedPhoneLogs.size() == 1) {
                                    Toast.makeText(getContext(), "1 phone log deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), selectedPhoneLogs.size() + " phone logs deleted", Toast.LENGTH_SHORT).show();
                                }
                                exitSelectionMode(collapsingToolbarLayout,toolbar);
                                selectedPhoneLogs.clear();
                                return true;
                            case R.id.upload_icon:
                                exitSelectionMode(collapsingToolbarLayout,toolbar);
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
                        exitSelectionMode(collapsingToolbarLayout,toolbar);
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
                    exitSelectionMode(collapsingToolbarLayout, toolbar);
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
            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            exitSelectionMode(collapsingToolbarLayout, toolbar);
            selectedPhoneLogs.clear();
        }
        super.onPause();
    }

    private void exitSelectionMode(CollapsingToolbarLayout collapsingToolbar, Toolbar toolbar) {
        toolbar.setNavigationIcon(null);
        toolbar.getMenu().findItem(R.id.delete_icon).setVisible(false);
        toolbar.getMenu().findItem(R.id.upload_icon).setVisible(false);
        selection_mode = false;
    }
}






