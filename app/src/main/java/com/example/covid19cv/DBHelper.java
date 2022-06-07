package com.example.covid19cv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_DB = "casos.db";
    public static final String TABLE_CASOS = "t_casos";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(@Nullable Context context) {
        super(context, NOMBRE_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table " + TABLE_CASOS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, municipio TEXT NOT NULL, fiebre TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists " + TABLE_CASOS);
        onCreate(DB);
    }

    public Boolean insertcaso(String municipio, String fiebre){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("municipio", municipio);
        contentValues.put("fiebre", fiebre);
        long id = db.insert(TABLE_CASOS, null, contentValues);
        if(id == -1)
            return false;
        else
            return true;
    }

    public Cursor findReportsByMunicipality(String name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_CASOS + " WHERE municipio = '" + name + "'", null);
        return cursor;
    }
}