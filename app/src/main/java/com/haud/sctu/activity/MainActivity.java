package com.haud.sctu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.haud.sctu.R;
import com.haud.sctu.viewmodel.CallViewModel;
import com.haud.sctu.viewmodel.SmsViewModel;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    String [] simCards = {"All SIM Cards", "SIM 1", "SIM 2"};
    FloatingActionButton add_button;
    String selectedSmsOa;
    String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBarListener();

        new ViewModelProvider(this).get(SmsViewModel.class);
        new ViewModelProvider(this).get(CallViewModel.class);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditSmsActivity.class);
                startActivity(intent);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SmsFragment()).commitNow();
        getBottomNavigation().setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_call:
                        selectedFragment = new CallFragment();
                        add_button = findViewById(R.id.add_button);
                        add_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, AddEditCallActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case R.id.navigation_sms:
                        selectedFragment = new SmsFragment();
                        add_button = findViewById(R.id.add_button);
                        add_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, AddEditSmsActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, selectedFragment).commit();
                }
                return true;
            }
        });

        EditText searchEditText = getSearch();
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

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item,simCards);
        adapter.setDropDownViewResource(R.layout.spinner_menu_item);

        Spinner spinnerExpanded = getSpinnerSimWhenHeaderExpanded();
        spinnerExpanded.setAdapter(adapter);
        spinnerExpanded.setDropDownVerticalOffset(58);
        spinnerExpanded.setOnItemSelectedListener(this);

        Spinner spinnerContracted = getSpinnerSimWhenHeaderContracted();
        spinnerContracted.setAdapter(adapter);
        spinnerContracted.setDropDownVerticalOffset(65);
        spinnerContracted.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;

        if (spinner == getSpinnerSimWhenHeaderExpanded()) {
            getSpinnerSimWhenHeaderContracted().setSelection(i);
            if (i == 0) {
                getSelectedSimInfo().setText(getSpinnerSimWhenHeaderExpanded().getAdapter().getCount() - 1 + " SIM Card(s) Selected");
            } else {
                // filler sim info
                getSelectedSimInfo().setText("ICCID 89123456789123456789\n" + "MSISDN 6289876545678987");
            }
        } else if (spinner == getSpinnerSimWhenHeaderContracted()){
            getSpinnerSimWhenHeaderExpanded().setSelection(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}