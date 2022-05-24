package com.haud.sctu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddEditSmsActivity extends AppCompatActivity {

    private EditText date_input, time_input, info_input;
    Button save_button;
    private SmsViewModel smsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sms);
        smsViewModel = new ViewModelProvider(this).get(SmsViewModel.class);

        date_input = findViewById(R.id.date_input);
        time_input = findViewById(R.id.time_input);
        info_input = findViewById(R.id.info_input);
        save_button = findViewById(R.id.save_button);

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("Edit SMS Log");
            date_input.setText(intent.getStringExtra("EXTRA_DATE"));
            time_input.setText(intent.getStringExtra("EXTRA_TIME"));
            info_input.setText(intent.getStringExtra("EXTRA_INFO"));
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = date_input.getText().toString();
                String time = time_input.getText().toString();
                String info = info_input.getText().toString();

                if (date.trim().isEmpty() || time.trim().isEmpty() || info.trim().isEmpty()) {
                    Toast.makeText(AddEditSmsActivity.this, "Inputs cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (intent.hasExtra("EXTRA_ID")) {
                    int id = intent.getIntExtra("EXTRA_ID", -1);
                    if (id == -1) {
                        Toast.makeText(AddEditSmsActivity.this, "SMS log cannot be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SmsLog smsLog = new SmsLog(date,time,info,false);
                    smsLog.setId(id);
                    smsViewModel.update(smsLog);
                    Toast.makeText(AddEditSmsActivity.this, "SMS log updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    SmsLog smsLog = new SmsLog(date,time,info,false);
                    smsViewModel.insert(smsLog);
                    Toast.makeText(AddEditSmsActivity.this, "SMS log saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}