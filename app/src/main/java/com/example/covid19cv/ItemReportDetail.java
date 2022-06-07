package com.example.covid19cv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItemReportDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_report);
        Intent receivedIntent = getIntent();
        String muniName = receivedIntent.getStringExtra("name");
        String description = receivedIntent.getStringExtra("description");

        //sets time to current time by default
        TextView tb1 = findViewById(R.id.date);
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        String newFormat = timeStamp.substring(6) + "/" + timeStamp.substring(4,6) + "/" + timeStamp.substring(0,4);
        tb1.setText(newFormat);

        setTitle(muniName + " Reports");
        TextView tb2 = findViewById(R.id.description);
        System.out.print(description);
        tb2.setText(description);
    }
}
