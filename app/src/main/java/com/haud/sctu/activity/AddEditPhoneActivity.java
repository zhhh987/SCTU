package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.haud.sctu.model.PhoneLog;
import com.haud.sctu.viewmodel.PhoneViewModel;
import com.haud.sctu.R;

public class AddEditPhoneActivity extends BaseActivity {

    private EditText sid_input, duration_input, sim_number_input;
    Button save_button;
    private PhoneViewModel phoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        sid_input = findViewById(R.id.phone_sid_input);
        duration_input = findViewById(R.id.call_duration_input);
        sim_number_input = findViewById(R.id.sim_number_input);
        save_button = findViewById(R.id.save_button);

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("Edit Phone Log");
            sid_input.setText(intent.getStringExtra("EXTRA_SID"));

            duration_input.setText(intent.getStringExtra("EXTRA_DURATION"));
            sim_number_input.setText(intent.getStringExtra("EXTRA_SIM"));
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = sid_input.getText().toString();
                String duration = duration_input.getText().toString();
                String sim = sim_number_input.getText().toString();

                if (sid.trim().isEmpty() || duration.trim().isEmpty() || sim.trim().isEmpty()) {
                    Toast.makeText(AddEditPhoneActivity.this, "Inputs cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (intent.hasExtra("EXTRA_ID")) {
                    int id = intent.getIntExtra("EXTRA_ID", -1);
                    if (id == -1) {
                        Toast.makeText(AddEditPhoneActivity.this, "Phone log cannot be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PhoneLog phoneLog = new PhoneLog(sid,duration,sim,false,false);
                    phoneLog.setId(id);
                    phoneViewModel.update(phoneLog);
                    Toast.makeText(AddEditPhoneActivity.this, "Phone log updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    PhoneLog phoneLog = new PhoneLog(sid,duration,sim,false,false);
                    phoneViewModel.insert(phoneLog);
                    Toast.makeText(AddEditPhoneActivity.this, "Phone log saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}