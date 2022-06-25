package com.example.covid19cv;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ItemReportDetail extends AppCompatActivity {
    DatabaseHelper myHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_report);
        myHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.reports);

        Intent receivedIntent = getIntent();
        String muniName = receivedIntent.getStringExtra("name");

        setTitle(muniName + " Reports");
        populateListView();

    }

    private void populateListView() {
        Cursor data = myHelper.getData();
        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapterView.getItemAtPosition(i).toString();
                Cursor cursorData = myHelper.getItemID(data); //get the id associated with that data
                int itemID = -1;
                while (cursorData.moveToNext()) {
                    itemID = cursorData.getInt(0);
                }
                myHelper.deleteData(itemID, data);
                listData.remove(i);
                adapter.notifyDataSetChanged();
                //                adapter.notifyDataSetChanged();

            }
        });
    }
}
