package com.example.covid19cv;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Report extends AppCompatActivity {
    String description = "";
    String time;
    DatabaseHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        Intent receivedIntent = getIntent();
        String muniName;
        muniName = receivedIntent.getStringExtra("muniName");

        EditText dateBox = findViewById(R.id.startDate);
        EditText muniBox = findViewById(R.id.munitextbox);
        myHelper = new DatabaseHelper(this);

        //Handles if the user reports from main activity
        if(muniName.toString().equals("")){muniBox.setHint("Municipality");}
        else{muniBox.setHint(muniName.toString());}
        dateBox.setHint(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        //----------------Onclicks-------------------

        Button submitReport = findViewById(R.id.submit);
        submitReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                description = "";
                CheckBox cb1 = findViewById(R.id.fever); if (cb1.isChecked()){description+="Fever, ";}
                CheckBox cb2 = findViewById(R.id.cough); if (cb2.isChecked()){description+="Cough, ";}
                CheckBox cb3 = findViewById(R.id.breathing); if (cb3.isChecked()){description+="Difficulty breathing, ";}
                CheckBox cb4 = findViewById(R.id.fatigue); if (cb4.isChecked()){description+="Fatigue, ";}
                CheckBox cb5 = findViewById(R.id.aching); if (cb5.isChecked()){description+="Aching, ";}
                CheckBox cb6 = findViewById(R.id.headache); if (cb6.isChecked()){description+="Headache, ";}
                CheckBox cb7 = findViewById(R.id.congestion); if (cb7.isChecked()){description+="Congestion, ";}
                CheckBox cb9 = findViewById(R.id.nausea); if (cb9.isChecked()){description+="Nausea, ";}
                CheckBox cb10 = findViewById(R.id.diarrhea); if (cb10.isChecked()){description+="Diarrhea, ";}
                CheckBox cb11 = findViewById(R.id.tasteLoss); if (cb11.isChecked()){description+="Loss of Taste, ";}
                CheckBox cb12 = findViewById(R.id.throat); if (cb12.isChecked()){description+="Sore Throat, ";}


                //checks to see if user set the time
                //sets time to current time by default

                String newMuni;
                if(dateBox.getText().toString().equals("")){
                    time = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                }
                else{
                    time = dateBox.getText().toString();
                }

                //checks to see if the user set the newMuni- if not, muni is the name passed through intent
                if(muniBox.getText().toString().equals("")){
                    newMuni = muniName;
                }
                else{
                    newMuni = muniBox.getText().toString();
                }

                if (description.length()>3){description = description.substring(0, description.length()-2);}// removes the very last comma
                description = newMuni + " - " + time + "\n" + description;

                addData(description);
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

    //---------------------------------------------

    public void addData(String data){
        if (myHelper!= null){
            boolean insert = myHelper.addData(data);
            if (insert){
                makeToast("Report Added!");
            }
            else{
                makeToast("Unable to Add Your Report. Sorry!");
            }}
        else{
            makeToast("Your Helper is Null!");
        }
    }
    public void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
