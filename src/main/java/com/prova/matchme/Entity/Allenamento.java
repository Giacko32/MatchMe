package com.prova.matchme.Entity;

import java.util.ArrayList;

public class Allenamento {
    public Partita partita;
    public Sede sede;
    public Campo campo;
    public ArrayList<Utente> partecipanti;
    public Utente allenatore;

    public void setPartita(Partita p){
        this.partita = p;
    }

    public void setSede(Sede s){
        this.sede = s;
    }

    public void setCampo(Campo c){
        this.campo = c;
    }

    public void setPartecipanti(ArrayList<Utente> partecipanti){
        this.partecipanti = partecipanti;
    }

    public void setAllenatore(Utente all){this.allenatore = all;}
}
