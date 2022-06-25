package com.example.covid19cv;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ItemMunicipalityDetail extends AppCompatActivity {

    private  String[] muni;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subitem:
                //open location
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+muni[0]);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_municipality_detail);
        Intent receivedIntent = getIntent();
        String[] muniVals = receivedIntent.getStringArrayExtra("datas");
        this.muni = muniVals;
        setTitle(muniVals[0]);

        Button submitReport = findViewById(R.id.report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent reportIntent = new Intent(getApplicationContext(), Report.class);
                reportIntent.putExtra("muniName", muniVals[0]);
                startActivity(reportIntent);
            }
        });

        TextView tv1 = (TextView)findViewById(R.id.casos);
        tv1.setText("Cases: "+ muniVals[3]);
        TextView tv2 = (TextView)findViewById(R.id.muniid);
        tv2.setText("ID: "+ muniVals[2]);
        TextView tv3 = (TextView)findViewById(R.id.code);
        tv3.setText("Code: "+ muniVals[1]);
        TextView tv4 = (TextView)findViewById(R.id.deaths);
        tv4.setText("Deaths: "+ muniVals[6]);
        TextView tv5 = (TextView)findViewById(R.id.deathRate);
        tv5.setText("Death Rate: "+ muniVals[7]);
        TextView tv6 = (TextView)findViewById(R.id.PCR1);
        tv6.setText("PCR: "+ muniVals[4]);
        TextView tv7 = (TextView)findViewById(R.id.PCR2);
        tv7.setText("PCR Cumulative: "+ muniVals[5]);


    }

}