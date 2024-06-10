package com.prova.matchme.Entity;

import java.util.ArrayList;

public class PartitaDetails {
    private Partita partita;
    private Sede sede;
    private Campo campo;
    private ArrayList<Utente> squadra1;
    private ArrayList<Utente> squadra2;

    public void setPartita(Partita p){
        this.partita = p;
    }

    public void setSede(Sede s){
        this.sede = s;
    }

    public void setCampo(Campo c){
        this.campo = c;
    }

    public void setSquadra1(ArrayList<Utente> utenti1){
        this.squadra1 = utenti1;
    }

    public void setSquadra2(ArrayList<Utente> utenti2){
        this.squadra2 = utenti2;
    }
}
