package com.example.covid19cv;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterMunicipios.ItemClickListener {
    private ArrayList<Municipio> municipio = new ArrayList<>();
    private AdapterMunicipios adapter;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitReport = findViewById(R.id.report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent reportIntent = new Intent(getApplicationContext(), Report.class);
                startActivity(reportIntent);
            }
        });
        try {
            this.Init();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = null;
        try {
            adapter = new AdapterMunicipios(this.municipio);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.setClickListener(MainActivity.this);
            recyclerView.setAdapter(adapter);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.covid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subitem:
                //open site
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://coronavirus.san.gva.es/inicio"));
                startActivity(browserIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRVItemClick(View view, int position) {
        final Intent intent;
        Municipio adap = adapter.getItem(position);
        String[] muniData = new String[] {
                adap.getMunicipi(),
                String.valueOf(adap.getCode()),
                String.valueOf(adap.getId()),
                String.valueOf(adap.getCasos()),
                String.valueOf(adap.getCasesPCR14()),
                adap.getCasesPCR14cumulativeIncidence(),
                String.valueOf(adap.getDeaths()),
                adap.getDeathRate()
        };
        intent = new Intent(this, ItemMunicipalityDetail.class);
        intent.putExtra("datas", muniData);
        startActivity(intent);
    }

        public void Init() throws JSONException {
            InputStream is = getResources().openRawResource(R.raw.data);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);

                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject jsonObject = new JSONObject(writer.toString());
            JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonObjectResult.optJSONArray("records");

            if (jsonArray != null) {

                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                        int id = Integer.parseInt(jsonObjectItem.getString("_id"));
                        int code = Integer.parseInt(jsonObjectItem.getString("CodMunicipio"));
                        String municipi = jsonObjectItem.getString("Municipi");
                        int casos  = Integer.parseInt(jsonObjectItem.getString("Casos PCR+"));
                    int casesPCR14 = Integer.parseInt(jsonObjectItem.getString("Casos PCR+ 14 dies"));
                    String casesPCR14cumulativeIncidence = jsonObjectItem.getString("Incidència acumulada PCR+14");
                    int deaths = Integer.parseInt(jsonObjectItem.getString("Defuncions"));
                    String deathRate = jsonObjectItem.getString("Taxa de defunció");
                        this.municipio.add(new Municipio(id, code, municipi, casos, casesPCR14, casesPCR14cumulativeIncidence,deaths, deathRate));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //adapterMunicipality.setMunicipality(this.municipality);
        }

    }


