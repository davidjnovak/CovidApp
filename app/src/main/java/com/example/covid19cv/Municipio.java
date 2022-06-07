package com.example.covid19cv;


import java.io.Serializable;

public class Municipio {
    private String municipi;
    private int code;
    private int id;
    private int casos;
    private int casesPCR14;
    private String casesPCR14cumulativeIncidence;
    private int deaths;
    private String deathRate;



    public int getCasesPCR14() {
        return casesPCR14;
    }

    public void setCasesPCR14(int casesPCR14) {
        this.casesPCR14 = casesPCR14;
    }

    public String getCasesPCR14cumulativeIncidence() {
        return casesPCR14cumulativeIncidence;
    }

    public void setCasesPCR14cumulativeIncidence(String casesPCR14cumulativeIncidence) {
        this.casesPCR14cumulativeIncidence = casesPCR14cumulativeIncidence;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(String deathRate) {
        this.deathRate = deathRate;
    }

    public Municipio(int id, int code, String municipi, int casos, int casesPCR14, String casesPCR14cumulativeIncidence, int deaths, String deathRate) {
        this.id = id;
        this.code = code;
        this.municipi = municipi;
        this.casos = casos;
        this.casesPCR14 = casesPCR14;
        this.casesPCR14cumulativeIncidence = casesPCR14cumulativeIncidence;
        this.deaths = deaths;
        this.deathRate = deathRate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public int getCasos() {
        return casos;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }
}