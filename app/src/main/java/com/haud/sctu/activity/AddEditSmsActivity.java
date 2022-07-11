package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Dao;

import com.haud.sctu.R;
import com.haud.sctu.model.CallLog;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.SmsViewModel;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class AddEditSmsActivity extends BaseActivity {

    Button save_button;
    private SmsViewModel smsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sms);
        smsViewModel = new ViewModelProvider(this).get(SmsViewModel.class);
        lockAppBarClosed(getAppBarLayout());

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String dateString = "20-05-2022 09:14:11";

                try {
                    Date date = sdf.parse(dateString);
                    long receivedDateTime = date.getTime();
                    SmsLog smsLog = new SmsLog(1, (byte) 6433,"HS161S","123123",receivedDateTime,
                            "Your verification code is 54134. Do not share this code with anyone.","HFA8401",
                            "731HJV2", 2,false,false);
                    smsViewModel.insert(smsLog);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }


}

