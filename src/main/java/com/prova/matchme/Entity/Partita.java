package com.prova.matchme.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Partita {
    private int id;
    private int ref_campo;
    private LocalDateTime dataOra;
    private String tipo;
    private String vincoli;


    public Partita(int id, int ref_campo, LocalDateTime dateTime, String tipo, String vincoli){
        this.id = id;
        this.ref_campo = ref_campo;
        this.dataOra = dateTime;
        this.tipo = tipo;
        this.vincoli = vincoli;
    }

    public int getRef_campo(){
        return this.ref_campo;
    }

    public LocalDateTime getDataOra(){
        return this.dataOra;
    }

    public String getTipo(){
        return this.tipo;
    }

    public String getVincoli(){
        return this.vincoli;
    }

    public String toString(){
        return "partita in data " + dataOra.toLocalDate().toString() + " alle ore " + dataOra.toLocalTime().toString();
    }
}
