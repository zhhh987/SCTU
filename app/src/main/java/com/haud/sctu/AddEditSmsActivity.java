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

    private EditText date_input, sid_input, content_input;
    Button save_button;
    private SmsViewModel smsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sms);
        smsViewModel = new ViewModelProvider(this).get(SmsViewModel.class);

        sid_input = findViewById(R.id.sms_sid_input);
        content_input = findViewById(R.id.sms_content_input);
        date_input = findViewById(R.id.sms_date_input);
        save_button = findViewById(R.id.save_button);

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("Edit SMS Log");
            sid_input.setText(intent.getStringExtra("EXTRA_SID"));
            content_input.setText(intent.getStringExtra("EXTRA_CONTENT"));
            date_input.setText(intent.getStringExtra("EXTRA_DATE"));

        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = sid_input.getText().toString();
                String content = content_input.getText().toString();
                String date = date_input.getText().toString();

                if (sid.trim().isEmpty() || content.trim().isEmpty() || date.trim().isEmpty()) {
                    Toast.makeText(AddEditSmsActivity.this, "Inputs cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (intent.hasExtra("EXTRA_ID")) {
                    int id = intent.getIntExtra("EXTRA_ID", -1);
                    if (id == -1) {
                        Toast.makeText(AddEditSmsActivity.this, "SMS log cannot be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SmsLog smsLog = new SmsLog(sid,content,date,false);
                    smsLog.setId(id);
                    smsViewModel.update(smsLog);
                    Toast.makeText(AddEditSmsActivity.this, "SMS log updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    SmsLog smsLog = new SmsLog(sid,content,date,false);
                    smsViewModel.insert(smsLog);
                    Toast.makeText(AddEditSmsActivity.this, "SMS log saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}