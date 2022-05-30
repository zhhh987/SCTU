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

public class AddEditPhoneActivity extends AppCompatActivity {

    private EditText date_input, time_input, info_input;
    Button save_button;
    private PhoneViewModel phoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        date_input = findViewById(R.id.date_input);
        time_input = findViewById(R.id.time_input);
        info_input = findViewById(R.id.info_input);
        save_button = findViewById(R.id.save_button);

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("Edit Phone Log");
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
                    Toast.makeText(AddEditPhoneActivity.this, "Inputs cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (intent.hasExtra("EXTRA_ID")) {
                    int id = intent.getIntExtra("EXTRA_ID", -1);
                    if (id == -1) {
                        Toast.makeText(AddEditPhoneActivity.this, "Phone log cannot be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PhoneLog phoneLog = new PhoneLog(date,time,info,false);
                    phoneLog.setId(id);
                    phoneViewModel.update(phoneLog);
                    Toast.makeText(AddEditPhoneActivity.this, "Phone log updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    PhoneLog phoneLog = new PhoneLog(date,time,info,false);
                    phoneViewModel.insert(phoneLog);
                    Toast.makeText(AddEditPhoneActivity.this, "Phone log saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}