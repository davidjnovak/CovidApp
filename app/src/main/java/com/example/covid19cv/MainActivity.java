package com.example.covid19cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterMunicipios.ItemClickListener {
    private ArrayList<Municipio> municipios = new ArrayList<>();
    private AdapterMunicipios adapter;
    RecyclerView recyclerView;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String pms = "";
        HTTPConnector myConnector = new HTTPConnector();
        myConnector.execute(pms);


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
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = null;
        try {
            adapter = new AdapterMunicipios(this.municipios);

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

    class HTTPConnector extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList municipioList = new ArrayList();
            String url = "https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id=7fd9a2bf-ffee-4604-907e-643a8009b04e&limit=1000";
            JSONArray myJsonArray;
            String myWriter = getMethod(url);
            JSONObject myObject;
            String municipioJson = myWriter;
            try{
                myObject = new JSONObject(municipioJson);
                myObject = myObject.getJSONObject("result");
                myJsonArray = myObject.getJSONArray("records");
                for(int i=0;i<myJsonArray.length();i++){
                    myObject = myJsonArray.getJSONObject(i);
                    int id = Integer.parseInt(myObject.getString("_id"));
                    int code = Integer.parseInt(myObject.getString("CodMunicipio"));
                    String municipi = myObject.getString("Municipi");
                    int casos  = Integer.parseInt(myObject.getString("Casos PCR+"));
                    int casesPCR14 = Integer.parseInt(myObject.getString("Casos PCR+ 14 dies"));
                    String casesPCR14cumulativeIncidence = myObject.getString("Incidència acumulada PCR+14");
                    int deaths = Integer.parseInt(myObject.getString("Defuncions"));
                    String deathRate = myObject.getString("Taxa de defunció");
                    municipioList.add(new Municipio(id, code, municipi, casos, casesPCR14, casesPCR14cumulativeIncidence,deaths, deathRate));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return municipioList;
        }
        private String getMethod(String auxUrl){
            String url = auxUrl;
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                URL obj = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                urlConnection.setRequestProperty("accept", "application/json;");
                urlConnection.setRequestProperty("accept-language", "es");
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                int n;
                while ((n = in.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                in.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return writer.toString();
        }
        @Override
        protected void onPostExecute(ArrayList municipios){
            System.out.println("It worked!");
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            try {
                adapter = new AdapterMunicipios(municipios);

            } catch (JSONException e) {
                e.printStackTrace();
            }            adapter.setClickListener(MainActivity.this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void Init() throws JSONException {
            InputStream myStream = getResources().openRawResource(R.raw.data);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(myStream, "UTF-8"));
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
                    myStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

////            JSONObject myObject = new JSONObject(writer.toString());
//            System.out.println(myObject.toString());
//            JSONObject jsonObjectResult = myObject.getJSONObject("result");
//            JSONArray jsonArray = jsonObjectResult.optJSONArray("records");
//
//            if (jsonArray != null) {
//
//                for (int i=0;i<jsonArray.length();i++){
//                    try {
//                        JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
//                        int id = Integer.parseInt(jsonObjectItem.getString("_id"));
//                        int code = Integer.parseInt(jsonObjectItem.getString("CodMunicipio"));
//                        String municipi = jsonObjectItem.getString("Municipi");
//                        int casos  = Integer.parseInt(jsonObjectItem.getString("Casos PCR+"));
//                    int casesPCR14 = Integer.parseInt(jsonObjectItem.getString("Casos PCR+ 14 dies"));
//                    String casesPCR14cumulativeIncidence = jsonObjectItem.getString("Incidència acumulada PCR+14");
//                    int deaths = Integer.parseInt(jsonObjectItem.getString("Defuncions"));
//                    String deathRate = jsonObjectItem.getString("Taxa de defunció");
//                        this.municipios.add(new Municipio(id, code, municipi, casos, casesPCR14, casesPCR14cumulativeIncidence,deaths, deathRate));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

            //adapterMunicipality.setMunicipality(this.municipality);
        }

    }


