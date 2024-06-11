package com.prova.matchme.Entity;

import java.time.LocalDate;

public class Abbonamento {

    private int refTesserato;
    private LocalDate datadiscadenza;
    private String codice;
    private int refGestore;
    private int notificato;

    public Abbonamento(int refTesserato,LocalDate datadiscadenza,String codice,int refGestore,int notificato){
        this.refTesserato=refTesserato;
        this.datadiscadenza=datadiscadenza;
        this.codice=codice;
        this.refGestore=refGestore;
        this.notificato=notificato;
    }


    public int getRefGestore() {
        return refGestore;
    }

    public int getRefTesserato() {
        return refTesserato;
    }

    public LocalDate getDatadiscadenza() {
        return datadiscadenza;
    }

    public String getCodice() {
        return codice;
    }

    public int getNotificato() {
        return notificato;
    }
}
