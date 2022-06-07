package com.example.covid19cv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private String muni;
    private String startDate;
    private String diagnosticCode;
    private boolean closeContact;
    private boolean fever;
    private boolean cough;
    private boolean breathing;
    private boolean fatigue;
    private boolean aching;
    private boolean headache;
    private boolean tasteLoss;
    private boolean throat;
    private boolean congestion;
    private boolean nausea;
    private boolean diarrhea;
    public void Database(String muni, String startDate, String diagnosticCode, boolean closeContact,boolean fever, boolean cough, boolean breathing, boolean fatigue, boolean aching, boolean headache, boolean throat, boolean congestion, boolean nausea, boolean diarrea){
        this.muni = muni;
        this.startDate = startDate;
        this.diagnosticCode = diagnosticCode;
        this.closeContact = closeContact;
        this.congestion = congestion;
        this.diagnosticCode = diagnosticCode;
        this.fever = fever;
        this.cough = cough;
        this.breathing = breathing;
        this.fatigue = fatigue;
        this.aching = aching;
        this.headache = headache;
        this.throat = throat;
        this.nausea = nausea;
        this.diarrhea = diarrea;
        this.tasteLoss = tasteLoss;
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
   }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
