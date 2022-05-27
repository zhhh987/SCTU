package com.haud.sctu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add_button;
    String [] simCards = {"All SIM Cards", "SIM 1", "SIM 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new SmsFragment()).commitNow();

        TextView simInfo = this.findViewById(R.id.selected_sim_info);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item,simCards);
        adapter.setDropDownViewResource(R.layout.spinner_menu_item);
        spinner.setAdapter(adapter);

        spinner.setDropDownHorizontalOffset(-33);
        spinner.setDropDownVerticalOffset(68);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    simInfo.setText(spinner.getAdapter().getCount() - 1 + " SIM Card(s) Selected");
                } else {
                    // filler sim info
                    simInfo.setText("ICCID 89123456789123456789\n" + "MSISDN 6289876545678987");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditSmsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_phone:
                        selectedFragment = new PhoneFragment();
                        add_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, AddEditPhoneActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;

                    case R.id.navigation_sms:
                        selectedFragment = new SmsFragment();
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
    }
}