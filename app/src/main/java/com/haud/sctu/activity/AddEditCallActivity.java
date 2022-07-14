package com.haud.sctu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.haud.sctu.model.CallLog;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.CallViewModel;
import com.haud.sctu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditCallActivity extends BaseActivity {

    Button save_button;
    private CallViewModel callViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_call);
        callViewModel = new ViewModelProvider(this).get(CallViewModel.class);
        lockAppBarClosed(getAppBarLayout());

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String startDateString = "17-06-2022 22:05:22";
                String endDateString = "17-06-2022 22:16:34";

                try {
                    Date startDateTime = sdf.parse(startDateString);
                    Date endDateTime = sdf.parse(endDateString);
                    long startTime = startDateTime.getTime();
                    long endTime = endDateTime.getTime();
                    CallLog callLog = new CallLog(64262,"JVEH123","FAJHF23","1234567","Missed",startTime,endTime,
                            "FI223","4124","JDIWD","E014",
                            startTime,endTime,5645,63456,
                            null,false,true,false);
                    callViewModel.insert(callLog);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}