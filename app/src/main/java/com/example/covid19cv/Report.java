package com.example.covid19cv;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Report extends AppCompatActivity {
    String description = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] reportData = new String[]{"firstItem"
        };
        Intent receivedIntent = getIntent();
        String muniName = receivedIntent.getStringExtra("muniName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        Button submitReport = findViewById(R.id.submit);
        submitReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("Clicked");
                Context context = getApplicationContext();
                CharSequence text = "Report Submitted, feel better!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                description = "";
                CheckBox cb1 = findViewById(R.id.fever); if (cb1.isChecked()){description+="Fever, ";}
                CheckBox cb2 = findViewById(R.id.cough); if (cb2.isChecked()){description+="Cough, ";}
                CheckBox cb3 = findViewById(R.id.breathing); if (cb3.isChecked()){description+="Difficulty breathing, ";}
                CheckBox cb4 = findViewById(R.id.fatigue); if (cb4.isChecked()){description+="Fatigue, ";}
                CheckBox cb5 = findViewById(R.id.aching); if (cb5.isChecked()){description+="Aching, ";}
                CheckBox cb6 = findViewById(R.id.headache); if (cb6.isChecked()){description+="Headache, ";}
                CheckBox cb7 = findViewById(R.id.congestion); if (cb7.isChecked()){description+="Congestion, ";}
                CheckBox cb8 = findViewById(R.id.headache); if (cb8.isChecked()){description+="Headache, ";}
                CheckBox cb9 = findViewById(R.id.nausea); if (cb9.isChecked()){description+="Nausea, ";}
                CheckBox cb10 = findViewById(R.id.diarrhea); if (cb10.isChecked()){description+="Diarrhea, ";}
                CheckBox cb11 = findViewById(R.id.tasteLoss); if (cb11.isChecked()){description+="Loss of Taste, ";}
                CheckBox cb12 = findViewById(R.id.throat); if (cb12.isChecked()){description+="Sore Throat, ";}

                description = description.substring(0, description.length()-2);
            }
        });


        Button viewReports = findViewById(R.id.viewAll);
        viewReports.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CheckBox cb1 = findViewById(R.id.fever); if (cb1.isChecked()){cb1.toggle();}
                CheckBox cb2 = findViewById(R.id.cough); if (cb2.isChecked()){cb2.toggle();}
                CheckBox cb3 = findViewById(R.id.breathing); if (cb3.isChecked()){cb3.toggle();}
                CheckBox cb4 = findViewById(R.id.fatigue); if (cb4.isChecked()){cb4.toggle();}
                CheckBox cb5 = findViewById(R.id.aching); if (cb5.isChecked()){cb5.toggle();}
                CheckBox cb6 = findViewById(R.id.headache); if (cb6.isChecked()){cb6.toggle();}
                CheckBox cb7 = findViewById(R.id.congestion); if (cb7.isChecked()){cb7.toggle();}
                CheckBox cb8 = findViewById(R.id.headache); if (cb8.isChecked()){cb8.toggle();}
                CheckBox cb9 = findViewById(R.id.nausea); if (cb9.isChecked()){cb9.toggle();}
                CheckBox cb10 = findViewById(R.id.diarrhea); if (cb10.isChecked()){cb10.toggle();}
                CheckBox cb11 = findViewById(R.id.tasteLoss); if (cb11.isChecked()){cb11.toggle();}
                CheckBox cb12 = findViewById(R.id.throat); if (cb12.isChecked()){cb12.toggle();}

                Intent reportIntent = new Intent(getApplicationContext(), ItemReportDetail.class);
                reportIntent.putExtra("name", muniName);
                reportIntent.putExtra("description", description);
                startActivity(reportIntent);
            }
        });
    }
}